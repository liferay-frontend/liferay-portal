/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.util;

import com.liferay.petra.string.StringPool;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.kernel.util.SocialRelationTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class SocialRelationTypesImpl implements SocialRelationTypes {

	@Override
	public List<Integer> getAllSocialRelationTypes() {
		return _allSocialRelationTypes;
	}

	@Override
	public String getTypeLabel(int type) {
		if (type == SocialRelationConstants.TYPE_BI_CONNECTION) {
			return "connection";
		}
		else if (type == SocialRelationConstants.TYPE_BI_COWORKER) {
			return "coworker";
		}
		else if (type == SocialRelationConstants.TYPE_BI_FRIEND) {
			return "friend";
		}
		else if (type == SocialRelationConstants.TYPE_BI_ROMANTIC_PARTNER) {
			return "romantic-partner";
		}
		else if (type == SocialRelationConstants.TYPE_BI_SIBLING) {
			return "sibling";
		}
		else if (type == SocialRelationConstants.TYPE_BI_SPOUSE) {
			return "spouse";
		}
		else if (type == SocialRelationConstants.TYPE_UNI_CHILD) {
			return "child";
		}
		else if (type == SocialRelationConstants.TYPE_UNI_ENEMY) {
			return "enemy";
		}
		else if (type == SocialRelationConstants.TYPE_UNI_FOLLOWER) {
			return "follower";
		}
		else if (type == SocialRelationConstants.TYPE_UNI_PARENT) {
			return "parent";
		}
		else if (type == SocialRelationConstants.TYPE_UNI_SUBORDINATE) {
			return "subordinate";
		}
		else if (type == SocialRelationConstants.TYPE_UNI_SUPERVISOR) {
			return "supervisor";
		}

		return StringPool.BLANK;
	}

	@Override
	public boolean isTypeBi(int type) {
		return !isTypeUni(type);
	}

	@Override
	public boolean isTypeUni(int type) {
		if ((type == SocialRelationConstants.TYPE_UNI_CHILD) ||
			(type == SocialRelationConstants.TYPE_UNI_ENEMY) ||
			(type == SocialRelationConstants.TYPE_UNI_FOLLOWER) ||
			(type == SocialRelationConstants.TYPE_UNI_PARENT) ||
			(type == SocialRelationConstants.TYPE_UNI_SUBORDINATE) ||
			(type == SocialRelationConstants.TYPE_UNI_SUPERVISOR)) {

			return true;
		}

		return false;
	}

	private static final List<Integer> _allSocialRelationTypes = Arrays.asList(
		SocialRelationConstants.TYPE_BI_CONNECTION,
		SocialRelationConstants.TYPE_BI_COWORKER,
		SocialRelationConstants.TYPE_BI_FRIEND,
		SocialRelationConstants.TYPE_BI_ROMANTIC_PARTNER,
		SocialRelationConstants.TYPE_BI_SIBLING,
		SocialRelationConstants.TYPE_BI_SPOUSE,
		SocialRelationConstants.TYPE_UNI_CHILD,
		SocialRelationConstants.TYPE_UNI_ENEMY,
		SocialRelationConstants.TYPE_UNI_FOLLOWER,
		SocialRelationConstants.TYPE_UNI_PARENT,
		SocialRelationConstants.TYPE_UNI_SUBORDINATE,
		SocialRelationConstants.TYPE_UNI_SUPERVISOR);

}