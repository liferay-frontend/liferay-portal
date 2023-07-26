/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';
import './Collapse.scss';
export default function Collapse({
	children,
	label,
	onOpen,
	open,
}: ICollapseProps): JSX.Element;
interface ICollapseProps {
	children: ReactNode;
	label: string;
	onOpen?: (value: boolean) => void;
	open: boolean;
}
export {};
