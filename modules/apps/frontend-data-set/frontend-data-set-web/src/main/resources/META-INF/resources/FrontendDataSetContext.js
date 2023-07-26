/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const FrontendDataSetContext = React.createContext({
	actionParameterName: null,
	apiURL: null,
	appURL: null,
	formId: null,
	formName: null,
	id: null,
	loadData: () => {},
	modalId: null,
	namespace: null,
	openModal: () => {},
	openSidePanel: () => {},
	portletId: null,
	selectItems: () => {},
	selectable: false,
	selectedItemsValue: [],
	sidePanelId: null,
});

export default FrontendDataSetContext;
