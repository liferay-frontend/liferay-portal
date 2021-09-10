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
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import React, {useMemo, useState} from 'react';

const IconSearch = ({
	initialIcons,
	label = Liferay.Language.get('search-icons'),
	placeholder = Liferay.Language.get('search-icons'),
	portletNamespace,
	submitURL,
	redirectURL,
	deleteURL,
}) => {
	const svgFileInputRef = React.useRef(null);
	const [icons, setIcons] = React.useState(JSON.parse(initialIcons));

	const [iconName, setIconName] = useState('');
	const [iconPackName, setIconPackName] = useState('');
	const [searchQuery, setSearchQuery] = useState('');

	const iconPackNames = Object.keys(icons);

	const filteredIcons = useMemo(() => {
		return iconPackNames.reduce((acc, packName) => {
			return {
				...acc,
				[packName]: icons[packName].filter((icon) =>
					icon.toLowerCase().includes(searchQuery.toLocaleLowerCase())
				),
			};
		}, {});
	}, [iconPackNames, icons, searchQuery]);

	const handleDelete = (iconName, iconPackName) => {
		const formData = new FormData();

		formData.append(portletNamespace + 'name', iconName);
		formData.append(portletNamespace + 'iconPack', iconPackName);

		Liferay.Util.fetch(deleteURL, {body: formData, method: 'post'}).then(
			() => {
				Liferay.Util.openToast({
					message: Liferay.Language.get('icon-deleted'),
					title: Liferay.Language.get('success'),
					toastProps: {
						autoClose: 5000,
					},
					type: 'success',
				});

				const newIcons = {...icons};

				newIcons[iconPackName] = newIcons[iconPackName].filter(
					(icon) => icon !== iconName
				);

				if (newIcons[iconPackName].length === 0) {
					delete newIcons[iconPackName];
				}

				setIcons(newIcons);
			}
		);
	};

	const handleSubmit = () => {
		const formData = new FormData();

		formData.append(
			portletNamespace + 'svgFile',
			svgFileInputRef.current.files[0]
		);
		formData.append(portletNamespace + 'name', iconName);
		formData.append(portletNamespace + 'iconPack', iconPackName);

		Liferay.Util.fetch(submitURL, {body: formData, method: 'post'}).then(
			() => {
				Liferay.Util.openToast({
					message: Liferay.Language.get('icon-added'),
					title: Liferay.Language.get('success'),
					toastProps: {
						autoClose: 5000,
					},
					type: 'success',
				});

				const newIcons = {...icons};

				if (newIcons[iconPackName]) {
					newIcons[iconPackName].push(iconName);
				}
				else {
					newIcons[iconPackName] = [iconName];
				}

				setIcons(newIcons);
			}
		);
	};

	return (
		<ClayLayout.Sheet>
			<ClayLayout.ContentRow className="mb-5" containerElement="h2">
				<ClayLayout.ContentCol containerElement="span" expand>
					{Liferay.Language.get('icons-admin-configuration-name')}
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<h4>{Liferay.Language.get('icon-packs')}</h4>

			<ClayForm
				onSubmit={(e) => {
					e.preventDefault();

					handleSubmit();
				}}
			>
				<input name="redirect" type="hidden" value={redirectURL} />
				<ClayForm.Group>
					<label className="form-control-label">
						<span className="form-control-label-text">{label}</span>

						<ClayInput
							onChange={(event) =>
								setSearchQuery(event.target.value)
							}
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
								displayTitle={`${iconPackName} (${filteredIcons[iconPackName].length})`}
								displayType="secondary"
								key={iconPackName}
								showCollapseIcon={true}
							>
								<ClayPanel.Body className="list-group-card">
									<ul className="list-group">
										{filteredIcons[iconPackName]
											.sort()
											.map((icon) => (
												<li
													className="list-group-card-item w-25"
													key={icon}
												>
													<ClayButton
														displayType={null}
														onClick={() => {
															if (
																confirm(
																	Liferay.Language.get(
																		'are-you-sure-you-want-to-delete-this-icon'
																	)
																)
															) {
																handleDelete(
																	icon,
																	iconPackName
																);
															}
														}}
													>
														<ClayIcon
															spritemap={`/o/icons/${iconPackName}.svg`}
															symbol={icon}
														/>

														<span className="list-group-card-item-text">
															{icon}
														</span>
													</ClayButton>
												</li>
											))}

										{!filteredIcons[iconPackName]
											.length && (
											<li className="list-group-card-item w-100">
												{Liferay.Language.get(
													'no-results-found'
												)}
											</li>
										)}
									</ul>
								</ClayPanel.Body>
							</ClayPanel>
						))}
					</ClayPanel.Group>
				</ClayForm.Group>

				<h4>{Liferay.Language.get('add-icon')}</h4>

				<ClayForm.Group>
					<label htmlFor={portletNamespace + 'iconPack'}>
						{Liferay.Language.get('pack-name')}
					</label>

					<ClayInput
						name={portletNamespace + 'iconPack'}
						placeholder="Name"
						type="text"
						onChange={(e) => setIconPackName(e.target.value)}
						value={iconPackName}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={portletNamespace + 'name'}>
						{Liferay.Language.get('icon-name')}
					</label>

					<ClayInput
						name={portletNamespace + 'name'}
						placeholder="Name"
						type="text"
						onChange={(e) => setIconName(e.target.value)}
						value={iconName}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={portletNamespace + 'svgFile'}>
						{Liferay.Language.get('svg-file')}
					</label>

					<ClayInput
						name={portletNamespace + 'svgFile'}
						placeholder="Paste SVG content"
						type="file"
						ref={svgFileInputRef}
					/>
				</ClayForm.Group>
				<ClayLayout.SheetFooter>
					<ClayButton.Group className="mt-4">
						<ClayButton type="submit">
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				</ClayLayout.SheetFooter>
			</ClayForm>
		</ClayLayout.Sheet>
	);
};

export default IconSearch;
