/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ButtonWithIcon} from '@clayui/core';
import i18n from '../../../../../../../../../../common/I18n';
import {ButtonDropDown} from '../../../../../../../../../../common/components';
import MenuUserActions from './components/MenuUserActions';

const OptionsColumn = ({
	edit,
	onCancel,
	onEdit,
	onRemove,
	onSave,
	saveDisabled,
}) => {
	const userOptions = [
		{
			customOptionStyle: 'pr-5',
			label: i18n.translate('edit'),
			onClick: () => onEdit(),
		},
		{
			customOptionStyle: 'pr-5',
			label: i18n.translate('remove'),
			onClick: () => onRemove(),
		},
	];

	return edit ? (
		<MenuUserActions
			onCancel={() => onCancel()}
			onSave={() => onSave()}
			saveDisabled={saveDisabled}
		/>
	) : (
		<ButtonDropDown
			customDropDownButton={
				<ButtonWithIcon displayType="null" small symbol="ellipsis-v" />
			}
			items={userOptions}
			menuElementAttrs={{
				className: 'p-0',
			}}
			menuWidth="shrink"
		/>
	);
};

export default OptionsColumn;
