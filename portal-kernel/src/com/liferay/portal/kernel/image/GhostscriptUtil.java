/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.image;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Ivica Cardic
 */
public class GhostscriptUtil {

	public static Future<?> execute(List<String> arguments) throws Exception {
		return _ghostscript.execute(arguments);
	}

	public static Ghostscript getGhostscript() {
		return _ghostscript;
	}

	public static boolean isEnabled() {
		return _ghostscript.isEnabled();
	}

	public static void reset() {
		_ghostscript.reset();
	}

	public void setGhostscript(Ghostscript ghostscript) {
		_ghostscript = ghostscript;
	}

	private static Ghostscript _ghostscript;

}