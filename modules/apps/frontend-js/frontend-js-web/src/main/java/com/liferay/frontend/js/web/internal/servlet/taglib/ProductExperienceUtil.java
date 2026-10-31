/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet.taglib;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletRequest;

import java.util.function.Function;

/**
 * Computes the members of <code>Liferay.ProductExperience</code>, the single
 * object the product experience tracking scripts read to decide who and where
 * to track (role, area) and how to name the current screen (product, screen).
 * Every member is a noun with a value, so consumers read them the same way.
 *
 * @author Marcos Castro
 */
public class ProductExperienceUtil {

	public static final String AREA_BACK_OFFICE = "back-office";

	public static final String AREA_SITE = "site";

	public static final String FEATURE_FLAG_KEY = "LPD-101586";

	public static final String PRODUCT_AI_HUB = "ai-hub";

	public static final String PRODUCT_CMS = "cms";

	public static final String PRODUCT_COMMERCE = "commerce";

	public static final String PRODUCT_DIGITAL_SALES_ROOM =
		"digital-sales-room";

	public static final String PRODUCT_DXP = "dxp";

	public static final String ROLE_ADMIN = "admin";

	public static final String ROLE_USER = "user";

	/**
	 * Returns a stable, instance independent key for a portlet, built from
	 * its class name: <code>AccountEntriesAdminPortlet</code> becomes
	 * <code>account_entries_admin</code>.
	 */
	public static String getPortletKey(String portletName) {
		if (Validator.isNull(portletName)) {
			return null;
		}

		String simpleName = portletName.substring(
			portletName.lastIndexOf(CharPool.UNDERLINE) + 1);

		if (simpleName.endsWith(_PORTLET_SUFFIX) &&
			(simpleName.length() > _PORTLET_SUFFIX.length())) {

			simpleName = simpleName.substring(
				0, simpleName.length() - _PORTLET_SUFFIX.length());
		}

		StringBuilder sb = new StringBuilder(simpleName.length() + 8);

		for (int i = 0; i < simpleName.length(); i++) {
			char c = simpleName.charAt(i);

			if (Character.isUpperCase(c) && (i > 0) &&
				!Character.isUpperCase(simpleName.charAt(i - 1))) {

				sb.append(CharPool.UNDERLINE);
			}

			sb.append(Character.toLowerCase(c));
		}

		return _sanitize(sb.toString());
	}

	/**
	 * Returns the product the current page belongs to. An AI Hub deployment
	 * is AI Hub everywhere. Otherwise the CMS and Digital Sales Room have
	 * their own sites, Commerce is recognized by its portlets, and everything
	 * else is DXP.
	 */
	public static String getProduct(
		boolean aiHubDeployment, String siteFriendlyURL, String portletId) {

		if (aiHubDeployment) {
			return PRODUCT_AI_HUB;
		}

		if (GroupConstants.CMS_FRIENDLY_URL.equals(siteFriendlyURL)) {
			return PRODUCT_CMS;
		}

		if (_DSR_FRIENDLY_URL.equals(siteFriendlyURL)) {
			return PRODUCT_DIGITAL_SALES_ROOM;
		}

		if (Validator.isNotNull(portletId)) {
			String portletName = PortletIdCodec.decodePortletName(portletId);

			if (portletName.contains("_commerce_")) {
				return PRODUCT_COMMERCE;
			}
		}

		return PRODUCT_DXP;
	}

	/**
	 * Returns the screen of an administration portlet: the portlet key plus
	 * whichever of the routing parameters the portal already uses applies
	 * (MVC render command or JSP, then screen navigation entry,
	 * configuration screen or configuration PID). Returns <code>null</code>
	 * when there is no portlet.
	 *
	 * <p>
	 * The parameters come from the request, so only characters that can
	 * appear in command names, JSP names and configuration PIDs are kept,
	 * and the value is capped in length.
	 * </p>
	 */
	public static String getScreen(
		String portletId, Function<String, String> parameterFunction) {

		if (Validator.isNull(portletId)) {
			return null;
		}

		String portletKey = getPortletKey(
			PortletIdCodec.decodePortletName(portletId));

		if (Validator.isNull(portletKey)) {
			return null;
		}

		StringBundler sb = new StringBundler(9);

		sb.append(portletKey);

		String view = parameterFunction.apply("mvcRenderCommandName");

		if (Validator.isNull(view)) {
			view = _getJSPName(parameterFunction.apply("mvcPath"));
		}

		_appendSegment(sb, view);

		for (String parameterName : _REFINING_PARAMETER_NAMES) {
			_appendSegment(sb, parameterFunction.apply(parameterName));
		}

		String screen = sb.toString();

		if (screen.length() > _SCREEN_MAX_LENGTH) {
			screen = screen.substring(0, _SCREEN_MAX_LENGTH);
		}

		return screen;
	}

