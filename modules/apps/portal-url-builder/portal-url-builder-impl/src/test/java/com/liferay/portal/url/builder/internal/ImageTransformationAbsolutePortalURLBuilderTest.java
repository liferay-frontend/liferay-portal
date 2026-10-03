/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.frontend.hashed.files.CachingStrategy;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.ImageTransformationAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.ImageTransformationURLRenderer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.mockito.Mockito;

/**
 * @author Daniel Sanz
 */
@RunWith(Parameterized.class)
public class ImageTransformationAbsolutePortalURLBuilderTest
	extends BaseAbsolutePortalURLBuilderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(name = "{0}: cdnHost={1}, context={2}, proxy={3}")
	public static Collection<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{0, false, false, false}, {1, false, false, true},
				{2, false, true, false}, {3, false, true, true},
				{4, true, false, false}
			});
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_absolutePortalURLBuilder = new AbsolutePortalURLBuilderImpl(
			mockCacheHelper(),
			mockHashedFilesRegistry(CachingStrategy.DO_NOT_USE_HASHES),
			mockPortal(context, proxy, cdnHost), mockHttpServletRequest());

		_imageTransformationAbsolutePortalURLBuilder =
			_absolutePortalURLBuilder.forImageTransformation(
				_imageTransformationURLRenderer, _RESOURCE_PATH);
	}

	@After
	public void tearDown() {
		super.tearDown();
	}

	@Test
	public void test() {
		Assert.assertEquals(
			_RESULTS[index],
			_imageTransformationAbsolutePortalURLBuilder.build());
	}

	@Test
	public void testBuildDropsImageTransformationsWithoutARenderer() {
		ImageTransformationAbsolutePortalURLBuilder
			imageTransformationAbsolutePortalURLBuilder =
				_absolutePortalURLBuilder.forImageTransformation(
					null, _RESOURCE_PATH);

		imageTransformationAbsolutePortalURLBuilder.setImageTransformation(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertEquals(
			_RESULTS[index],
			imageTransformationAbsolutePortalURLBuilder.build());
	}

	@Test
	public void testBuildRendersTheURLAndTheImageTransformations() {
		_imageTransformationAbsolutePortalURLBuilder.setImageTransformation(
			"quality", "80"
		).setImageTransformation(
			"width", "320"
		);

		Assert.assertEquals(
			_RESULTS[index] + "?quality=80&width=320",
			_imageTransformationAbsolutePortalURLBuilder.build());
	}

	@Test
	public void testBuildSkipsTheRendererWithoutImageTransformations() {
		Assert.assertEquals(
			_RESULTS[index],
			_imageTransformationAbsolutePortalURLBuilder.build());

		Mockito.verifyNoInteractions(_imageTransformationURLRenderer);
	}

	@Test
	public void testIgnoreCDN() {
		_imageTransformationAbsolutePortalURLBuilder.ignoreCDNHost();

		Assert.assertEquals(
			_RESULTS_IGNORE_CDN[index],
			_imageTransformationAbsolutePortalURLBuilder.build());
	}

	@Test
	public void testSetImageTransformationIgnoresNullNamesAndValues() {
		_imageTransformationAbsolutePortalURLBuilder.setImageTransformation(
			RandomTestUtil.randomString(), null
		).setImageTransformation(
			null, RandomTestUtil.randomString()
		);

		Assert.assertEquals(
			_RESULTS[index],
			_imageTransformationAbsolutePortalURLBuilder.build());

		Mockito.verifyNoInteractions(_imageTransformationURLRenderer);
	}

	@Test
	public void testSetImageTransformationTwiceReplacesTheValue() {
		String value = RandomTestUtil.randomString();

		_imageTransformationAbsolutePortalURLBuilder.setImageTransformation(
			"width", RandomTestUtil.randomString()
		).setImageTransformation(
			"width", value
		);

		Assert.assertEquals(
			_RESULTS[index] + "?width=" + value,
			_imageTransformationAbsolutePortalURLBuilder.build());
	}

	@Parameterized.Parameter(1)
	public boolean cdnHost;

	@Parameterized.Parameter(2)
	public boolean context;

	@Parameterized.Parameter
	public int index;

	@Parameterized.Parameter(3)
	public boolean proxy;

	private static final String _RESOURCE_PATH = "/documents/d/guest/image.png";

	private static final String[] _RESULTS = {
		_RESOURCE_PATH, "/proxy" + _RESOURCE_PATH, _RESOURCE_PATH,
		"/proxy" + _RESOURCE_PATH, "http://cdn-host" + _RESOURCE_PATH
	};

	private static final String[] _RESULTS_IGNORE_CDN = {
		_RESOURCE_PATH, "/proxy" + _RESOURCE_PATH, _RESOURCE_PATH,
		"/proxy" + _RESOURCE_PATH, _RESOURCE_PATH
	};

	private AbsolutePortalURLBuilder _absolutePortalURLBuilder;
	private ImageTransformationAbsolutePortalURLBuilder
		_imageTransformationAbsolutePortalURLBuilder;

	private final ImageTransformationURLRenderer
		_imageTransformationURLRenderer = Mockito.mock(
			ImageTransformationURLRenderer.class,
			invocation -> {
				StringBundler sb = new StringBundler();

				sb.append(invocation.getArgument(0, String.class));

				Map<String, String> imageTransformations =
					invocation.getArgument(1);

				String delimiter = StringPool.QUESTION;

				for (Map.Entry<String, String> entry :
						imageTransformations.entrySet()) {

					sb.append(delimiter);
					sb.append(entry.getKey());
					sb.append(StringPool.EQUAL);
					sb.append(entry.getValue());

					delimiter = StringPool.AMPERSAND;
				}

				return sb.toString();
			});

}