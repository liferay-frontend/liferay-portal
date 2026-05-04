/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

type Direction = 'all' | 'horizontal' | 'vertical';

type Size = 'md' | 'sm';

export type Props = {

	/**
	 * Optional `class` for the root element.
	 */
	className?: string;

	/**
	 * Which arrow keys are active for navigation. Inactive keys are rendered
	 * muted but remain visible so the inverted-T layout stays recognizable.
	 */
	direction: Direction;

	/**
	 * Localized label announced by assistive technology. Defaults to an
	 * English phrase derived from `direction`; pass an explicit string to
	 * localize (e.g. via Liferay's `Liferay.Language.get`).
	 */
	label?: string;

	/**
	 * Pixel scale of the indicator. `md` (default) ≈ 64×40 px to match the
	 * source mockup; `sm` ≈ 48×30 px for tighter chrome.
	 */
	size?: Size;

	/**
	 * Path to the Clay icon spritemap. Required when the consumer cannot
	 * resolve `ClayIcon`'s default spritemap from context.
	 */
	spritemap?: string;
} & Omit<React.HTMLAttributes<HTMLDivElement>, 'aria-label' | 'role'>;

const DEFAULT_LABELS: Record<Direction, string> = {
	all: 'Use arrow keys to navigate',
	horizontal: 'Use left and right arrow keys to navigate',
	vertical: 'Use up and down arrow keys to navigate',
};

export function KeyboardArrowsIndicator({
	className,
	direction,
	label,
	size = 'md',
	spritemap,
	...otherProps
}: Props) {
	const verticalActive = direction === 'all' || direction === 'vertical';
	const horizontalActive = direction === 'all' || direction === 'horizontal';

	return (
		<div
			{...otherProps}
			aria-label={label ?? DEFAULT_LABELS[direction]}
			className={classNames(
				'clay-keyboard-arrows-indicator',
				`clay-keyboard-arrows-indicator-${size}`,
				className
			)}
			role="img"
		>
			<Key active={verticalActive} position="up" spritemap={spritemap} />

			<Key
				active={horizontalActive}
				position="left"
				spritemap={spritemap}
			/>

			<Key
				active={verticalActive}
				position="down"
				spritemap={spritemap}
			/>

			<Key
				active={horizontalActive}
				position="right"
				spritemap={spritemap}
			/>
		</div>
	);
}

type KeyProps = {
	active: boolean;
	position: 'up' | 'down' | 'left' | 'right';
	spritemap?: string;
};

function Key({active, position, spritemap}: KeyProps) {
	return (
		<span
			aria-hidden
			className={classNames(
				'clay-keyboard-arrows-indicator-key',
				`clay-keyboard-arrows-indicator-key-${position}`,
				{'clay-keyboard-arrows-indicator-key-inactive': !active}
			)}
		>
			<ClayIcon spritemap={spritemap} symbol={`arrow-key-${position}`} />
		</span>
	);
}
