export const NAICS_ALLOWED = {
	OWN_BRAND_LABEL: ['453910', '722513', '424930'],
	PERCENT_SALES: ['453910', '424930'],
};
export const SEGMENT_ALLOWED = {
	OVERALL_SALES: ['Retail'],
};

export function businessTotalFields(properties) {
	let fieldCount = 4;

	if (NAICS_ALLOWED.OWN_BRAND_LABEL.includes(properties?.naics)) {
		fieldCount++;
	}
	if (NAICS_ALLOWED.PERCENT_SALES.includes(properties?.naics)) {
		fieldCount++;
	}
	if (SEGMENT_ALLOWED.OVERALL_SALES.includes(properties?.segment)) {
		fieldCount++;
	}

	return fieldCount;
}

export function validatePercentSales(naics) {
	return NAICS_ALLOWED.PERCENT_SALES.includes(naics);
}
export function validateOwnBrandLabel(naics) {
	return NAICS_ALLOWED.OWN_BRAND_LABEL.includes(naics);
}
export function validateOverallSales(segment) {
	return SEGMENT_ALLOWED.OVERALL_SALES.includes(segment);
}
