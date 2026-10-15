/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.web.internal.client;

import com.liferay.ai.creator.openai.web.internal.exception.AICreatorOpenAIClientException;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

/**
 * @author Lourdes Fernández Besada
 */
public class AICreatorOpenAIClientTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws IOException {
		_aiCreatorOpenAIClient = new AICreatorOpenAIClientImpl();

		_http = Mockito.mock(Http.class);

		ReflectionTestUtil.setFieldValue(
			_aiCreatorOpenAIClient, "_http", _http);

		_jsonFactory = Mockito.mock(JSONFactory.class);

		ReflectionTestUtil.setFieldValue(
			_aiCreatorOpenAIClient, "_jsonFactory", _jsonFactory);
	}

	@Test
	public void testValidateAPIKey() throws Exception {
		JSONObject responseJSONObject = Mockito.mock(JSONObject.class);

		Http.Response response = _getMockResponse(
			HttpURLConnection.HTTP_OK, responseJSONObject);

		String apiKey = RandomTestUtil.randomString();

		_aiCreatorOpenAIClient.validateAPIKey(apiKey);

		_assertOptions(apiKey, AICreatorOpenAIClientImpl.ENDPOINT_VALIDATION);

		_assertResponse(response);
		_assertResponseJSONObject(responseJSONObject);
	}

	@Test
	public void testValidateAPIKeyIOException() throws Exception {
		_testIOException(
			AICreatorOpenAIClientImpl.ENDPOINT_VALIDATION,
			apiKey -> _aiCreatorOpenAIClient.validateAPIKey(apiKey));
	}

	@Test
	public void testValidateAPIKeyResponseWithErrorKey() throws Exception {
		_testResponseWithErrorKey(
			AICreatorOpenAIClientImpl.ENDPOINT_VALIDATION,
			apiKey -> _aiCreatorOpenAIClient.validateAPIKey(apiKey));
	}

	@Test
	public void testValidateAPIKeyUnauthorizedResponseCode() throws Exception {
		_testUnauthorizedResponseCode(
			AICreatorOpenAIClientImpl.ENDPOINT_VALIDATION,
			apiKey -> _aiCreatorOpenAIClient.validateAPIKey(apiKey));
	}

	private static void _assertResponse(Http.Response response) {
		_assertResponse(response, Mockito.times(1));
	}

	private static void _assertResponse(
		Http.Response response, VerificationMode verificationMode) {

		Mockito.verify(
			response, verificationMode
		).getResponseCode();
	}

	private void _assertOptions(String apiKey, String location)
		throws Exception {

		ArgumentCaptor<Http.Options> argumentCaptor = ArgumentCaptor.forClass(
			Http.Options.class);

		Mockito.verify(
			_http
		).URLtoInputStream(
			argumentCaptor.capture()
		);

		Http.Options options = argumentCaptor.getValue();

		Assert.assertNull(options.getBody());
		Assert.assertEquals(
			"Bearer " + apiKey, options.getHeader("Authorization"));
		Assert.assertNull(options.getHeader("Content-Type"));
		Assert.assertEquals(location, options.getLocation());
	}

	private void _assertResponseJSONObject(JSONObject responseJSONObject) {
		Mockito.verify(
			responseJSONObject
		).has(
			"error"
		);
	}

	private Http.Response _getMockResponse(
			int responseCode, JSONObject responseJSONObject)
		throws Exception {

		Http.Response response = Mockito.mock(Http.Response.class);

		Mockito.when(
			response.getResponseCode()
		).thenReturn(
			responseCode
		);

		Mockito.when(
			_http.URLtoInputStream(Mockito.any(Http.Options.class))
		).thenAnswer(
			invocationOnMock -> {
				Http.Options options = invocationOnMock.getArgument(
					0, Http.Options.class);

				options.setResponse(response);

				InputStream inputStream = Mockito.mock(InputStream.class);

				Mockito.when(
					inputStream.read(
						Mockito.any(), Mockito.anyInt(), Mockito.anyInt())
				).thenReturn(
					-1
				);

				return inputStream;
			}
		);

		Mockito.when(
			_jsonFactory.createJSONObject(Mockito.anyMap())
		).thenReturn(
			responseJSONObject
		);

		Mockito.when(
			_jsonFactory.createJSONObject(Mockito.anyString())
		).thenReturn(
			responseJSONObject
		);

		return response;
	}

	private void _testIOException(
			String location, UnsafeConsumer<String, Exception> unsafeConsumer)
		throws Exception {

		IOException ioException = new IOException();

		Mockito.when(
			_http.URLtoInputStream(Mockito.any(Http.Options.class))
		).thenThrow(
			ioException
		);

		String apiKey = RandomTestUtil.randomString();

		try {
			unsafeConsumer.accept(apiKey);

			Assert.fail();
		}
		catch (AICreatorOpenAIClientException aiCreatorOpenAIClientException) {
			Assert.assertEquals(
				ioException, aiCreatorOpenAIClientException.getCause());
		}

		_assertOptions(apiKey, location);
	}

	private void _testResponseWithErrorKey(
			String location, UnsafeConsumer<String, Exception> unsafeConsumer)
		throws Exception {

		JSONObject errorJSONObject = JSONUtil.put(
			"code", RandomTestUtil.randomString()
		).put(
			"message", RandomTestUtil.randomString()
		);

		Http.Response response = _getMockResponse(
			HttpURLConnection.HTTP_OK, JSONUtil.put("error", errorJSONObject));

		String apiKey = RandomTestUtil.randomString();

		try {
			unsafeConsumer.accept(apiKey);

			Assert.fail();
		}
		catch (AICreatorOpenAIClientException aiCreatorOpenAIClientException) {
			Assert.assertEquals(
				errorJSONObject.getString("code"),
				aiCreatorOpenAIClientException.getCode());
			Assert.assertEquals(
				errorJSONObject.getString("message"),
				aiCreatorOpenAIClientException.getMessage());

			Assert.assertEquals(
				HttpURLConnection.HTTP_OK,
				aiCreatorOpenAIClientException.getResponseCode());
		}

		_assertOptions(apiKey, location);

		_assertResponse(response);
	}

	private void _testUnauthorizedResponseCode(
			String location, UnsafeConsumer<String, Exception> unsafeConsumer)
		throws Exception {

		JSONObject responseJSONObject = Mockito.mock(JSONObject.class);

		Http.Response response = _getMockResponse(
			HttpURLConnection.HTTP_UNAUTHORIZED, responseJSONObject);

		String apiKey = RandomTestUtil.randomString();

		try {
			unsafeConsumer.accept(apiKey);

			Assert.fail();
		}
		catch (AICreatorOpenAIClientException aiCreatorOpenAIClientException) {
			Assert.assertEquals(
				HttpURLConnection.HTTP_UNAUTHORIZED,
				aiCreatorOpenAIClientException.getResponseCode());
		}

		_assertOptions(apiKey, location);
		_assertResponse(response, Mockito.times(2));
		_assertResponseJSONObject(responseJSONObject);
	}

	private AICreatorOpenAIClient _aiCreatorOpenAIClient;
	private Http _http;
	private JSONFactory _jsonFactory;

}