/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.html.parser.internal;

import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Olaf Kock
 * @author Neil Zhao Jin
 */
public class HtmlParserImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testExtraction() {
		Assert.assertEquals(
			"whitespace removal",
			_htmlParserImpl.extractText("   whitespace \n   <br/> removal   "));
		Assert.assertEquals(
			"script removal",
			_htmlParserImpl.extractText(
				"script <script>   test   </script> removal"));
		Assert.assertEquals(
			"HTML attribute removal",
			_htmlParserImpl.extractText(
				"<h1>HTML</h1> <i>attribute</i> <strong>removal</strong>"));
		Assert.assertEquals(
			"onclick removal",
			_htmlParserImpl.extractText(
				"<div onclick=\"honk()\">onclick removal</div>"));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.html.parser.internal" + "HtmlParserImpl",
				LoggerTestUtil.ERROR)) {

			_htmlParserImpl.extractText(
				"<div name=\"test\" class=\"test\">Okay</div>");

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.isEmpty());

			_htmlParserImpl.extractText(
				"<div name=\"test\"class=\"test\">Error</div>");

			logEntries = logCapture.getLogEntries();

			Assert.assertFalse(logEntries.isEmpty());
		}
	}

	private final HtmlParserImpl _htmlParserImpl = new HtmlParserImpl();

}