/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import React from 'react';

const {default: CollapsibleSection} = require('./CollapsibleSection');
const {default: ItemVocabularies} = require('./ItemVocabularies');
import {
	getCategoriesCountFromVocabularies,
	groupVocabulariesBy,
} from './utils/taxonomiesUtils';

const Categorization = ({tags, vocabularies}: IProps): JSX.Element | null => {
	const [publicVocabularies, internalVocabularies] = groupVocabulariesBy({
		array: Object.values(vocabularies),
		key: 'isPublic',
		value: true,
	});

	const internalCategoriesCount = getCategoriesCountFromVocabularies(
		internalVocabularies
	);

	const publicCategoriesCount = getCategoriesCountFromVocabularies(
		publicVocabularies
	);

	const showTaxonomies =
		!!internalCategoriesCount || !!publicCategoriesCount || !!tags?.length;

	if (!showTaxonomies) {
		return null;
	}

	return (
		<CollapsibleSection
			expanded
			title={Liferay.Language.get('categorization')}
		>
			{!!publicCategoriesCount && (
				<ItemVocabularies
					title={Liferay.Language.get('public-categories')}
					vocabularies={publicVocabularies}
				/>
			)}

			{!!internalCategoriesCount && (
				<ItemVocabularies
					cssClassNames="c-mt-4"
					title={Liferay.Language.get('internal-categories')}
					vocabularies={internalVocabularies}
				/>
			)}

			{!!tags.length && (
				<div className="c-mb-4 sidebar-section">
					<h5 className="c-mb-1 font-weight-semi-bold">
						{Liferay.Language.get('tags')}
					</h5>

					<p>
						{tags.map((tag) => (
							<ClayLabel
								className="c-mb-2 c-mr-2"
								displayType="secondary"
								key={tag}
								large
							>
								{tag}
							</ClayLabel>
						))}
					</p>
				</div>
			)}
		</CollapsibleSection>
	);
};

export interface Vocabulary {
	categories: string[];
	groupName: string;
	isPublic: boolean;
	vocabularyName: string;
}

interface Vocabularies {
	[id: string]: Vocabulary;
}

interface IProps {
	tags: string[];
	vocabularies: Vocabularies;
}

export default Categorization;
