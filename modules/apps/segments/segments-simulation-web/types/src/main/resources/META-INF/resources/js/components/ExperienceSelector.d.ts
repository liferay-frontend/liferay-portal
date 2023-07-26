/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import SegmentExperience from '../../types/SegmentExperience';
interface Props {
	maximumDropdownEntries: number;
	namespace: string;
	onMoreSegmentExperiencesButtonClick: () => void;
	onSelectSegmentExperience: React.Dispatch<SegmentExperience>;
	segmentsExperiences: SegmentExperience[];
	selectedSegmentsExperience: SegmentExperience;
}
declare function ExperienceSelector({
	maximumDropdownEntries,
	namespace,
	onMoreSegmentExperiencesButtonClick,
	onSelectSegmentExperience,
	segmentsExperiences,
	selectedSegmentsExperience,
}: Props): JSX.Element;
export default ExperienceSelector;
