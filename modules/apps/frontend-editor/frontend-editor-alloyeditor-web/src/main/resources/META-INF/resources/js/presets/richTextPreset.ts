/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const richTextPreset = {
	disableNativeSpellChecker: true,
	extraPlugins:
		'addimages,ae_dragresize,ae_imagealignment,ae_placeholder,ae_selectionregion,ae_tableresize,ae_tabletools,ae_uicore,itemselector,media,videoembed',
	htmlEncodeOutput: true,
	imageScaleResize: 'scale',
	language: 'en_US',
	removePlugins:
		'autolink,contextmenu,elementspath,floatingspace,image2,link,liststyle,resize,table,tabletools,toolbar,ae_embed',
	skin: 'moono-lisa',
	srcNode:
		'_com_liferay_editor_ckeditor_sample_web_internal_portlet_CKEditorSamplePortlet_INSTANCE_znup_sampleAlloyEditor',
	toolbars: {
		add: {
			buttons: ['image', 'video', 'table', 'hline'],
			tabIndex: 2,
		},
		styles: {
			selections: [
				{
					buttons: ['imageLeft', 'imageCenter', 'imageRight'],
					name: 'embedurl',
					test: 'AlloyEditor.SelectionTest.embedUrl',
				},
				{
					buttons: ['linkEditBrowse'],
					name: 'link',
					test: 'AlloyEditor.SelectionTest.link',
				},
				{
					buttons: [
						'imageLeft',
						'imageCenter',
						'imageRight',
						'linkBrowse',
						'imageAlt',
					],
					name: 'image',
					setPosition: 'AlloyEditor.SelectionSetPosition.image',
					test: 'AlloyEditor.SelectionTest.image',
				},
				{
					buttons: [
						{
							cfg: {
								styles: [
									{
										name: 'Normal',
										style: {
											element: 'p',
											type: 1,
										},
									},
									{
										name: 'Heading 1',
										style: {
											element: 'h1',
											type: 1,
										},
									},
									{
										name: 'Heading 2',
										style: {
											element: 'h2',
											type: 1,
										},
									},
									{
										name: 'Heading 3',
										style: {
											element: 'h3',
											type: 1,
										},
									},
									{
										name: 'Heading 4',
										style: {
											element: 'h4',
											type: 1,
										},
									},
									{
										name: 'Preformatted Text',
										style: {
											element: 'pre',
											type: 1,
										},
									},
									{
										name: 'Cited Work',
										style: {
											element: 'cite',
											type: 2,
										},
									},
									{
										name: 'Computer Code',
										style: {
											element: 'code',
											type: 2,
										},
									},
									{
										name: 'Info Message',
										style: {
											attributes: {
												class: 'overflow-auto portlet-msg-info',
											},
											element: 'div',
											type: 1,
										},
									},
									{
										name: 'Alert Message',
										style: {
											attributes: {
												class: 'overflow-auto portlet-msg-alert',
											},
											element: 'div',
											type: 1,
										},
									},
									{
										name: 'Error Message',
										style: {
											attributes: {
												class: 'overflow-auto portlet-msg-error',
											},
											element: 'div',
											type: 1,
										},
									},
								],
							},
							name: 'styles',
						},
						'bold',
						'italic',
						'underline',
						'ol',
						'ul',
						'linkBrowse',
						'anchor',
					],
					name: 'text',
					test: 'AlloyEditor.SelectionTest.text',
				},
				{
					buttons: [
						'tableHeading',
						'tableRow',
						'tableColumn',
						'tableCell',
						'tableRemove',
					],
					getArrowBoxClasses:
						'AlloyEditor.SelectionGetArrowBoxClasses.table',
					name: 'table',
					setPosition: 'AlloyEditor.SelectionSetPosition.table',
					test: 'AlloyEditor.SelectionTest.table',
				},
			],
			tabIndex: 1,
		},
	},
	uiNode: null,
};

export default richTextPreset;
