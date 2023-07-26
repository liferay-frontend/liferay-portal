/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {ReactNode, useState} from 'react';

import './Collapse.scss';

export default function Collapse({
	children,
	label,
	onOpen,
	open,
}: ICollapseProps) {
	const [internalIsOpen, setInternalIsOpen] = useState(open);
	const isOpen = onOpen ? open : internalIsOpen;
	const setIsOpen = onOpen || setInternalIsOpen;
	const collapseIcon = isOpen ? 'angle-down' : 'angle-right';
	const collapseIconClassName = isOpen ? 'open' : 'closed';

	const handleClick = () => {
		setIsOpen(!isOpen);
	};

	return (
		<div
			className={classNames(
				'panel-group panel-group-flush page-editor__collapse'
			)}
		>
			<button
				aria-expanded={isOpen}
				className={classNames(
					'btn',
					'btn-unstyled',
					'collapse-icon',
					'sheet-subtitle',
					{
						collapsed: !isOpen,
					}
				)}
				onClick={handleClick}
				type="button"
			>
				<span className="c-inner text-truncate" tabIndex={-1}>
					{label}

					<span className={`collapse-icon-${collapseIconClassName}`}>
						<ClayIcon key={collapseIcon} symbol={collapseIcon} />
					</span>
				</span>
			</button>

			<div className="page-editor__collapse__content">
				{isOpen && children}
			</div>
		</div>
	);
}

interface ICollapseProps {
	children: ReactNode;
	label: string;
	onOpen?: (value: boolean) => void;
	open: boolean;
}
