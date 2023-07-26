/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.testsuite;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.project.Project;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTestSuite extends BaseEntity implements TestSuite {

	@Override
	public void addProject(Project project) {
		addProjects(Collections.singleton(project));
	}

	@Override
	public void addProjects(Set<Project> projects) {
		_projects.addAll(projects);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("name", getName());

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Set<Project> getProjects() {
		return _projects;
	}

	@Override
	public void removeProject(Project project) {
		_projects.remove(project);
	}

	@Override
	public void removeProjects(Set<Project> projects) {
		_projects.removeAll(projects);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	protected BaseTestSuite(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");
	}

	private String _name;
	private final Set<Project> _projects = new HashSet<>();

}