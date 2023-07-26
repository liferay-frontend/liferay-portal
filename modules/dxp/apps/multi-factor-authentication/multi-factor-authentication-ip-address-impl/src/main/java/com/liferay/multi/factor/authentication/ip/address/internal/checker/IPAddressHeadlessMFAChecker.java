/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.ip.address.internal.checker;

import com.liferay.multi.factor.authentication.ip.address.internal.audit.MFAIPAddressAuditMessageBuilder;
import com.liferay.multi.factor.authentication.ip.address.internal.configuration.MFAIPAddressConfiguration;
import com.liferay.multi.factor.authentication.spi.checker.headless.HeadlessMFAChecker;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(
	configurationPid = "com.liferay.multi.factor.authentication.ip.address.internal.configuration.MFAIPAddressConfiguration.scoped",
	service = {}
)
public class IPAddressHeadlessMFAChecker implements HeadlessMFAChecker {

	@Override
	public boolean verifyHeadlessRequest(
		HttpServletRequest httpServletRequest, long userId) {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested IP address verification for nonexistent user " +
						userId);
			}

			MFAIPAddressAuditMessageBuilder mfaIPAddressAuditMessageBuilder =
				_mfaIPAddressAuditMessageBuilderSnapshot.get();

			if (mfaIPAddressAuditMessageBuilder != null) {
				mfaIPAddressAuditMessageBuilder.routeAuditMessage(
					mfaIPAddressAuditMessageBuilder.
						buildNonexistentUserVerificationFailureAuditMessage(
							CompanyThreadLocal.getCompanyId(), userId,
							_getClassName()));
			}

			return false;
		}

		if (AccessControlUtil.isAccessAllowed(
				httpServletRequest, _allowedIpAddressesAndNetmasks)) {

			MFAIPAddressAuditMessageBuilder mfaIPAddressAuditMessageBuilder =
				_mfaIPAddressAuditMessageBuilderSnapshot.get();

			if (mfaIPAddressAuditMessageBuilder != null) {
				mfaIPAddressAuditMessageBuilder.routeAuditMessage(
					mfaIPAddressAuditMessageBuilder.
						buildVerificationSuccessAuditMessage(
							user, _getClassName()));
			}

			return true;
		}

		MFAIPAddressAuditMessageBuilder mfaIPAddressAuditMessageBuilder =
			_mfaIPAddressAuditMessageBuilderSnapshot.get();

		if (mfaIPAddressAuditMessageBuilder != null) {
			mfaIPAddressAuditMessageBuilder.routeAuditMessage(
				mfaIPAddressAuditMessageBuilder.
					buildVerificationFailureAuditMessage(
						user, _getClassName(), "IP is not allowed"));
		}

		return false;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		MFAIPAddressConfiguration mfaIPAddressConfiguration =
			ConfigurableUtil.createConfigurable(
				MFAIPAddressConfiguration.class, properties);

		if (!mfaIPAddressConfiguration.enabled()) {
			return;
		}

		_allowedIpAddressesAndNetmasks = new HashSet<>(
			Arrays.asList(
				mfaIPAddressConfiguration.allowedIPAddressAndNetMask()));

		_serviceRegistration = bundleContext.registerService(
			HeadlessMFAChecker.class, this,
			new HashMapDictionary<>(properties));
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration == null) {
			return;
		}

		_serviceRegistration.unregister();
	}

	private String _getClassName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IPAddressHeadlessMFAChecker.class);

	private static final Snapshot<MFAIPAddressAuditMessageBuilder>
		_mfaIPAddressAuditMessageBuilderSnapshot = new Snapshot<>(
			IPAddressHeadlessMFAChecker.class,
			MFAIPAddressAuditMessageBuilder.class);

	private Set<String> _allowedIpAddressesAndNetmasks;
	private ServiceRegistration<HeadlessMFAChecker> _serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

}