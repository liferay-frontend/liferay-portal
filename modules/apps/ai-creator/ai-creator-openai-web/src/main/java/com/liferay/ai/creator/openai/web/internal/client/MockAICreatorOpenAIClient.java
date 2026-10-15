/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.web.internal.client;

import com.liferay.ai.creator.openai.web.internal.exception.AICreatorOpenAIClientException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.HttpURLConnection;

import java.util.Objects;

import org.apache.commons.lang.StringUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 * @author Roberto Díaz
 */
@Component(
	enabled = false, property = "service.ranking:Integer=200",
	service = AICreatorOpenAIClient.class
)
public class MockAICreatorOpenAIClient implements AICreatorOpenAIClient {

	@Override
	public String[] getGenerations(
		String apiKey, String prompt, String size, int numberOfImages) {

		if (Objects.equals(apiKey, "VALID_API_KEY") &&
			Objects.equals(prompt, "USER_IMAGES")) {

			return _getGenerations(
				"http/localhost:8080/mock/url", numberOfImages);
		}

		if (Validator.isNotNull(prompt) &&
			prompt.startsWith(_USER_IMAGES_URL_)) {

			return _getGenerations(
				GetterUtil.getString(
					prompt.substring(_USER_IMAGES_URL_.length())),
				numberOfImages);
		}

		throw _getAICreatorOpenAIClientException(prompt);
	}

	@Override
	public void validateAPIKey(String apiKey) {
		if (Objects.equals(apiKey, "VALID_API_KEY")) {
			return;
		}

		throw _getAICreatorOpenAIClientException(apiKey);
	}

	private AICreatorOpenAIClientException _getAICreatorOpenAIClientException(
		String key) {

		if (Objects.equals(key, "OPENAI_API_IOEXCEPTION")) {
			return new AICreatorOpenAIClientException(new IOException());
		}

		String errorMessage = StringUtils.substringBetween(
			key, "OPENAI_API_", "_ERROR_MESSAGE");

		if (Validator.isNotNull(errorMessage)) {
			return new AICreatorOpenAIClientException(
				"openai-api-error-code", errorMessage,
				HttpURLConnection.HTTP_INTERNAL_ERROR);
		}

		return new AICreatorOpenAIClientException(
			new UnsupportedOperationException("Unsupported key: " + key));
	}

	private String[] _getGenerations(String url, int numberOfImages) {
		String[] generations = new String[numberOfImages];

		if (numberOfImages > 1) {
			for (int i = 0; i < numberOfImages; i++) {
				generations[i] = url + "?t=" + i;
			}
		}
		else {
			generations[0] = url;
		}

		return generations;
	}

	private static final String _USER_IMAGES_URL_ = "USER_IMAGES_URL_";

}