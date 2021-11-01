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

import ClayLabel from '@clayui/label';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

export function OriginalDocumentTag({id}) {
	const [container, setContainer] = useState(null);

	useEffect(() => {
		const imageContainer = document.querySelector(
			'.preview-file-container .image-container'
		);
		if (imageContainer) {
			setContainer(imageContainer);
		}
		else {
			setContainer(
				document.querySelector('.preview-file .preview-file-container')
			);
		}
	}, [id]);

	if (!container) {
		return <></>;
	}

	return (
		<ReactPortal container={container}>
			<div className={classNames('preview-image-mark')}>
				<ClayLabel displayType="secondary">
					{Liferay.Language.get('original-document')}
				</ClayLabel>
			</div>
		</ReactPortal>
	);
}
