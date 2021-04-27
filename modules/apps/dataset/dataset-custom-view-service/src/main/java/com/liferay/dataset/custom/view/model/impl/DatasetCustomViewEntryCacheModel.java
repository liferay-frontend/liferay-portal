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

package com.liferay.dataset.custom.view.model.impl;

import com.liferay.dataset.custom.view.model.DatasetCustomViewEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DatasetCustomViewEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DatasetCustomViewEntryCacheModel
	implements CacheModel<DatasetCustomViewEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DatasetCustomViewEntryCacheModel)) {
			return false;
		}

		DatasetCustomViewEntryCacheModel datasetCustomViewEntryCacheModel =
			(DatasetCustomViewEntryCacheModel)object;

		if ((datasetCustomViewEntryId ==
				datasetCustomViewEntryCacheModel.datasetCustomViewEntryId) &&
			(mvccVersion == datasetCustomViewEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, datasetCustomViewEntryId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", datasetCustomViewEntryId=");
		sb.append(datasetCustomViewEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", datasetDisplayId=");
		sb.append(datasetDisplayId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", settingsJSON=");
		sb.append(settingsJSON);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DatasetCustomViewEntry toEntityModel() {
		DatasetCustomViewEntryImpl datasetCustomViewEntryImpl =
			new DatasetCustomViewEntryImpl();

		datasetCustomViewEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			datasetCustomViewEntryImpl.setUuid("");
		}
		else {
			datasetCustomViewEntryImpl.setUuid(uuid);
		}

		datasetCustomViewEntryImpl.setDatasetCustomViewEntryId(
			datasetCustomViewEntryId);
		datasetCustomViewEntryImpl.setCompanyId(companyId);
		datasetCustomViewEntryImpl.setUserId(userId);

		if (userName == null) {
			datasetCustomViewEntryImpl.setUserName("");
		}
		else {
			datasetCustomViewEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			datasetCustomViewEntryImpl.setCreateDate(null);
		}
		else {
			datasetCustomViewEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			datasetCustomViewEntryImpl.setModifiedDate(null);
		}
		else {
			datasetCustomViewEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (datasetDisplayId == null) {
			datasetCustomViewEntryImpl.setDatasetDisplayId("");
		}
		else {
			datasetCustomViewEntryImpl.setDatasetDisplayId(datasetDisplayId);
		}

		if (name == null) {
			datasetCustomViewEntryImpl.setName("");
		}
		else {
			datasetCustomViewEntryImpl.setName(name);
		}

		datasetCustomViewEntryImpl.setPlid(plid);

		if (portletId == null) {
			datasetCustomViewEntryImpl.setPortletId("");
		}
		else {
			datasetCustomViewEntryImpl.setPortletId(portletId);
		}

		if (settingsJSON == null) {
			datasetCustomViewEntryImpl.setSettingsJSON("");
		}
		else {
			datasetCustomViewEntryImpl.setSettingsJSON(settingsJSON);
		}

		datasetCustomViewEntryImpl.resetOriginalValues();

		return datasetCustomViewEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		datasetCustomViewEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		datasetDisplayId = objectInput.readUTF();
		name = objectInput.readUTF();

		plid = objectInput.readLong();
		portletId = objectInput.readUTF();
		settingsJSON = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(datasetCustomViewEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (datasetDisplayId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(datasetDisplayId);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(plid);

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		if (settingsJSON == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(settingsJSON);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long datasetCustomViewEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String datasetDisplayId;
	public String name;
	public long plid;
	public String portletId;
	public String settingsJSON;

}