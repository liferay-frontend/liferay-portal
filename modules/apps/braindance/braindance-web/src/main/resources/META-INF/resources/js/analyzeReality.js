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

export default function analyzeReality() {
	return {
		elements: [
			{
				id: 1,
				name: 'Flickering Light',
				info: 'That neon light is flickering, is quite anoying.',
				popoverAlign: 'right',
				sense: 'see',
				size: 60,
				x: 58,
				y: 232,
			},
			{
				id: 2,
				name: 'Wet balaustrade',
				info: 'It\'s raining, genius.',
				popoverAlign: 'top',
				sense: 'touch',
				size: 50,
				x: 475,
				y: 440,
			},
			{
				id: 3,
				name: 'Cigarette',
				info: 'That smells like a factory, with vanilla touches.',
				popoverAlign: 'left',
				sense: 'smell',
				size: 200,
				x: 1090,
				y: 315,
			},
			{
				id: 4,
				name: 'Hyperloop',
				info: 'The last design from Alon Misk, based on magnetic levitation that vehicle doesn\'t make a sound.',
				popoverAlign: 'left',
				sense: 'hear',
				size: 140,
				x: 700,
				y: 140,
			}
		]
	};
}