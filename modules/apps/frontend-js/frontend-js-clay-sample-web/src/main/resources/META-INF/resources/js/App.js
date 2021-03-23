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

import ClayCard from '@clayui/card';
import ClayForm, {ClayInput} from '@clayui/form';
import {useLiferayState} from '@liferay/frontend-js-react-web';
import {State} from '@liferay/frontend-js-state-web';
import React from 'react';

import '../css/main.scss';

// Shared state (atoms and selectors); normally these would be in a separate
// file.

const userAtom = State.atom('clay-sample-atom', {
	name: Liferay.ThemeDisplay.getUserName(),
});

const userSelector = State.selector('clay-sample-selector', (get) => {
	const user = get(userAtom);

	return `${user.name} (${user.name.length})`;
});

// Components that access that shared state:

function Name() {
	const [userNameAndLength] = useLiferayState(userSelector);

	return (
		<ClayCard>
			<ClayCard.Body>
				<ClayCard.Description displayType="title">
					{Liferay.Language.get('name')}
				</ClayCard.Description>
				<ClayCard.Description displayType="text" truncate={false}>
					{userNameAndLength}
				</ClayCard.Description>
			</ClayCard.Body>
		</ClayCard>
	);
}

function NameUpdater(portletId) {
	const id = `${portletId}_form`;
	const [user, setUser] = useLiferayState(userAtom);

	return (
		<ClayForm.Group>
			<label htmlFor={id}>Name</label>
			<ClayInput
				id={id}
				onChange={(event) => {
					setUser({
						...user,
						name: event.target.value,
					});
				}}
				type="text"
				value={user.name}
			/>
		</ClayForm.Group>
	);
}

export default ({portletId}) => {
	return (
		<div className="col-md-6">
			<NameUpdater portletId={portletId} />
			<Name />
		</div>
	);
};
