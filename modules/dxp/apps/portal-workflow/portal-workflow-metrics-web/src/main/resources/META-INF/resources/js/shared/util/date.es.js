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

import {format, getDefaultOptions, isDate, isValid, parse} from 'date-fns';
import {enUS} from 'date-fns/locale';

const defaultDateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

const formatDate = (
	date = new Date(),
	formatString = 'P',
	fromFormat = null
) => {
	if (!isDate(date) && fromFormat) {
		date = parse(date, fromFormat, new Date());
	}

	if (!isDate(date)) {
		date = new Date(date);
	}

	return format(date, formatString);
};

const getLocaleDateFormat = (width = 'short') => {
	const {locale = enUS} = getDefaultOptions();

	return locale.formatLong.date({width});
};

const getMaskByDateFormat = (formatString) => {
	const mask = [];

	for (let i = 0; i < formatString.length; i++) {
		if (/[a-z]/i.test(formatString[i])) {
			mask.push(/\d/);
		}
		else {
			mask.push(`${formatString[i]}`);
		}
	}

	return mask;
};

const isValidDate = (date, formatString = 'P') => {
	return isValid(parse(date, formatString, new Date()));
};

export {
	defaultDateFormat,
	formatDate,
	getLocaleDateFormat,
	getMaskByDateFormat,
	isValidDate,
};
