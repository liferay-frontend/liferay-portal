/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;

/**
 * @author André de Oliveira
 */
public interface RankingIndexReader {

	public Ranking fetch(RankingIndexName rankingIndexName, String id);

	public Ranking fetchByQueryString(
		RankingIndexName rankingIndexName, String queryString);

	public boolean isExists(RankingIndexName rankingIndexName);

}