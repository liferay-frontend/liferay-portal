/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.fastly.image.optimizer.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Daniel Sanz
 */
public class FastlyImageOptimizerImageTransformationURLRendererTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.when(
			_portal.stripURLAnchor(Mockito.anyString(), Mockito.anyString())
		).thenAnswer(
			input -> _testStripURLAnchor(
				(String)input.getArguments()[0],
				(String)input.getArguments()[1])
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	@Test
	public void testRenderAppendsBeforeTheAnchor() {
		Assert.assertEquals(
			_URL + "?width=320#anchor",
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL + "#anchor",
				HashMapBuilder.put(
					"width", "320"
				).build()));
	}

	@Test
	public void testRenderAppendsToExistingQueryString() {
		Assert.assertEquals(
			_URL + "?version=1.0&dpr=2",
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL + "?version=1.0",
				HashMapBuilder.put(
					"dpr", "2"
				).build()));
	}

	@Test
	public void testRenderAppliesSupportedImageTransformation() {
		Assert.assertEquals(
			_URL + "?orient=6",
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL,
				HashMapBuilder.put(
					"orient", "6"
				).build()));
	}

	@Test
	public void testRenderDoesNotApplyUnsupportedImageTransformations() {
		Assert.assertEquals(
			_URL + "?height=200",
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL,
				HashMapBuilder.put(
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				).put(
					"height", "200"
				).build()));
	}

	@Test
	public void testRenderEncodesValues() {
		Assert.assertEquals(
			_URL + "?bg-color=rgb%281%2C+2%2C+3%29",
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL,
				HashMapBuilder.put(
					"bg-color", "rgb(1, 2, 3)"
				).build()));
	}

	@Test
	public void testRenderIsDeterministicRegardlessOfImageTransformationsOrder() {
		String format = RandomTestUtil.randomString();
		String quality = RandomTestUtil.randomString();
		String width = RandomTestUtil.randomString();

		Assert.assertEquals(
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL,
				LinkedHashMapBuilder.put(
					"format", format
				).put(
					"quality", quality
				).put(
					"width", width
				).build()),
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL,
				LinkedHashMapBuilder.put(
					"width", width
				).put(
					"quality", quality
				).put(
					"format", format
				).build()));
	}

	@Test
	public void testRenderKeepsSupportedParametersItDoesNotSet() {
		Assert.assertEquals(
			_URL + "?height=200&blur=50&width=320",
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL + "?height=200",
				LinkedHashMapBuilder.put(
					"width", "320"
				).put(
					"blur", "50"
				).build()));
	}

	@Test
	public void testRenderKeepsUnsupportedParameters() {
		Assert.assertEquals(
			_URL + "?unsupported=1&width=320",
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL + "?unsupported=1",
				HashMapBuilder.put(
					"width", "320"
				).build()));
	}

	@Test
	public void testRenderReplacesTheSupportedParameterOfTheSameName() {
		Assert.assertEquals(
			_URL + "?width=320",
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL + "?width=" + RandomTestUtil.randomString(),
				HashMapBuilder.put(
					"width", "320"
				).build()));
	}

	@Test
	public void testRenderReturnsURLUnchangedWhenNoImageTransformationIsSupported() {
		Assert.assertEquals(
			_URL,
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL,
				HashMapBuilder.put(
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				).build()));
	}

	@Test
	public void testRenderReturnsURLUnchangedWhenNoImageTransformationsAreGiven() {
		Assert.assertEquals(
			_URL,
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL, Collections.<String, String>emptyMap()));

		Assert.assertEquals(
			_URL,
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL, null));
	}

	@Test
	public void testRenderSkipsBlankNamesAndValues() {
		Assert.assertEquals(
			_URL + "?sharpen=5",
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL,
				HashMapBuilder.put(
					"", RandomTestUtil.randomString()
				).put(
					"blur", ""
				).put(
					"sharpen", "5"
				).build()));
	}

	@Test
	public void testRenderSortsParametersCanonically() {
		Assert.assertEquals(
			_URL + "?format=webp&quality=80&width=320",
			_fastlyImageOptimizerImageTransformationURLRenderer.render(
				_URL,
				HashMapBuilder.put(
					"format", "webp"
				).put(
					"quality", "80"
				).put(
					"width", "320"
				).build()));
	}

	/**
	 * @see com.liferay.portal.util.PortalImpl
	 *
	 * _testStripURLAnchor is copied from PortalImpl for ease of testing
	 */
	private String[] _testStripURLAnchor(String url, String separator) {
		String anchor = StringPool.BLANK;

		int pos = url.indexOf(separator);

		if (pos != -1) {
			anchor = url.substring(pos);
			url = url.substring(0, pos);
		}

		return new String[] {url, anchor};
	}

	private static final String _URL = RandomTestUtil.randomString();

	private final FastlyImageOptimizerImageTransformationURLRenderer
		_fastlyImageOptimizerImageTransformationURLRenderer =
			new FastlyImageOptimizerImageTransformationURLRenderer();

	@Mock
	private Portal _portal;

}