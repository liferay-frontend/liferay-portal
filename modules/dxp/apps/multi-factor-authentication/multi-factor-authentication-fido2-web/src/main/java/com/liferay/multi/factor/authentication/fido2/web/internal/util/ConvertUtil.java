/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.fido2.web.internal.util;

import com.yubico.webauthn.data.ByteArray;

/**
 * @author Arthur Chan
 */
public class ConvertUtil {

	public static long byteArrayToLong(ByteArray bytes)
		throws IllegalArgumentException {

		return bytesToLong(bytes.getBytes());
	}

	public static long bytesToLong(byte[] bytes)
		throws IllegalArgumentException {

		if (bytes.length != Long.BYTES) {
			throw new IllegalArgumentException();
		}

		long result = 0;

		for (int i = 0; i < Long.BYTES; i++) {
			result <<= Byte.SIZE;
			result |= bytes[i] & 0xFF;
		}

		return result;
	}

	public static ByteArray longToByteArray(long num) {
		return new ByteArray(longToBytes(num));
	}

	public static byte[] longToBytes(long num) {
		byte[] result = new byte[Long.BYTES];

		for (int i = Long.BYTES - 1; i >= 0; i--) {
			result[i] = (byte)(num & 0xFF);
			num >>= Byte.SIZE;
		}

		return result;
	}

}