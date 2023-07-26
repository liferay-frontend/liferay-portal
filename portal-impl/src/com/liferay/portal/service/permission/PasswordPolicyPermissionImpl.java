/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PasswordPolicyPermission;

/**
 * @author Brian Wing Shun Chan
 */
public class PasswordPolicyPermissionImpl implements PasswordPolicyPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long passwordPolicyId,
			String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, passwordPolicyId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, PasswordPolicy.class.getName(),
				passwordPolicyId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long passwordPolicyId,
		String actionId) {

		return permissionChecker.hasPermission(
			null, PasswordPolicy.class.getName(), passwordPolicyId, actionId);
	}

}