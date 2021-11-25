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

import buildCustomViewDefinition from '../../src/main/resources/META-INF/resources/utils/buildCustomViewDefinition';

describe('buildCustomViewDefinition', () => {
	it('creates a custom view definition object', () => {
		const sampleFieldName1 = 'sampleFieldName1';
		const sampleFieldName2 = 'sampleFieldName2';

		const sampleActiveView = {
			name: 'sampleView',
			schema: {
				fields: [
					{
						fieldName: sampleFieldName1,
					},
					{
						fieldName: sampleFieldName2,
					},
				],
			},
		};

		const sampleComponentId = 'sampleComponentId';
		const sampleCustomViewLabel = 'sampleCustomViewLabel';

		const sampleVisibleFieldNames = {};

		sampleVisibleFieldNames[sampleFieldName1] = true;
		sampleVisibleFieldNames[sampleFieldName2] = false;

		const customViewDefinition = buildCustomViewDefinition({
			activeView: sampleActiveView,
			componentId: sampleComponentId,
			customViewLabel: sampleCustomViewLabel,
			visibleFieldNames: sampleVisibleFieldNames,
		});

		expect(customViewDefinition).toStrictEqual({
			label: sampleCustomViewLabel,
			scope: {
				componentId: sampleComponentId,
			},
			state: {
				defaultViewName: sampleActiveView.name,
				fields: [
					{
						name: sampleFieldName1,
						visible: true,
					},
					{
						name: sampleFieldName2,
						visible: false,
					},
				],
			},
		});
	});

	it('throws error if new custom view does not have a name', () => {
		const testFn = () => {
			buildCustomViewDefinition({
				customViewId: null,
				customViewName: null,
			});
		};

		expect(testFn).toThrow(Error);
	});
});
