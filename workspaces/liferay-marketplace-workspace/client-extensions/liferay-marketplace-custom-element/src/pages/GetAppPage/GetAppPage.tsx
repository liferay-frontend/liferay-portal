/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useState} from 'react';

import {GetAppModal} from '../../components/GetAppModal/GetAppModal';

export default function GetAppPage() {
	const [showModal, setShowModal] = useState(false);

	return (
		<>
			<ClayButton onClick={() => setShowModal(true)}>Get App</ClayButton>
			{showModal && (
				<GetAppModal handleClose={() => setShowModal(false)} />
			)}
		</>
	);
}
