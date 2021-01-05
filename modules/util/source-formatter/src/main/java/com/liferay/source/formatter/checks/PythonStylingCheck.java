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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.PythonSourceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PythonStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _sortMethods(fileName, content, StringPool.BLANK);
	}

	private List<String> _combineAnnotationsAndComments(
		List<String> statements, String indent) {

		List<String> statementsList = new ArrayList<>();

		StringBundler sb = new StringBundler();

		String previousStatement = StringPool.BLANK;

		for (String statement : statements) {
			if (statement.startsWith(indent + StringPool.AT) ||
				statement.startsWith(indent + StringPool.POUND)) {

				sb.append(statement);
				sb.append("\n");
			}
			else if (previousStatement.startsWith(indent + StringPool.AT) ||
					 previousStatement.startsWith(indent + StringPool.POUND)) {

				sb.append(statement);

				statementsList.add(sb.toString());

				sb.setIndex(0);
			}
			else {
				statementsList.add(statement);
			}

			previousStatement = statement;
		}

		if (sb.index() > 0) {
			statementsList.add(StringUtil.trimTrailing(sb.toString()));
		}

		return statementsList;
	}

	private int _sortMethods(String statement1, String statement2) {
		String trimmedStatement1 = StringUtil.trimLeading(statement1);
		String trimmedStatement2 = StringUtil.trimLeading(statement2);

		trimmedStatement1 = trimmedStatement1.replaceAll(
			"(\t*[#@].*(\\Z|\n))*(.*)", "$3");
		trimmedStatement2 = trimmedStatement2.replaceAll(
			"(\t*[#@].*(\\Z|\n))*(.*)", "$3");

		if (Validator.isNull(trimmedStatement1) ||
			Validator.isNull(trimmedStatement2)) {

			return 0;
		}

		String[] trimmedStatement1Lines = trimmedStatement1.split("\n", 2);

		Matcher matcher = _methodDefinationPattern.matcher(
			trimmedStatement1Lines[0]);

		if (!matcher.find()) {
			return 0;
		}

		String methodName1 = matcher.group(1);

		String[] trimmedStatement2Lines = trimmedStatement2.split("\n", 2);

		matcher = _methodDefinationPattern.matcher(trimmedStatement2Lines[0]);

		if (!matcher.find()) {
			return 0;
		}

		String methodName2 = matcher.group(1);

		return methodName1.compareTo(methodName2);
	}

	private String _sortMethods(
		String fileName, String content, String indent) {

		List<String> statements = PythonSourceUtil.getPythonStatements(
			content, indent);

		statements = _combineAnnotationsAndComments(statements, indent);

		List<String> oldStatements = new ArrayList<>(statements);

		Collections.sort(
			statements,
			new Comparator<String>() {

				@Override
				public int compare(String statement1, String statement2) {
					return _sortMethods(statement1, statement2);
				}

			});

		if (!oldStatements.equals(statements)) {
			StringBundler sb = new StringBundler(statements.size() * 2);

			for (String statement : statements) {
				sb.append(statement);
				sb.append("\n");
			}

			sb.setIndex(sb.index() - 1);

			String[] lines = content.split("\n");

			if (!indent.equals("")) {
				content = lines[0] + "\n" + sb.toString();
			}
			else {
				content = sb.toString();
			}
		}

		statements = PythonSourceUtil.getPythonStatements(content, indent);

		for (String statement : statements) {
			String nestedStatementIndent =
				PythonSourceUtil.getNestedStatementIndent(statement);

			if (!nestedStatementIndent.equals(StringPool.BLANK)) {
				content = StringUtil.replaceFirst(
					content, statement,
					_sortMethods(fileName, statement, nestedStatementIndent));
			}
		}

		return content;
	}

	private static final Pattern _methodDefinationPattern = Pattern.compile(
		"def (\\w+).*");

}