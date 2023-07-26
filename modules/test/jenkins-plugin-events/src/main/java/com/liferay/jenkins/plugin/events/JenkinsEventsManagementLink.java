/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.events;

import com.liferay.jenkins.plugin.events.publisher.JenkinsPublisher;
import com.liferay.jenkins.plugin.events.publisher.JenkinsPublisherUtil;

import hudson.Extension;

import hudson.model.ManagementLink;

import hudson.security.Permission;

import java.io.IOException;

import javax.servlet.ServletException;

import jenkins.model.Jenkins;

import org.json.JSONArray;
import org.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * @author Michael Hashimoto
 */
@Extension
public class JenkinsEventsManagementLink extends ManagementLink {

	public JenkinsEventsManagementLink() {
		jenkinsEventsDescriptor = new JenkinsEventsDescriptor();

		jenkinsEventsDescriptor.load();
	}

	public void doJenkinsEventsConfiguration(
			StaplerRequest staplerRequest, StaplerResponse staplerResponse)
		throws IOException, ServletException {

		jenkinsEventsDescriptor.jenkinsPublishers.clear();

		JSONObject jsonObject = new JSONObject(
			staplerRequest.getParameter("json"));

		Object jenkinsPublishersObject = jsonObject.opt("jenkinsPublishers");

		if (jenkinsPublishersObject instanceof JSONArray) {
			JSONArray jenkinsPublishersJSONArray =
				(JSONArray)jenkinsPublishersObject;

			for (int i = 0; i < jenkinsPublishersJSONArray.length(); i++) {
				JSONObject jenkinsPublisherJSONObject =
					jenkinsPublishersJSONArray.optJSONObject(i);

				if (jenkinsPublisherJSONObject == null) {
					continue;
				}

				jenkinsEventsDescriptor.jenkinsPublishers.add(
					new JenkinsPublisher(jenkinsPublisherJSONObject));
			}
		}
		else if (jenkinsPublishersObject instanceof JSONObject) {
			jenkinsEventsDescriptor.jenkinsPublishers.add(
				new JenkinsPublisher((JSONObject)jenkinsPublishersObject));
		}
		else {
			jenkinsEventsDescriptor.jenkinsPublishers.clear();
		}

		jenkinsEventsDescriptor.save();

		JenkinsPublisherUtil.setJenkinsEventsDescriptor(
			jenkinsEventsDescriptor);

		Jenkins jenkins = Jenkins.getInstanceOrNull();

		if (jenkins != null) {
			staplerResponse.sendRedirect(jenkins.getRootUrl() + "/manage");
		}
	}

	@Override
	public String getDescription() {
		return "Configure Jenkins event listeners and publishers";
	}

	@Override
	public String getDisplayName() {
		return "Jenkins Events";
	}

	@Override
	public String getIconFileName() {
		return "clipboard.png";
	}

	@Override
	public Permission getRequiredPermission() {
		return Jenkins.ADMINISTER;
	}

	@Override
	public String getUrlName() {
		return "jenkins-events-configuration";
	}

	public JenkinsEventsDescriptor jenkinsEventsDescriptor;

}