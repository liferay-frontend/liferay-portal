/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.patcher;

import java.io.File;

import java.util.Properties;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 * @author Zoltán Takács
 */
@ProviderType
public interface Patcher {

	public static final String PATCHER_PROPERTIES = "patcher.properties";

	public static final String PATCHER_SERVICE_PROPERTIES =
		"patcher-service.properties";

	public static final String PROPERTY_FIXED_ISSUES = "fixed.issues";

	public static final String PROPERTY_INSTALLED_PATCHES = "installed.patches";

	public static final String PROPERTY_PATCH_DIRECTORY = "patch.directory";

	public static final String PROPERTY_PATCH_LEVELS = "patch.levels";

	public static final String PROPERTY_PATCHING_TOOL_VERSION =
		"patching.tool.version";

	public static final String PROPERTY_PATCHING_TOOL_VERSION_DISPLAY_NAME =
		"patching.tool.version.display.name";

	public static final String PROPERTY_SEPARATED = "separated";

	public static final String PROPERTY_SEPARATION_ID = "separation.id";

	public boolean applyPatch(File patchFile);

	public String[] getFixedIssues();

	public String[] getInstalledPatches();

	public File getPatchDirectory();

	public int getPatchingToolVersion();

	public String getPatchingToolVersionDisplayName();

	public String[] getPatchLevels();

	public Properties getProperties();

	public String getSeparationId();

	public boolean isConfigured();

	public boolean isSeparated();

}