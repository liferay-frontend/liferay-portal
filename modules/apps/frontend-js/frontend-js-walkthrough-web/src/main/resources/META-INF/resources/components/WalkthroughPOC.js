/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './Walkthrough.scss';

import React, {useEffect, useState} from 'react';

import {useLocalStorage} from '../hooks/useLocalStorage';
import Step from './Step';

/**
 * Gets the step configuration and index for the matching step id.
 * Index is used to show the step count in the title (i.e. "Step 1 of 2") and
 * to easily get the next or previous step like `stepIndex + 1`.
 * @param {object} walkthroughConfiguration
 * @param {number} stepId
 * @returns {Array} [stepConfiguration, stepIndex]
 */
function getStepConfiguration(walkthroughConfiguration, stepId) {
	if (!walkthroughConfiguration) {
		return [null, 0];
	}

	const stepIndex = walkthroughConfiguration?.steps?.findIndex(
		(stepConfiguration) => stepConfiguration.id === stepId
	);

	const stepConfiguration = walkthroughConfiguration?.steps?.[stepIndex];

	if (stepConfiguration) {
		return [stepConfiguration, stepIndex];
	}

	// If no matching step ID is found, return the first step.

	return [walkthroughConfiguration?.steps?.[0], 0];
}

/**
 * Gets the walkthrough state with the matching ID.
 * @param {object} walkthroughStates Local storage walkthrough states.
 * @param {string} walkthroughId ID defined in the walkthrough configuration.
 * @return {object} A walkthrough state object.
 */
function getWalkthroughState(walkthroughStates, walkthroughId) {
	return walkthroughStates.find(
		(walkthroughState) => walkthroughState.id === walkthroughId
	);
}

/**
 * Displays the walkthrough defined by the `walkthroughConfiguration` state.
 *
 * This component focuses on managing the walkthrough state and using the
 * correct walkthrough configuration when there are multiple walkthroughs.
 */
function Walkthrough({configuration}) {
	const [walkthroughStates, setWalkthroughStates] = useLocalStorage(
		`${themeDisplay.getUserId()}-walkthroughStates`,
		[]
	);

	const [walkthroughConfiguration, setWalkthroughConfiguration] = useState(
		configuration
	);

	const currentWalkthroughState = getWalkthroughState(
		walkthroughStates,
		walkthroughConfiguration?.id
	);

	const [currentStepConfiguration, currentStepIndex] = getStepConfiguration(
		walkthroughConfiguration,
		currentWalkthroughState?.currentStep
	);

	/**
	 * A helper function to update the walkthrough state for the current
	 * walkthrough by finding the matching ID and merging with the passed-in
	 * `newConfiguration` object.
	 * @param {object} newConfiguration
	 */
	const updateWalkthroughStates = (newConfiguration) => {
		setWalkthroughStates(
			walkthroughStates.map((walkthrough) => {
				if (walkthrough.id === currentWalkthroughState?.id) {
					return {
						...walkthrough,
						...newConfiguration,
					};
				}
				else {
					return walkthrough;
				}
			})
		);
	};

	/**
	 * When the "next" button is clicked in the Step popover.
	 */
	const handleNextClick = () => {
		if (isLastStep()) {
			updateWalkthroughStates({dismissed: true, popoverVisible: false});
		}
		else {
			const nextIndex = currentStepIndex + 1;

			const nextStepConfiguration =
				walkthroughConfiguration?.steps?.[nextIndex];

			if (nextStepConfiguration) {
				if (currentStepConfiguration.pause) {
					updateWalkthroughStates({
						paused: true,
					});
				}
				else {
					updateWalkthroughStates({
						currentStep: nextStepConfiguration.id,
					});
				}
			}
		}

		// Perform `onNext` function if defined.

		if (typeof currentStepConfiguration.onNext === 'function') {
			currentStepConfiguration.onNext(
				document.querySelector(currentStepConfiguration.nodeToHighlight)
			);
		}
	};

	/**
	 * Update the walkthrough state when popover is opened or closed.
	 */
	const handlePopoverVisibleChange = (value) => {
		updateWalkthroughStates({
			popoverVisible: value,
		});
	};

	/**
	 * When the "previous" button is clicked in the Step popover.
	 */
	const handlePreviousClick = () => {
		const previousIndex = currentStepIndex - 1;

		const previousStepConfiguration =
			walkthroughConfiguration?.steps?.[previousIndex];

		if (previousStepConfiguration) {
			updateWalkthroughStates({
				currentStep: previousStepConfiguration.id,
			});
		}
	};

	/**
	 * If the current step is the last step.
	 * @returns {boolean}
	 */
	const isLastStep = () => {
		return currentStepIndex + 1 >= walkthroughConfiguration?.steps?.length;
	};

	/**
	 * Setup event handlers for Liferay.Walkthrough functions.
	 */
	useEffect(() => {

		/**
		 * Adds a new walkthrough state object if one doesn't exist yet.
		 */
		function handleInitialize({configuration}) {
			const walkthroughIdExists = walkthroughStates.some(
				(walkthroughState) => walkthroughState.id === configuration.id
			);

			if (!walkthroughIdExists) {
				setWalkthroughStates([
					...walkthroughStates,
					{
						dismissed: false,
						id: configuration.id,
						paused: false,
						popoverVisible: false,
					},
				]);
			}

			// Set configuration if it isn't dismissed.

			const walkthroughState = getWalkthroughState(
				walkthroughStates,
				configuration.id
			);

			if (!walkthroughState?.dismissed || !walkthroughState) {
				setWalkthroughConfiguration(configuration);
			}
		}

		/**
		 * Finds the matching walkthrough ID and replaces the current step ID.
		 */
		function handleSetStep({stepId, walkthroughId}) {
			setWalkthroughStates(
				walkthroughStates.map((walkthrough) => {
					if (walkthrough.id === walkthroughId) {
						return {
							...walkthrough,
							currentStep: stepId,
							paused: false,
							popoverVisible: true,
						};
					}
					else {
						return walkthrough;
					}
				})
			);
		}

		Liferay.on('walkthroughInitialize', handleInitialize);
		Liferay.on('walkthroughSetStep', handleSetStep);

		return () => {
			Liferay.detach('walkthroughInitialize', handleInitialize);
			Liferay.detach('walkthroughSetStep', handleSetStep);
		};
	}, [walkthroughStates, setWalkthroughStates]);

	if (currentWalkthroughState?.dismissed || currentWalkthroughState?.paused) {
		return null;
	}

	return (
		<Step
			closeOnClickOutside={walkthroughConfiguration?.closeOnClickOutside}
			closeable={walkthroughConfiguration?.closeable}
			content={currentStepConfiguration?.content}
			currentStepIndex={currentStepIndex}
			darkbg={walkthroughConfiguration?.darkbg}
			initialPopoverAlignPosition={currentStepConfiguration?.positioning}
			nodeToHighlightSelector={currentStepConfiguration?.nodeToHighlight}
			onNextClick={handleNextClick}
			onPopoverVisibleChange={handlePopoverVisibleChange}
			onPreviousClick={handlePreviousClick}
			popoverVisible={currentWalkthroughState?.popoverVisible}
			skippable={walkthroughConfiguration?.skippable}
			title={currentStepConfiguration?.title}
			totalStepCount={walkthroughConfiguration?.steps?.length}
		/>
	);
}

export default Walkthrough;
