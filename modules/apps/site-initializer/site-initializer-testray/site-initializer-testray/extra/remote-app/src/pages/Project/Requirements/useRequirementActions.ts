/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';
import {useNavigate} from 'react-router-dom';
import useFormModal from '~/hooks/useFormModal';

import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {
	TestrayRequirement,
	testrayRequirementsImpl,
} from '../../../services/rest';
import {Action, ActionsHookParameter} from '../../../types';

const useRequirementActions = ({
	isHeaderActions = false,
}: ActionsHookParameter = {}) => {
	const {
		modal: {onError, onSave},
	} = useFormModal();
	const {removeItemFromList} = useMutate();
	const navigate = useNavigate();

	const actionsRef = useRef([
		{
			action: ({id}) =>
				navigate(isHeaderActions ? 'update' : `${id}/update`),
			icon: 'pencil',
			name: i18n.translate(isHeaderActions ? 'edit-requirement' : 'edit'),
			permission: 'UPDATE',
		},
		{
			action: ({id}) => alert(id),
			icon: 'reload',
			name: i18n.translate('resync-with-jira'),
			permission: 'UPDATE',
		},
		{
			action: ({id}) => navigate(`${id}`),
			icon: 'list-ul',
			name: i18n.translate('link-cases'),
			permission: 'UPDATE',
		},
		{
			action: ({id}, mutate) =>
				testrayRequirementsImpl
					.removeResource(id)
					?.then(() => removeItemFromList(mutate, id))
					.then(onSave)
					.then(() => {
						if (isHeaderActions) {
							navigate('../');
						}
					})
					.catch(onError),
			icon: 'trash',
			name: i18n.translate(
				isHeaderActions ? 'delete-requirement' : 'delete'
			),
			permission: 'DELETE',
		},
	] as Action<TestrayRequirement>[]);

	return {
		actions: actionsRef.current,
		navigate,
	};
};

export default useRequirementActions;
