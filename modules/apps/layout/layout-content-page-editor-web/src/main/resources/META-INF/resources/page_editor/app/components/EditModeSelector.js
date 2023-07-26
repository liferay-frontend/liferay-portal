/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import {sub} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import togglePermission from '../actions/togglePermission';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectCanSwitchEditMode from '../selectors/selectCanSwitchEditMode';

const EDIT_MODES = {
	contentEditing: Liferay.Language.get('content-editing'),
	pageDesign: Liferay.Language.get('page-design'),
};

export default function EditModeSelector() {
	const canSwitchEditMode = useSelector(selectCanSwitchEditMode);
	const dispatch = useDispatch();

	const [editMode, setEditMode] = useState(
		canSwitchEditMode ? EDIT_MODES.pageDesign : EDIT_MODES.contentEditing
	);

	const permissions = useSelector((state) => state.permissions);

	const higherUpdatePermissionRef = useRef();

	useEffect(() => {
		if (permissions.UPDATE) {
			higherUpdatePermissionRef.current = 'UPDATE';
		}
		else if (permissions.UPDATE_LAYOUT_BASIC) {
			higherUpdatePermissionRef.current = 'UPDATE_LAYOUT_BASIC';
		}
		else {
			higherUpdatePermissionRef.current = 'UPDATE_LAYOUT_LIMITED';
		}

		/* eslint-disable-next-line react-hooks/exhaustive-deps */
	}, []);

	return (
		<ClayDropDown
			alignmentPosition={Align.BottomLeft}
			closeOnClick
			menuElementAttrs={{
				className: 'page-editor__edit-mode-dropdown-menu',
				containerProps: {
					className: 'cadmin',
				},
			}}
			trigger={
				<ClayButton
					aria-label={sub(
						Liferay.Language.get('page-edition-mode-x'),
						editMode
					)}
					className="form-control-select page-editor__edit-mode-selector text-left"
					disabled={!canSwitchEditMode}
					displayType="secondary"
					size="sm"
					type="button"
				>
					<span>{editMode}</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				<ClayDropDown.Item
					onClick={() => {
						setEditMode(EDIT_MODES.pageDesign);

						dispatch(
							togglePermission(
								higherUpdatePermissionRef.current,
								true
							)
						);
					}}
				>
					{EDIT_MODES.pageDesign}
				</ClayDropDown.Item>

				<ClayDropDown.Item
					onClick={() => {
						setEditMode(EDIT_MODES.contentEditing);

						dispatch(
							togglePermission(
								higherUpdatePermissionRef.current,
								false
							)
						);
					}}
				>
					{EDIT_MODES.contentEditing}
				</ClayDropDown.Item>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
