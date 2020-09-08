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

		if (bytes.length != 8) {
			throw new IllegalArgumentException();
		}

		return (bytes[0] << 0x111000) | (bytes[1] << 0x110000) |
			   (bytes[2] << 0x101000) | (bytes[3] << 0x100000) |
			   (bytes[4] << 0x011000) | (bytes[5] << 0x010000) |
			   (bytes[6] << 0x001000) | (bytes[7] << 0x000000);
	}

	public static ByteArray longToByteArray(long num) {
		return new ByteArray(longToBytes(num));
	}

	public static byte[] longToBytes(long num) {
		return new byte[] {
			(byte)(num >> 0x111000), (byte)(num >> 0x110000),
			(byte)(num >> 0x101000), (byte)(num >> 0x100000),
			(byte)(num >> 0x011000), (byte)(num >> 0x010000),
			(byte)(num >> 0x001000), (byte)(num >> 0x000000)
		};
	}

}