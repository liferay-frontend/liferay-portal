/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.layout.tab.screen.navigation.category;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.object.constants.ObjectWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.portal.kernel.model.User;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Feliphe Marinho
 */
public class ObjectLayoutTabScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<ObjectLayoutTab> {

	public ObjectLayoutTabScreenNavigationCategory(
		ObjectDefinition objectDefinition, ObjectLayoutTab objectLayoutTab) {

		_objectDefinition = objectDefinition;
		_objectLayoutTab = objectLayoutTab;
	}

	@Override
	public String getCategoryKey() {
		return String.valueOf(_objectLayoutTab.getObjectLayoutTabId());
	}

	@Override
	public String getEntryKey() {
		return String.valueOf(_objectLayoutTab.getObjectLayoutTabId());
	}

	@Override
	public String getLabel(Locale locale) {
		return _objectLayoutTab.getName(locale);
	}

	@Override
	public String getScreenNavigationKey() {
		return _objectDefinition.getClassName();
	}

	@Override
	public boolean isVisible(User user, ObjectLayoutTab objectLayoutTab) {
		long objectRelationshipId = _objectLayoutTab.getObjectRelationshipId();

		if (objectRelationshipId == 0) {
			return true;
		}

		ObjectRelationship objectRelationship =
			ObjectRelationshipLocalServiceUtil.fetchObjectRelationship(
				objectRelationshipId);

		if (objectRelationship == null) {
			return false;
		}

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		if ((objectDefinition == null) || !objectDefinition.isActive()) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			ObjectWebKeys.REGULAR_OBJECT_LAYOUT_TAB, Boolean.TRUE);
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectLayoutTab _objectLayoutTab;

}