/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.web.internal.client;

/**
 * @author Lourdes Fernández Besada
 * @author Roberto Díaz
 */
public interface AICreatorOpenAIClient {

	public String[] getGenerations(
			String apiKey, String prompt, String size, int numberOfImages)
		throws Exception;

	public void validateAPIKey(String apiKey) throws Exception;

}