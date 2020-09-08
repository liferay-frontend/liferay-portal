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

package com.liferay.multi.factor.authentication.fido2.web.internal.credential.repository;

import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
import com.liferay.multi.factor.authentication.fido2.credential.service.MFAFIDO2CredentialEntryLocalService;
import com.liferay.multi.factor.authentication.fido2.web.internal.util.ConvertUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;

import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;
import com.yubico.webauthn.data.PublicKeyCredentialType;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Arthur Chan
 */
public class MFAFIDO2CredentialRepository implements CredentialRepository {

	public MFAFIDO2CredentialRepository(
		MFAFIDO2CredentialEntryLocalService mfaFIDO2CredentialEntryLocalService,
		UserLocalService userLocalService) {

		_mfaFIDO2CredentialEntryLocalService =
			mfaFIDO2CredentialEntryLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(
		String userName) {

		long userId = _getUserIdByUserName(userName);

		Set<PublicKeyCredentialDescriptor> descriptors = new HashSet<>();

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			_mfaFIDO2CredentialEntryLocalService.
				fetchMFAFIDO2CredentialEntryByUserId(userId);

		if (mfaFIDO2CredentialEntry != null) {
			descriptors.add(
				_buildPublicKeyCredentialDescriptor(mfaFIDO2CredentialEntry));
		}

		return descriptors;
	}

	@Override
	public Optional<ByteArray> getUserHandleForUsername(String userName) {
		long userId = _getUserIdByUserName(userName);

		return Optional.of(ConvertUtil.longToByteArray(userId));
	}

	@Override
	public Optional<String> getUsernameForUserHandle(ByteArray userHandle) {
		return Optional.of(_getScreenNameByUserHandle(userHandle));
	}

	@Override
	public Optional<RegisteredCredential> lookup(
		ByteArray credentialId, ByteArray userHandle) {

		long userId = ConvertUtil.byteArrayToLong(userHandle);

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			_mfaFIDO2CredentialEntryLocalService.
				fetchMFAFIDO2CredentialEntryByUserId(userId);

		if (mfaFIDO2CredentialEntry == null) {
			Optional.of(null);
		}

		String idString = new String(credentialId.getBytes());

		String storedId = mfaFIDO2CredentialEntry.getCredentialId();

		if (!idString.equals(storedId)) {
			Optional.of(null);
		}

		return Optional.of(_buildRegisteredCredential(mfaFIDO2CredentialEntry));
	}

	@Override
	public Set<RegisteredCredential> lookupAll(ByteArray credentialId) {
		String idString = new String(credentialId.getBytes());

		Set<RegisteredCredential> credentials = new HashSet<>();

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			_mfaFIDO2CredentialEntryLocalService.
				fetchMFAFIDO2CredentialEntryByCredentialId(idString);

		if (mfaFIDO2CredentialEntry != null) {
			credentials.add(
				_buildRegisteredCredential(mfaFIDO2CredentialEntry));
		}

		return credentials;
	}

	private PublicKeyCredentialDescriptor _buildPublicKeyCredentialDescriptor(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		String credentialId = mfaFIDO2CredentialEntry.getCredentialId();

		return PublicKeyCredentialDescriptor.builder(
		).id(
			new ByteArray(credentialId.getBytes())
		).type(
			PublicKeyCredentialType.PUBLIC_KEY
		).build();
	}

	private RegisteredCredential _buildRegisteredCredential(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		String credentialId = mfaFIDO2CredentialEntry.getCredentialId();

		String publicKeyCose = mfaFIDO2CredentialEntry.getPublicKeyCose();

		return RegisteredCredential.builder(
		).credentialId(
			new ByteArray(credentialId.getBytes())
		).userHandle(
			ConvertUtil.longToByteArray(mfaFIDO2CredentialEntry.getUserId())
		).publicKeyCose(
			new ByteArray(publicKeyCose.getBytes())
		).signatureCount(
			mfaFIDO2CredentialEntry.getSignatureCount()
		).build();
	}

	private String _getScreenNameByUserHandle(ByteArray userHandle) {
		long userId = ConvertUtil.byteArrayToLong(userHandle);

		User user = _userLocalService.fetchUserById(userId);

		if (user == null) {
			return "";
		}

		return user.getScreenName();
	}

	private long _getUserIdByUserName(String userName) {
		User user = _userLocalService.fetchUserByScreenName(
			CompanyThreadLocal.getCompanyId(), userName);

		if (user == null) {
			return -1;
		}

		return user.getUserId();
	}

	private final MFAFIDO2CredentialEntryLocalService
		_mfaFIDO2CredentialEntryLocalService;
	private final UserLocalService _userLocalService;

}