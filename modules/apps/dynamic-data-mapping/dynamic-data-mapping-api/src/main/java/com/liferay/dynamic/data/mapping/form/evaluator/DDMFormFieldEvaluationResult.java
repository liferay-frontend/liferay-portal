/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author     Marcellus Tavares
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
public class DDMFormFieldEvaluationResult {

	public DDMFormFieldEvaluationResult(String name, String instanceId) {
		_name = name;
		_instanceId = instanceId;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMFormFieldEvaluationResult)) {
			return false;
		}

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			(DDMFormFieldEvaluationResult)object;

		if (Objects.equals(
				_instanceId, ddmFormFieldEvaluationResult._instanceId) &&
			Objects.equals(_name, ddmFormFieldEvaluationResult._name)) {

			return true;
		}

		return false;
	}

	public String getErrorMessage() {
		return MapUtil.getString(_properties, "errorMessage");
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public String getName() {
		return _name;
	}

	public <T> T getProperty(String name) {
		return (T)_properties.get(name);
	}

	public <T> T getValue() {
		return (T)_properties.get("value");
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _name);

		return HashUtil.hash(hash, _instanceId);
	}

	public boolean isReadOnly() {
		return MapUtil.getBoolean(_properties, "readOnly");
	}

	public boolean isRequired() {
		return MapUtil.getBoolean(_properties, "required");
	}

	public boolean isValid() {
		return MapUtil.getBoolean(_properties, "valid", true);
	}

	public boolean isVisible() {
		return MapUtil.getBoolean(_properties, "visible", true);
	}

	public void setErrorMessage(String errorMessage) {
		_properties.put("errorMessage", errorMessage);
	}

	public void setProperty(String name, Object value) {
		_properties.put(name, value);
	}

	public void setReadOnly(boolean readOnly) {
		_properties.put("readOnly", readOnly);
	}

	public void setRequired(boolean required) {
		_properties.put("required", required);
	}

	public void setValid(boolean valid) {
		_properties.put("valid", valid);
	}

	public void setValue(Object value) {
		_properties.put("value", value);
	}

	public void setVisible(boolean visible) {
		_properties.put("visible", visible);
	}

	private final String _instanceId;
	private final String _name;
	private final Map<String, Object> _properties = new HashMap<>();

}