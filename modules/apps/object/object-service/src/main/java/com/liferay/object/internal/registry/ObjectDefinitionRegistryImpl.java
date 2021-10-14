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

package com.liferay.object.internal.registry;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.registry.ObjectDefinitionRegistry;
import org.osgi.service.component.annotations.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Javier de Arcos
 */
@Component(service = ObjectDefinitionRegistry.class, immediate = true)
public class ObjectDefinitionRegistryImpl implements ObjectDefinitionRegistry {


	@Override
	public boolean containsRESTContextPath(String restContextPath) {
		return _objectDefinitionsMap.containsKey(restContextPath);
	}

	@Override
	public ObjectDefinition getObjectDefinition(
		long companyId, String restContextPath) {

		Map<Long, ObjectDefinition> objectDefinitions =
			_objectDefinitionsMap.get(restContextPath);

		if (objectDefinitions != null) {
			return objectDefinitions.get(companyId);
		}

		return null;
	}

	@Override
	public void addObjectDefinition(
		ObjectDefinition objectDefinition) {

		Map<Long, ObjectDefinition> objectDefinitions =
			_objectDefinitionsMap.computeIfAbsent(
				objectDefinition.getRESTContextPath(), k -> new HashMap<>());

		objectDefinitions.put(
			objectDefinition.getCompanyId(), objectDefinition);
	}

	@Override
	public void removeObjectDefinition(
		ObjectDefinition objectDefinition) {

		Map<Long, ObjectDefinition> objectDefinitions =
			_objectDefinitionsMap.get(objectDefinition.getRESTContextPath());

		if (objectDefinitions != null) {
			objectDefinitions.remove(objectDefinition.getCompanyId());

			if (objectDefinitions.isEmpty()) {
				_objectDefinitionsMap.remove(
					objectDefinition.getRESTContextPath());
			}
		}
	}

	private final Map<String, Map<Long, ObjectDefinition>>
		_objectDefinitionsMap = new HashMap<>();
}
