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
import Screen from './Screen';
import type { State } from './Screen';
declare type Nullable<T> = T | null;
declare type HttpHeaders = Record<string, string>;
interface Request {
    method: string;
    requestBody: Nullable<FormData>;
    requestHeaders: HttpHeaders;
    url: string;
}
declare class RequestScreen extends Screen {
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
    constructor();
    /**
     * Asserts that response status code is valid.
     */
    assertValidResponseStatusCode(status: number): void;
    beforeUpdateHistoryPath(path: string): string;
    beforeUpdateHistoryState(state: State): State | null;
    /**
     * Formats load path before invoking ajax call.
     */
    formatLoadPath(path: string): string;
    /**
     * Gets the http headers.
     */
    getHttpHeaders(): HttpHeaders;
    /**
     * Gets the http method.
     */
    getHttpMethod(): string;
    /**
     * Gets request path.
     */
    getRequestPath(): string | null;
    /**
     * Gets the request object.
     */
    getResponse(): Response | undefined;
    /**
     * Gets the request object.
     */
    getRequest(): Nullable<Request>;
    /**
     * Gets the request timeout.
     */
    getTimeout(): number;
    /**
     * Checks if response succeeded. Any status code 2xx or 3xx is considered
     * valid.
     */
    isValidResponseStatusCode(statusCode: number): boolean;
    /**
     * Returns the form data
     * This method can be extended in order to have a custom implementation of the form params
     */
    getFormData(formElement: HTMLFormElement, submittedButtonElement?: HTMLButtonElement): FormData;
    load(path: string): Promise<unknown>;
    /**
     * Adds aditional data to the body of the request in case a submit button
     * is captured during form submission.
     */
    maybeAppendSubmitButtonValue_(formData: FormData, submittedButtonElement?: HTMLButtonElement): void;
    /**
     * Sets the http headers.
     */
    setHttpHeaders(httpHeaders: HttpHeaders): void;
    /**
     * Sets the http method.
     */
    setHttpMethod(httpMethod: string): void;
    /**
     * Sets the request object.
     */
    setRequest(request: Request): void;
    /**
     * Sets the request object.
     */
    setResponse(response: Response): void;
    /**
     * Sets the request timeout in milliseconds.
     */
    setTimeout(timeout: number): void;
}
export default RequestScreen;
