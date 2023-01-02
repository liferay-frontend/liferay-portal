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

import {
	differenceInDays,
	differenceInYears,
	endOfDay,
	format,
	isDate,
	isValid,
	parse,
	startOfDay,
} from 'date-fns';

import {
	defaultDateFormat,
	formatDate,
	getLocaleDateFormat,
	isValidDate,
} from '../../../shared/util/date.es';

const convertQueryDate = (date = '', formatString = 'P') => {
	return format(new Date(decodeURIComponent(date)), formatString);
};

const parseDate = (date, formatString = 'P') => {
	if (!isValid(date)) {
		date = parse(date, formatString, new Date());
	}

	return date;
};

const formatDateTime = (date, formatString, isEndDate) => {
	if (!isDate(date)) {
		date = new Date(date);
	}

	let dateTime = parseDate(date, formatString || 'P');

	dateTime = isEndDate ? endOfDay(dateTime) : startOfDay(dateTime);

	return format(dateTime, defaultDateFormat);
};

const formatDescriptionDate = (date) => {
	return formatDate(
		date && decodeURIComponent(date),
		getLocaleDateFormat('medium'),
		defaultDateFormat
	);
};

const getFormatPattern = (dateEnd, dateStart, isAmPm) => {
	let dateStartPattern = 'PP';

	if (differenceInDays(dateStart, dateEnd) <= 1) {
		if (isAmPm) {
			dateStartPattern = "MMM dd',' hh':'mm a";
		}
		else {
			dateStartPattern = "MMM dd',' HH':'mm";
		}
	}
	else if (differenceInYears(dateStart, dateEnd) < 1) {
		dateStartPattern = 'MMM dd';
	}

	let dateEndPattern = dateStartPattern;

	if (differenceInDays(dateStart, dateEnd) > 90) {
		dateEndPattern = 'PP';
	}

	return {
		dateEndPattern,
		dateStartPattern,
	};
};

const formatTimeRange = (timeRange, isAmPm) => {
	const {dateEnd, dateStart} = timeRange;

	if (!dateEnd && !dateStart) {
		return null;
	}

	const {dateEndPattern, dateStartPattern} = getFormatPattern(
		dateEnd,
		dateStart,
		isAmPm
	);

	return `${format(new Date(dateStart), dateStartPattern)} - ${format(
		new Date(dateEnd),
		dateEndPattern
	)}`;
};

const getCustomTimeRange = (dateEnd, dateStart) => {
	const customTimeRange = {
		active: false,
		dateEnd: dateEnd ? decodeURIComponent(dateEnd) : undefined,
		dateStart: dateStart ? decodeURIComponent(dateStart) : undefined,
		dividerAfter: true,
		id: 'custom',
		name: Liferay.Language.get('custom-range'),
	};

	customTimeRange.resultName = `${formatDescriptionDate(
		dateStart
	)} - ${formatDescriptionDate(dateEnd)}`;

	return customTimeRange;
};

const getTimeRangeParams = (dateStartEncoded = '', dateEndEncoded = '') => {
	let params = {};

	const dateEnd = decodeURIComponent(dateEndEncoded);
	const dateStart = decodeURIComponent(dateStartEncoded);

	if (
		isValidDate(dateEnd, defaultDateFormat) &&
		isValidDate(dateStart, defaultDateFormat)
	) {
		params = {
			dateEnd,
			dateStart,
		};
	}

	return params;
};

const parseDateItems = (isAmPm) => (items) => {
	return items.map((item) => {
		const parsedItem = {
			...item,
			dateEnd: item.dateEnd,
			dateStart: item.dateStart,
			key: item.key,
		};

		if (parsedItem.key !== 'custom') {
			parsedItem.description = formatTimeRange(item, isAmPm);
		}

		return parsedItem;
	});
};

export {
	convertQueryDate,
	formatDateTime,
	formatDescriptionDate,
	formatTimeRange,
	getCustomTimeRange,
	getTimeRangeParams,
	isValidDate,
	parseDate,
	parseDateItems,
};
