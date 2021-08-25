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
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResourceImpl;
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResourcesContributor;
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResourcesContributorImpl;
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

	public void addCustomIcon(String id, String svgContent) {
		String name = "custom_" + id;

		_customIconResourcesContributorImpl.addIconResource(
			new IconResourceImpl(name, svgContent));

		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		_serviceRegistration = _bundleContext.registerService(
			IconResourcesContributor.class, _customIconResourcesContributorImpl,
			new Hashtable<>());
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

		System.out.println(svgContent);

		String name = ParamUtil.getString(actionRequest, "name");

		addCustomIcon(name, svgContent);
	}

	private BundleContext _bundleContext;
	private final IconResourcesContributorImpl
		_customIconResourcesContributorImpl =
			new IconResourcesContributorImpl();

	@Reference
	private Portal _portal;

	private ServiceRegistration<IconResourcesContributor> _serviceRegistration;

}