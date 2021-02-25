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

package com.liferay.portal.kernel.servlet.taglib.ui;

/**
 * @author Iván Zaera
 */
public abstract class MenuItem extends BaseUIItem implements UIActionItem {

	@Override
	public String getIcon() {
		return _icon;
	}

	@Override
	public String getLabel() {
		return _label;
	}

	public String getLinkCssClass() {
		return _linkCssClass;
	}

	public boolean hasSeparator() {
		return _separator;
	}

	@Override
	public void setIcon(String icon) {
		_icon = icon;
	}

	@Override
	public void setLabel(String label) {
		_label = label;
	}

	public void setLinkCssClass(String linkCssClass) {
		_linkCssClass = linkCssClass;
	}

	public void setSeparator(boolean separator) {
		_separator = separator;
	}

	private static final String _LINK_CSS_CLASS_DEFAULT = "dropdown-item";

	private String _icon;
	private String _label;
	private String _linkCssClass = _LINK_CSS_CLASS_DEFAULT;
	private boolean _separator;

}