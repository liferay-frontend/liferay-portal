/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The Token List Component.
 *
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-token-list
 */

AUI().add(
	'liferay-token-list',
	(A) => {
		const TPL_TOKEN = A.Template(
			'<tpl for=".">',
			'<span class="lfr-token" data-fieldValues="{fieldValues}" data-clearFields="{clearFields}">',
			'<span class="align-middle d-inline-block">{text:this.getTokenText}</span>',

			'<a class="align-middle c-ml-1 d-inline-block icon icon-remove lfr-token-close" href="javascript:void(0);"></a>',
			'</span>',
			'</tpl>',
			{
				getTokenText(str, values) {
					if ('html' in values) {
						str = values.html;
					}
					else {
						str = A.Escape.html(str);
					}

					return str;
				},
			}
		);

		const TokenList = A.Component.create({
			ATTRS: {
				children: {
					validator: Array.isArray,
					value: [],
				},
				cssClass: {
					value: 'lfr-token-list',
				},
			},

			NAME: 'liferaytokenlist',

			prototype: {
				_addToken() {
					const instance = this;

					const buffer = instance._buffer;

					instance.get('contentBox').append(TPL_TOKEN.parse(buffer));

					buffer.length = 0;
				},

				_defCloseFn(event) {
					event.item.remove();
				},

				_onClick(event) {
					const instance = this;

					instance.fire('close', {
						item: event.currentTarget.ancestor('.lfr-token'),
					});
				},

				add(token) {
					const instance = this;

					if (token) {
						const buffer = instance._buffer;

						if (Array.isArray(token)) {
							instance._buffer = buffer.concat(token);
						}
						else {
							buffer.push(token);
						}

						instance._addTokenTask();
					}
				},

				bindUI() {
					const instance = this;

					const boundingBox = instance.get('boundingBox');

					boundingBox.delegate(
						'click',
						instance._onClick,
						'.lfr-token-close',
						instance
					);

					instance.publish('close', {
						defaultFn: A.bind('_defCloseFn', instance),
					});
				},

				initializer() {
					const instance = this;

					instance._buffer = [];

					instance._addTokenTask = A.debounce(
						instance._addToken,
						100
					);
				},

				renderUI() {
					const instance = this;

					instance.add(instance.get('children'));
				},
			},
		});

		Liferay.TokenList = TokenList;
	},
	'',
	{
		requires: ['aui-base', 'aui-template-deprecated', 'escape'],
	}
);
