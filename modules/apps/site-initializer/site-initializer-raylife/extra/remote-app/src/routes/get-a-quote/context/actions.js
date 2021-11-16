export const ActionTypes = {
	SET_SELECTED_PRODUCT: 'SET_SELECTED_PRODUCT',
	SET_SELECTED_STEP: 'SET_SELECTED_STEP',
	SET_SELECTED_TRIGGER: 'SET_SELECTED_TRIGGER',
};

/**
 * @param {{
 *  title: string,
 *  section: string,
 *  subsection: string,
 *  percentage: number,
 * }} payload - selectedStep
 * @returns {Object} action object used by the reducer
 */
export function setSelectedStep(payload) {
	return {
		payload,
		type: ActionTypes.SET_SELECTED_STEP,
	};
}

export function setSelectedTrigger(payload) {
	return {
		payload,
		type: ActionTypes.SET_SELECTED_TRIGGER,
	};
}

export function setSelectedProduct(payload) {
	return {
		payload,
		type: ActionTypes.SET_SELECTED_PRODUCT,
	};
}
