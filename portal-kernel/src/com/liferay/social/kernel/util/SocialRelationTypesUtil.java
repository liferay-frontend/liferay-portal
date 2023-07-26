/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.util;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class SocialRelationTypesUtil {

	public static List<Integer> getAllSocialRelationTypes() {
		return _socialRelationTypes.getAllSocialRelationTypes();
	}

	public static SocialRelationTypes getSocialRelationTypes() {
		return _socialRelationTypes;
	}

	public static String getTypeLabel(int type) {
		return _socialRelationTypes.getTypeLabel(type);
	}

	public static boolean isTypeBi(int type) {
		return _socialRelationTypes.isTypeBi(type);
	}

	public static boolean isTypeUni(int type) {
		return _socialRelationTypes.isTypeUni(type);
	}

	public void setSocialRelationTypes(
		SocialRelationTypes socialRelationTypes) {

		_socialRelationTypes = socialRelationTypes;
	}

	private static SocialRelationTypes _socialRelationTypes;

}