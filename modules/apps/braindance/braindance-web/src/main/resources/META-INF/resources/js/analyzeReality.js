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
				info: 'That neon light is flickering, is quite anoying.',
				name: 'Flickering Light',
				popoverAlign: 'right',
				sense: 'see',
				size: 60,
				x: 58,
				y: 232,
			},
			{
				id: 2,
				info: "It's raining, genius.",
				name: 'Wet balaustrade',
				popoverAlign: 'top',
				sense: 'touch',
				size: 50,
				x: 475,
				y: 440,
			},
			{
				id: 3,
				info: 'That smells like a factory, with vanilla touches.',
				name: 'Cigarette',
				popoverAlign: 'left',
				sense: 'smell',
				size: 200,
				x: 1090,
				y: 315,
			},
			{
				id: 4,
				info:
					"The last design from Alon Misk, based on magnetic levitation that vehicle doesn't make a sound.",
				name: 'Hyperloop',
				popoverAlign: 'left',
				sense: 'hear',
				size: 140,
				x: 700,
				y: 140,
			},
		],
	};
}
