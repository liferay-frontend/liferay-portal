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

import {config} from '../../config/index';
import InfoItemService from '../../services/InfoItemService';

export default function (
	editableValues,
	editableId,
	processorType,
	languageId,
	getFieldValue = InfoItemService.getInfoItemFieldValue
) {
	const editableValue = editableValues[processorType][editableId];

	let valuePromise;

	if (editableIsMappedToInfoItem(editableValue)) {
		valuePromise = getFieldValue({
			classNameId: editableValue.classNameId,
			classPK: editableValue.classPK,
			collectionFieldId: editableValue.collectionFieldId,
			fieldId: editableValue.fieldId,
			languageId,
		}).catch(() => {
			return selectEditableValueContent(editableValue, languageId);
		});
	}
	else {
		valuePromise = Promise.resolve(
			selectEditableValueContent(editableValue, languageId)
		);
	}

	let configPromise;

	const editableConfig = editableValue.config
		? editableValue.config[languageId] ||
		  editableValue.config[config.defaultLanguageId] ||
		  editableValue.config
		: editableValue.config;

	if (editableIsMappedToInfoItem(editableConfig)) {
		configPromise = getFieldValue({
			classNameId: editableConfig.classNameId,
			classPK: editableConfig.classPK,
			collectionFieldId: editableConfig.collectionFieldId,
			fieldId: editableConfig.fieldId,
			languageId,
		})
			.then((href) => {
				return {...editableConfig, href};
			})
			.catch(() => {
				return {...editableConfig};
			});
	}
	else {
		configPromise = Promise.resolve(editableConfig);
	}

	return Promise.all([valuePromise, configPromise]);
}

function selectEditableValueContent(editableValue, languageId) {
	let content = editableValue;

	if (content[languageId]) {
		content = content[languageId];
	}
	else if (content[config.defaultLanguageId]) {
		content = content[config.defaultLanguageId];
	}

	if (content == null || content.defaultValue) {
		content = editableValue.defaultValue;
	}

	return content;
}

function editableIsMappedToInfoItem(editableValue) {
	return (
		editableValue &&
		((editableValue.classNameId &&
			editableValue.classPK &&
			editableValue.fieldId) ||
			editableValue.collectionFieldId)
	);
}
