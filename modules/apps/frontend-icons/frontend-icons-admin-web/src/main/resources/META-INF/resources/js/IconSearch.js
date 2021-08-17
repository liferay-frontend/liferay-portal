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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import React, {useMemo, useState} from 'react';

/**
 * Component that allows searching through icons
 * @param {string} label - Label of the search input field.
 * @param {string} placeholder - Placeholder of the search input field.
 * @param {string} type - Type of icons being provided, supports "icons" and "flags"
 */
const IconSearch = ({
	icons,
	label = 'Search Icons',
	placeholder = 'Search Icons...',
	portletNamespace,
}) => {
	icons = JSON.parse(icons);

	const iconPackNames = Object.keys(icons);

	const [searchQuery, setSearchQuery] = useState('');

	const filteredIcons = useMemo(() => {
		return iconPackNames.map((packName) => {
			icons[packName].filter((icon) =>
				icon.toLowerCase().includes(searchQuery.toLocaleLowerCase())
			);
		});
	}, [searchQuery, icons]);

	return (
		<>
			<h4>Icon Packs</h4>

			<ClayForm.Group>
				<label className="form-control-label">
					<span className="form-control-label-text">{label}</span>

					<ClayInput
						onChange={(event) => setSearchQuery(event.target.value)}
						placeholder={placeholder}
						type="text"
						value={searchQuery}
					/>
				</label>
			</ClayForm.Group>

			<ClayForm.Group>
				<ClayPanel.Group>
					{iconPackNames.map((iconPackName) => (
						<ClayPanel
							collapsable
							displayTitle={iconPackName}
							displayType="secondary"
							key={iconPackName}
							showCollapseIcon={true}
						>
							<ClayPanel.Body className="list-group-card">
								<ul className="list-group">
									{icons[iconPackName].sort().map((icon) => (
										<li
											className="list-group-card-item w-25"
											key={icon}
										>
											<ClayButton displayType={null}>
												<ClayIcon
													spritemap="/o/icons/clay.svg"
													symbol={icon}
												/>

												<span className="list-group-card-item-text">
													{icon}
												</span>
											</ClayButton>
										</li>
									))}
								</ul>
							</ClayPanel.Body>
						</ClayPanel>
					))}

					{!iconPackNames.find(
						(packName) => icons[packName].length
					) && <span>{`No results found for ${searchQuery}`}</span>}
				</ClayPanel.Group>
			</ClayForm.Group>

			<h4>Add Custom Icon</h4>

			<ClayForm.Group>
				<label htmlFor={portletNamespace + 'iconPack'}>Pack Name</label>

				<ClayInput
					name={portletNamespace + 'iconPack'}
					placeholder="Name"
					type="text"
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor={portletNamespace + 'name'}>Icon Name</label>

				<ClayInput
					name={portletNamespace + 'name'}
					placeholder="Name"
					type="text"
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor={portletNamespace + 'svgFile'}>SVG File</label>

				<ClayInput
					name={portletNamespace + 'svgFile'}
					placeholder="Paste SVG content"
					type="file"
				/>
			</ClayForm.Group>
		</>
	);
};

export default IconSearch;
