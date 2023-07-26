/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.image;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * The ImageMagick utility class.
 *
 * @author Alexander Chow
 */
public class ImageMagickUtil {

	/**
	 * Executes the <code>convert</code> command in ImageMagick.
	 *
	 * @param  arguments the command arguments being passed to <code>convert
	 *         </code>
	 * @return the converted command arguments
	 * @throws Exception if an unexpected error occurred while executing command
	 * @see    <a href="http://www.imagemagick.org/script/convert.php">Convert
	 *         documentation</a>
	 */
	public static Future<?> convert(List<String> arguments) throws Exception {
		return _imageMagick.convert(arguments);
	}

	public static void destroy() {
		_imageMagick.destroy();
	}

	/**
	 * Returns the global search path configured for ImageMagick.
	 *
	 * @return the global search path
	 * @throws Exception if an unexpected error occurred
	 */
	public static String getGlobalSearchPath() throws Exception {
		return _imageMagick.getGlobalSearchPath();
	}

	public static ImageMagick getImageMagick() {
		return _imageMagick;
	}

	/**
	 * Returns the cache and resource usage limits configured for ImageMagick.
	 *
	 * @return the cache and resource usage limits
	 * @throws Exception if an unexpected error occurred
	 */
	public static Properties getResourceLimitsProperties() throws Exception {
		return _imageMagick.getResourceLimitsProperties();
	}

	/**
	 * Executes the <code>identify</code> command in ImageMagick.
	 *
	 * @param  arguments the command arguments being passed to <code>identify
	 *         </code>
	 * @return the results of the <code>identify</code> call
	 * @throws Exception if an unexpected error occurred while executing command
	 * @see    <a href="http://www.imagemagick.org/script/identify.php">Identify
	 *         documentation</a>
	 */
	public static String[] identify(List<String> arguments) throws Exception {
		return _imageMagick.identify(arguments);
	}

	/**
	 * Returns <code>true</code> if ImageMagick is enabled.
	 *
	 * @return <code>true</code> if ImageMagick is enabled; <code>false</code>
	 *         otherwise
	 */
	public static boolean isEnabled() {
		return _imageMagick.isEnabled();
	}

	/**
	 * Resets the global search path and resource limits for ImageMagick.
	 */
	public static void reset() {
		_imageMagick.reset();
	}

	public static byte[] scale(
			byte[] bytes, String mimeType, int width, int height)
		throws Exception {

		return _imageMagick.scale(bytes, mimeType, width, height);
	}

	private static volatile ImageMagick _imageMagick =
		ServiceProxyFactory.newServiceTrackedInstance(
			ImageMagick.class, ImageMagickUtil.class, "_imageMagick", false);

}