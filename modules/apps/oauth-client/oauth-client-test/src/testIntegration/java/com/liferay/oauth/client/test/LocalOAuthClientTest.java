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

package com.liferay.oauth.client.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.LocalOAuthClient;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Arthur Chan
 */
@RunWith(Arquillian.class)
public class LocalOAuthClientTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		User user = UserTestUtil.getAdminUser(PortalUtil.getDefaultCompanyId());

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.addOAuth2Application(
				user.getCompanyId(), user.getUserId(), user.getLogin(),
				Arrays.asList(GrantType.RESOURCE_OWNER_PASSWORD),
				"client_secret_post", user.getUserId(), "testClient", 0,
				"testClientSecret", "test oauth client",
				Collections.singletonList("token.introspection"),
				"http://client/homepage", 0, null, "test application",
				"http://client/privacypolicy",
				Collections.singletonList("http://client/redirect"), false,
				Collections.singletonList("everything"), false,
				new ServiceContext());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_localOAuthClient.requestTokens(
				oAuth2Application, user.getUserId()));

		Assert.assertTrue(
			Validator.isNotNull(jsonObject.getString("access_token")));

		_oAuth2ApplicationLocalService.deleteOAuth2Application(
			oAuth2Application);
	}

	@Inject
	private LocalOAuthClient _localOAuthClient;

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

}