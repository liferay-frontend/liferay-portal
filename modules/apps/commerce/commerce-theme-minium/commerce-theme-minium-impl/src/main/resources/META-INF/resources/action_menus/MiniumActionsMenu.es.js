/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import Soy from 'metal-soy';

import template from './MiniumActionsMenu.soy';

class MiniumActionsMenu extends Component {
	_handleToggle(event) {
		const actions = this.refs.actions;
		const row = event.target.closest('tr');
		const width = actions.getBoundingClientRect().width;
		row.style.setProperty('--translate-space', `${width - 10}px`);
		row.classList.toggle('is-active');
	}
}

Soy.register(MiniumActionsMenu, template);

export {MiniumActionsMenu};
export default MiniumActionsMenu;
