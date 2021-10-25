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

package com.liferay.frontend.icons.admin.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.frontend.icons.admin.web.internal.helper.IconResourceHelper;
import com.liferay.frontend.icons.admin.web.internal.model.IconResourcePackImpl;
import com.liferay.frontend.icons.admin.web.internal.util.SVGUtil;
import com.liferay.frontend.icons.model.IconResource;
import com.liferay.frontend.icons.model.IconResourcePack;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.InputStream;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/frontend_icons_admin/save_icon_pack_from_spritemap"
	},
	service = MVCActionCommand.class
)
public class SaveIconPackFromSpritemapMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(themeDisplay.getCompanyId())) {
			SessionErrors.add(actionRequest, PrincipalException.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		String contentType = uploadPortletRequest.getContentType("svgFile");

		if (!contentType.equals(ContentTypes.IMAGE_SVG_XML)) {
			return;
		}

		InputStream inputStream = uploadPortletRequest.getFileAsStream(
			"svgFile");

		String iconPack = ParamUtil.getString(actionRequest, "iconPack");

		IconResourcePack iconResourcePack = new IconResourcePackImpl(iconPack);

		List<IconResource> iconResources = SVGUtil.getIconResources(
			StringUtil.read(inputStream), StringPool.BLANK);

		iconResourcePack.addIconResources(iconResources);

		_iconResourceHelper.addIconResourcePack(
			themeDisplay.getCompanyId(), iconResourcePack);

		JSONArray iconsJSONArray = JSONFactoryUtil.createJSONArray();

		for (IconResource iconResource : iconResources) {
			iconsJSONArray.put(JSONUtil.put("name", iconResource.getId()));
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			JSONUtil.put(
				"editable", true
			).put(
				"icons", iconsJSONArray
			));
	}

	@Reference
	private IconResourceHelper _iconResourceHelper;

	@Reference
	private Portal _portal;

}