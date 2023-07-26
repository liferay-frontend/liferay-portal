/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.events.listener;

import com.liferay.jenkins.plugin.events.publisher.JenkinsPublisher;
import com.liferay.jenkins.plugin.events.publisher.JenkinsPublisherUtil;

import hudson.Extension;

import hudson.model.Build;
import hudson.model.Executor;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;

/**
 * @author Michael Hashimoto
 */
@Extension
public class JenkinsBuildListener extends RunListener<Build> {

	@Override
	public void onCompleted(Build build, TaskListener taskListener) {
		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.BUILD_COMPLETED, build);

		Executor executor = build.getExecutor();

		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.COMPUTER_IDLE, executor.getOwner());
	}

	@Override
	public void onStarted(Build build, TaskListener taskListener) {
		Executor executor = build.getExecutor();

		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.COMPUTER_BUSY, executor.getOwner());

		JenkinsPublisherUtil.publish(
			JenkinsPublisher.EventTrigger.BUILD_STARTED, build);
	}

}