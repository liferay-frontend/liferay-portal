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

package com.liferay.frontend.view.state.model.impl;

import com.liferay.frontend.view.state.model.FVSFrontendDataSet;
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
 * The cache model class for representing FVSFrontendDataSet in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FVSFrontendDataSetCacheModel
	implements CacheModel<FVSFrontendDataSet>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FVSFrontendDataSetCacheModel)) {
			return false;
		}

		FVSFrontendDataSetCacheModel fvsFrontendDataSetCacheModel =
			(FVSFrontendDataSetCacheModel)object;

		if ((fvsFrontendDataSetId ==
				fvsFrontendDataSetCacheModel.fvsFrontendDataSetId) &&
			(mvccVersion == fvsFrontendDataSetCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fvsFrontendDataSetId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", fvsFrontendDataSetId=");
		sb.append(fvsFrontendDataSetId);
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
		sb.append(", fvsEntryId=");
		sb.append(fvsEntryId);
		sb.append(", fdsName=");
		sb.append(fdsName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", system=");
		sb.append(system);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FVSFrontendDataSet toEntityModel() {
		FVSFrontendDataSetImpl fvsFrontendDataSetImpl =
			new FVSFrontendDataSetImpl();

		fvsFrontendDataSetImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			fvsFrontendDataSetImpl.setUuid("");
		}
		else {
			fvsFrontendDataSetImpl.setUuid(uuid);
		}

		fvsFrontendDataSetImpl.setFVSFrontendDataSetId(fvsFrontendDataSetId);
		fvsFrontendDataSetImpl.setCompanyId(companyId);
		fvsFrontendDataSetImpl.setUserId(userId);

		if (userName == null) {
			fvsFrontendDataSetImpl.setUserName("");
		}
		else {
			fvsFrontendDataSetImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			fvsFrontendDataSetImpl.setCreateDate(null);
		}
		else {
			fvsFrontendDataSetImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			fvsFrontendDataSetImpl.setModifiedDate(null);
		}
		else {
			fvsFrontendDataSetImpl.setModifiedDate(new Date(modifiedDate));
		}

		fvsFrontendDataSetImpl.setFVSEntryId(fvsEntryId);

		if (fdsName == null) {
			fvsFrontendDataSetImpl.setFDSName("");
		}
		else {
			fvsFrontendDataSetImpl.setFDSName(fdsName);
		}

		if (name == null) {
			fvsFrontendDataSetImpl.setName("");
		}
		else {
			fvsFrontendDataSetImpl.setName(name);
		}

		fvsFrontendDataSetImpl.setPlid(plid);

		if (portletId == null) {
			fvsFrontendDataSetImpl.setPortletId("");
		}
		else {
			fvsFrontendDataSetImpl.setPortletId(portletId);
		}

		fvsFrontendDataSetImpl.setSystem(system);

		fvsFrontendDataSetImpl.resetOriginalValues();

		return fvsFrontendDataSetImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		fvsFrontendDataSetId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		fvsEntryId = objectInput.readLong();
		fdsName = objectInput.readUTF();
		name = objectInput.readUTF();

		plid = objectInput.readLong();
		portletId = objectInput.readUTF();

		system = objectInput.readBoolean();
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

		objectOutput.writeLong(fvsFrontendDataSetId);

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

		objectOutput.writeLong(fvsEntryId);

		if (fdsName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fdsName);
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

		objectOutput.writeBoolean(system);
	}

	public long mvccVersion;
	public String uuid;
	public long fvsFrontendDataSetId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long fvsEntryId;
	public String fdsName;
	public String name;
	public long plid;
	public String portletId;
	public boolean system;

}