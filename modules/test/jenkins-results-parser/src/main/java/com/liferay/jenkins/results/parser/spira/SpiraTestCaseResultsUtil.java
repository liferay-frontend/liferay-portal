/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser.spira;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.NotificationUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Yi-Chen Tsai
 */
public class SpiraTestCaseResultsUtil {

	public static void sendUpstreamTestSuiteSlackNotification(
		String branchName, String channel, String testSuite) {

		String message = _getLatestUpstreamComparisonMessage(
			branchName, testSuite);

		String subject = JenkinsResultsParserUtil.combine(
			"Inconsistent Upstream Test Suite Test Report (", branchName,
			") - ", testSuite);

		NotificationUtil.sendSlackNotification(message, channel, subject);
	}

	private static String _getLatestUpstreamComparisonMessage(
		String branchName, String testSuite) {

		StringBuilder upstreamComparisonMessageStringBuilder =
			new StringBuilder();

		String comparisonUpstreamSuites = JenkinsResultsParserUtil.getProperty(
			_buildProperties,
			"spira.test.case.results.comparison.upstream.suites");

		if (comparisonUpstreamSuites == null) {
			throw new RuntimeException(
				"The Property " +
					"'spira.test.case.results.comparison.upstream.suites' is " +
						"undefined.");
		}

		Map<String, Map<Integer, SpiraTestCaseRun>> comparisonTestSuiteMaps =
			new HashMap<>();

		for (String comparisonUpstreamSuite :
				comparisonUpstreamSuites.split(",")) {

			comparisonTestSuiteMaps.put(
				comparisonUpstreamSuite,
				_getSpiraTestCaseRunMapFromList(
					_getLatestUpstreamSpiraTestCaseRuns(
						branchName, comparisonUpstreamSuite)));
		}

		int inconsistentTestCount = 0;

		Map<Integer, SpiraTestCaseRun> spiraTestCaseRuns =
			_getSpiraTestCaseRunMapFromList(
				_getLatestUpstreamSpiraTestCaseRuns(branchName, testSuite));

		for (Map.Entry<Integer, SpiraTestCaseRun> entry :
				spiraTestCaseRuns.entrySet()) {

			boolean inconsistent = false;

			StringBuilder spiraTestCaseRunMessageStringBuilder =
				new StringBuilder();

			SpiraTestCaseRun spiraTestCaseRun = entry.getValue();

			spiraTestCaseRunMessageStringBuilder.append(
				spiraTestCaseRun.getName());

			spiraTestCaseRunMessageStringBuilder.append("\n");
			spiraTestCaseRunMessageStringBuilder.append(testSuite);
			spiraTestCaseRunMessageStringBuilder.append("\n<");
			spiraTestCaseRunMessageStringBuilder.append(
				spiraTestCaseRun.getURL());
			spiraTestCaseRunMessageStringBuilder.append("|");

			int executionStatusId = (int)spiraTestCaseRun.getProperty(
				"ExecutionStatusId");

			spiraTestCaseRunMessageStringBuilder.append(
				SpiraTestCaseRun.Status.getStatusName(executionStatusId));

			spiraTestCaseRunMessageStringBuilder.append(">\n");

			int testCaseID = entry.getKey();

			for (Map.Entry<String, Map<Integer, SpiraTestCaseRun>>
					comparisonEntry : comparisonTestSuiteMaps.entrySet()) {

				Map<Integer, SpiraTestCaseRun> comparisonSpiraTestCaseRuns =
					comparisonEntry.getValue();

				if (!comparisonSpiraTestCaseRuns.containsKey(testCaseID)) {
					inconsistent = false;

					break;
				}

				spiraTestCaseRunMessageStringBuilder.append(
					comparisonEntry.getKey());
				spiraTestCaseRunMessageStringBuilder.append("\n<");

				SpiraTestCaseRun comparisonSpiraTestCaseRun =
					comparisonSpiraTestCaseRuns.get(testCaseID);

				spiraTestCaseRunMessageStringBuilder.append(
					comparisonSpiraTestCaseRun.getURL());

				spiraTestCaseRunMessageStringBuilder.append("|");

				int comparisonExecutionStatusId =
					(int)comparisonSpiraTestCaseRun.getProperty(
						"ExecutionStatusId");

				if (comparisonExecutionStatusId != executionStatusId) {
					inconsistent = true;
				}

				spiraTestCaseRunMessageStringBuilder.append(
					SpiraTestCaseRun.Status.getStatusName(
						comparisonExecutionStatusId));
				spiraTestCaseRunMessageStringBuilder.append(">\n");
			}

			if (inconsistent) {
				upstreamComparisonMessageStringBuilder.append(
					spiraTestCaseRunMessageStringBuilder.toString());
				upstreamComparisonMessageStringBuilder.append("\n");

				inconsistentTestCount++;
			}
		}

		if (inconsistentTestCount == 0) {
			upstreamComparisonMessageStringBuilder.append(
				"There are no inconsistent test results in the latest ");
			upstreamComparisonMessageStringBuilder.append(branchName);
			upstreamComparisonMessageStringBuilder.append(
				" upstream test run for the test suite '");
			upstreamComparisonMessageStringBuilder.append(testSuite);
			upstreamComparisonMessageStringBuilder.append(
				"' compared to the following upstream test suite(s):\n");

			for (String comparisonUpstreamSuite :
					comparisonUpstreamSuites.split(",")) {

				upstreamComparisonMessageStringBuilder.append(
					comparisonUpstreamSuite);
				upstreamComparisonMessageStringBuilder.append("\n");
			}

			return upstreamComparisonMessageStringBuilder.toString();
		}

		return JenkinsResultsParserUtil.combine(
			"There are ", String.valueOf(inconsistentTestCount),
			" tests with inconsistent test results.\n",
			upstreamComparisonMessageStringBuilder.toString());
	}

