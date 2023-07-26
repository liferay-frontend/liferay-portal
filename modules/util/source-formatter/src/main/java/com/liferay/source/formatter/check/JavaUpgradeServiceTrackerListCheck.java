/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class JavaUpgradeServiceTrackerListCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		List<String> importNames = javaTerm.getImportNames();

		String javaTermContent = javaTerm.getContent();

		if (!importNames.contains(
				"com.liferay.osgi.service.tracker.collections.list." +
					"ServiceTrackerList")) {

			return javaTermContent;
		}

		Matcher matcher = _serviceTrackerListVariablePattern.matcher(
			javaTermContent);

		if (matcher.find()) {
			javaTermContent = StringUtil.replace(
				javaTermContent, matcher.group(1), matcher.group(2));
		}

		return javaTermContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_VARIABLE};
	}

	private static final Pattern _serviceTrackerListVariablePattern =
		Pattern.compile("ServiceTrackerList\\s*<(.+, (.+))>");

}