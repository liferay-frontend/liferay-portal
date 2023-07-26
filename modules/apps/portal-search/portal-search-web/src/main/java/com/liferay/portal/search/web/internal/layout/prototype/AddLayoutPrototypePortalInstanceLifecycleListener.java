/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.layout.prototype;

import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 * @author Lino Alves
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddLayoutPrototypePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		Layout layout = searchLayoutFactory.createSearchLayoutPrototype(
			company);

		if (layout == null) {
			return;
		}

		Group guestGroup = groupLocalService.getGroup(
			company.getCompanyId(), GroupConstants.GUEST);

		try {
			MergeLayoutPrototypesThreadLocal.setInProgress(true);

			searchLayoutFactory.createSearchLayout(guestGroup);
		}
		finally {
			MergeLayoutPrototypesThreadLocal.setInProgress(false);
		}
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected SearchLayoutFactory searchLayoutFactory;

	@Reference(target = ModuleServiceLifecycle.PORTLETS_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

}