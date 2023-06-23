/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.license.validator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.license.License;
import com.liferay.portal.license.LicenseConstants;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 */
public class KeyValidator {

	public static License registerTrial(License license) {
		String licenseEntryType = license.getLicenseEntryType();

		if (!licenseEntryType.equals(LicenseConstants.TYPE_TRIAL) ||
			!validate(license)) {

			return license;
		}

		license.setLicenseEntryType(LicenseConstants.TYPE_DEVELOPER);

		license.setKey(_keyValidator._encrypt(license.getProperties()));

		return license;
	}

	public static boolean validate(License license) {
		String key = _keyValidator._encrypt(license.getProperties());

		for (String bannedKey : _BANNED_KEYS) {
			if (key.equals(bannedKey)) {
				return false;
			}
		}

		if (key.equals(license.getKey())) {
			return true;
		}

		return false;
	}

	private KeyValidator() {
	}

	private String _digest(String text, String algorithm) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

		messageDigest.update(text.getBytes());

		byte[] bytes = messageDigest.digest();

		StringBuilder sb = new StringBuilder(bytes.length << 1);

		for (byte b : bytes) {
			int byteInt = b & 0xff;

			sb.append(_HEX_CHARACTERS[byteInt >> 4]);
			sb.append(_HEX_CHARACTERS[byteInt & 0xf]);
		}

		return sb.toString();
	}

	private String _digestsToString(List<String> digests) {
		StringBundler sb = new StringBundler(digests.size());

		for (String digest : digests) {
			sb.append(digest);
		}

		return sb.toString();
	}

	private String _encrypt(Map<String, String> properties) {
		int licenseVersion = GetterUtil.getInteger(properties.get("version"));
		String productId = properties.get("productId");

		try {
			if (licenseVersion == 1) {
				throw new IllegalArgumentException(
					"Invalid version " + licenseVersion);
			}
			else if (licenseVersion >= 2) {
				return _encryptVersion2(productId, properties);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return StringPool.BLANK;
	}

	private String _encryptVersion2(
			String productId, Map<String, String> properties)
		throws Exception {

		List<String> keys = new ArrayList<>(properties.keySet());

		Collections.sort(keys);

		List<String> digests = new ArrayList<>(properties.size());

		for (int i = 0; i < keys.size(); i++) {
			String text = properties.get(keys.get(i));

			String digest = _digest(text, _getAlgorithm(productId, i));

			digests.add(digest);
		}

		digests = _shortenDigests(digests);

		for (int i = 0; i < digests.size(); i++) {
			String digest = digests.get(i);

			digest = _digest(digest, _getAlgorithm(productId, i));

			digests.set(i, digest);
		}

		if (_DXP &&
			(Validator.isNull(productId) ||
			 productId.equals(LicenseConstants.PRODUCT_ID_PORTAL))) {

			return _interweaveDigest(digests);
		}

		return _digestsToString(digests);
	}

	private String _getAlgorithm(String productId, int i) {
		if (_DXP &&
			(Validator.isNull(productId) ||
			 productId.equals(LicenseConstants.PRODUCT_ID_PORTAL))) {

			return _ALGORITHMS[i % _ALGORITHMS.length];
		}

		return _ALGORITHMS[2];
	}

	private String _interweaveDigest(List<String> digests) {
		int size = digests.size();

		int finalLength = 0;
		int shortestLength = Integer.MAX_VALUE;

		for (String digest : digests) {
			int length = digest.length();

			finalLength += length;

			if (length < shortestLength) {
				shortestLength = length;
			}
		}

		StringBuilder sb = new StringBuilder(finalLength);

		for (int i = 0; i < shortestLength; i++) {
			for (int j = 0; j < size; j++) {
				String digest = digests.get(j);

				sb.append(digest.charAt(i));
			}
		}

		for (String digest : digests) {
			if (digest.length() > shortestLength) {
				sb.append(digest.substring(shortestLength));
			}
		}

		return sb.toString();
	}

	private List<String> _shortenDigests(List<String> digests)
		throws Exception {

		int size = digests.size();

		int groupSize = size / 4;

		if ((groupSize * 4) < size) {
			groupSize++;
		}

		List<String> shortenedDigests = new ArrayList<>(4);

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < size; i++) {
			String digest = digests.get(i);

			if ((i != 0) && ((i % groupSize) == 0)) {
				shortenedDigests.add(sb.toString());

				sb.setLength(0);
			}

			sb.append(digest);
		}

		if (shortenedDigests.size() < 4) {
			shortenedDigests.add(sb.toString());
		}

		return shortenedDigests;
	}

	private static final String[] _ALGORITHMS = {
		"MD5", "SHA-1", "SHA-256", "SHA-512"
	};

	private static final String[] _BANNED_KEYS = {
		StringBundler.concat(
			"4a4beb2b97c151cff83cbca7096325086817360a7b8c912b66e1d1dea172033a",
			"8c5934cbbacbf7b443496cc119a6a482fc6225d28bcbcb2384f52862e6fd35e4",
			"9a2625f1458d24a1f62e71235dc16b9de5a971e638af32a9784e566f33dd9023",
			"4d89e1dde83e8a4a100a70d999b2bb7fa77eeb34fd1be9cdf3645f9478b14c2c",
			"d6b8f955"),
		StringBundler.concat(
			"54538af2d017334262c28dab47f3ce9103f7aa67417b056fead163cffb140ee3",
			"47c0cb02fc21ac60b32a2db70d3c4dc9977330a750dfd0849d80c5a7450cb6ba",
			"a0a23907084a5e233740003a69ff5d6a4d3d57fe481808e91745f48c3ea03e96",
			"94a40e36ae053bd48aaf7c466a46204dede8728f0b1d1349f3471ad61157f205",
			"d9296e4a"),
		StringBundler.concat(
			"5bc38e22d0f733d266128c8b4fb3ca710297ac974a3b00ffe881655ffa2403e3",
			"4f00cb82fc11a070b7ba28a704bc49f99d233c0756bfdb949be0c317459cb54a",
			"61ebbf9be6df549e0e4ab339b9c2ec04753fe286481808e91745f48c3ea03e96",
			"94a40e36ae053bd48aaf7c466a46204dede8728f0b1d1349f3471ad61157f205",
			"d9296e4a")
	};

	private static final boolean _DXP;

	private static final char[] _HEX_CHARACTERS = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
		'e', 'f'
	};

	private static final Log _log = LogFactoryUtil.getLog(KeyValidator.class);

	private static final KeyValidator _keyValidator = new KeyValidator();

	static {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		boolean dxp = false;

		try {
			classLoader.loadClass(
				"com.liferay.portal.ee.license.LCSLicenseManager");

			dxp = true;
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(reflectiveOperationException);
			}
		}

		_DXP = dxp;
	}

}