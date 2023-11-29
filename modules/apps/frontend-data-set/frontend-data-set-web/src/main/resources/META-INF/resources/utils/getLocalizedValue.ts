/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface ILocalizedItemDetails {
	rootPropertyName: string;
	value: string;
	valuePath: Array<string>;
}

const languageId = Liferay.ThemeDisplay.getLanguageId();
const BCP47LanguageId = Liferay.ThemeDisplay.getBCP47LanguageId();
const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

function getLanguageKey(data: any): string {
	let languageKey = '';

	if (data[languageId]) {
		languageKey = languageId as string;
	}
	else if (data[defaultLanguageId]) {
		languageKey = defaultLanguageId as string;
	}
	else if (data['en_US']) {
		languageKey = 'en_US';
	}
	else {
		languageKey = Object.keys(data)[0];
	}

	return languageKey;
}

function resolveLocalizedObjectFields({
	fieldName,
	navigatedValue,
	valuePath,
}: {
	fieldName: string;
	navigatedValue: any;
	valuePath: Array<string>;
}) {
	const i18nFieldName = `${fieldName}_i18n`;

	if (!navigatedValue) {
		valuePath.push(fieldName);
	}
	else if (
		typeof fieldName === 'string' &&
		navigatedValue[i18nFieldName] &&
		Object.keys(Liferay.Language.available).includes(
			Object.keys(navigatedValue[i18nFieldName])[0]
		)
	) {
		navigatedValue =
			navigatedValue[i18nFieldName][
				getLanguageKey(navigatedValue[i18nFieldName])
			];
		valuePath.push(fieldName);
	}
	else if (
		typeof fieldName === 'string' &&
		navigatedValue[fieldName] &&
		Object.keys(Liferay.Language.available).includes(
			Object.keys(navigatedValue[fieldName])[0]
		)
	) {
		navigatedValue =
			navigatedValue[fieldName][
				getLanguageKey(navigatedValue[fieldName])
			];
		valuePath.push(fieldName);
	}
	else {
		navigatedValue = navigatedValue[fieldName];
		valuePath.push(fieldName);
	}

	return {
		navigatedValue,
		valuePath,
	};
}

export function getLocalizedValue(
	item: any,
	fieldName: string | Array<string>
): ILocalizedItemDetails | null {
	if (!fieldName) {
		return null;
	}

	const rootPropertyName =
		typeof fieldName === 'string' ? fieldName : fieldName[0];
	let navigatedValue = item;
	const valuePath: Array<string> = [];

	if (Array.isArray(fieldName)) {
		fieldName.forEach((property) => {
			let formattedProperty = property;

			if (property === 'LANG') {
				if (navigatedValue[languageId]) {
					formattedProperty = languageId;
				}
				else if (navigatedValue[BCP47LanguageId]) {
					formattedProperty = BCP47LanguageId;
				}
				else {
					formattedProperty = defaultLanguageId;
				}

				valuePath.push(formattedProperty);
			}
			else {
				const resolvedValue = resolveLocalizedObjectFields({
					fieldName: property,
					navigatedValue,
					valuePath,
				});
				navigatedValue = resolvedValue.navigatedValue;
				valuePath.concat(resolvedValue.valuePath);
			}
		});
	}
	else {
		const resolvedValue = resolveLocalizedObjectFields({
			fieldName,
			navigatedValue,
			valuePath,
		});
		navigatedValue = resolvedValue.navigatedValue;
		valuePath.concat(resolvedValue.valuePath);
	}

	return {
		rootPropertyName,
		value: navigatedValue,
		valuePath,
	};
}
