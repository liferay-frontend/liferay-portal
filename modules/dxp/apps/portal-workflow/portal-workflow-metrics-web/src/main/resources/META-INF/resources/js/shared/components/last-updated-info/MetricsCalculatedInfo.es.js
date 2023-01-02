/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayLayout from '@clayui/layout';
import {format} from 'date-fns';
import React from 'react';

import {sub} from '../../util/lang.es';

export default function MetricsCalculatedInfo({dateModified}) {
	const date = dateModified
		? format(new Date(dateModified), "MMM dd',' p")
		: null;

	const infoLabel = sub(Liferay.Language.get('sla-metrics-calculated'), [
		date,
	]);

	return (
		date && (
			<div className="updated-info-container">
				<ClayLayout.ContainerFluid className="mt-3">
					<span className="metrics-calculated-info">{infoLabel}</span>
				</ClayLayout.ContainerFluid>
			</div>
		)
	);
}
