/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './AutocompleteItem.soy';

class AutocompleteItem extends Component {
	processQuery() {
		const regex = new RegExp(`(.*?)(${this.query})(.*)`, 'gmi');
		const results = regex.exec(this.text);

		if (results) {
			this.updateHighlightedText(
				results.map((element) => element.toString())
			);
		}
		else {
			this.reinitializeTextGroups();
		}

		return !!results;
	}

	reinitializeTextGroups() {
		this.firstGroup = this.text.toString();
		this.secondGroup = null;
		this.thirdGroup = null;

		return false;
	}

	syncQuery() {
		this.processQuery();
	}

	syncText() {
		this.processQuery();
	}

	updateHighlightedText(results) {
		this.firstGroup = results[1] || null;
		this.secondGroup = results[2] || null;
		this.thirdGroup = results[3] || null;

		return true;
	}
}

Soy.register(AutocompleteItem, template);

AutocompleteItem.STATE = {
	firstGroup: Config.string().internal(),
	query: Config.string(),
	secondGroup: Config.string().internal(),
	text: Config.any(),
	thirdGroup: Config.string().internal(),
};

export {AutocompleteItem};
export default AutocompleteItem;
