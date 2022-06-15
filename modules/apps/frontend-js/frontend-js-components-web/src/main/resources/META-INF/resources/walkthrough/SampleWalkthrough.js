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

import React from 'react';

import Walkthrough from './Walkthrough';

const WALKTHROUGH_CONFIG = {
	closeOnClickOutside: false,
	closeable: true,
	pages: {
		'/group/guest/~/control_panel/manage?p_p_id=com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_mvcRenderCommandName=%2Fadmin%2Fedit_form_instance&_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_redirect=http%3A%2F%2Flocalhost%3A8080%2Fgroup%2Fguest%2F%7E%2Fcontrol_panel%2Fmanage%3Fp_p_id%3Dcom_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet%26p_p_lifecycle%3D0%26p_p_state%3Dmaximized%26p_p_mode%3Dview&_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_displayStyle=list&_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_formInstanceId=41581#/rules': [
			'button-2',
		],
		'/page-2': ['button-1'],
		'/page-3': ['fragment-1', 'fragment-2', 'fragment-3'],
	},
	skippable: true,
	steps: [
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: true,
			id: 'button-1',
			next: '/page-3',
			nodeToHighlight: '#fragment-zgbz-link',
			title: 'Button 1',
		},
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: true,
			id: 'fragment-1',
			nodeToHighlight: '#fragment-0-tkrl',
			previous: '/page-2',
			title: 'Fragment 1',
		},
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: true,
			id: 'fragment-2',
			nodeToHighlight: '#fragment-0-kluh',
			title: 'Fragment 2',
		},
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: true,
			id: 'fragment-3',
			next:
				'/group/guest/~/control_panel/manage?p_p_id=com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_mvcRenderCommandName=%2Fadmin%2Fedit_form_instance&_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_redirect=http%3A%2F%2Flocalhost%3A8080%2Fgroup%2Fguest%2F%7E%2Fcontrol_panel%2Fmanage%3Fp_p_id%3Dcom_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet%26p_p_lifecycle%3D0%26p_p_state%3Dmaximized%26p_p_mode%3Dview&_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_displayStyle=list&_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_formInstanceId=41581#/rules',
			nodeToHighlight: '#fragment-0-eitz',
			title: 'Fragment 3',
		},
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: true,
			id: 'button-2',
			nodeToHighlight: '#addFieldButton',
			previous: '/page-3',
			title: 'Add new rule',
		},
	],
};

export default function SampleWalkthrough(...props) {
	return <Walkthrough {...WALKTHROUGH_CONFIG} {...props} />;
}
