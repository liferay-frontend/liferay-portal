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

const ACTIONS = {
	showInfo() {
		showSidebar();
	}
};

const getComputedWidth = element => {
	const computedStyle = window.getComputedStyle(element, null);

	const computedWidth = parseFloat(computedStyle.width.replace("px","")) -
		(parseFloat(computedStyle.paddingLeft.replace("px","")) * 2) -
		(parseFloat(computedStyle.paddingRight.replace("px","")) * 2);

	return computedWidth;
};

const showSidebar = () => {
	const sidebarContainer = document.querySelector('.sidebar-container');
	const dashboardContents = document.querySelectorAll('.dashboard-content');
	
	if(sidebarContainer.classList.contains('in')) {
		dashboardContents.forEach(element => {
			element.style.width = '100%';
	  	});
  
	  	sidebarContainer.classList.remove('in');
	  }
	  else {
		const windowWidth = window.outerWidth;
		const sidebarContainerWidth = sidebarContainer.offsetWidth;

		dashboardContents.forEach(element => {
			const dashboardContentContainerWidth = 
				getComputedWidth(element.parentElement);
		
			const newDashboardContentContainerWidth = 
				dashboardContentContainerWidth - 
				(sidebarContainerWidth -
					((windowWidth - dashboardContentContainerWidth) / 2)
				);
		
			element.style.width = `${dashboardContentContainerWidth}px`;
			element.offsetWidth = element.offsetWidth;
			element.style.width = `${newDashboardContentContainerWidth}px`;
		});

		sidebarContainer.classList.add('in');
	}
};

export default function propsTransformer({
	items,
	...otherProps
}) {
	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action]();
					}
				},
			};
		}),
	};
}