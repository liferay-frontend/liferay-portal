/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayPopover from '@clayui/popover';
import {ReactPortal, usePrevious} from '@liferay/frontend-js-react-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {Hotspot} from '../components/Hotspot';
import {Overlay} from '../components/Overlay';
import {useClickOutside} from '../hooks/useClickOutside';
import {useObserveRect} from '../hooks/useObserveRect';
import {doAlign} from '../utils';

const OVERLAY_OFFSET_X = 15;
const OVERLAY_OFFSET_Y = -10;

/**
 * This map humanize tuples received from dom-align
 * library to be passed in a format that ClayPopover allows
 */
const ALIGNMENTS_MAP = {
	'bottom': ['tc', 'bc'],
	'bottom-left': ['tl', 'bl'],
	'bottom-right': ['tr', 'br'],
	'left': ['cr', 'cl'],
	'left-bottom': ['br', 'bl'],
	'left-top': ['tr', 'tl'],
	'right': ['cl', 'cr'],
	'right-bottom': ['bl', 'br'],
	'right-top': ['tl', 'tr'],
	'top': ['bc', 'tc'],
	'top-left': ['bl', 'tl'],
	'top-right': ['br', 'tr'],
};

/**
 * Since we can't set tuples as keys for literal dictionaries
 * and maps where are some errors with references like a.get(['bc','tc']) => undefined.
 * We are joining tuples to string to map there and use to lookup in some usages.
 */
const ALIGNMENTS_INVERSE_MAP = {
	bctc: 'top',
	blbr: 'right-bottom',
	bltl: 'top-left',
	brbl: 'left-bottom',
	brtr: 'top-right',
	clcr: 'right',
	crcl: 'left',
	tcbc: 'bottom',
	tlbl: 'bottom-left',
	tltr: 'right-top',
	trbr: 'bottom-right',
	trtl: 'left-top',
};

/**
 * This map matches with new positions when the positioning has been given by the user or the default positioning fails.
 *
 * For example, if we receive on ALIGNMENTS_INVERSE_MAP, a lookup for brtr,
 * it points to 'top-right'. Here, at the ALIGNMENTS_GUESS_MAP,
 * we should make corrections if the positioning cannot be achieved in this case, for brtr.
 *
 * A general rule was applied:
 * If the received INVERSE value is top or top something, we should place it to bottom, and vice-versa for bottom and bottom something.
 * If the received INVERSE value is right, it should place to left, and vice-versa.
 * We should remove guesses from the top since people will not place things on top that will collide at the beginning of the viewport.
 */
const ALIGNMENTS_GUESS_MAP = {
	...ALIGNMENTS_INVERSE_MAP,
	blbr: 'top',
	brbl: 'top',
	clcr: 'left',
	crcl: 'right',
	tcbc: 'top',
	tlbl: 'top',
	tltr: 'top',
	trbr: 'top',
};

let walkthroughExceptionShown = false;

/**
 * Gets the node to highlight element and prints a warning in the console if
 * one isn't found.
 * @param {string} nodeToHighlightSelector A query selector.
 * @returns {Element|null}
 */
const getNodeToHighlight = (nodeToHighlightSelector) => {
	const selectedElement = document.querySelector(nodeToHighlightSelector);

	if (selectedElement) {
		return selectedElement;
	}

	// Only show the warning once.

	if (!walkthroughExceptionShown) {
		console.warn(
			`Walkthrough Exception: ${nodeToHighlightSelector} element for highlight does not exist in DOM`
		);

		walkthroughExceptionShown = true;
	}

	return null;
};

/**
 * Checks if a determining element is inside the viewport
 * @param {Node} element Element to be checked
 * @param {ClientRect} maybeRect boundingClientRect
 * of the element for avoiding calling getBoundingClientRect again.
 * This operation is very costly for browsers.
 * @returns {boolean}
 */
function isVisibleInViewport(element, maybeRect) {
	let boundingRect = maybeRect;

	if (!boundingRect) {
		boundingRect = element.getBoundingClientRect();
	}

	return (
		boundingRect.top >= 0 &&
		boundingRect.left >= 0 &&
		boundingRect.bottom <=
			(window.innerHeight || document.documentElement.clientHeight) &&
		boundingRect.right <=
			(window.innerWidth || document.documentElement.clientWidth)
	);
}

/**
 * Displays a hotspot and popover aligned to the `nodeToHighlightSelector`.
 *
 * This component should focus on the display and the functionality of aligning
 * the hotspot and popover.
 */
