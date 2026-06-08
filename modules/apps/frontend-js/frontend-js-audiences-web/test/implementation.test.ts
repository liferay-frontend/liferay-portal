/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as audiences from '../src/main/resources/META-INF/resources/main/implementation';
import {store} from '../src/main/resources/META-INF/resources/main/store';

describe('implementation', () => {
	afterEach(async () => {
		store.clear();

		// Flush any handlers a test left registered (the store is already
		// empty, so none of them run)

		await audiences.runHandlers();
	});

	describe('runHandlers', () => {
		it('runs the audiences in audiencesRunOrder first and in order, then the rest in an indeterminate order', async () => {
			const executionOrder: string[] = [];

			const audienceIds = ['a', 'b', 'c', 'd', 'e'];

			store.setPageAudienceIds(new Set(audienceIds));

			for (const audienceId of audienceIds) {
				audiences.on(audienceId, () => {
					executionOrder.push(audienceId);
				});
			}

			const audiencesRunOrder = ['d', 'b'];

			await audiences.runHandlers({audiencesRunOrder});

			// The prioritized audiences run first, in the given order

			expect(executionOrder.slice(0, audiencesRunOrder.length)).toEqual(
				audiencesRunOrder
			);

			// The remaining audiences run afterwards, in an indeterminate order

			expect(
				new Set(executionOrder.slice(audiencesRunOrder.length))
			).toEqual(new Set(['a', 'c', 'e']));

			// Every audience handler runs exactly once

			expect(executionOrder).toHaveLength(audienceIds.length);
		});

		it('clears the registered handlers after running by default', async () => {
			let runCount = 0;

			store.setPageAudienceIds(new Set(['a']));

			audiences.on('a', () => {
				runCount += 1;
			});

			await audiences.runHandlers();

			expect(runCount).toBe(1);

			// The handler was cleared, so a second run does not invoke it again

			await audiences.runHandlers();

			expect(runCount).toBe(1);
		});

		it('keeps the registered handlers when clearHandlers is false', async () => {
			let runCount = 0;

			store.setPageAudienceIds(new Set(['a']));

			audiences.on('a', () => {
				runCount += 1;
			});

			await audiences.runHandlers({clearHandlers: false});

			expect(runCount).toBe(1);

			// The handler remains, so a second run invokes it again

			await audiences.runHandlers({clearHandlers: false});

			expect(runCount).toBe(2);
		});
	});
});
