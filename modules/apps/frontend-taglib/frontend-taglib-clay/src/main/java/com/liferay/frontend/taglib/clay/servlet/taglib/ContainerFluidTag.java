/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib;

import com.liferay.portal.kernel.util.Validator;

import javax.servlet.jsp.JspException;

/**
 * @author Chema Balsas
 */
public class ContainerFluidTag extends ContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setFluid(true);

		if (Validator.isNull(getSize())) {
			setSize("xl");
		}

		return super.doStartTag();
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:container-fluid:";

}