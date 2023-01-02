/* eslint-disable radix */
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
	differenceInMonths,
	endOfMonth,
	endOfWeek,
	endOfYear,
	format,
	isSameDay,
	isSameMonth,
	isSameWeek,
	isSameYear,
	startOfMonth,
	startOfWeek,
	startOfYear,
} from 'date-fns';

import {
	DAYS,
	HOURS,
	LAST_180_DAYS,
	LAST_30_DAYS,
	LAST_7_DAYS,
	LAST_90_DAYS,
	LAST_YEAR,
	MONTHS,
	TODAY,
	WEEKS,
	YEARS,
	YESTERDAY,
} from './chartConstants.es';

export function formatMonthDate(date, timeRange) {
	const currentDate = new Date(date);
	const dateEnd = new Date(timeRange.dateEnd);
	const dateStart = new Date(timeRange.dateStart);

	let firstDayOfMonth = startOfMonth(currentDate);
	let lastDayOfMonth = endOfMonth(currentDate);

	if (isSameMonth(currentDate, dateStart)) {
		firstDayOfMonth = currentDate;
	}
	else if (isSameMonth(currentDate, dateEnd)) {
		lastDayOfMonth = dateEnd;
	}

	if (isSameMonth(firstDayOfMonth, lastDayOfMonth)) {
		return format(firstDayOfMonth, "MMM dd',' yyyy");
	}

	return `${format(firstDayOfMonth, 'MMM dd')}-${format(
		lastDayOfMonth,
		"dd',' yyyy"
	)}`;
}

export function formatWeekDate(date, timeRange) {
	const currentDate = new Date(date);
	const dateEnd = new Date(timeRange.dateEnd);
	const dateStart = new Date(timeRange.dateStart);

	let firstDayOfWeek = startOfWeek(currentDate);
	let lastDayOfWeek = endOfWeek(currentDate);

	if (isSameWeek(currentDate, dateStart)) {
		firstDayOfWeek = currentDate;
	}
	else if (isSameWeek(currentDate, dateEnd)) {
		lastDayOfWeek = dateEnd;
	}
	const firstMonth = format(firstDayOfWeek, 'MMM');
	const lastMonth = format(lastDayOfWeek, 'MMM');

	if (isSameDay(firstDayOfWeek, lastDayOfWeek)) {
		return format(firstDayOfWeek, 'MMM dd');
	}
	else if (firstMonth === lastMonth) {
		return `${format(firstDayOfWeek, 'MMM dd')}-${format(
			lastDayOfWeek,
			'DD'
		)}`;
	}

	return `${format(firstDayOfWeek, 'MMM dd')}-${format(
		lastDayOfWeek,
		'MMM dd'
	)}`;
}

export function formatWeekDateWithYear(date, timeRange) {
	const currentDate = new Date(date);
	const dateEnd = new Date(timeRange.dateEnd);
	const dateStart = new Date(timeRange.dateStart);

	let firstDayOfWeek = startOfWeek(currentDate);
	let lastDayOfWeek = endOfWeek(currentDate);

	if (isSameWeek(currentDate, dateStart)) {
		firstDayOfWeek = currentDate;
	}
	else if (isSameWeek(currentDate, dateEnd)) {
		lastDayOfWeek = dateEnd;
	}
	const firstMonth = format(firstDayOfWeek, 'MMM');
	const lastMonth = format(lastDayOfWeek, 'MMM');

	const firstYear = format(firstDayOfWeek, 'yyyy');
	const lastYear = format(lastDayOfWeek, 'yyyy');

	if (isSameDay(firstDayOfWeek, lastDayOfWeek)) {
		return format(firstDayOfWeek, "MMM dd',' yyyy");
	}
	else if (firstYear !== lastYear) {
		return `${format(firstDayOfWeek, "MMM dd',' yyyy")} - ${format(
			lastDayOfWeek,
			"MMM dd',' yyyy"
		)}`;
	}
	else if (firstMonth !== lastMonth) {
		return `${format(firstDayOfWeek, 'MMM dd')} - ${format(
			lastDayOfWeek,
			"MMM dd',' yyyy"
		)}`;
	}

	return `${format(firstDayOfWeek, 'MMM dd')} - ${format(
		lastDayOfWeek,
		"dd',' yyyy"
	)}`;
}

export function getRangeKey(timeRange) {
	const endDate = new Date(timeRange.dateEnd);
	const startDate = new Date(timeRange.dateStart);

	const diff = differenceInDays(startDate, endDate);

	const diffList = [
		TODAY,
		YESTERDAY,
		LAST_7_DAYS,
		LAST_30_DAYS,
		LAST_90_DAYS,
		LAST_180_DAYS,
		LAST_YEAR,
	];

	const diffItem = diffList.find((key) => key >= diff);
	if (typeof diffItem !== 'undefined') {
		return diffItem;
	}

	return LAST_YEAR;
}

export function formatXAxisDate(date, isAmPm, timeRangeKey, timeRange) {
	const currentDate = new Date(date);
	const rangeUnit = getRangeKey(timeRange);

	if (timeRangeKey === HOURS) {
		let datetPattern = "HH':'mm";

		if (isAmPm) {
			datetPattern = "hh':'mm a";
		}

		return format(currentDate, datetPattern);
	}
	else if (timeRangeKey === YEARS) {
		return format(currentDate, 'yyyy');
	}
	else if (
		[LAST_YEAR, LAST_180_DAYS].includes(rangeUnit) &&
		MONTHS === timeRangeKey
	) {
		return format(currentDate, 'MMM yyyy');
	}
	else if (timeRangeKey === MONTHS) {
		return format(currentDate, 'MMM');
	}
	else if (timeRangeKey === WEEKS) {
		return formatWeekDate(date, timeRange);
	}

	return format(currentDate, 'MMM dd');
}

