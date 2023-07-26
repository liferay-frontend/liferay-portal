/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './CategorySelector.es';

import './TagSelector.es';

import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './AutoField.soy';

const DEFAULT_RULE = {
	queryContains: true,
	type: 'assetTags',
};

/**
 * AutoField
 *
 */

class AutoField extends Component {

	/**
	 * @inheritDoc
	 */

	created() {
		this.on('rulesChanged', this.onRulesChanged_);
	}

	/**
	 * Adds a new rule of type Tags (by default) to the current list of rules.
	 * @protected
	 */

	addRule_() {
		this.rules = this.rules.concat(DEFAULT_RULE);
	}

	/**
	 * Updates a given rule when the user changes the type of selection (from Tags
	 * to Categories) that wants to apply to it
	 * @param {Event} event
	 * @protected
	 */

	changeSelector_(event) {
		const itemIndex = event.delegateTarget.dataset.itemIndex;
		const rules = this.rules;

		rules[itemIndex] = {
			queryAndOperator: 'all',
			queryContains: true,
			type: event.target.value,
		};

		this.rules = rules;
	}

	/**
	 * Deletes a rule from the current list. This change can not be undone. Changes
	 * made to the deleted rule will be lost.
	 * @param {Event} event
	 * @protected
	 */

	deleteRule_(event) {
		const itemIndex = event.delegateTarget.dataset.ruleId;

		this.rules = this.rules.filter((_rule, i) => i !== Number(itemIndex));
	}

	/**
	 * Updates the queryLogicIndexes whenever the list of rules change. The value
	 * of this property is as follow:
	 * - rules: [{}, {}, {}]
	 * - queryLogicIndexes: "0,1,2";
	 */

	onRulesChanged_() {
		this.queryLogicIndexes = Object.keys(this.rules).toString();
	}
}

AutoField.STATE = {

	/**
	 * Array of group (sites) ids where the information is going
	 * to be fetched. This parementer is passed by to the child
	 * components being rendered as rules.
	 */

	groupIds: {
		value: [],
	},

	/**
	 * List of indices (rules) that must be sent to the server.
	 * @see onRulesChanged_ method por more information.
	 */

	queryLogicIndexes: {
		value: '0',
	},

	/**
	 * Array of rules being rendered as children. Each rule
	 * represents a step on the filtering process, being either
	 * a TagSelector or a CategorySelector.
	 * @type {array}
	 */

	rules: {
		value: [DEFAULT_RULE],
	},
};

Soy.register(AutoField, templates);

export {AutoField};
export default AutoField;
