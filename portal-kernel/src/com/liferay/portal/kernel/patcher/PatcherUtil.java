/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.patcher;

import java.io.File;

import java.util.Properties;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class PatcherUtil {

	public static boolean applyPatch(File patchFile) {
		return _patcher.applyPatch(patchFile);
	}

	public static String[] getFixedIssues() {
		return _patcher.getFixedIssues();
	}

	public static String[] getInstalledPatches() {
		return _patcher.getInstalledPatches();
	}

	public static File getPatchDirectory() {
		return _patcher.getPatchDirectory();
	}

	public static Patcher getPatcher() {
		return _patcher;
	}

	public static int getPatchingToolVersion() {
		return _patcher.getPatchingToolVersion();
	}

	public static String getPatchingToolVersionDisplayName() {
		return _patcher.getPatchingToolVersionDisplayName();
	}

	public static String[] getPatchLevels() {
		return _patcher.getPatchLevels();
	}

	public static Properties getProperties() {
		return _patcher.getProperties();
	}

	public static String getSeparationId() {
		return _patcher.getSeparationId();
	}

	public static boolean isConfigured() {
		return _patcher.isConfigured();
	}

	public static boolean isSeparated() {
		return _patcher.isSeparated();
	}

	public void setPatcher(Patcher patcher) {
		_patcher = patcher;
	}

	private static Patcher _patcher;

}