export function formatYearDate(date, timeRange) {
	const currentDate = new Date(date);
	const dateEnd = new Date(timeRange.dateEnd);
	const dateStart = new Date(timeRange.dateStart);

	let firstDayOfYear = startOfYear(currentDate);
	let lastDayOfYear = endOfYear(currentDate);

	if (isSameYear(currentDate, dateStart)) {
		firstDayOfYear = currentDate;
	}
	else if (isSameYear(currentDate, dateEnd)) {
		lastDayOfYear = dateEnd;
	}

	if (isSameDay(firstDayOfYear, lastDayOfYear)) {
		return format(firstDayOfYear, "MMM dd',' yyyy");
	}

	return `${format(firstDayOfYear, 'MMM dd')}-${format(
		lastDayOfYear,
		"MMM dd',' yyyy"
	)}`;
}

export function getAxisMeasures(value) {
	const numChars = Math.floor(value).toString().length;
	const decOrder = Math.pow(10, numChars - 1);
	let maxValue = decOrder * Math.floor(value / decOrder) + decOrder;
	let firstDic = maxValue / decOrder;

	if ([3, 7, 9].indexOf(firstDic) > -1) {
		firstDic += 1;
	}
	maxValue = firstDic * decOrder;
	let intervalCount = 4;

	if ([1, 5, 10].indexOf(firstDic) > -1) {
		intervalCount = 5;
	}
	const intervalValue = maxValue / intervalCount;

	for (let i = 0; i < intervalCount; i++) {
		const tempMaxValue = intervalValue * (i + 1);
		if (tempMaxValue > value) {
			maxValue = tempMaxValue;
			intervalCount = i + 1;
			break;
		}
	}
	const intervals = [];
	intervals.push(0);

	for (let i = 0; i < intervalCount; i++) {
		intervals.push(intervalValue * (i + 1));
	}

	return {
		intervalCount,
		intervalValue,
		intervals,
		maxValue,
	};
}

export function getAxisMeasuresFromData(data) {
	return getAxisMeasures(
		Math.max(
			...data
				.reduce((prev, next) => prev.concat(next), [])
				.filter((value) => typeof value === 'number')
		)
	);
}

export function getXAxisIntervals(timeRange, keys, type) {
	const endDate = new Date(timeRange.dateEnd);
	const secondDate = new Date(keys[1]);
	const startDate = new Date(timeRange.dateStart);

	const lengthKeys = keys.length;

	const diffLeftDays = differenceInDays(startDate, secondDate);

	const diffLeftMonths = differenceInMonths(startDate, secondDate);

	const nextToLastDay = new Date(keys[lengthKeys - 2]);

	const diffRightDays = differenceInDays(endDate, nextToLastDay);

	const diffMap = {
		[TODAY]: () => {
			return {
				offset: 4,
				padLeft: 0,
				padRight: 0,
			};
		},
		[YESTERDAY]: () => {
			return {
				offset: 6,
				padLeft: 0,
				padRight: 0,
			};
		},
		// eslint-disable-next-line sort-keys
		[LAST_7_DAYS]: () => {
			return {
				offset: 1,
				padLeft: 0,
				padRight: 0,
			};
		},
		[LAST_30_DAYS]: (type) => {
			if (type === DAYS) {
				return {
					offset: 6,
					padLeft: 0,
					padRight: diffRightDays < 2 ? diffRightDays + 2 : 0,
				};
			}

			return {
				offset: 1,
				padLeft: 0,
				padRight: 0,
			};
		},
		[LAST_90_DAYS]: (type) => {
			if (type === DAYS) {
				return {
					offset: 11,
					padLeft: 0,
					padRight: 3,
				};
			}
			else if (type === WEEKS) {
				const pad = diffLeftDays < 7 ? 3 : 0;

				return {
					offset: 2,
					padLeft: pad,
					padRight: pad,
				};
			}

			return {
				offset: 1,
				padLeft: 0,
				padRight: 0,
			};
		},
		[LAST_180_DAYS]: (type) => {
			if (type === WEEKS) {
				return {
					offset: 4,
					padLeft: 0,
					padRight: 3,
				};
			}

			return {
				offset: 1,
				padLeft: diffLeftDays < 14 ? 1 : 0,
				padRight: 0,
			};
		},
		[LAST_YEAR]: (type) => {
			if (type === WEEKS) {
				const lengthWeek = lengthKeys === 52 ? 5 : 6;

				return {
					offset: parseInt(lengthKeys / lengthWeek),
					padLeft: 0,
					padRight: 6,
				};
			}
			else if (type === YEARS) {
				return {
					offset: lengthKeys > 12 ? parseInt(lengthKeys / 6) : 1,
					padLeft:
						(lengthKeys > 12 && diffLeftMonths < 7) ||
						diffLeftMonths < 2
							? 1
							: 0,
					padRight: 0,
				};
			}

			return {
				offset: parseInt(lengthKeys / 5),
				padLeft: 0,
				padRight: 0,
			};
		},
	};
	const diffMapKey = getRangeKey(timeRange);

	const diffIndex = diffMap[diffMapKey](type);

	return keys.filter(
		(key, index) =>
			index === 0 ||
			index === lengthKeys - 1 ||
			(index % diffIndex.offset === 0 &&
				index > diffIndex.padLeft &&
				index < lengthKeys - diffIndex.padRight + 1)
	);
}
