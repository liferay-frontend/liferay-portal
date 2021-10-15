/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import toggleDisabled from '../../../src/main/resources/META-INF/resources/liferay/util/toggle_disabled';

describe('Liferay.Uitl.toggleDisabled', () => {
	beforeEach(() => {
		const fragment = document.createDocumentFragment();

		for (let i = 0; i < 10; i++) {
			const button = document.createElement('button');
			button.setAttribute('id', `button-${i + 1}`);
			button.classList.add('test-button');
			fragment.appendChild(button);
		}

		document.body.appendChild(fragment);
	});

	afterEach(() => {
		const buttons = document.querySelectorAll('button.test-button');

		if (buttons) {
			buttons.forEach((button) => {
				document.body.removeChild(button);
			});
		}
	});

	it('sets the `disabled` attribute on a collection of nodes if the `state` argument is passed', () => {
		toggleDisabled('button.test-button', 'disabled');

		const nodes: NodeListOf<HTMLButtonElement> = document.querySelectorAll(
			'button.test-button'
		);

		nodes.forEach((node) => {
			expect(node.disabled).toEqual(true);
		});
	});
});