	private static List<SpiraTestCaseRun> _getLatestUpstreamSpiraTestCaseRuns(
		String branchName, String testSuite) {

		SpiraProject spiraProject = SpiraProject.getSpiraProjectByID(
			SpiraProject.getID("dxp"));

		int testSuiteReleaseID = Integer.parseInt(
			JenkinsResultsParserUtil.getProperty(
				_buildProperties,
				"spira.release.id[test-portal-testsuite-upstream(" +
					branchName + ")][" + testSuite + "]"));

		SpiraRelease spiraRelease = spiraProject.getSpiraReleaseByID(
			testSuiteReleaseID);

		SearchQuery.SearchParameter searchParameter =
			new SearchQuery.SearchParameter(
				SpiraRelease.getKeyID(SpiraRelease.class), testSuiteReleaseID);

		List<SpiraReleaseBuild> spiraReleaseBuilds =
			SpiraReleaseBuild.getSpiraReleaseBuilds(
				spiraProject, spiraRelease, searchParameter);

		if (spiraReleaseBuilds.isEmpty()) {
			return new ArrayList<>();
		}

		SpiraReleaseBuild spiraReleaseBuild = spiraReleaseBuilds.get(0);

		return spiraReleaseBuild.getSpiraTestCaseRuns(50000);
	}

	private static Map<Integer, SpiraTestCaseRun>
		_getSpiraTestCaseRunMapFromList(
			List<SpiraTestCaseRun> spiraTestCaseRuns) {

		Map<Integer, SpiraTestCaseRun> spiraTestCaseRunMap = new HashMap<>();

		for (SpiraTestCaseRun spiraTestCaseRun : spiraTestCaseRuns) {
			spiraTestCaseRunMap.put(
				(int)spiraTestCaseRun.getProperty("TestCaseId"),
				spiraTestCaseRun);
		}

		return spiraTestCaseRunMap;
	}

	private static final Properties _buildProperties;

	static {
		try {
			_buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build.properties", ioException);
		}
	}

}