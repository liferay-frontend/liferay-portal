/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const getRandomPositiveReview = () => {
	switch (Math.floor(Math.random() * 5)) {
		case 0:
			return 'Life Changing!';
		case 1:
			return 'Brilliant!';
		case 2:
			return 'Astonishing!';
		case 3:
			return 'Bewildering!';
		default:
			return 'Amazing!';
	}
};

const getRandomUserName = () => {
	switch (Math.floor(Math.random() * 5)) {
		case 0:
			return 'Mitch';
		case 1:
			return 'Jimmy';
		case 2:
			return 'Susan';
		case 3:
			return 'Johnny';
		default:
			return 'Mary';
	}
};

const getReviewsHTML = () => {
	const reviews = [];

	for (let i = 0; i < 10; i++) {
		reviews.push(
			`<div>⭐⭐⭐⭐⭐</div><div>${getRandomUserName()}: ${getRandomPositiveReview()}</div><hr />`
		);
	}

	return reviews.join('');
};

export {getReviewsHTML};
