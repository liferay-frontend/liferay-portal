/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The History Manager, a utility for SPA.
 *
 * @deprecated As of Mueller (7.2.x), replaced by senna.js
 * @module liferay-history-manager
 */

AUI.add(
	'liferay-history-manager',
	(A) => {
		const HistoryBase = A.HistoryBase;
		const HistoryManager = new Liferay.History();

		const EVENT_STATE_CHANGE = 'stateChange';

		HistoryManager.SRC_ADD = HistoryBase.SRC_ADD;
		HistoryManager.SRC_REPLACE = HistoryBase.SRC_REPLACE;

		HistoryManager.SRC_HASH = A.HistoryHash
			? A.HistoryHash.SRC_HASH
			: 'hash';
		HistoryManager.SRC_POPSTATE = A.HistoryHTML5
			? A.HistoryHTML5.SRC_POPSTATE
			: 'popstate';

		HistoryManager.HTML5 = HistoryBase.html5;

		HistoryManager.PAIR_SEPARATOR = Liferay.History.PAIR_SEPARATOR;
		HistoryManager.VALUE_SEPARATOR = Liferay.History.VALUE_SEPARATOR;

		HistoryManager.publish(EVENT_STATE_CHANGE, {
			broadcast: 2,
		});

		HistoryManager.after('change', (event) => {
			if (
				event.newVal.liferay &&
				(event.src === HistoryManager.SRC_HASH ||
					event.src === HistoryManager.SRC_POPSTATE)
			) {
				HistoryManager.fire(EVENT_STATE_CHANGE, event);
			}
		});

		Liferay.HistoryManager = HistoryManager;
	},
	'',
	{
		requires: ['liferay-history'],
	}
);
