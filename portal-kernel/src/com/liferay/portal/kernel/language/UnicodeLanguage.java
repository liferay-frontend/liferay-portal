/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.language;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface UnicodeLanguage {

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper argument);

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper argument, boolean translateArguments);

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper[] arguments);

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper[] arguments, boolean translateArguments);

	public String format(
		HttpServletRequest httpServletRequest, String pattern, Object argument);

	public String format(
		HttpServletRequest httpServletRequest, String pattern, Object argument,
		boolean translateArguments);

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		Object[] arguments);

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		Object[] arguments, boolean translateArguments);

	public String format(Locale locale, String pattern, Object argument);

	public String format(
		Locale locale, String pattern, Object argument,
		boolean translateArguments);

	public String format(Locale locale, String pattern, Object[] arguments);

	public String format(
		Locale locale, String pattern, Object[] arguments,
		boolean translateArguments);

	public String format(
		ResourceBundle resourceBundle, String pattern, Object argument);

	public String format(
		ResourceBundle resourceBundle, String pattern, Object argument,
		boolean translateArguments);

	public String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments);

	public String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments,
		boolean translateArguments);

	public String get(HttpServletRequest httpServletRequest, String key);

	public String get(
		HttpServletRequest httpServletRequest, String key, String defaultValue);

	public String get(Locale locale, String key);

	public String get(Locale locale, String key, String defaultValue);

	public String get(ResourceBundle resourceBundle, String key);

	public String get(
		ResourceBundle resourceBundle, String key, String defaultValue);

	public String getTimeDescription(
		HttpServletRequest httpServletRequest, long milliseconds);

	public String getTimeDescription(
		HttpServletRequest httpServletRequest, Long milliseconds);

}