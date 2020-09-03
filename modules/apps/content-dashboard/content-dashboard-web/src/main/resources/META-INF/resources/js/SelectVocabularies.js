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
import {ClayDualListBox} from '@clayui/form';
import React, {useState} from 'react';

export default function ({
	availableVocabularyNames,
	currentVocabularyNames,
	namespace,
}) {
	const vocabularies = [currentVocabularyNames, availableVocabularyNames];

	const data = vocabularies.map((inner) =>
		inner.map(({key, value}) => ({label: value, value: key}))
	);

	const [items, setItems] = useState(data);

	const [firstSelectBoxItems] = items;

	const [leftSelected, setLeftSelected] = useState([]);
	const [rightSelected, setRightSelected] = useState([]);

	const [showDismisible, setShowDismisible] = useState(true);

	return (
		<>
			{showDismisible && !firstSelectBoxItems.length && (
				<ClayAlert
					displayType="warning"
					onClose={() => setShowDismisible(false)}
					title={Liferay.Language.get('warning')}
				>
					{Liferay.Language.get(
						'select-at-least-one-vocabulary-to-show-on-the-chart'
					)}
				</ClayAlert>
			)}

			<p className="text-secondary">
				{Liferay.Language.get('select-vocabularies-description')}
			</p>

			<ClayDualListBox
				ariaLabels={{
					transferLTR: Liferay.Util.sub(
						Liferay.Language.get('move-selected-items-from-x-to-x'),
						Liferay.Language.get('in-use'),
						Liferay.Language.get('available')
					),
					transferRTL: Liferay.Util.sub(
						Liferay.Language.get('move-selected-items-from-x-to-x'),
						Liferay.Language.get('available'),
						Liferay.Language.get('in-use')
					),
				}}
				disableRTL={
					firstSelectBoxItems.length >= 2 || rightSelected.length >= 2
				}
				items={items}
				left={{
					id: `${namespace}currentAssetVocabularyNames`,
					label: Liferay.Language.get('in-use'),
					onSelectChange: setLeftSelected,
					selected: leftSelected,
				}}
				onItemsChange={setItems}
				right={{
					id: `${namespace}availableAssetVocabularyNames`,
					label: Liferay.Language.get('available'),
					onSelectChange: setRightSelected,
					selected: rightSelected,
				}}
				size={8}
			/>
		</>
	);
}