	/**
	 * Identity: is the user an administrator? Permission based, so it covers
	 * omniadmins, company admins, and organization or site admins of the
	 * current scope, including custom and inherited admin roles.
	 */
	public static boolean isAdmin(ThemeDisplay themeDisplay) {
		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!themeDisplay.isSignedIn() || (permissionChecker == null)) {
			return false;
		}

		if (permissionChecker.isOmniadmin() ||
			permissionChecker.isCompanyAdmin() ||
			permissionChecker.isGroupAdmin(themeDisplay.getScopeGroupId())) {

			return true;
		}

		return false;
	}

	/**
	 * Context: is the current page an administration surface? The Control
	 * Panel or Site Administration, the CMS and Digital Sales Room management
	 * sites, a page with the CMS theme (AI Hub), or any page open in the
	 * editor.
	 */
	public static boolean isBackOffice(
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		if (!themeDisplay.isSignedIn()) {
			return false;
		}

		Layout layout = themeDisplay.getLayout();

		if ((layout != null) && layout.isTypeControlPanel()) {
			return true;
		}

		String siteFriendlyURL = getSiteFriendlyURL(themeDisplay);

		if (GroupConstants.CMS_FRIENDLY_URL.equals(siteFriendlyURL) ||
			_DSR_FRIENDLY_URL.equals(siteFriendlyURL)) {

			return true;
		}

		if ((themeDisplay.getTheme() != null) &&
			StringUtil.startsWith(
				themeDisplay.getThemeId(), _CMS_THEME_ID_PREFIX)) {

			return true;
		}

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		return layoutMode.equals(Constants.EDIT);
	}

	protected static String getSiteFriendlyURL(ThemeDisplay themeDisplay) {
		Group group = themeDisplay.getSiteGroup();

		if (group == null) {
			return null;
		}

		return group.getFriendlyURL();
	}

	private static void _appendSegment(StringBundler sb, String value) {
		value = _sanitize(value);

		if (Validator.isNull(value)) {
			return;
		}

		sb.append(StringPool.SLASH);
		sb.append(value);
	}

	private static String _getJSPName(String mvcPath) {
		if (Validator.isNull(mvcPath)) {
			return null;
		}

		String name = mvcPath.substring(
			mvcPath.lastIndexOf(CharPool.SLASH) + 1);

		int index = name.lastIndexOf(CharPool.PERIOD);

		if (index > 0) {
			name = name.substring(0, index);
		}

		return name;
	}

	private static boolean _isSafeCharacter(char c) {
		if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) ||
			((c >= '0') && (c <= '9')) || (c == CharPool.DASH) ||
			(c == CharPool.PERIOD) || (c == CharPool.SLASH) ||
			(c == CharPool.UNDERLINE)) {

			return true;
		}

		return false;
	}

	private static String _sanitize(String value) {
		if (Validator.isNull(value)) {
			return null;
		}

		StringBuilder sb = new StringBuilder(value.length());

		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);

			if (!_isSafeCharacter(c)) {
				continue;
			}

			sb.append(c);
		}

		String sanitized = StringUtil.removeSubstring(sb.toString(), "..");

		while (sanitized.contains("//")) {
			sanitized = StringUtil.replace(sanitized, "//", StringPool.SLASH);
		}

		if (sanitized.startsWith(StringPool.SLASH)) {
			sanitized = sanitized.substring(1);
		}

		if (sanitized.endsWith(StringPool.SLASH)) {
			sanitized = sanitized.substring(0, sanitized.length() - 1);
		}

		if (sanitized.isEmpty()) {
			return null;
		}

		return sanitized;
	}

	private static final String _CMS_THEME_ID_PREFIX = "cms";

	// Same value as DSRConstants.DSR_FRIENDLY_URL in digital-sales-room-web,
	// which this module does not depend on

	private static final String _DSR_FRIENDLY_URL = "/dsr";

	private static final String _PORTLET_SUFFIX = "Portlet";

	private static final String[] _REFINING_PARAMETER_NAMES = {
		"screenNavigationEntryKey", "configurationScreenKey", "factoryPid"
	};

	private static final int _SCREEN_MAX_LENGTH = 200;

}