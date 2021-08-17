/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: BSD-3-Clause
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayButton from '@clayui/button';
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
	...otherProps
}) => {
	console.log(otherProps);
	const [searchQuery, setSearchQuery] = useState('');

	const filteredIcons = useMemo(() => {
		const query = searchQuery.toLowerCase();

		return icons.filter((name) => name.toLowerCase().includes(query));
	}, [searchQuery, icons]);

	const list = searchQuery ? filteredIcons : icons;

	const iconMap = list.reduce((acc, name) => {
		const category = name.split('_')[0];

		if (!acc[category]) {
			acc[category] = [];
		}

		acc[category].push(name);

		return acc;
	}, {});

	return (
		<>
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
				<h4>{'Icon Packs'}</h4>

				<ClayPanel.Group>
					{Object.keys(iconMap).map((category) => (
						<ClayPanel
							collapsable
							displayTitle={category}
							displayType="secondary"
							showCollapseIcon={true}
						>
							<ClayPanel.Body className="list-group-card">
								<ul className="list-group">
									{iconMap[category].sort().map((icon) => (
										<li
											className="list-group-card-item w-25"
											key={icon}
										>
											<ClayButton displayType={null}>
												<ClayIcon
													spritemap={
														window.location.href
													}
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

					{!list.length && (
						<span>{`No results found for ${searchQuery}`}</span>
					)}
				</ClayPanel.Group>
			</ClayForm.Group>

			<h4>{'Add Custom Icon'}</h4>

			<ClayForm.Group>
				<label htmlFor={portletNamespace + 'name'}>{'Icon Name'}</label>

				<ClayInput
					name={portletNamespace + 'name'}
					placeholder="Name"
					type="text"
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor={portletNamespace + 'svgContent'}>
					{'SVG Markup'}
				</label>

				<ClayInput
					component="textarea"
					name={portletNamespace + 'svgContent'}
					placeholder="Paste SVG content"
					type="text"
				/>
			</ClayForm.Group>
		</>
	);
};

export default IconSearch;
