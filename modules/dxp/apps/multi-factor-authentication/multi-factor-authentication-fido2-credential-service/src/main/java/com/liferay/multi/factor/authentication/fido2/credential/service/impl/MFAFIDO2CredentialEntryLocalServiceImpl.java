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

package com.liferay.multi.factor.authentication.fido2.credential.service.impl;

import com.liferay.multi.factor.authentication.fido2.credential.exception.DuplicateMFAFIDO2CredentialEntryException;
import com.liferay.multi.factor.authentication.fido2.credential.exception.NoSuchMFAFIDO2CredentialEntryException;
import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
import com.liferay.multi.factor.authentication.fido2.credential.service.base.MFAFIDO2CredentialEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 */
@Component(
	property = "model.class.name=com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry",
	service = AopService.class
)
public class MFAFIDO2CredentialEntryLocalServiceImpl
	extends MFAFIDO2CredentialEntryLocalServiceBaseImpl {

	@Override
	public MFAFIDO2CredentialEntry addMFAFIDO2CredentialEntry(
			long userId, String credentialId, int credentialType,
			String publicKeyCose)
		throws PortalException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			mfafido2CredentialEntryPersistence.fetchByUserId(userId);

		if (mfaFIDO2CredentialEntry != null) {
			throw new DuplicateMFAFIDO2CredentialEntryException(
				"User ID " + userId);
		}

		User user = userLocalService.getUserById(userId);

		long entryId = counterLocalService.increment();

		mfaFIDO2CredentialEntry = mfafido2CredentialEntryPersistence.create(
			entryId);

		mfaFIDO2CredentialEntry.setCompanyId(user.getCompanyId());
		mfaFIDO2CredentialEntry.setUserId(userId);
		mfaFIDO2CredentialEntry.setUserName(user.getFullName());
		mfaFIDO2CredentialEntry.setCreateDate(new Date());
		mfaFIDO2CredentialEntry.setCredentialId(credentialId);
		mfaFIDO2CredentialEntry.setCredentialType(credentialType);
		mfaFIDO2CredentialEntry.setPublicKeyCose(publicKeyCose);
		mfaFIDO2CredentialEntry.setSignatureCount(0);

		return mfafido2CredentialEntryPersistence.update(
			mfaFIDO2CredentialEntry);
	}

	@Override
	public MFAFIDO2CredentialEntry fetchMFAFIDO2CredentialEntryByCredentialId(
		String credentialId) {

		return mfafido2CredentialEntryPersistence.fetchByCredentialId(
			credentialId);
	}

	@Override
	public MFAFIDO2CredentialEntry fetchMFAFIDO2CredentialEntryByUserId(
		long userId) {

		return mfafido2CredentialEntryPersistence.fetchByUserId(userId);
	}

	@Override
	public MFAFIDO2CredentialEntry updateAttempts(
			long userId, String ip, long signatureCount)
		throws PortalException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			mfafido2CredentialEntryPersistence.fetchByUserId(userId);

		if (mfaFIDO2CredentialEntry == null) {
			throw new NoSuchMFAFIDO2CredentialEntryException(
				"User ID " + userId);
		}

		if (signatureCount < 1) {
			mfaFIDO2CredentialEntry.setSignatureCount(0);
			mfaFIDO2CredentialEntry.setFailedAttempts(
				mfaFIDO2CredentialEntry.getFailedAttempts() + 1);
			mfaFIDO2CredentialEntry.setLastFailDate(new Date());
			mfaFIDO2CredentialEntry.setLastFailIP(ip);
		}
		else {
			mfaFIDO2CredentialEntry.setSignatureCount(signatureCount);
			mfaFIDO2CredentialEntry.setFailedAttempts(0);
			mfaFIDO2CredentialEntry.setLastSuccessDate(new Date());
			mfaFIDO2CredentialEntry.setLastSuccessIP(ip);
		}

		return mfafido2CredentialEntryPersistence.update(
			mfaFIDO2CredentialEntry);
	}

}