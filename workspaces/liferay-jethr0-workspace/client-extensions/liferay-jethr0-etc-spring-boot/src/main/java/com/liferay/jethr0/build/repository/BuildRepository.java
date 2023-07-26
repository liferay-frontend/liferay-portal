/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.build.repository;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.dalo.BuildDALO;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.dalo.ProjectToBuildsDALO;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildRepository extends BaseEntityRepository<Build> {

	public Build add(Project project, JSONObject jsonObject) {
		jsonObject.put("r_projectToBuilds_c_projectId", project.getId());

		Build build = add(jsonObject);

		build.setProject(project);

		project.addBuild(build);

		return build;
	}

	public Build add(
		Project project, String buildName, String jobName, Build.State state) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"buildName", buildName
		).put(
			"jobName", jobName
		).put(
			"state", state.getJSONObject()
		);

		return add(project, jsonObject);
	}

	public Set<Build> getAll(Project project) {
		Set<Build> projectBuilds = new HashSet<>();

		Set<Long> buildIds = _projectToBuildsDALO.getChildEntityIds(project);

		for (Build build : getAll()) {
			if (!buildIds.contains(build.getId())) {
				continue;
			}

			build.setProject(project);

			project.addBuild(build);

			projectBuilds.add(build);
		}

		return projectBuilds;
	}

	@Override
	public EntityDALO<Build> getEntityDALO() {
		return _buildDALO;
	}

	@Autowired
	private BuildDALO _buildDALO;

	@Autowired
	private ProjectToBuildsDALO _projectToBuildsDALO;

}