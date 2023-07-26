/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.tuning.rankings.web.internal.index.importer.SingleIndexToMultipleIndexImporter;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingIndexCreationBackgroundTaskExecutorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingIndexCreationBackgroundTaskExecutor =
			new RankingIndexCreationBackgroundTaskExecutor();

		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationBackgroundTaskExecutor,
			"_searchEngineInformation", _searchEngineInformation);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationBackgroundTaskExecutor,
			"_singleIndexToMultipleIndexImporter",
			_singleIndexToMultipleIndexImporter);
	}

	@Test
	public void testClone() {
		Assert.assertEquals(
			_rankingIndexCreationBackgroundTaskExecutor,
			_rankingIndexCreationBackgroundTaskExecutor.clone());
	}

	@Test
	public void testExecute() throws Exception {
		Mockito.doNothing(
		).when(
			_singleIndexToMultipleIndexImporter
		).importRankings();

		Assert.assertEquals(
			BackgroundTaskResult.SUCCESS,
			_rankingIndexCreationBackgroundTaskExecutor.execute(
				Mockito.mock(BackgroundTask.class)));
	}

	@Test
	public void testGetBackgroundTaskDisplay() {
		Assert.assertNull(
			_rankingIndexCreationBackgroundTaskExecutor.
				getBackgroundTaskDisplay(Mockito.mock(BackgroundTask.class)));
	}

	private RankingIndexCreationBackgroundTaskExecutor
		_rankingIndexCreationBackgroundTaskExecutor;
	private final SearchEngineInformation _searchEngineInformation =
		Mockito.mock(SearchEngineInformation.class);
	private final SingleIndexToMultipleIndexImporter
		_singleIndexToMultipleIndexImporter = Mockito.mock(
			SingleIndexToMultipleIndexImporter.class);

}