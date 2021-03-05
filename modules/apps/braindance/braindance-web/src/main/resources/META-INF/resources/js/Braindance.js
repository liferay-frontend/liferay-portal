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

import ClayPopover from '@clayui/popover';
import React, {useEffect, useState} from 'react';

import analyzeReality from './analyzeReality';

import '../css/Braindance.scss';

const HUDElement = ({align, info, name, size, x, y}) => {
	const [show, setShow] = useState();

	const handleMouseOut = () => {
		setShow(false);
	};

	const handleMouseOver = () => {
		setShow(true);
	};

	return (
		<ClayPopover
			alignPosition={align}
			header={name}
			show={show}
			trigger={
				<div
					className="braindance-hud"
					onMouseOut={handleMouseOut}
					onMouseOver={handleMouseOver}
					style={{
						height: `${size}px`,
						left: x - size / 2,
						top: y - size / 2,
						width: `${size}px`,
					}}
				></div>
			}
		>
			{info}
		</ClayPopover>
	);
};

export default ({activeSenses = []}) => {
	const [realityElements, setRealityElements] = useState([]);

	useEffect(() => {
		const {elements} = analyzeReality();
		setRealityElements(elements);
	}, []);

	return (
		<div className="braindance-simulation">
			<img
				className="braindance-image"
				src="https://steamuserimages-a.akamaihd.net/ugc/2438013375536940927/D370DBF7BFC83ED36F783F08A598FFF3E71A1D61/"
			/>

			{realityElements.map((element) => (
				<>
					{activeSenses.includes(element.sense) && (
						<HUDElement
							align={element.popoverAlign}
							info={element.info}
							key={element.id}
							name={element.name}
							size={element.size}
							x={element.x}
							y={element.y}
						/>
					)}
				</>
			))}
		</div>
	);
};
