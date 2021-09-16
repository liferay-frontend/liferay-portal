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

import {fetch, openToast} from 'frontend-js-web';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useEffect, useMemo, useState} from 'react';

const IconSearch = ({
	initialIcons,
	label = Liferay.Language.get('search-icons'),
	placeholder = Liferay.Language.get('search-icons'),
	portletNamespace,
	submitURL,
	deleteURL,
}) => {
	const svgFileInputRef = React.useRef(null);

	const [icons, setIcons] = useState(JSON.parse(initialIcons));
	const [iconName, setIconName] = useState('');
	const [iconPackName, setIconPackName] = useState('');
	const [searchQuery, setSearchQuery] = useState('');
	const [showModal, setShowModal] = useState(false);
	const [selectedIcon, setSelectedIcon] = useState(null);

	const {observer} = useModal({
		onClose: () => {
			setShowModal(false);
			setSelectedIcon(null);
		},
	});

	const iconPackNames = Object.keys(icons);

	const filteredIcons = useMemo(() => {
		return iconPackNames.reduce((acc, packName) => {
			return {
				...acc,
				[packName]: icons[packName].filter((icon) =>
					icon.name
						.toLowerCase()
						.includes(searchQuery.toLocaleLowerCase())
				),
			};
		}, {});
	}, [iconPackNames, icons, searchQuery]);

	useEffect(() => {
		setIconName('');
		setIconPackName('');
	}, [icons]);

	const handleDelete = (iconName, iconPackName) => {
		const formData = new FormData();

		formData.append(portletNamespace + 'name', iconName);
		formData.append(portletNamespace + 'iconPack', iconPackName);

		return fetch(deleteURL, {body: formData, method: 'post'}).then(() => {
			openToast({
				message: Liferay.Language.get('icon-deleted'),
				title: Liferay.Language.get('success'),
				toastProps: {
					autoClose: 5000,
				},
				type: 'success',
			});

			const newIcons = {...icons};

			newIcons[iconPackName] = newIcons[iconPackName].filter(
				(icon) => icon.name !== iconName
			);

			if (newIcons[iconPackName].length === 0) {
				delete newIcons[iconPackName];
			}

			setIcons(newIcons);
		});
	};

	const handleSubmit = () => {
		const formData = new FormData();

		formData.append(
			portletNamespace + 'svgFile',
			svgFileInputRef.current.files[0]
		);
		formData.append(portletNamespace + 'name', iconName);
		formData.append(portletNamespace + 'iconPack', iconPackName);

		return fetch(submitURL, {body: formData, method: 'post'}).then(() => {
			openToast({
				message: Liferay.Language.get('icon-added'),
				title: Liferay.Language.get('success'),
				toastProps: {
					autoClose: 5000,
				},
				type: 'success',
			});

			const newIcons = {...icons};

			const newIcon = {name: iconName, removable: true};

			if (newIcons[iconPackName]) {
				newIcons[iconPackName].push(newIcon);
			}
			else {
				newIcons[iconPackName] = [newIcon];
			}

			setIcons(newIcons);
		});
	};

	return (
		<ClayLayout.Sheet>
			<ClayLayout.ContentRow className="mb-5" containerElement="h2">
				<ClayLayout.ContentCol containerElement="span" expand>
					{Liferay.Language.get('icons-admin-configuration-name')}
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<h4>{Liferay.Language.get('icon-packs')}</h4>

			<label className="form-control-label">
				<span className="form-control-label-text">{label}</span>

				<ClayInput
					onChange={(event) => setSearchQuery(event.target.value)}
					placeholder={placeholder}
					type="text"
					value={searchQuery}
				/>
			</label>

			<ClayPanel.Group className="mt-4">
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
											key={icon.name}
										>
											<ClayButton
												displayType={null}
												onClick={() => {
													setShowModal(true);
													setSelectedIcon({
														...icon,
														iconPackName,
													});
												}}
											>
												<ClayIcon
													spritemap={`/o/icons/${iconPackName}.svg`}
													symbol={icon.name}
												/>

												<span className="list-group-card-item-text">
													{icon.name}
												</span>
											</ClayButton>
										</li>
									))}

								{!filteredIcons[iconPackName].length && (
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

			<ClayLayout.SheetFooter>
				<ClayButton onClick={() => setShowModal(true)}>
					{Liferay.Language.get('add-icon')}
				</ClayButton>
			</ClayLayout.SheetFooter>

			{showModal && (
				<ClayModal observer={observer} size="lg">
					<ClayModal.Header withTitle>
						{selectedIcon
							? Liferay.Language.get('edit-icon')
							: Liferay.Language.get('add-icon')}
					</ClayModal.Header>
					<ClayModal.Body>
						<ClayForm
							onSubmit={(e) => {
								e.preventDefault();
							}}
						>
							<ClayForm.Group>
								<label htmlFor={portletNamespace + 'iconPack'}>
									{Liferay.Language.get('pack-name')}
								</label>

								<ClayInput
									name={portletNamespace + 'iconPack'}
									placeholder="Name"
									type="text"
									onChange={(e) =>
										setIconPackName(e.target.value)
									}
									value={
										iconPackName ||
										selectedIcon?.iconPackName
									}
									readOnly={selectedIcon}
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
									onChange={(e) =>
										setIconName(e.target.value)
									}
									value={iconName || selectedIcon?.name}
									readOnly={selectedIcon}
								/>
							</ClayForm.Group>

							{!selectedIcon && (
								<ClayForm.Group>
									<label
										htmlFor={portletNamespace + 'svgFile'}
									>
										{Liferay.Language.get('svg-file')}
									</label>

									<ClayInput
										accept=".svg"
										name={portletNamespace + 'svgFile'}
										type="file"
										ref={svgFileInputRef}
									/>
								</ClayForm.Group>
							)}
						</ClayForm>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								{selectedIcon ? (
									<ClayButton
										displayType="danger"
										onClick={() => {
											if (
												confirm(
													Liferay.Language.get(
														'are-you-sure'
													)
												)
											) {
												handleDelete(
													selectedIcon.name,
													selectedIcon.iconPackName
												).then(() => {
													setShowModal(false);
													setSelectedIcon(null);
												});
											}
										}}
										title={
											selectedIcon.removable
												? Liferay.Language.get('delete')
												: Liferay.Language.get(
														'non-removable-icon'
												  )
										}
										disabled={!selectedIcon.removable}
									>
										{Liferay.Language.get('delete')}
									</ClayButton>
								) : (
									<ClayButton
										type="submit"
										onClick={() => {
											handleSubmit().then(() => {
												setShowModal(false);
												setSelectedIcon(null);
											});
										}}
									>
										{Liferay.Language.get('save')}
									</ClayButton>
								)}
								<ClayButton
									displayType="secondary"
									onClick={() => {
										setShowModal(false);
										setSelectedIcon(null);
									}}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}
		</ClayLayout.Sheet>
	);
};

export default IconSearch;
