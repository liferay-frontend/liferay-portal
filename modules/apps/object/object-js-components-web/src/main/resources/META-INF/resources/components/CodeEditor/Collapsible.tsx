/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Collapse} from '@liferay/layout-content-page-editor-web';
import React from 'react';

export function Collapsible({children, label}: IProps) {
	return (
		<div className="lfr-objects__code-editor-sidebar-collapsible-button-list">
			<Collapse label={label} open>
				{children}
			</Collapse>
		</div>
	);
}

interface IProps {
	children: React.ReactNode;
	label: string;
}
