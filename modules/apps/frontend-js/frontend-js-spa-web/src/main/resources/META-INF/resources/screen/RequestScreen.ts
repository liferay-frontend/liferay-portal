/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {fetch} from 'frontend-js-web';

import {getUrlPath} from '../util/utils';
import Screen from './Screen';

import type {State} from './Screen';

const INVALID_STATUS = 'Invalid status code';

const FAILED_TO_FETCH_MSG = 'Failed to fetch';
const NETWORK_ERROR_MSG = 'NetworkError when attempting to fetch resource.';
const PREFLIGHT_ERROR_MSG = 'Preflight response is not successful';
const REQUEST_ERROR_MSG = 'Request error';
const REQUEST_TIMEOUT_MSG = 'Request timeout';
const REQUEST_PREMATURE_TERMINATION_MSG = 'Request terminated prematurely';

type Nullable<T> = T | null;
type HttpHeaders = Record<string, string>;

interface Request {
	method: string;
	requestBody: Nullable<FormData>;
	requestHeaders: HttpHeaders;
	url: string;
}

class RequestScreen extends Screen {
	static GET: string;
	static POST: string;

	httpHeaders: HttpHeaders;
	httpMethod: string;
	request: Nullable<Request>;
	timeout: number;
	response?: Response;

	/**
	 * Request screen abstract class to perform io operations on descendant
	 * screens.
	 */
	constructor() {
		super();

		this.cacheable = true;

		/**
		 * Holds default http headers to set on request.
		 */
		this.httpHeaders = {
			'X-PJAX': 'true',
			'X-Requested-With': 'XMLHttpRequest',
		};

		/**
		 * Holds default http method to perform the request.
		 */
		this.httpMethod = RequestScreen.GET;

		/**
		 * Holds the XHR object responsible for the request.
		 */
		this.request = null;

		/**
		 * Holds the request timeout in milliseconds.
		 */
		this.timeout = 30000;
	}

	/**
	 * Asserts that response status code is valid.
	 */
	assertValidResponseStatusCode(status: number) {
		if (!this.isValidResponseStatusCode(status)) {
			var error = new Error(INVALID_STATUS);

			error.invalidStatus = true;
			error.statusCode = status;

			throw error;
		}
	}

	beforeUpdateHistoryPath(path: string) {
		var redirectPath = this.getRequestPath();

		if (redirectPath && redirectPath !== path) {
			return redirectPath;
		}

		return path;
	}

	beforeUpdateHistoryState(state: State) {

		// If state is ours and navigate to post-without-redirect-get set
		// history state to null, that way Senna will reload the page on
		// popstate since it cannot predict post data.

		if (state.senna && state.form && state.redirectPath === state.path) {
			return null;
		}

		return state;
	}

	/**
	 * Formats load path before invoking ajax call.
	 */
	formatLoadPath(path: string) {
		var uri = new URL(path, window.location.origin);

		uri.hostname = window.location.hostname;
		uri.protocol = window.location.protocol;

		if (window.location.port) {
			uri.port = window.location.port;
		}

		return uri.toString();
	}

	/**
	 * Gets the http headers.
	 */
	getHttpHeaders() {
		return this.httpHeaders;
	}

	/**
	 * Gets the http method.
	 */
	getHttpMethod() {
		return this.httpMethod;
	}

	/**
	 * Gets request path.
	 */
	getRequestPath() {
		var request = this.getRequest();

		if (request) {
			var requestPath = request.url;

			var response = this.getResponse();

			if (response) {
				var responseUrl = response.url;

				if (responseUrl) {
					requestPath = responseUrl;
				}
			}

			return getUrlPath(requestPath);
		}

		return null;
	}

	/**
	 * Gets the request object.
	 */
	getResponse() {
		return this.response;
	}

	/**
	 * Gets the request object.
	 */
	getRequest() {
		return this.request;
	}

	/**
	 * Gets the request timeout.
	 */
	getTimeout() {
		return this.timeout;
	}

	/**
	 * Checks if response succeeded. Any status code 2xx or 3xx is considered
	 * valid.
	 */
	isValidResponseStatusCode(statusCode: number) {
		return statusCode >= 200 && statusCode <= 399;
	}

	/**
	 * Returns the form data
	 * This method can be extended in order to have a custom implementation of the form params
	 */
	getFormData(
		formElement: HTMLFormElement,
		submittedButtonElement?: HTMLButtonElement
	) {
		const formData = new FormData(formElement);
		this.maybeAppendSubmitButtonValue_(formData, submittedButtonElement);

		return formData;
	}

	load(path: string) {
		const cache = this.getCache();
		if (cache) {
			return Promise.resolve(cache);
		}
		let body = null;
		let httpMethod = this.httpMethod;
		const requestHeaders = {'X-PJAX': 'true', ...this.httpHeaders};
		if (Liferay.SPA?.__capturedFormElement__) {
			body = this.getFormData(
				Liferay.SPA.__capturedFormElement__,
				Liferay.SPA.__capturedFormButtonElement__
			);
			httpMethod = RequestScreen.POST;
		}

		const url = this.formatLoadPath(path);

		this.setRequest({
			method: httpMethod,
			requestBody: body,
			requestHeaders,
			url,
		});

		return Promise.race([
			fetch(url, {
				body,
				headers: requestHeaders,
				method: httpMethod,
				mode: 'cors',
				redirect: 'follow',
				referrer: 'client',
			})
				.then((resp) => {
					this.assertValidResponseStatusCode(resp.status);

					this.setResponse(resp);

					return resp.clone().text();
				})
				.then((text) => {
					if (
						httpMethod === RequestScreen.GET &&
						this.isCacheable()
					) {
						this.addCache(text);
					}

					return text;
				}),
			new Promise((_, reject) => {
				setTimeout(
					() => reject(new Error(REQUEST_TIMEOUT_MSG)),
					this.timeout
				);
			}),
		]).catch((reason) => {
			switch (reason.message) {
				case REQUEST_TIMEOUT_MSG:
					reason.timeout = true;
					break;
				case REQUEST_PREMATURE_TERMINATION_MSG:
				case FAILED_TO_FETCH_MSG:
				case NETWORK_ERROR_MSG:
				case PREFLIGHT_ERROR_MSG:
					reason.requestError = true;
					reason.requestPrematureTermination = true;
					break;
				case REQUEST_ERROR_MSG:
				default:
					reason.requestError = true;
					break;
			}
			throw reason;
		});
	}

	/**
	 * Adds aditional data to the body of the request in case a submit button
	 * is captured during form submission.
	 */
	maybeAppendSubmitButtonValue_(
		formData: FormData,
		submittedButtonElement?: HTMLButtonElement
	) {
		if (submittedButtonElement && submittedButtonElement.name) {
			formData.append(
				submittedButtonElement.name,
				submittedButtonElement.value
			);
		}
	}

	/**
	 * Sets the http headers.
	 */
	setHttpHeaders(httpHeaders: HttpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	/**
	 * Sets the http method.
	 */
	setHttpMethod(httpMethod: string) {
		this.httpMethod = httpMethod.toLowerCase();
	}

	/**
	 * Sets the request object.
	 */
	setRequest(request: Request) {
		this.request = request;
	}

	/**
	 * Sets the request object.
	 */
	setResponse(response: Response) {
		this.response = response;
	}

	/**
	 * Sets the request timeout in milliseconds.
	 */
	setTimeout(timeout: number) {
		this.timeout = timeout;
	}
}

/**
 * Holds value for method get.
 */
RequestScreen.GET = 'get';

/**
 * Holds value for method post.
 */
RequestScreen.POST = 'post';

export default RequestScreen;
