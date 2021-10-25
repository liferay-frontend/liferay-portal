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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {fetch, openToast} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

export default function AddIconPackModal({
	icons,
	portletNamespace,
	setIcons,
	setVisible,
	submitURL,
	visible,
}) {
	const svgFileInputRef = useRef();

	const [iconPackName, setIconPackName] = useState('');
	const [loading, setLoading] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
		},
	});

	const handleSubmit = () => {
		setLoading(true);

		const formData = new FormData();

		formData.append(
			portletNamespace + 'svgFile',
			svgFileInputRef.current.files[0]
		);
		formData.append(portletNamespace + 'iconPack', iconPackName);

		return fetch(submitURL, {body: formData, method: 'post'})
			.then((response) => response.json())
			.then((iconPack) => {
				openToast({
					message: Liferay.Language.get('icon-added'),
					title: Liferay.Language.get('success'),
					toastProps: {
						autoClose: 5000,
					},
					type: 'success',
				});

				const newIcons = {...icons};

				newIcons[iconPackName] = iconPack;

				setIcons(newIcons);
				setLoading(false);
			});
	};

	return (
		visible && (
			<ClayModal observer={observer} size="lg">
				<ClayModal.Header withTitle>
					{Liferay.Language.get('add-icon-pack')}
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
								type="text"
								value={iconPackName}
							/>
						</ClayForm.Group>

						<ClayForm.Group>
							<label htmlFor={portletNamespace + 'svgFile'}>
								{Liferay.Language.get('svg-file')}
							</label>

							<ClayInput
								accept=".svg"
								name={portletNamespace + 'svgFile'}
								ref={svgFileInputRef}
								type="file"
							/>
						</ClayForm.Group>
					</ClayForm>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
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
		)
	);
}
