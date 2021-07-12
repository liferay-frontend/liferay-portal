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

import {useModal} from '@clayui/modal';
import React from 'react';

import TranslationModal from './TranslationModal';

const TranslationModalOpener = ({
	ariaLabels = {
		default: Liferay.Language.get('default'),
		manageTranslations: Liferay.Language.get('manage-translations'),
		translated: Liferay.Language.get('translated'),
		untranslated: Liferay.Language.get('untranslated'),
	},
	defaultLocale,
	locales,
	translations,
}) => {
	const [visible, setVisible] = React.useState(true);

	const {observer} = useModal({
		onClose: () => setVisible(false),
	});

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
		</>
	);
};

export default TranslationModalOpener;
