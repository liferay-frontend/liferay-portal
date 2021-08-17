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
import com.liferay.frontend.icons.admin.web.contributor.extender.IconResourcesContributor;
import com.liferay.frontend.icons.admin.web.contributor.extender.internal.IconResourceImpl;
import com.liferay.frontend.icons.admin.web.contributor.extender.internal.IconResourcesContributorImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bryce Osterhaus
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/icons_admin/save_custom_icon"
	},
	service = MVCActionCommand.class
)
public class SaveCustomIconMVCActionCommand
	extends BaseMVCActionCommand {

	private ServiceRegistration<IconResourcesContributor> _serviceRegistration;

	public void addCustomIcon(String id, String svgContent) {
		String name = "custom_" + id;
		String viewBox = "";

		Pattern pattern = Pattern.compile("(?i)(viewBox.*\"(?=[\\s, >]))");
		Matcher matcher = pattern.matcher(svgContent);
		if (matcher.find()) {
			viewBox = matcher.group(1);
		}

		svgContent = svgContent.replaceFirst(
			"(?i)<svg[^>]*>", StringPool.BLANK);

		svgContent = svgContent.replaceFirst(
			"(?i)</svg[^>]*>", StringPool.BLANK);


		svgContent = "<symbol id=" + StringPool.QUOTE +name+ StringPool.QUOTE + StringPool.SPACE + viewBox + ">" + svgContent + "</symbol>";

		_customIconResourcesContributor.addIconResource(
			new IconResourceImpl(name, svgContent)
		);

		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		_serviceRegistration =
			_bundleContext.registerService(IconResourcesContributor.class,
				_customIconResourcesContributor, new Hashtable<>());
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;

		addCustomIcon("default","<svg viewBox=\"0 0 16 16\"><path d=\"M8.1,13.1C5.3,13.1,3,10.8,3,8h1.1c0,2.2,1.7,4,3.9,4s4-1.7,4-3.9c0,0,0-0.1,0-0.1h1.1C13.1,10.8,10.9,13.1,8.1,13.1z\"/><path d=\"M8,0C3.6,0,0,3.6,0,8s3.6,8,8,8s8-3.6,8-8S12.4,0,8,0z M12.9,3H3.1c1.3-1.2,3-2,4.9-2C9.9,1,11.6,1.8,12.9,3z M12,3.5  l-1,2.8h-1l1-2.8H12z M7,3.5L6,6.3H5l1-2.8H7z M8,15c-3.9,0-7-3.1-7-7c0-1.5,0.5-2.9,1.3-4h0.1L3,6c0.3,0.9,0.4,1,1,1h2  c0.6,0,0.7-0.3,1-1l0.7-2h0.7L9,6c0.3,0.7,0.4,1,1,1h2c0.6,0,0.7-0.1,1-1l0.7-2h0.1C14.5,5.1,15,6.5,15,8C15,11.9,11.9,15,8,15z\" /></svg>");
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

		String svgContent = ParamUtil.getString(actionRequest, "svgContent");
		String name = ParamUtil.getString(actionRequest, "name");

		addCustomIcon(name, svgContent);
	 }

	private BundleContext _bundleContext;
	private final IconResourcesContributorImpl _customIconResourcesContributor = new IconResourcesContributorImpl();
}