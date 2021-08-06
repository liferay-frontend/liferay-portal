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

package com.liferay.remote.app.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CustomElementPortletEntrySoap implements Serializable {

	public static CustomElementPortletEntrySoap toSoapModel(
		CustomElementPortletEntry model) {

		CustomElementPortletEntrySoap soapModel =
			new CustomElementPortletEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setCustomElementPortletEntryId(
			model.getCustomElementPortletEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCssURLs(model.getCssURLs());
		soapModel.setInstanceable(model.getInstanceable());
		soapModel.setTagAttributes(model.getTagAttributes());
		soapModel.setName(model.getName());
		soapModel.setPortletDisplayCategory(model.getPortletDisplayCategory());
		soapModel.setTagName(model.getTagName());

		return soapModel;
	}

	public static CustomElementPortletEntrySoap[] toSoapModels(
		CustomElementPortletEntry[] models) {

		CustomElementPortletEntrySoap[] soapModels =
			new CustomElementPortletEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CustomElementPortletEntrySoap[][] toSoapModels(
		CustomElementPortletEntry[][] models) {

		CustomElementPortletEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CustomElementPortletEntrySoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CustomElementPortletEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CustomElementPortletEntrySoap[] toSoapModels(
		List<CustomElementPortletEntry> models) {

		List<CustomElementPortletEntrySoap> soapModels =
			new ArrayList<CustomElementPortletEntrySoap>(models.size());

		for (CustomElementPortletEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CustomElementPortletEntrySoap[soapModels.size()]);
	}

	public CustomElementPortletEntrySoap() {
	}

	public long getPrimaryKey() {
		return _customElementPortletEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCustomElementPortletEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCustomElementPortletEntryId() {
		return _customElementPortletEntryId;
	}

	public void setCustomElementPortletEntryId(
		long customElementPortletEntryId) {

		_customElementPortletEntryId = customElementPortletEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getCssURLs() {
		return _cssURLs;
	}

	public void setCssURLs(String cssURLs) {
		_cssURLs = cssURLs;
	}

	public Boolean getInstanceable() {
		return _instanceable;
	}

	public void setInstanceable(Boolean instanceable) {
		_instanceable = instanceable;
	}

	public String getTagAttributes() {
		return _tagAttributes;
	}

	public void setTagAttributes(String tagAttributes) {
		_tagAttributes = tagAttributes;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getPortletDisplayCategory() {
		return _portletDisplayCategory;
	}

	public void setPortletDisplayCategory(String portletDisplayCategory) {
		_portletDisplayCategory = portletDisplayCategory;
	}

	public String getTagName() {
		return _tagName;
	}

	public void setTagName(String tagName) {
		_tagName = tagName;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _customElementPortletEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _cssURLs;
	private Boolean _instanceable;
	private String _tagAttributes;
	private String _name;
	private String _portletDisplayCategory;
	private String _tagName;

}