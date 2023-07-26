/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.repository.util.ExternalRepositoryFactory;

import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinitionCatalogUtil {

	public static Iterable<RepositoryClassDefinition>
		getExternalRepositoryClassDefinitions() {

		return _repositoryClassDefinitionCatalog.
			getExternalRepositoryClassDefinitions();
	}

	public static Collection<String> getExternalRepositoryClassNames() {
		return _repositoryClassDefinitionCatalog.
			getExternalRepositoryClassNames();
	}

	public static RepositoryClassDefinition getRepositoryClassDefinition(
		String repositoryTypeKey) {

		return _repositoryClassDefinitionCatalog.getRepositoryClassDefinition(
			repositoryTypeKey);
	}

	public static RepositoryClassDefinitionCatalog
		getRepositoryClassDefinitionCatalog() {

		return _repositoryClassDefinitionCatalog;
	}

	public static void registerLegacyExternalRepositoryFactory(
		String className, ExternalRepositoryFactory externalRepositoryFactory,
		ResourceBundleLoader resourceBundleLoader) {

		_repositoryClassDefinitionCatalog.
			registerLegacyExternalRepositoryFactory(
				className, externalRepositoryFactory, resourceBundleLoader);
	}

	public static void unregisterLegacyExternalRepositoryFactory(
		String className) {

		_repositoryClassDefinitionCatalog.
			unregisterLegacyExternalRepositoryFactory(className);
	}

	public void setRepositoryClassDefinitionCatalog(
		RepositoryClassDefinitionCatalog repositoryClassDefinitionCatalog) {

		_repositoryClassDefinitionCatalog = repositoryClassDefinitionCatalog;
	}

	private static RepositoryClassDefinitionCatalog
		_repositoryClassDefinitionCatalog;

}