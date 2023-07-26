/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormEvaluationResult {

	public DDMFormFieldEvaluationResult getDDMFormFieldEvaluationResult(
		String fieldName, String instanceId) {

		return _ddmFormFieldEvaluationResultsMap.get(
			StringBundler.concat(fieldName, "_INSTANCE_", instanceId));
	}

	@JSON(name = "fields")
	public List<DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResults() {

		return _ddmFormFieldEvaluationResults;
	}

	@JSON(include = false)
	public Map<String, DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResultsMap() {

		return _ddmFormFieldEvaluationResultsMap;
	}

	public Set<Integer> getDisabledPagesIndexes() {
		return _disabledPagesIndexes;
	}

	public void setDDMFormFieldEvaluationResults(
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults) {

		_ddmFormFieldEvaluationResults = ddmFormFieldEvaluationResults;
	}

	public void setDDMFormFieldEvaluationResultsMap(
		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultsMap) {

		_ddmFormFieldEvaluationResultsMap = ddmFormFieldEvaluationResultsMap;
	}

	public void setDisabledPagesIndexes(Set<Integer> disabledPagesIndexes) {
		_disabledPagesIndexes = disabledPagesIndexes;
	}

	private List<DDMFormFieldEvaluationResult> _ddmFormFieldEvaluationResults =
		new ArrayList<>();
	private Map<String, DDMFormFieldEvaluationResult>
		_ddmFormFieldEvaluationResultsMap = new HashMap<>();
	private Set<Integer> _disabledPagesIndexes = new HashSet<>();

}