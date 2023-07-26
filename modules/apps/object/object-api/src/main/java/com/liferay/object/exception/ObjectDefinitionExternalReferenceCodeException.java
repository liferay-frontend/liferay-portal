/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Selton Guedes
 */
public class ObjectDefinitionExternalReferenceCodeException
	extends PortalException {

	public static class
		ForbiddenUnmodifiableSystemObjectDefinitionExternalReferenceCode
			extends ObjectDefinitionExternalReferenceCodeException {

		public ForbiddenUnmodifiableSystemObjectDefinitionExternalReferenceCode(
			String externalReferenceCode) {

			super(
				StringBundler.concat(
					"Forbidden unmodifiable system object definition external ",
					"reference code ", externalReferenceCode));
		}

	}

	public static class MustNotStartWithPrefix
		extends ObjectDefinitionExternalReferenceCodeException {

		public MustNotStartWithPrefix() {
			super("The prefix L_ is reserved for Liferay");
		}

	}

	private ObjectDefinitionExternalReferenceCodeException(String msg) {
		super(msg);
	}

}