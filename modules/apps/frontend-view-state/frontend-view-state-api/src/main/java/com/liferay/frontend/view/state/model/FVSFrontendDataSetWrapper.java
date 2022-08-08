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

package com.liferay.frontend.view.state.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link FVSFrontendDataSet}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSFrontendDataSet
 * @generated
 */
public class FVSFrontendDataSetWrapper
	extends BaseModelWrapper<FVSFrontendDataSet>
	implements FVSFrontendDataSet, ModelWrapper<FVSFrontendDataSet> {

	public FVSFrontendDataSetWrapper(FVSFrontendDataSet fvsFrontendDataSet) {
		super(fvsFrontendDataSet);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("fvsFrontendDataSetId", getFVSFrontendDataSetId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fvsEntryId", getFVSEntryId());
		attributes.put("fdsName", getFDSName());
		attributes.put("name", getName());
		attributes.put("plid", getPlid());
		attributes.put("portletId", getPortletId());
		attributes.put("system", isSystem());

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

		Long fvsFrontendDataSetId = (Long)attributes.get(
			"fvsFrontendDataSetId");

		if (fvsFrontendDataSetId != null) {
			setFVSFrontendDataSetId(fvsFrontendDataSetId);
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

		Long fvsEntryId = (Long)attributes.get("fvsEntryId");

		if (fvsEntryId != null) {
			setFVSEntryId(fvsEntryId);
		}

		String fdsName = (String)attributes.get("fdsName");

		if (fdsName != null) {
			setFDSName(fdsName);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}

		Boolean system = (Boolean)attributes.get("system");

		if (system != null) {
			setSystem(system);
		}
	}

	@Override
	public FVSFrontendDataSet cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this fvs frontend data set.
	 *
	 * @return the company ID of this fvs frontend data set
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this fvs frontend data set.
	 *
	 * @return the container model ID of this fvs frontend data set
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this fvs frontend data set.
	 *
	 * @return the container name of this fvs frontend data set
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the create date of this fvs frontend data set.
	 *
	 * @return the create date of this fvs frontend data set
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the fds name of this fvs frontend data set.
	 *
	 * @return the fds name of this fvs frontend data set
	 */
	@Override
	public String getFDSName() {
		return model.getFDSName();
	}

	/**
	 * Returns the fvs entry ID of this fvs frontend data set.
	 *
	 * @return the fvs entry ID of this fvs frontend data set
	 */
	@Override
	public long getFVSEntryId() {
		return model.getFVSEntryId();
	}

	/**
	 * Returns the fvs frontend data set ID of this fvs frontend data set.
	 *
	 * @return the fvs frontend data set ID of this fvs frontend data set
	 */
	@Override
	public long getFVSFrontendDataSetId() {
		return model.getFVSFrontendDataSetId();
	}

	/**
	 * Returns the modified date of this fvs frontend data set.
	 *
	 * @return the modified date of this fvs frontend data set
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this fvs frontend data set.
	 *
	 * @return the mvcc version of this fvs frontend data set
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this fvs frontend data set.
	 *
	 * @return the name of this fvs frontend data set
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent container model ID of this fvs frontend data set.
	 *
	 * @return the parent container model ID of this fvs frontend data set
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	/**
	 * Returns the plid of this fvs frontend data set.
	 *
	 * @return the plid of this fvs frontend data set
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the portlet ID of this fvs frontend data set.
	 *
	 * @return the portlet ID of this fvs frontend data set
	 */
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the primary key of this fvs frontend data set.
	 *
	 * @return the primary key of this fvs frontend data set
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the system of this fvs frontend data set.
	 *
	 * @return the system of this fvs frontend data set
	 */
	@Override
	public boolean getSystem() {
		return model.getSystem();
	}

	/**
	 * Returns the user ID of this fvs frontend data set.
	 *
	 * @return the user ID of this fvs frontend data set
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this fvs frontend data set.
	 *
	 * @return the user name of this fvs frontend data set
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this fvs frontend data set.
	 *
	 * @return the user uuid of this fvs frontend data set
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this fvs frontend data set.
	 *
	 * @return the uuid of this fvs frontend data set
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this fvs frontend data set is system.
	 *
	 * @return <code>true</code> if this fvs frontend data set is system; <code>false</code> otherwise
	 */
	@Override
	public boolean isSystem() {
		return model.isSystem();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this fvs frontend data set.
	 *
	 * @param companyId the company ID of this fvs frontend data set
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this fvs frontend data set.
	 *
	 * @param containerModelId the container model ID of this fvs frontend data set
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the create date of this fvs frontend data set.
	 *
	 * @param createDate the create date of this fvs frontend data set
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the fds name of this fvs frontend data set.
	 *
	 * @param fdsName the fds name of this fvs frontend data set
	 */
	@Override
	public void setFDSName(String fdsName) {
		model.setFDSName(fdsName);
	}

	/**
	 * Sets the fvs entry ID of this fvs frontend data set.
	 *
	 * @param fvsEntryId the fvs entry ID of this fvs frontend data set
	 */
	@Override
	public void setFVSEntryId(long fvsEntryId) {
		model.setFVSEntryId(fvsEntryId);
	}

	/**
	 * Sets the fvs frontend data set ID of this fvs frontend data set.
	 *
	 * @param fvsFrontendDataSetId the fvs frontend data set ID of this fvs frontend data set
	 */
	@Override
	public void setFVSFrontendDataSetId(long fvsFrontendDataSetId) {
		model.setFVSFrontendDataSetId(fvsFrontendDataSetId);
	}

	/**
	 * Sets the modified date of this fvs frontend data set.
	 *
	 * @param modifiedDate the modified date of this fvs frontend data set
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this fvs frontend data set.
	 *
	 * @param mvccVersion the mvcc version of this fvs frontend data set
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this fvs frontend data set.
	 *
	 * @param name the name of this fvs frontend data set
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent container model ID of this fvs frontend data set.
	 *
	 * @param parentContainerModelId the parent container model ID of this fvs frontend data set
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the plid of this fvs frontend data set.
	 *
	 * @param plid the plid of this fvs frontend data set
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the portlet ID of this fvs frontend data set.
	 *
	 * @param portletId the portlet ID of this fvs frontend data set
	 */
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	 * Sets the primary key of this fvs frontend data set.
	 *
	 * @param primaryKey the primary key of this fvs frontend data set
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this fvs frontend data set is system.
	 *
	 * @param system the system of this fvs frontend data set
	 */
	@Override
	public void setSystem(boolean system) {
		model.setSystem(system);
	}

	/**
	 * Sets the user ID of this fvs frontend data set.
	 *
	 * @param userId the user ID of this fvs frontend data set
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this fvs frontend data set.
	 *
	 * @param userName the user name of this fvs frontend data set
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this fvs frontend data set.
	 *
	 * @param userUuid the user uuid of this fvs frontend data set
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this fvs frontend data set.
	 *
	 * @param uuid the uuid of this fvs frontend data set
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
	protected FVSFrontendDataSetWrapper wrap(
		FVSFrontendDataSet fvsFrontendDataSet) {

		return new FVSFrontendDataSetWrapper(fvsFrontendDataSet);
	}

}