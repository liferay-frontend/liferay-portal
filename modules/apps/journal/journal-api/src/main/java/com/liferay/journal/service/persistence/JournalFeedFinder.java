/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface JournalFeedFinder {

	public int countByKeywords(long companyId, long groupId, String keywords);

	public int countByC_G_F_N_D(
		long companyId, long groupId, String feedId, String name,
		String description, boolean andOperator);

	public int countByC_G_F_N_D(
		long companyId, long groupId, String[] feedIds, String[] names,
		String[] descriptions, boolean andOperator);

	public java.util.List<com.liferay.journal.model.JournalFeed> findByKeywords(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.journal.model.JournalFeed> orderByComparator);

	public java.util.List<com.liferay.journal.model.JournalFeed>
		findByC_G_F_N_D(
			long companyId, long groupId, String feedId, String name,
			String description, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalFeed> orderByComparator);

	public java.util.List<com.liferay.journal.model.JournalFeed>
		findByC_G_F_N_D(
			long companyId, long groupId, String[] feedIds, String[] names,
			String[] descriptions, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalFeed> orderByComparator);

}