/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.ip.address.internal.audit;

import com.liferay.multi.factor.authentication.ip.address.internal.constants.MFAIPAddressEventTypes;
import com.liferay.portal.kernel.audit.AuditException;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(service = MFAIPAddressAuditMessageBuilder.class)
public class MFAIPAddressAuditMessageBuilder {

	public AuditMessage buildNonexistentUserVerificationFailureAuditMessage(
		long companyId, long userId, String mfaCheckerClassName) {

		return new AuditMessage(
			MFAIPAddressEventTypes.MFA_IP_ADDRESS_VERIFICATION_FAILURE,
			companyId, userId, "Nonexistent", mfaCheckerClassName,
			String.valueOf(userId), null,
			JSONUtil.put("reason", "Nonexistent User"));
	}

	public AuditMessage buildVerificationFailureAuditMessage(
		User user, String mfaCheckerClassName, String reason) {

		return new AuditMessage(
			MFAIPAddressEventTypes.MFA_IP_ADDRESS_VERIFICATION_FAILURE,
			user.getCompanyId(), user.getUserId(), user.getFullName(),
			mfaCheckerClassName, String.valueOf(user.getPrimaryKey()), null,
			JSONUtil.put("reason", reason));
	}

	public AuditMessage buildVerificationSuccessAuditMessage(
		User user, String mfaCheckerClassName) {

		return new AuditMessage(
			MFAIPAddressEventTypes.MFA_IP_ADDRESS_VERIFICATION_SUCCESS,
			user.getCompanyId(), user.getUserId(), user.getFullName(),
			mfaCheckerClassName, String.valueOf(user.getPrimaryKey()), null,
			null);
	}

	public void routeAuditMessage(AuditMessage auditMessage) {
		try {
			_auditRouter.route(auditMessage);
		}
		catch (AuditException auditException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to route audit message", auditException);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MFAIPAddressAuditMessageBuilder.class);

	@Reference
	private AuditRouter _auditRouter;

}