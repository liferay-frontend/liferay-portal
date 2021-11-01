/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayDropDown from '@clayui/drop-down';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import AccountMenuContent from './AccountMenuContent';
import OrganizationMenuContent from './OrganizationMenuContent';
import UserMenuContent from './UserMenuContent';

const CONTENT = {
	account: AccountMenuContent,
	organization: OrganizationMenuContent,
	user: UserMenuContent,
};

export default function MenuProvider({
	alignElementRef,
	data: propData,
	parentData,
}) {
	const [active, setActive] = useState(false);
	const [data, setData] = useState(false);
	const menuRef = useRef(null);

	// The useEffect below force the component repositioning

	useEffect(() => {
		setActive(false);
		setData(propData);
	}, [propData]);

	useEffect(() => {
		if (data) {
			setActive(true);
		}
	}, [data]);

	const MenuContent = useMemo(() => data && CONTENT[data.type], [data]);

	const closeMenu = () => {
		setData(null);
		setActive(false);
	};

	return (
		<ClayDropDown.Menu
			active={active}
			alignElementRef={alignElementRef}
			onSetActive={closeMenu}
			ref={menuRef}
		>
			{MenuContent && (
				<MenuContent
					closeMenu={closeMenu}
					data={data}
					parentData={parentData}
				/>
			)}
		</ClayDropDown.Menu>
	);
}
