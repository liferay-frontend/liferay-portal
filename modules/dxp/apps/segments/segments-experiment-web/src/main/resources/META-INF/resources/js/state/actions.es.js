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

export function addSegmentsExperiment(payload) {
	return {
		payload,
		type: 'ADD_EXPERIMENT',
	};
}

export function addVariant(payload) {
	return {
		payload,
		type: 'ADD_VARIANT',
	};
}

export function archiveExperiment(payload) {
	return {
		payload,
		type: 'ARCHIVE_EXPERIMENT',
	};
}

export function closeCreationModal() {
	return {
		type: 'CREATE_EXPERIMENT_FINISH',
	};
}

export function closeEditionModal() {
	return {
		type: 'EDIT_EXPERIMENT_FINISH',
	};
}

export function closeReviewAndRunExperiment() {
	return {
		type: 'REVIEW_AND_RUN_EXPERIMENT_FINISH',
	};
}

export function deleteArchivedExperiment(experimentId) {
	return {
		payload: {
			experimentId,
		},
		type: 'DELETE_ARCHIVED_EXPERIMENT',
	};
}

export function editSegmentsExperiment(payload) {
	return {
		payload,
		type: 'EDIT_EXPERIMENT',
	};
}

export function openCreationModal(payload) {
	return {
		payload,
		type: 'CREATE_EXPERIMENT_START',
	};
}

export function openEditionModal(payload) {
	return {
		payload,
		type: 'EDIT_EXPERIMENT_START',
	};
}

export function reviewAndRunExperiment() {
	return {
		type: 'REVIEW_AND_RUN_EXPERIMENT',
	};
}

export function reviewVariants() {
	return {
		type: 'REVIEW_VARIANTS',
	};
}

export function reviewClickTargetElement() {
	return {
		type: 'REVIEW_CLICK_TARGET_ELEMENT',
	};
}

export function runExperiment({experiment, splitVariantsMap}) {
	return {
		payload: {
			experiment,
			splitVariantsMap,
		},
		type: 'RUN_EXPERIMENT',
	};
}

export function updateSegmentsExperimentTarget(payload) {
	return {
		payload,
		type: 'UPDATE_SEGMENTS_EXPERIMENT_TARGET',
	};
}

export function updateSegmentsExperimentStatus(payload) {
	return {
		payload,
		type: 'UPDATE_EXPERIMENT_STATUS',
	};
}

export function updateVariant(payload) {
	return {
		payload,
		type: 'UPDATE_VARIANT',
	};
}

export function updateVariants(payload) {
	return {
		payload,
		type: 'UPDATE_VARIANTS',
	};
}
