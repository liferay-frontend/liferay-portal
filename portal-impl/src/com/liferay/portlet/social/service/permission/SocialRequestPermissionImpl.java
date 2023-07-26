/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.social.kernel.model.SocialRequest;
import com.liferay.social.kernel.service.SocialRequestLocalServiceUtil;
import com.liferay.social.kernel.service.permission.SocialRequestPermission;

/**
 * @author Shinn Lok
 */
public class SocialRequestPermissionImpl implements SocialRequestPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long requestId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, requestId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SocialRequest.class.getName(), requestId,
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long requestId,
			String actionId)
		throws PortalException {

		if (permissionChecker.isOmniadmin()) {
			return true;
		}

		if (actionId.equals(ActionKeys.UPDATE)) {
			SocialRequest request =
				SocialRequestLocalServiceUtil.getSocialRequest(requestId);

			if (permissionChecker.getUserId() == request.getReceiverUserId()) {
				return true;
			}
		}

		return false;
	}

}