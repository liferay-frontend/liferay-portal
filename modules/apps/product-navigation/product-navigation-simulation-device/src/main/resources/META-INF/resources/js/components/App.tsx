/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

import {SIZES, Size} from '../constants/sizes';
import Preview from './Preview';
import SizeSelector from './SizeSelector';

import '../../css/main.scss';

interface IProps {
	portletNamespace: string;
}

export default function App({portletNamespace: namespace}: IProps) {
	const [activeSize, setActiveSize] = useState<Size>(SIZES.desktop);

	const previewRef = useRef<HTMLDivElement>(null);

	return (
		<>
			<SizeSelector
				activeSize={activeSize}
				namespace={namespace}
				previewRef={previewRef}
				setActiveSize={setActiveSize}
			/>

			<Preview activeSize={activeSize} previewRef={previewRef} />
		</>
	);
}

App.propTypes = {
	portletNamespace: PropTypes.string.isRequired,
};
