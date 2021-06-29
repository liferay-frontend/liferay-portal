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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React from 'react';

import TranslationModal from './TranslationModal';

const TranslationSelector = ({
	ariaLabels = {
		default: Liferay.Language.get('default'),
		manageTranslations: Liferay.Language.get('manage-translations'),
		translated: Liferay.Language.get('translated'),
		untranslated: Liferay.Language.get('untranslated'),
	},
	defaultLocale,
	locales,
	onSelectedLocaleChange = () => {},
	selectedLocale,
	small = false,
	translations,
	variant,
}) => {
	const [active, setActive] = React.useState(false);

	const [visible, setVisible] = React.useState(false);

	const {observer} = useModal({
		onClose: () => setVisible(false),
	});

	selectedLocale = selectedLocale ?? defaultLocale;

	return (
		<>
			{visible && (
				<TranslationModal
					ariaLabels={ariaLabels}
					defaultLocale={defaultLocale}
					locales={locales}
					observer={observer}
					translations={translations}
				/>
			)}

			<ClayDropDown
				active={active}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						displayType="secondary"
						monospaced
						onClick={() => setActive(!active)}
						small={small}
						title={Liferay.Language.get(
							'select-translation-language'
						)}
					>
						<span className="inline-item">
							<ClayIcon symbol={selectedLocale.symbol} />
						</span>
						<span className="btn-section">
							{selectedLocale.label}
						</span>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					{locales.map((locale) => {
						const value = translations[locale.label];

						return (
							<ClayDropDown.Item
								key={locale.label}
								onClick={() => onSelectedLocaleChange(locale)}
							>
								<ClayLayout.ContentRow containerElement="span">
									<ClayLayout.ContentCol
										containerElement="span"
										expand
									>
										<ClayLayout.ContentSection>
											<ClayIcon
												className="inline-item inline-item-before"
												symbol={locale.symbol}
											/>

											{locale.label}
										</ClayLayout.ContentSection>
									</ClayLayout.ContentCol>
									{variant !== 'language' && (
										<ClayLayout.ContentCol containerElement="span">
											<ClayLayout.ContentSection>
												<ClayLabel
													displayType={
														locale.label ===
														defaultLocale.label
															? 'info'
															: value
															? 'success'
															: 'warning'
													}
												>
													{locale.label ===
													defaultLocale.label
														? ariaLabels.default
														: value
														? ariaLabels.translated
														: ariaLabels.untranslated}
												</ClayLabel>
											</ClayLayout.ContentSection>
										</ClayLayout.ContentCol>
									)}
								</ClayLayout.ContentRow>
							</ClayDropDown.Item>
						);
					})}
					{!variant && (
						<>
							<ClayDropDown.Divider />
							<ClayDropDown.Item onClick={() => setVisible(true)}>
								<ClayLayout.ContentRow containerElement="span">
									<ClayLayout.ContentCol
										containerElement="span"
										expand
									>
										<ClayLayout.ContentSection>
											<ClayIcon
												className="inline-item inline-item-before"
												symbol="automatic-translate"
											/>

											{ariaLabels.manageTranslations}
										</ClayLayout.ContentSection>
									</ClayLayout.ContentCol>
								</ClayLayout.ContentRow>
							</ClayDropDown.Item>
						</>
					)}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
};

TranslationSelector.propTypes = {
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
	onSelectedLocaleChange: PropTypes.func,
	selectedLocale: PropTypes.shape({
		label: PropTypes.string,
		symbol: PropTypes.string,
	}).isRequired,
	small: PropTypes.bool,
	translations: PropTypes.object.isRequired,
	variant: PropTypes.oneOf([undefined, 'language', 'translation']),
};

export default TranslationSelector;
