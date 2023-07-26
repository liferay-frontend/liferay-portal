/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './AddToTickItem.soy';

class AddToTickItem extends Component {}

Soy.register(AddToTickItem, template);

AddToTickItem.STATE = {
	added: Config.bool().value(false),
};

export {AddToTickItem};
export default AddToTickItem;
