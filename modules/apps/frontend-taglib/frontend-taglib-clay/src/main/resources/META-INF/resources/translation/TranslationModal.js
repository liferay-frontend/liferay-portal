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

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayModal from '@clayui/modal';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React from 'react';

const TranslationModal = ({
	ariaLabels = {
		default: Liferay.Language.get('default'),
		manageTranslations: Liferay.Language.get('manage-translations'),
		managementToolbar: Liferay.Language.get('management-toolbar'),
		translated: Liferay.Language.get('translated'),
		untranslated: Liferay.Language.get('untranslated'),
	},
	defaultLocale,
	locales,
	observer,
	translations,
}) => {
	const [active, setActive] = React.useState(false);
	const [deletedLocale, setDeletedLocale] = React.useState(null);
	const [deletedLocales, setDeletedLocales] = React.useState(new Set([]));
	const [initialLocales, setInitialLocales] = React.useState(locales);
	const [value, setValue] = React.useState('');

	const filteredLocales = React.useMemo(() => {
		return initialLocales.filter((locale) =>
			locale.label.match(new RegExp(value, 'i'))
		);
	}, [initialLocales, value]);

	const restoreLocale = (locale) => {
		if (deletedLocales.has(locale)) {
			deletedLocales.delete(locale);
			setDeletedLocales(deletedLocales);
		}

		filteredLocales.splice(locale.index, 0, locale);
		setInitialLocales(filteredLocales);
		setActive(false);
		setDeletedLocale(null);
	};

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('manage-translations')}
			</ClayModal.Header>

			<ClayModal.Body scrollable>
				<ClayManagementToolbar
					aria-label={ariaLabels.managementToolbar}
				>
					<ClayManagementToolbar.Search showMobile={true}>
						<ClayInput.Group>
							<ClayInput.GroupItem>
								<ClayInput
									aria-label={Liferay.Language.get('search')}
									insetAfter={true}
									onChange={(event) => {
										const {value} = event.target;

										setValue(value);
									}}
									placeholder={Liferay.Language.get('search')}
									value={value}
								/>
								<ClayInput.GroupInsetItem after tag="span">
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'search'
										)}
										displayType="unstyled"
										onClick={() => {
											setValue('');
										}}
										symbol={value ? 'times' : 'search'}
									/>
								</ClayInput.GroupInsetItem>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayManagementToolbar.Search>

					<ClayManagementToolbar.ItemList>
						<ClayDropDown
							active={active}
							hasLeftSymbols
							onActiveChange={setActive}
							trigger={
								<ClayButtonWithIcon
									disabled={deletedLocales.size < 1}
									symbol="plus"
								/>
							}
						>
							<ClayDropDown.ItemList>
								{Array.from(deletedLocales).map((locale) => {
									return (
										<ClayDropDown.Item
											key={locale.label}
											onClick={() => {
												restoreLocale(locale);
											}}
											symbolLeft={locale.symbol}
										>
											{locale.label}
										</ClayDropDown.Item>
									);
								})}
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</ClayManagementToolbar.ItemList>
				</ClayManagementToolbar>

				{deletedLocale && (
					<ClayAlert
						displayType="success"
						onClose={() => {
							setDeletedLocale(null);
						}}
						title={Liferay.Language.get('success')}
					>
						{Liferay.Util.sub(
							Liferay.Language.get('translation-deleted'),
							deletedLocale.label
						)}
						<ClayLink
							onClick={() => {
								restoreLocale(deletedLocale);
							}}
						>
							{Liferay.Language.get('undo')}
						</ClayLink>
					</ClayAlert>
				)}

				<ClayTable>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell>
								{Liferay.Language.get('code')}
							</ClayTable.Cell>
							<ClayTable.Cell headingCell>
								{Liferay.Language.get('language')}
							</ClayTable.Cell>
							<ClayTable.Cell headingCell>
								{Liferay.Language.get('status')}
							</ClayTable.Cell>
							<ClayTable.Cell headingCell />
						</ClayTable.Row>
					</ClayTable.Head>
					<ClayTable.Body>
						{filteredLocales.map((locale, index) => {
							const label = locale.label;

							const isDefaultLocale =
								locale.label === defaultLocale.label;
							const value = translations[label];

							return (
								<ClayTable.Row key={label}>
									<ClayTable.Cell>
										<>
											<ClayIcon
												className="inline-item inline-item-before"
												symbol={locale.symbol}
											/>
											<strong>{label}</strong>
										</>
									</ClayTable.Cell>
									<ClayTable.Cell expanded>
										{locale.displayName}
									</ClayTable.Cell>
									<ClayTable.Cell>
										<ClayLabel
											displayType={
												isDefaultLocale
													? 'info'
													: value
													? 'success'
													: 'warning'
											}
										>
											{isDefaultLocale
												? ariaLabels.default
												: value
												? ariaLabels.translated
												: ariaLabels.untranslated}
										</ClayLabel>
									</ClayTable.Cell>
									<ClayTable.Cell>
										{!isDefaultLocale && (
											<ClayIcon
												className="inline-item"
												onClick={() => {
													filteredLocales.splice(
														index,
														1
													);

													deletedLocales.add(locale);
													setDeletedLocales(
														deletedLocales
													);

													setDeletedLocale(locale);

													setInitialLocales(
														filteredLocales
													);
												}}
												symbol="trash"
											/>
										)}
									</ClayTable.Cell>
								</ClayTable.Row>
							);
						})}
					</ClayTable.Body>
				</ClayTable>
			</ClayModal.Body>
		</ClayModal>
	);
};

TranslationModal.propTypes = {
	arialLabels: PropTypes.shape({
		default: PropTypes.string,
		manageTranslations: PropTypes.string,
		managementToolbar: PropTypes.string,
		tranlated: PropTypes.string,
		untranslated: PropTypes.string,
	}),
	defaultLocale: PropTypes.shape({
		label: PropTypes.string,
		symbol: PropTypes.string,
	}).isRequired,
	locales: PropTypes.arrayOf(PropTypes.object).isRequired,
	observer: PropTypes.object.isRequired,
	translations: PropTypes.object,
};

export default TranslationModal;
