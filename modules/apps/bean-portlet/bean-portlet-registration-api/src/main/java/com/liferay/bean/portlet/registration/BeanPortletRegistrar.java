/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.registration;

import com.liferay.bean.portlet.extension.BeanFilterMethodFactory;
import com.liferay.bean.portlet.extension.BeanFilterMethodInvoker;
import com.liferay.bean.portlet.extension.BeanPortletMethodFactory;
import com.liferay.bean.portlet.extension.BeanPortletMethodInvoker;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Neil Griffin
 */
@ProviderType
public interface BeanPortletRegistrar {

	public List<ServiceRegistration<?>> register(
		BeanFilterMethodFactory beanFilterMethodFactory,
		BeanFilterMethodInvoker beanFilterMethodInvoker,
		BeanPortletMethodFactory beanPortletMethodFactory,
		BeanPortletMethodInvoker beanPortletMethodInvoker,
		Set<Class<?>> discoveredClasses, ServletContext servletContext);

	public void unregister(
		List<ServiceRegistration<?>> serviceRegistrations,
		ServletContext servletContext);

}