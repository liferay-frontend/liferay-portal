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
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResource;
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResourceImpl;
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResourcePack;
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResourcePackImpl;
import com.liferay.frontend.icons.admin.web.internal.helper.IconResourceHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/frontend_icons_admin/save_custom_icon"
	},
	service = MVCActionCommand.class
)
public class SaveCustomIconMVCActionCommand extends BaseMVCActionCommand {

	public void addCustomIcon(String iconPackName, String id, String svgContent) {

		Map<String, IconResourcePack> iconResourcePackMaps = _iconResourceHelper.getIconResourceMaps();

		IconResourcePack iconResourcePack;
		boolean packExists = false;

		if (iconResourcePackMaps.containsKey(iconPackName)) {
			iconResourcePack = iconResourcePackMaps.get(iconPackName);

			packExists = true;
		} else {
			iconResourcePack = new IconResourcePackImpl(iconPackName);
		}

		iconResourcePack.addIconResource(id, svgContent);

		if (!packExists) {
			_bundleContext.registerService(
				IconResourcePack.class, iconResourcePack,
				new Hashtable<>());
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;
	}

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

		String svgContent = FileUtil.read(
			uploadPortletRequest.getFile("svgFile"));

		String name = ParamUtil.getString(actionRequest, "name");

		String iconPack = ParamUtil.getString(actionRequest, "iconPack");

		addCustomIcon(iconPack, name, svgContent);
	}

	private BundleContext _bundleContext;

	@Reference
	private Portal _portal;

	@Reference
	private IconResourceHelper _iconResourceHelper;

}