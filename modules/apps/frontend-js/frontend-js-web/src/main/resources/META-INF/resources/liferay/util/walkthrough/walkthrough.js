/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function initialize(configuration) {
	Liferay.fire('walkthroughInitialize', {configuration});
}

function setStep(walkthroughId, stepId) {
	Liferay.fire('walkthroughSetStep', {stepId, walkthroughId});
}

export default {initialize, setStep};
