/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.frontend.hashed.files.CachingStrategy;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.url.builder.ImageTransformationAbsolutePortalURLBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Daniel Sanz
 */
public class AbsolutePortalURLBuilderImplTest
	extends BaseAbsolutePortalURLBuilderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_portal = mockPortal(false, false, false);
	}

	@After
	public void tearDown() {
		CompanyThreadLocal.setCompanyId(CompanyConstants.SYSTEM);

		super.tearDown();
	}

	@Test
	public void testForImageTransformationOmitsCDNHostWithoutCompany() {
		_setCDNHosts(_CDN_HOST_HTTP, _CDN_HOST_HTTPS);

		CompanyThreadLocal.setCompanyId(CompanyConstants.SYSTEM);

		Assert.assertEquals(_RESOURCE_PATH, _build());
	}

	@Test
	public void testForImageTransformationStripsTheCDNHostTrailingSlash() {
		_setCDNHosts(_CDN_HOST_HTTP, _CDN_HOST_HTTPS + StringPool.SLASH);

		CompanyThreadLocal.setCompanyId(_COMPANY_ID);

		Assert.assertEquals(_CDN_HOST_HTTPS + _RESOURCE_PATH, _build());
	}

	@Test
	public void testForImageTransformationUsesHTTPCDNHostWhenHTTPSIsBlank() {
		_setCDNHosts(_CDN_HOST_HTTP, StringPool.BLANK);

		CompanyThreadLocal.setCompanyId(_COMPANY_ID);

		Assert.assertEquals(_CDN_HOST_HTTP + _RESOURCE_PATH, _build());
	}

	@Test
	public void testForImageTransformationUsesHTTPSCDNHost() {
		_setCDNHosts(_CDN_HOST_HTTP, _CDN_HOST_HTTPS);

		CompanyThreadLocal.setCompanyId(_COMPANY_ID);

		Assert.assertEquals(_CDN_HOST_HTTPS + _RESOURCE_PATH, _build());
	}

	private String _build() {
		AbsolutePortalURLBuilderImpl absolutePortalURLBuilderImpl =
			new AbsolutePortalURLBuilderImpl(
				mockCacheHelper(),
				mockHashedFilesRegistry(CachingStrategy.DO_NOT_USE_HASHES),
				_portal, null);

		ImageTransformationAbsolutePortalURLBuilder
			imageTransformationAbsolutePortalURLBuilder =
				absolutePortalURLBuilderImpl.forImageTransformation(
					null, _RESOURCE_PATH);

		return imageTransformationAbsolutePortalURLBuilder.build();
	}

	private void _setCDNHosts(String cdnHostHttp, String cdnHostHttps) {
		Mockito.when(
			_portal.getCDNHostHttp(_COMPANY_ID)
		).thenReturn(
			cdnHostHttp
		);

		Mockito.when(
			_portal.getCDNHostHttps(_COMPANY_ID)
		).thenReturn(
			cdnHostHttps
		);
	}

	private static final String _CDN_HOST_HTTP = "http://cdn-host";

	private static final String _CDN_HOST_HTTPS = "https://cdn-host";

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _RESOURCE_PATH = "/documents/d/guest/image.png";

	private Portal _portal;

}