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

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.UserCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;

import java.util.List;

/**
 * @author Carlos Lancha
 */
public class UserCardsDisplayContext implements UserCard {

	public List<DropdownItem> getActionDropdownItems() {
		return _actionDropdownItems;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getHref() {
		return _href;
	}

	public String getIcon() {
		return _icon;
	}

	public String getId() {
		return _id;
	}

	public String getImageAlt() {
		return _imageAlt;
	}

	public String getImageSrc() {
		return _imageSrc;
	}

	public String getInputName() {
		return _inputName;
	}

	public String getInputValue() {
		return _inputValue;
	}

	public List<LabelItem> getLabels() {
		return _labels;
	}

	public String getName() {
		return _name;
    };

	public String getSubtitle() {
		return _subtitle;
	}

	public String getUserColorClass() {
		return _userColorClass;
	}

	public boolean isDisabled() {
		return _disabled;
	}

	public boolean isSelectable() {
		return _selectable;
	}

	public boolean isSelected() {
		return _selected;
	}

	public void setActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		_actionDropdownItems = actionDropdownItems;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setImageAlt(String imageAlt) {
		_imageAlt = imageAlt;
	}

	public void setImageSrc(String imageSrc) {
		_imageSrc = imageSrc;
	}

	public void setInputName(String inputName) {
		_inputName = inputName;
	}

	public void setInputValue(String inputValue) {
		_inputValue = inputValue;
	}

	public void setLabels(List<LabelItem> labels) {
		_labels = labels;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSelectable(boolean selectable) {
		_selectable = selectable;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	public void setSubtitle(String subtitle) {
		_subtitle = subtitle;
	}

	public void setUserColorClass(String userColorClass) {
		_userColorClass = userColorClass;
	}

	private List<DropdownItem> _actionDropdownItems;
	private String _cssClass = "custom-css-class";
	private boolean _disabled;
	private String _href = "user-card-href";
	private String _icon;
	private String _id = "userCardId";
	private String _imageAlt = "User Card Image Alt Text";
	private String _imageSrc;
	private String _inputName = "user-card-input-name";
	private String _inputValue = "user-card-input-value";
	private List<LabelItem> _labels;
	private String _name = "User Name";
	private boolean _selectable;
	private boolean _selected;
	private String _subtitle = "Latest Action";
	private String _userColorClass = "danger";

}