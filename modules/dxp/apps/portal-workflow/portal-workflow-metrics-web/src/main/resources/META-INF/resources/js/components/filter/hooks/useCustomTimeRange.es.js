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

import {isAfter, isBefore, isValid} from 'date-fns';
import {useState} from 'react';

import {getCapitalizedFilterKey} from '../../../shared/components/filter/util/filterUtil.es';
import {useFilter} from '../../../shared/hooks/useFilter.es';
import {useRouterParams} from '../../../shared/hooks/useRouterParams.es';
import {getLocaleDateFormat} from '../../../shared/util/date.es';
import {
	convertQueryDate,
	formatDateTime,
	parseDate,
} from '../util/timeRangeUtil.es';

const updateErrors = (errors, fieldName, message) => ({
	...(errors || {}),
	[fieldName]: message,
});

const validateDate = (dateEnd, dateStart) => {
	const dateNow = new Date();
	let errors;

	if (!isValid(dateEnd) || isAfter(dateEnd, dateNow)) {
		errors = updateErrors(
			errors,
			'dateEnd',
			Liferay.Language.get('please-enter-a-valid-date')
		);
	}

	if (!isValid(dateStart) || isAfter(dateStart, dateNow)) {
		errors = updateErrors(
			errors,
			'dateStart',
			Liferay.Language.get('please-enter-a-valid-date')
		);
	}

	return errors;
};

const validateEarlierDate = (dateEnd, dateStart) => {
	const earlierDate = new Date(1970, 0, 1);
	let errors;

	if (isBefore(dateEnd, earlierDate)) {
		errors = updateErrors(
			errors,
			'dateEnd',
			Liferay.Language.get('the-date-cannot-be-earlier-than-1970')
		);
	}

	if (isBefore(dateStart, earlierDate)) {
		errors = updateErrors(
			errors,
			'dateStart',
			Liferay.Language.get('the-date-cannot-be-earlier-than-1970')
		);
	}

	return errors;
};

const validateRangeConsistency = (dateEnd, dateStart) => {
	let errors;

	if (isBefore(dateEnd, dateStart)) {
		errors = updateErrors(
			errors,
			'dateEnd',
			Liferay.Language.get(
				'the-end-date-cannot-be-earlier-than-the-start-date'
			)
		);
	}

	if (isAfter(dateStart, dateEnd)) {
		errors = updateErrors(
			errors,
			'dateStart',
			Liferay.Language.get(
				'the-start-date-cannot-be-later-than-the-end-date'
			)
		);
	}

	return errors;
};

const useCustomTimeRange = (prefixKey, withoutRouteParams) => {
	const [errors, setErrors] = useState(undefined);
	const {filters} = useRouterParams();
	const {filterValues} = useFilter({
		withoutRouteParams,
	});

	const dateEndKey = getCapitalizedFilterKey(prefixKey, 'dateEnd');
	const dateFormat = getLocaleDateFormat();
	const dateStartKey = getCapitalizedFilterKey(prefixKey, 'dateStart');

	const values = !withoutRouteParams ? filters : filterValues;

	const [dateEnd, setDateEnd] = useState(
		convertQueryDate(values[dateEndKey], dateFormat)
	);
	const [dateStart, setDateStart] = useState(
		convertQueryDate(values[dateStartKey], dateFormat)
	);

	const applyCustomFilter = (handleApply) => {
		const {dateEnd: dateEndError, dateStart: dateStartError} = errors || {};

		if (!dateEndError && !dateStartError) {
			handleApply({
				dateEnd: formatDateTime(dateEnd, dateFormat, true),
				dateStart: formatDateTime(dateStart, dateFormat),
				key: 'custom',
			});
		}
	};

	const validate = () => {
		const dateEndParsed = parseDate(dateEnd, dateFormat);
		const dateStartParsed = parseDate(dateStart, dateFormat);

		const errors = {
			...validateDate(dateEndParsed, dateStartParsed),
			...validateEarlierDate(dateEndParsed, dateStartParsed),
			...validateRangeConsistency(dateEndParsed, dateStartParsed),
		};

		setErrors(errors);

		return errors;
	};

	return {
		applyCustomFilter,
		dateEnd,
		dateFormat,
		dateStart,
		errors,
		setDateEnd,
		setDateStart,
		validate,
	};
};

export {useCustomTimeRange};
