/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.action.FDSCreationMenuSerializer;
import com.liferay.frontend.data.set.constants.FDSTypes;
import com.liferay.frontend.data.set.internal.serializer.CustomFDSSerializerHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 * @author Marko Cikos
 */
@Component(
	property = "dataset.type=" + FDSTypes.CUSTOM,
	service = FDSCreationMenuSerializer.class
)
public class CustomFDSCreationMenuSerializerImpl
	implements FDSCreationMenuSerializer {

	@Override
	public CreationMenu serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		CreationMenu creationMenu = new CreationMenu();

		for (ObjectEntry objectEntry :
				_customFDSSerializerHelper.getCreationActionObjectEntries(
					fdsName, httpServletRequest)) {

			creationMenu.addPrimaryDropdownItem(
				dropdownItem -> {
					Map<String, Object> properties =
						objectEntry.getProperties();

					dropdownItem.putData(
						"disableHeader",
						(boolean)Validator.isNull(properties.get("title")));

					dropdownItem.putData(
						"permissionKey", properties.get("permissionKey"));
					dropdownItem.putData("size", properties.get("modalSize"));
					dropdownItem.putData("title", properties.get("title"));

					dropdownItem.setHref(properties.get("url"));
					dropdownItem.setIcon(
						String.valueOf(properties.get("icon")));
					dropdownItem.setLabel(
						String.valueOf(properties.get("label")));
					dropdownItem.setTarget(
						String.valueOf(properties.get("target")));
				});
		}

		return creationMenu;
	}

	@Reference
	private CustomFDSSerializerHelper _customFDSSerializerHelper;

}