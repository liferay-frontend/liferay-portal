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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayLayout from '@clayui/layout';
import ClayModal, {useModal} from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import {fetch, openToast} from 'frontend-js-web';
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

	const [loading, setLoading] = useState(false);
	const [icons, setIcons] = useState(JSON.parse(initialIcons));
	const [iconName, setIconName] = useState('');
	const [iconPackName, setIconPackName] = useState('');
	const [searchQuery, setSearchQuery] = useState('');
	const [showModal, setShowModal] = useState(false);
	const [selectedIcon, setSelectedIcon] = useState(null);

	const {observer, onClose} = useModal({
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
		setLoading(true);

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
			setLoading(false);
		});
	};

	const handleSubmit = () => {
		setLoading(true);

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
			setLoading(false);
		});
	};

	const referenceTime = useMemo(() => new Date().getTime(), [icons]);

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
													setSelectedIcon({
														...icon,
														iconPackName,
													});

													setShowModal(true);
												}}
											>
												<ClayIcon
													spritemap={
														Liferay.Icons.getSpritemapPath(
															iconPackName
														) +
														'?' +
														referenceTime
													}
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
							onSubmit={(event) => {
								event.preventDefault();
							}}
						>
							<ClayForm.Group>
								<label htmlFor={portletNamespace + 'iconPack'}>
									{Liferay.Language.get('pack-name')}
								</label>

								<ClayInput
									name={portletNamespace + 'iconPack'}
									onChange={(event) =>
										setIconPackName(event.target.value)
									}
									placeholder="Name"
									readOnly={selectedIcon}
									type="text"
									value={
										iconPackName ||
										selectedIcon?.iconPackName
									}
								/>
							</ClayForm.Group>

							<ClayForm.Group>
								<label htmlFor={portletNamespace + 'name'}>
									{Liferay.Language.get('icon-name')}
								</label>

								<ClayInput
									name={portletNamespace + 'name'}
									onChange={(event) =>
										setIconName(event.target.value)
									}
									placeholder="Name"
									readOnly={selectedIcon}
									type="text"
									value={iconName || selectedIcon?.name}
								/>
							</ClayForm.Group>

							{selectedIcon && (
								<ClayForm.Group>
									<label>
										{Liferay.Language.get(
											'icon-reference-read-only'
										)}
									</label>

									<ClayInput
										onClick={(event) => {
											event.target.select();
										}}
										readOnly
										type="text"
										value={
											window.location.origin +
											Liferay.Icons.getSpritemapPath(
												selectedIcon.iconPackName
											) +
											'#' +
											selectedIcon.name
										}
									/>
								</ClayForm.Group>
							)}

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
										ref={svgFileInputRef}
										type="file"
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
										disabled={
											!selectedIcon.removable || loading
										}
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
													onClose();
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
									>
										{loading ? (
											<ClayLoadingIndicator
												className="d-inline-block m-0"
												small
											/>
										) : (
											Liferay.Language.get('delete')
										)}
									</ClayButton>
								) : (
									<ClayButton
										disabled={loading}
										onClick={() => {
											handleSubmit().then(() => {
												onClose();
											});
										}}
										type="submit"
									>
										{loading ? (
											<ClayLoadingIndicator
												className="d-inline-block m-0"
												small
											/>
										) : (
											Liferay.Language.get('save')
										)}
									</ClayButton>
								)}
								<ClayButton
									displayType="secondary"
									onClick={onClose}
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
