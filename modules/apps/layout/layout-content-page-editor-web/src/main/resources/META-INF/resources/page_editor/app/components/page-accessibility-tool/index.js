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

import React, {useRef} from 'react';

import Violations from './components/Violations';
import {violations as violationsMock} from './mock';

export default function PageAccessibilityToolSidebar({
	violations = violationsMock,
}) {
	const sidebarRef = useRef(null);

	const hasViolations = !!violations.length;

	return (
		<div
			className="page-accessibility-tool__sidebar sidebar sidebar-light"
			ref={sidebarRef}
		>
			{hasViolations ? (
				<Violations.Panel violations={violations} />
			) : (
				<Violations.EmptyState />
			)}
		</div>
	);
}
