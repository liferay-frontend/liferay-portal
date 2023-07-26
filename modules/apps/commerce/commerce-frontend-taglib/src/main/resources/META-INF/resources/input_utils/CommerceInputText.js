/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './CommerceInputText.soy';

class CommerceInputText extends Component {}

Soy.register(CommerceInputText, template);

CommerceInputText.STATE = {
	_handleInputKeyUp: Config.func(),
	contextName: Config.string(),
	label: Config.string(),
	name: Config.string(),
	pattern: Config.any(),
	required: Config.bool(),
	type: Config.string(),
	value: Config.string(),
};

export {CommerceInputText};
export default CommerceInputText;
