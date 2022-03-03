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

// It throw an error during build time where @liferay/frontend-js-react-web module was not found :shrug:
// import {openModal} from './modal/Modal';

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {render} from '@liferay/frontend-js-react-web';
import React, {useCallback, useState} from 'react';

function PromptDialog({defaultPlaceholder, message, onClose}) {
	const [value, setValue] = useState(() => defaultPlaceholder);
	const [visible, setVisible] = useState(true);

	const {observer} = useModal({
		onClose: () => processClose(),
	});

	const processClose = useCallback(() => {
		setVisible(false);

		// TODO - Is this really necessary?? REFLITAO

		document.body.classList.remove('modal-open');

		if (onClose) {
			onClose(value);
		}
	}, [onClose, value]);

	if (!visible) {
		return null;
	}

	return (
		<ClayModal
			className="liferay-modal"
			observer={observer}
			role="dialog"
			size="sm"
		>
			<ClayModal.Body>
				<ClayForm.Group>
					<label htmlFor="promptInputText">{message}</label>

					<ClayInput
						id="promptInputText"
						onChange={setValue}
						type="text"
					/>
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<>
						<ClayButton
							aria-label={Liferay.Language.get('cancel')}
							displayType="secondary"
							onClick={() => onClose(null)}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							aria-label={Liferay.Language.get('ok')}
							displayType="primary"
							onClick={processClose}
							type="button"
						>
							{Liferay.Language.get('ok')}
						</ClayButton>
					</>
				}
			/>
		</ClayModal>
	);
}

/**
 * Display a dialog with an optional message prompting the user to input some text,
 * and to wait until the user either submits the text or cancels the dialog.
 * @param {string} message
 * @param {string?} [defaultPlaceholder=""] - Optional placeholder
 * @returns {Promise} Promise object wrapping the result of the user input
 */
export function promptAsync(message, defaultPlaceholder) {
	return new Promise((resolve, _) => {

		// Mount in detached node; Clay will take care of appending to `document.body`.
		// See: https://github.com/liferay/clay/blob/master/packages/clay-shared/src/Portal.tsx

		render(
			PromptDialog,
			{
				defaultPlaceholder,
				message,
				onClose: (returnValue) => resolve(returnValue),
			},
			document.createElement('div')
		);
	});
}
