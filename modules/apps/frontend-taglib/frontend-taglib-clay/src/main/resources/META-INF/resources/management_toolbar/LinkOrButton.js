/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React, {useEffect, useMemo, useState} from 'react';

import './LinkOrButton.scss';

const DEFAULT_BREAKPOINT_MD = '768px';

/**
 * A dynamic link/button that includes a symbol, a label, or both of them. All
 * props are optional except for the label prop.
 *
 * It renders a ClayButton or a ClayLink depending on the provided:
 * - disabled state (a ClayButton will be used).
 * - href (a ClayLink will be used).
 * - otherwise a ClayButton will be used.
 *
 * Then it shows a symbol on smaller screens, and label on larger screens.
 * When only the symbol is being shown, an aria-label and title are added to the
 * button for accessibility purposes. If there is no provided title, the label
 * prop will be reused as title.
 *
 * It will always add a title to the button if it is specified as prop.
 */
function LinkOrButton(
	{
		className = '',
		disabled = false,
		href = '',
		label,
		symbol = '',
		title = '',
		...otherProps
	},
	ref
) {
	const [element, setElement] = useState(null);
	const TagName = href && !disabled ? ClayLink : ClayButton;

	const breakpointMd = element
		? window.getComputedStyle(element).getPropertyValue('--breakpoint-md')
		: DEFAULT_BREAKPOINT_MD;

	const showLabel = useMediaQuery(`(min-width: ${breakpointMd})`);

	const buttonProps = {
		...otherProps,
		className: 'link-or-button',
		disabled,
		href,
		ref: mergeRefs(setElement, ref),
	};

	if (showLabel || !symbol) {
		return (
			<TagName
				{...buttonProps}
				className={classNames(
					className,
					buttonProps.className,
					'link-or-button--with-label'
				)}
				title={title}
			>
				{label}
			</TagName>
		);
	}

	return (
		<TagName
			{...buttonProps}
			aria-label={label}
			className={classNames(
				className,
				buttonProps.className,
				'nav-btn-monospaced'
			)}
			title={title || label}
		>
			<ClayIcon symbol={symbol} />
		</TagName>
	);
}

function mergeRefs(...refs) {
	return (element) => {
		refs.forEach((ref) => {
			if (typeof ref === 'function') {
				ref(element);
			}
			else if (ref) {
				ref.current = element;
			}
		});
	};
}

function useMediaQuery(query) {
	const mediaQueryList = useMemo(() => window.matchMedia(query), [query]);
	const [matches, setMatches] = useState(mediaQueryList.matches);

	useEffect(() => {
		const handleQueryChange = (event) => {
			setMatches(event.matches);
		};

		mediaQueryList.addEventListener('change', handleQueryChange);

		return () => {
			mediaQueryList.removeEventListener('change', handleQueryChange);
		};
	}, [mediaQueryList]);

	return matches;
}

export default React.forwardRef(LinkOrButton);
