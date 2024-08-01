/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.display.context;

import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marko Cikos
 */
public class FDSAdminItemSelectorDisplayContext {

	public FDSAdminItemSelectorDisplayContext(
		HttpServletRequest httpServletRequest,
		String itemSelectedEventName,
		InfoItemItemSelectorCriterion infoItemItemSelectorCriterion) {

		_classPK = ParamUtil.getLong(httpServletRequest, "refererClassPK");
		_infoItemItemSelectorCriterion = infoItemItemSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getClassName() {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
				_themeDisplay.getCompanyId(), "FDSView");

		if (objectDefinition != null) {
			return objectDefinition.getClassName();
		}

		return ObjectDefinition.class.getName();
	}

	public long getClassNameId() {
		return PortalUtil.getClassNameId(getClassName());
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public long getClassPK() {
		return _classPK;
		// return _infoItemItemSelectorCriterion.getRefererClassPK();
	}

	private final long _classPK;
	private final InfoItemItemSelectorCriterion _infoItemItemSelectorCriterion;
	private final String _itemSelectedEventName;
	private final ThemeDisplay _themeDisplay;

}