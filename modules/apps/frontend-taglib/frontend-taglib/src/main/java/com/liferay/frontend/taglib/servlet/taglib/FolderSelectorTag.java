/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Diego Hu
 */
public class FolderSelectorTag extends IncludeTag {

	public String getFolderName() {
		return _folderName;
	}

	public long getFolderValue() {
		return _folderValue;
	}

	public String getLabel() {
		return _label;
	}

	public String getSelectEventName() {
		return _selectEventName;
	}

	public String getSelectFolderURL() {
		return _selectFolderURL;
	}

	public boolean isFolderInTrash() {
		return _folderInTrash;
	}

	public boolean isFolderNotFound() {
		return _folderNotFound;
	}

	public boolean isShowRemoveButton() {
		return _showRemoveButton;
	}

	public void setFolderInTrash(boolean folderInTrash) {
		_folderInTrash = folderInTrash;
	}

	public void setFolderName(String folderName) {
		_folderName = folderName;
	}

	public void setFolderNotFound(boolean folderNotFound) {
		_folderNotFound = folderNotFound;
	}

	public void setFolderValue(long folderValue) {
		_folderValue = folderValue;
	}

	public void setLabel(String label) {
		_label = label;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setSelectEventName(String selectEventName) {
		_selectEventName = selectEventName;
	}

	public void setSelectFolderURL(String selectFolderURL) {
		_selectFolderURL = selectFolderURL;
	}

	public void setShowRemoveButton(boolean showRemoveButton) {
		_showRemoveButton = showRemoveButton;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_folderInTrash = false;
		_folderName = null;
		_folderNotFound = false;
		_folderValue = 0;
		_label = null;
		_selectEventName = null;
		_selectFolderURL = null;
		_showRemoveButton = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-frontend:folder-selector:folderInTrash", _folderInTrash);
		httpServletRequest.setAttribute(
			"liferay-frontend:folder-selector:folderName", _folderName);
		httpServletRequest.setAttribute(
			"liferay-frontend:folder-selector:folderNotFound", _folderNotFound);
		httpServletRequest.setAttribute(
			"liferay-frontend:folder-selector:folderValue", _folderValue);
		httpServletRequest.setAttribute(
			"liferay-frontend:folder-selector:label", _label);
		httpServletRequest.setAttribute(
			"liferay-frontend:folder-selector:selectEventName",
			_selectEventName);
		httpServletRequest.setAttribute(
			"liferay-frontend:folder-selector:selectFolderURL",
			_selectFolderURL);
		httpServletRequest.setAttribute(
			"liferay-frontend:folder-selector:showRemoveButton",
			_showRemoveButton);
	}

	private static final String _PAGE = "/folder_selector/page.jsp";

	private boolean _folderInTrash;
	private String _folderName;
	private boolean _folderNotFound;
	private long _folderValue;
	private String _label;
	private String _selectEventName;
	private String _selectFolderURL;
	private boolean _showRemoveButton;

}