/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.comparator;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;

import java.util.Comparator;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseProjectComparator
	extends BaseEntity implements Comparator<Project>, ProjectComparator {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		ProjectComparator.Type type = getType();

		jsonObject.put(
			"position", getPosition()
		).put(
			"type", type.getJSONObject()
		).put(
			"value", getValue()
		);

		ProjectPrioritizer projectPrioritizer = getProjectPrioritizer();

		if (projectPrioritizer != null) {
			jsonObject.put(
				"r_projectPrioritizerToProjectComparators_c_" +
					"projectPrioritizerId",
				projectPrioritizer.getId());
		}

		return jsonObject;
	}

	@Override
	public int getPosition() {
		return _position;
	}

	@Override
	public ProjectPrioritizer getProjectPrioritizer() {
		return _projectPrioritizer;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public void setPosition(int position) {
		_position = position;
	}

	@Override
	public void setProjectPrioritizer(ProjectPrioritizer projectPrioritizer) {
		_projectPrioritizer = projectPrioritizer;
	}

	@Override
	public void setValue(String value) {
		_value = value;
	}

	protected BaseProjectComparator(JSONObject jsonObject) {
		super(jsonObject);

		_position = jsonObject.getInt("position");
		_type = Type.get(jsonObject.getJSONObject("type"));
		_value = jsonObject.optString("value");
	}

	protected BaseProjectComparator(
		ProjectPrioritizer projectPrioritizer, JSONObject jsonObject) {

		super(jsonObject);

		_projectPrioritizer = projectPrioritizer;

		_projectPrioritizer.addProjectComparator(this);

		_position = jsonObject.getInt("position");
		_type = Type.get(jsonObject.getJSONObject("type"));
		_value = jsonObject.optString("value");
	}

	private int _position;
	private ProjectPrioritizer _projectPrioritizer;
	private final Type _type;
	private String _value;

}