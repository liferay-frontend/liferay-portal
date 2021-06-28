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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayModal from '@clayui/modal';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React from 'react';

const TranslationModal = ({
	ariaLabels = {
		default: Liferay.Language.get('default'),
		manageTranslations: Liferay.Language.get('manage-translations'),
		translated: Liferay.Language.get('translated'),
		untranslated: Liferay.Language.get('untranslated'),
	},
	defaultLocale,
	locales,
	observer,
	translations,
}) => {
	const [active, setActive] = React.useState(false);
	const [icon, setIcon] = React.useState('search');
	const inputRef = React.createRef();
	const [visibleLocales, setVisibleLocales] = React.useState(locales);

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('manage-translations')}
			</ClayModal.Header>

			<ClayModal.Body scrollable>
				<ClayManagementToolbar aria-label="Management toolbar">
					<ClayManagementToolbar.Search showMobile={true}>
						<ClayInput.Group>
							<ClayInput.GroupItem>
								<ClayInput
									aria-label={Liferay.Language.get('search')}
									className="form-control input-group-inset input-group-inset-after"
									onInput={() => {
										const value = inputRef.current?.value;

										const icon = value ? 'times' : 'search';
										setIcon(icon);

										if (!value) {
											setVisibleLocales(locales);

											return;
										}

										const result = Object.values(
											visibleLocales
										).filter((locale) => {
											return locale.label.match(
												new RegExp(value, 'i')
											);
										});

										setVisibleLocales(result);
									}}
									placeholder={Liferay.Language.get('search')}
									ref={inputRef}
									type="text"
								/>
								<ClayInput.GroupInsetItem after tag="span">
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'search'
										)}
										displayType="unstyled"
										onClick={() => {
											if (
												icon === 'times' &&
												inputRef.current?.value
											) {
												inputRef.current.value = '';
												setIcon('search');
												setVisibleLocales(locales);
											}
										}}
										symbol={icon}
									/>
								</ClayInput.GroupInsetItem>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayManagementToolbar.Search>

					<ClayManagementToolbar.ItemList>
						<ClayDropDown
							active={active}
							onActiveChange={setActive}
							trigger={<ClayButtonWithIcon symbol="plus" />}
						></ClayDropDown>
					</ClayManagementToolbar.ItemList>
				</ClayManagementToolbar>

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
							<ClayTable.Cell headingCell></ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>
					<ClayTable.Body>
						{visibleLocales.map((locale) => {
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
									<ClayTable.Cell>Long name</ClayTable.Cell>
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
