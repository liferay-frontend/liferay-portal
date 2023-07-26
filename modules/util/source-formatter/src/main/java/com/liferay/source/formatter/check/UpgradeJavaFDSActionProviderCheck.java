/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaFDSActionProviderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java")) {
			return content;
		}

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<String> importNames = javaClass.getImportNames();

		if (!importNames.contains(
				"com.liferay.frontend.data.set.provider.FDSActionProvider")) {

			return content;
		}

		return _formatMethodDefinitions(content, javaClass);
	}

	private String _formatMethodCalls(
		String content, String javaMethodContent) {

		Matcher matcher = _getDropdownItemsMethodCallPattern.matcher(
			javaMethodContent);

		while (matcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				javaMethodContent, matcher.start());

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			if (_hasClassNameHttpServletRequest(
					javaMethodContent, content, parameterList.get(0)) &&
				_isFDSActionProviderMethodCall(
					javaMethodContent, content, methodCall)) {

				javaMethodContent = StringUtil.replace(
					javaMethodContent, methodCall,
					_reorderParameters(methodCall, matcher.group(1)));
			}
		}

		return javaMethodContent;
	}

	private String _formatMethodDefinition(String javaMethodContent) {
		Matcher matcher = _getDropdownItemsMethodPattern.matcher(
			javaMethodContent);

		if (!matcher.find()) {
			return javaMethodContent;
		}

		String methodCall = JavaSourceUtil.getMethodCall(
			javaMethodContent, matcher.start());

		return StringUtil.replace(
			javaMethodContent, methodCall,
			_reorderParameters(methodCall, matcher.group(1)));
	}

	private String _formatMethodDefinitions(
		String content, JavaClass javaClass) {

		List<String> implementedClassNames =
			javaClass.getImplementedClassNames();

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			if (implementedClassNames.contains("FDSActionProvider")) {
				javaMethodContent = _formatMethodDefinition(javaMethodContent);
			}

			javaMethodContent = _formatMethodCalls(
				javaClass.getContent(), javaMethodContent);

			content = StringUtil.replace(
				content, javaMethod.getContent(), javaMethodContent);
		}

		return content;
	}

	private boolean _hasClassNameHttpServletRequest(
		String content, String fileContent, String variableName) {

		return Objects.equals(
			getVariableTypeName(content, fileContent, variableName),
			"HttpServletRequest");
	}

	private boolean _isFDSActionProviderMethodCall(
		String content, String fileContent, String methodCall) {

		String variableTypeName = getVariableTypeName(
			content, fileContent, getVariableName(methodCall), true);

		return variableTypeName.contains("FDSActionProvider");
	}

	private String _reorderParameters(String methodCall, String parameters) {
		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		return StringUtil.replace(
			methodCall, parameters,
			StringBundler.concat(
				parameterList.get(1), StringPool.COMMA_AND_SPACE,
				parameterList.get(0), StringPool.COMMA_AND_SPACE,
				parameterList.get(2)));
	}

	private static final Pattern _getDropdownItemsMethodCallPattern =
		Pattern.compile(
			"\\w+\\.getDropdownItems\\((\\s*.+,\\s*.+,\\s*.+)\\s*\\)");
	private static final Pattern _getDropdownItemsMethodPattern =
		Pattern.compile(
			"getDropdownItems\\((\\s*HttpServletRequest\\s+.+,\\s*.+," +
				"\\s*.+)\\s*\\)");

}