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

package com.liferay.dataset.custom.view.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DatasetCustomViewEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DatasetCustomViewEntry
 * @generated
 */
public class DatasetCustomViewEntryWrapper
	extends BaseModelWrapper<DatasetCustomViewEntry>
	implements DatasetCustomViewEntry, ModelWrapper<DatasetCustomViewEntry> {

	public DatasetCustomViewEntryWrapper(
		DatasetCustomViewEntry datasetCustomViewEntry) {

		super(datasetCustomViewEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"datasetCustomViewEntryId", getDatasetCustomViewEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("datasetDisplayId", getDatasetDisplayId());
		attributes.put("plid", getPlid());
		attributes.put("portletId", getPortletId());
		attributes.put("settingsJSON", getSettingsJSON());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long datasetCustomViewEntryId = (Long)attributes.get(
			"datasetCustomViewEntryId");

		if (datasetCustomViewEntryId != null) {
			setDatasetCustomViewEntryId(datasetCustomViewEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String datasetDisplayId = (String)attributes.get("datasetDisplayId");

		if (datasetDisplayId != null) {
			setDatasetDisplayId(datasetDisplayId);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}

		String settingsJSON = (String)attributes.get("settingsJSON");

		if (settingsJSON != null) {
			setSettingsJSON(settingsJSON);
		}
	}

	/**
	 * Returns the company ID of this dataset custom view entry.
	 *
	 * @return the company ID of this dataset custom view entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this dataset custom view entry.
	 *
	 * @return the container model ID of this dataset custom view entry
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this dataset custom view entry.
	 *
	 * @return the container name of this dataset custom view entry
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the create date of this dataset custom view entry.
	 *
	 * @return the create date of this dataset custom view entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the dataset custom view entry ID of this dataset custom view entry.
	 *
	 * @return the dataset custom view entry ID of this dataset custom view entry
	 */
	@Override
	public long getDatasetCustomViewEntryId() {
		return model.getDatasetCustomViewEntryId();
	}

	/**
	 * Returns the dataset display ID of this dataset custom view entry.
	 *
	 * @return the dataset display ID of this dataset custom view entry
	 */
	@Override
	public String getDatasetDisplayId() {
		return model.getDatasetDisplayId();
	}

	/**
	 * Returns the modified date of this dataset custom view entry.
	 *
	 * @return the modified date of this dataset custom view entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this dataset custom view entry.
	 *
	 * @return the mvcc version of this dataset custom view entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parent container model ID of this dataset custom view entry.
	 *
	 * @return the parent container model ID of this dataset custom view entry
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	/**
	 * Returns the plid of this dataset custom view entry.
	 *
	 * @return the plid of this dataset custom view entry
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the portlet ID of this dataset custom view entry.
	 *
	 * @return the portlet ID of this dataset custom view entry
	 */
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the primary key of this dataset custom view entry.
	 *
	 * @return the primary key of this dataset custom view entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the settings json of this dataset custom view entry.
	 *
	 * @return the settings json of this dataset custom view entry
	 */
	@Override
	public String getSettingsJSON() {
		return model.getSettingsJSON();
	}

	/**
	 * Returns the user ID of this dataset custom view entry.
	 *
	 * @return the user ID of this dataset custom view entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this dataset custom view entry.
	 *
	 * @return the user name of this dataset custom view entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this dataset custom view entry.
	 *
	 * @return the user uuid of this dataset custom view entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this dataset custom view entry.
	 *
	 * @return the uuid of this dataset custom view entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this dataset custom view entry.
	 *
	 * @param companyId the company ID of this dataset custom view entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this dataset custom view entry.
	 *
	 * @param containerModelId the container model ID of this dataset custom view entry
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the create date of this dataset custom view entry.
	 *
	 * @param createDate the create date of this dataset custom view entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the dataset custom view entry ID of this dataset custom view entry.
	 *
	 * @param datasetCustomViewEntryId the dataset custom view entry ID of this dataset custom view entry
	 */
	@Override
	public void setDatasetCustomViewEntryId(long datasetCustomViewEntryId) {
		model.setDatasetCustomViewEntryId(datasetCustomViewEntryId);
	}

	/**
	 * Sets the dataset display ID of this dataset custom view entry.
	 *
	 * @param datasetDisplayId the dataset display ID of this dataset custom view entry
	 */
	@Override
	public void setDatasetDisplayId(String datasetDisplayId) {
		model.setDatasetDisplayId(datasetDisplayId);
	}

	/**
	 * Sets the modified date of this dataset custom view entry.
	 *
	 * @param modifiedDate the modified date of this dataset custom view entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this dataset custom view entry.
	 *
	 * @param mvccVersion the mvcc version of this dataset custom view entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent container model ID of this dataset custom view entry.
	 *
	 * @param parentContainerModelId the parent container model ID of this dataset custom view entry
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the plid of this dataset custom view entry.
	 *
	 * @param plid the plid of this dataset custom view entry
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the portlet ID of this dataset custom view entry.
	 *
	 * @param portletId the portlet ID of this dataset custom view entry
	 */
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	 * Sets the primary key of this dataset custom view entry.
	 *
	 * @param primaryKey the primary key of this dataset custom view entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the settings json of this dataset custom view entry.
	 *
	 * @param settingsJSON the settings json of this dataset custom view entry
	 */
	@Override
	public void setSettingsJSON(String settingsJSON) {
		model.setSettingsJSON(settingsJSON);
	}

	/**
	 * Sets the user ID of this dataset custom view entry.
	 *
	 * @param userId the user ID of this dataset custom view entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this dataset custom view entry.
	 *
	 * @param userName the user name of this dataset custom view entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this dataset custom view entry.
	 *
	 * @param userUuid the user uuid of this dataset custom view entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this dataset custom view entry.
	 *
	 * @param uuid the uuid of this dataset custom view entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DatasetCustomViewEntryWrapper wrap(
		DatasetCustomViewEntry datasetCustomViewEntry) {

		return new DatasetCustomViewEntryWrapper(datasetCustomViewEntry);
	}

}