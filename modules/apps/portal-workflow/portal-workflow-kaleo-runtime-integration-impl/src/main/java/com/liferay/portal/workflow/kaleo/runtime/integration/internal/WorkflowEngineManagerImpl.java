/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.integration.internal;

import com.liferay.portal.kernel.workflow.WorkflowEngineManager;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = WorkflowEngineManager.class)
public class WorkflowEngineManagerImpl implements WorkflowEngineManager {

	@Override
	public String getKey() {
		return _KEY;
	}

	@Override
	public String getName() {
		return _NAME;
	}

	@Override
	public Map<String, Object> getOptionalAttributes() {
		return _optionalAttributes;
	}

	@Override
	public String getVersion() {
		return _VERSION;
	}

	@Override
	public boolean isDeployed() {
		return true;
	}

	private static final String _KEY = "liferay";

	private static final String _NAME = "Liferay Kaleo Workflow Engine";

	private static final String _VERSION = "6.0.0";

	private static final Map<String, Object> _optionalAttributes =
		Collections.emptyMap();

}