/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.fido2.credential.model.impl;

import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
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
 * The cache model class for representing MFAFIDO2CredentialEntry in entity cache.
 *
 * @author Arthur Chan
 * @generated
 */
public class MFAFIDO2CredentialEntryCacheModel
	implements CacheModel<MFAFIDO2CredentialEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MFAFIDO2CredentialEntryCacheModel)) {
			return false;
		}

		MFAFIDO2CredentialEntryCacheModel mfafido2CredentialEntryCacheModel =
			(MFAFIDO2CredentialEntryCacheModel)object;

		if ((mfaFIDO2CredentialEntryId ==
				mfafido2CredentialEntryCacheModel.mfaFIDO2CredentialEntryId) &&
			(mvccVersion == mfafido2CredentialEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, mfaFIDO2CredentialEntryId);

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
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", mfaFIDO2CredentialEntryId=");
		sb.append(mfaFIDO2CredentialEntryId);
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
		sb.append(", credentialId=");
		sb.append(credentialId);
		sb.append(", credentialType=");
		sb.append(credentialType);
		sb.append(", publicKeyCose=");
		sb.append(publicKeyCose);
		sb.append(", signatureCount=");
		sb.append(signatureCount);
		sb.append(", failedAttempts=");
		sb.append(failedAttempts);
		sb.append(", lastFailDate=");
		sb.append(lastFailDate);
		sb.append(", lastFailIP=");
		sb.append(lastFailIP);
		sb.append(", lastSuccessDate=");
		sb.append(lastSuccessDate);
		sb.append(", lastSuccessIP=");
		sb.append(lastSuccessIP);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MFAFIDO2CredentialEntry toEntityModel() {
		MFAFIDO2CredentialEntryImpl mfafido2CredentialEntryImpl =
			new MFAFIDO2CredentialEntryImpl();

		mfafido2CredentialEntryImpl.setMvccVersion(mvccVersion);
		mfafido2CredentialEntryImpl.setMfaFIDO2CredentialEntryId(
			mfaFIDO2CredentialEntryId);
		mfafido2CredentialEntryImpl.setCompanyId(companyId);
		mfafido2CredentialEntryImpl.setUserId(userId);

		if (userName == null) {
			mfafido2CredentialEntryImpl.setUserName("");
		}
		else {
			mfafido2CredentialEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mfafido2CredentialEntryImpl.setCreateDate(null);
		}
		else {
			mfafido2CredentialEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mfafido2CredentialEntryImpl.setModifiedDate(null);
		}
		else {
			mfafido2CredentialEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (credentialId == null) {
			mfafido2CredentialEntryImpl.setCredentialId("");
		}
		else {
			mfafido2CredentialEntryImpl.setCredentialId(credentialId);
		}

		mfafido2CredentialEntryImpl.setCredentialType(credentialType);

		if (publicKeyCose == null) {
			mfafido2CredentialEntryImpl.setPublicKeyCose("");
		}
		else {
			mfafido2CredentialEntryImpl.setPublicKeyCose(publicKeyCose);
		}

		mfafido2CredentialEntryImpl.setSignatureCount(signatureCount);
		mfafido2CredentialEntryImpl.setFailedAttempts(failedAttempts);

		if (lastFailDate == Long.MIN_VALUE) {
			mfafido2CredentialEntryImpl.setLastFailDate(null);
		}
		else {
			mfafido2CredentialEntryImpl.setLastFailDate(new Date(lastFailDate));
		}

		if (lastFailIP == null) {
			mfafido2CredentialEntryImpl.setLastFailIP("");
		}
		else {
			mfafido2CredentialEntryImpl.setLastFailIP(lastFailIP);
		}

		if (lastSuccessDate == Long.MIN_VALUE) {
			mfafido2CredentialEntryImpl.setLastSuccessDate(null);
		}
		else {
			mfafido2CredentialEntryImpl.setLastSuccessDate(
				new Date(lastSuccessDate));
		}

		if (lastSuccessIP == null) {
			mfafido2CredentialEntryImpl.setLastSuccessIP("");
		}
		else {
			mfafido2CredentialEntryImpl.setLastSuccessIP(lastSuccessIP);
		}

		mfafido2CredentialEntryImpl.resetOriginalValues();

		return mfafido2CredentialEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		mfaFIDO2CredentialEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		credentialId = objectInput.readUTF();

		credentialType = objectInput.readInt();
		publicKeyCose = objectInput.readUTF();

		signatureCount = objectInput.readLong();

		failedAttempts = objectInput.readInt();
		lastFailDate = objectInput.readLong();
		lastFailIP = objectInput.readUTF();
		lastSuccessDate = objectInput.readLong();
		lastSuccessIP = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(mfaFIDO2CredentialEntryId);

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

		if (credentialId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(credentialId);
		}

		objectOutput.writeInt(credentialType);

		if (publicKeyCose == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(publicKeyCose);
		}

		objectOutput.writeLong(signatureCount);

		objectOutput.writeInt(failedAttempts);
		objectOutput.writeLong(lastFailDate);

		if (lastFailIP == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(lastFailIP);
		}

		objectOutput.writeLong(lastSuccessDate);

		if (lastSuccessIP == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(lastSuccessIP);
		}
	}

	public long mvccVersion;
	public long mfaFIDO2CredentialEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String credentialId;
	public int credentialType;
	public String publicKeyCose;
	public long signatureCount;
	public int failedAttempts;
	public long lastFailDate;
	public String lastFailIP;
	public long lastSuccessDate;
	public String lastSuccessIP;

}