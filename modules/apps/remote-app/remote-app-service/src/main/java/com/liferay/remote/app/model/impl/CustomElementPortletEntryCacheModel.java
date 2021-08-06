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

package com.liferay.remote.app.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.remote.app.model.CustomElementPortletEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CustomElementPortletEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CustomElementPortletEntryCacheModel
	implements CacheModel<CustomElementPortletEntry>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CustomElementPortletEntryCacheModel)) {
			return false;
		}

		CustomElementPortletEntryCacheModel
			customElementPortletEntryCacheModel =
				(CustomElementPortletEntryCacheModel)object;

		if ((customElementPortletEntryId ==
				customElementPortletEntryCacheModel.
					customElementPortletEntryId) &&
			(mvccVersion == customElementPortletEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, customElementPortletEntryId);

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
		sb.append(", customElementPortletEntryId=");
		sb.append(customElementPortletEntryId);
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
		sb.append(", cssURLs=");
		sb.append(cssURLs);
		sb.append(", instanceable=");
		sb.append(instanceable);
		sb.append(", tagAttributes=");
		sb.append(tagAttributes);
		sb.append(", name=");
		sb.append(name);
		sb.append(", portletDisplayCategory=");
		sb.append(portletDisplayCategory);
		sb.append(", tagName=");
		sb.append(tagName);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CustomElementPortletEntry toEntityModel() {
		CustomElementPortletEntryImpl customElementPortletEntryImpl =
			new CustomElementPortletEntryImpl();

		customElementPortletEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			customElementPortletEntryImpl.setUuid("");
		}
		else {
			customElementPortletEntryImpl.setUuid(uuid);
		}

		customElementPortletEntryImpl.setCustomElementPortletEntryId(
			customElementPortletEntryId);
		customElementPortletEntryImpl.setCompanyId(companyId);
		customElementPortletEntryImpl.setUserId(userId);

		if (userName == null) {
			customElementPortletEntryImpl.setUserName("");
		}
		else {
			customElementPortletEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			customElementPortletEntryImpl.setCreateDate(null);
		}
		else {
			customElementPortletEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			customElementPortletEntryImpl.setModifiedDate(null);
		}
		else {
			customElementPortletEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (cssURLs == null) {
			customElementPortletEntryImpl.setCssURLs("");
		}
		else {
			customElementPortletEntryImpl.setCssURLs(cssURLs);
		}

		customElementPortletEntryImpl.setInstanceable(instanceable);

		if (tagAttributes == null) {
			customElementPortletEntryImpl.setTagAttributes("");
		}
		else {
			customElementPortletEntryImpl.setTagAttributes(tagAttributes);
		}

		if (name == null) {
			customElementPortletEntryImpl.setName("");
		}
		else {
			customElementPortletEntryImpl.setName(name);
		}

		if (portletDisplayCategory == null) {
			customElementPortletEntryImpl.setPortletDisplayCategory("");
		}
		else {
			customElementPortletEntryImpl.setPortletDisplayCategory(
				portletDisplayCategory);
		}

		if (tagName == null) {
			customElementPortletEntryImpl.setTagName("");
		}
		else {
			customElementPortletEntryImpl.setTagName(tagName);
		}

		customElementPortletEntryImpl.resetOriginalValues();

		return customElementPortletEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		customElementPortletEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		cssURLs = objectInput.readUTF();

		instanceable = objectInput.readBoolean();
		tagAttributes = objectInput.readUTF();
		name = objectInput.readUTF();
		portletDisplayCategory = objectInput.readUTF();
		tagName = objectInput.readUTF();
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

		objectOutput.writeLong(customElementPortletEntryId);

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

		if (cssURLs == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(cssURLs);
		}

		objectOutput.writeBoolean(instanceable);

		if (tagAttributes == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(tagAttributes);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (portletDisplayCategory == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletDisplayCategory);
		}

		if (tagName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(tagName);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long customElementPortletEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String cssURLs;
	public boolean instanceable;
	public String tagAttributes;
	public String name;
	public String portletDisplayCategory;
	public String tagName;

}