function Step({
	closeOnClickOutside = false,
	closeable = true,
	content,
	currentStepIndex,
	darkbg = true,
	nodeToHighlightSelector,
	onNextClick = () => {},
	onPopoverVisibleChange = () => {},
	onPreviousClick = () => {},
	popoverVisible = false,
	initialPopoverAlignPosition = 'right-top',
	skippable = true,
	title,
	totalStepCount,
}) {
	const [popoverAlignPosition, setPopoverAlignPosition] = useState(
		initialPopoverAlignPosition
	);
	const [doNotShowMeThisAgainValue, setDoNotShowMeThisAgainValue] = useState(
		false
	);
	const [nodeToHighlight, setNodeToHighlight] = useState(() =>
		getNodeToHighlight(nodeToHighlightSelector)
	);

	const hotspotRef = useRef(null);
	const popoverRef = useRef(null);

	const previousNodeToHighlight = usePrevious(nodeToHighlight);

	/**
	 * Find and keep `nodeToHighlight` up-to-date.
	 *
	 * Creates a Mutation Observer to continually check if element exists. This
	 * will keep `nodeToHighlight` accurate if the element is ever added or
	 * removed from the page.
	 */
	useEffect(() => {
		if (nodeToHighlightSelector) {

			// Reset popoverAlignPosition, otherwise the value from a previous
			// or next step could be carried over.

			setPopoverAlignPosition(initialPopoverAlignPosition);

			// Update nodeToHighlight if an element is found.

			setNodeToHighlight(getNodeToHighlight(nodeToHighlightSelector));

			// Initialize MutationObserver for continual changes in the DOM.

			const observer = new MutationObserver(() => {
				const selectedElement = document.querySelector(
					nodeToHighlightSelector
				);

				if (selectedElement) {
					setNodeToHighlight(selectedElement);
				}
				else {
					setNodeToHighlight(null);
				}
			});

			observer.observe(document.body, {
				attributes: true,
				childList: true,
				subtree: true,
			});

			return () => {
				observer.disconnect();
			};
		}
	}, [initialPopoverAlignPosition, nodeToHighlightSelector]);

	/**
	 * Performs the following:
	 * - Aligns the positioning of the hotspot and popover. Finds the
	 * appropriate side to display the popover where space allows.
	 * - If darkbg = false, a shadow around the nodeToHighlight is added and the
	 * shadow from the previous nodeToHighlight is removed.
	 * - Scrolls to the popover if the popover isn't visible in the viewport.
	 */
	const align = useCallback(
		(boundingRect) => {
			if (popoverVisible && popoverRef.current && nodeToHighlight) {
				const points = ALIGNMENTS_MAP[popoverAlignPosition];

				const alignment = doAlign({
					offset: [OVERLAY_OFFSET_X, OVERLAY_OFFSET_Y],
					overflow: {
						adjustX: true,
						adjustY: true,
					},
					points,
					sourceElement: popoverRef.current,
					targetElement: nodeToHighlight,
				});

				const alignmentString = alignment.points.join('');

				const pointsString = points.join('');

				if (alignment.overflow.adjustX) {
					setPopoverAlignPosition(
						ALIGNMENTS_GUESS_MAP[alignmentString]
					);
				}
				else if (pointsString !== alignmentString) {
					setPopoverAlignPosition(
						ALIGNMENTS_INVERSE_MAP[alignmentString]
					);
				}

				if (!darkbg) {
					nodeToHighlight?.classList.add(
						'lfr-walkthrough-element-shadow'
					);

					if (
						previousNodeToHighlight &&
						nodeToHighlight !== previousNodeToHighlight
					) {
						previousNodeToHighlight?.classList.remove(
							'lfr-walkthrough-element-shadow'
						);
					}
				}

				if (!isVisibleInViewport(popoverRef.current, boundingRect)) {
					popoverRef.current.scrollIntoView();
				}
			}
		},
		[
			darkbg,
			nodeToHighlight,
			popoverAlignPosition,
			previousNodeToHighlight,
			popoverVisible,
		]
	);

	useEffect(() => {
		align();
	}, [align]);

	useObserveRect(align, popoverRef?.current);

	useClickOutside(
		['.lfr-walkthrough-popover', '.lfr-walkthrough-hotspot'],
		() => {
			if (closeOnClickOutside) {
				onPopoverVisibleChange(false);
			}
		}
	);

	if (!nodeToHighlight) {
		return null;
	}

	if (!popoverVisible) {
		return (
			<Hotspot
				nodeToHighlight={nodeToHighlight}
				onClick={() => onPopoverVisibleChange(true)}
				ref={hotspotRef}
			/>
		);
	}

	return (
		<>
			{darkbg && (
				<Overlay
					popoverVisible={popoverVisible}
					trigger={nodeToHighlight}
				/>
			)}

			<ReactPortal>
				<ClayPopover
					alignPosition={popoverAlignPosition}
					className="lfr-walkthrough-popover"
					displayType="secondary"
					header={
						<ClayLayout.ContentRow noGutters verticalAlign="center">
							<ClayLayout.ContentCol expand>
								{/* @TODO: Use language key */}

								<span>{`Step ${
									currentStepIndex + 1
								} of ${totalStepCount}: ${title}`}</span>
							</ClayLayout.ContentCol>

							{closeable && (
								<ClayLayout.ContentCol>
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'close'
										)}
										className="close"
										displayType="unstyled"
										onClick={() =>
											onPopoverVisibleChange(false)
										}
										small
										symbol="times"
									/>
								</ClayLayout.ContentCol>
							)}
						</ClayLayout.ContentRow>
					}
					onShowChange={onPopoverVisibleChange}
					ref={popoverRef}
					show={popoverVisible}
					size="lg"
				>
					<div
						dangerouslySetInnerHTML={{
							__html: content,
						}}
					/>

					<ClayLayout.ContentRow noGutters verticalAlign="center">
						{skippable && (
							<ClayLayout.ContentCol expand>
								<ClayCheckbox
									checked={doNotShowMeThisAgainValue}
									label={Liferay.Language.get(
										'do-not-show-me-this-again'
									)}
									onChange={() => {
										setDoNotShowMeThisAgainValue(
											!doNotShowMeThisAgainValue
										);
									}}
								/>
							</ClayLayout.ContentCol>
						)}

						<ClayLayout.ContentCol>
							<ClayButton.Group spaced>
								{currentStepIndex > 0 && (
									<ClayButton
										displayType="secondary"
										onClick={onPreviousClick}
										small
									>
										{Liferay.Language.get('previous')}
									</ClayButton>
								)}

								<ClayButton onClick={onNextClick} small>
									{currentStepIndex + 1 !== totalStepCount
										? Liferay.Language.get('ok')
										: Liferay.Language.get('close')}
								</ClayButton>
							</ClayButton.Group>
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>
				</ClayPopover>
			</ReactPortal>
		</>
	);
}

export default Step;
