/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The Sortable Component.
 *
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-sortable
 */

AUI.add(
	'liferay-sortable',
	(A) => {
		const Sortable = A.Sortable;

		const STR_CONT = 'container';

		const STR_ID = 'id';

		const STR_NODES = 'nodes';

		A.mix(
			Sortable.prototype,
			{
				_onDragHorizontal(event) {
					const instance = this;

					const pageX = event.pageX;

					const x = instance._x;

					instance._up = pageX < x;

					instance._x = pageX;
				},

				_setDragMethod() {
					const instance = this;

					let dragMethod;

					const node = instance
						.get(STR_CONT)
						.one(instance.get(STR_NODES));

					const floated = node ? node.getStyle('float') : 'none';

					if (floated === 'left' || floated === 'right') {
						dragMethod = '_onDragHorizontal';
					}
					else {
						dragMethod = '_onDrag';
					}

					instance._dragMethod = A.bind(dragMethod, instance);
				},

				_x: null,

				initializer() {
					const instance = this;

					const id = 'sortable-' + A.guid();

					const delegateConfig = {
						container: instance.get(STR_CONT),
						dragConfig: {
							groups: [id],
						},
						invalid: instance.get('invalid'),
						nodes: instance.get(STR_NODES),
						target: true,
					};

					if (instance.get('handles')) {
						delegateConfig.handles = instance.get('handles');
					}

					const delegate = new A.DD.Delegate(delegateConfig);

					instance.set(STR_ID, id);

					delegate.dd.plug(A.Plugin.DDProxy, {
						cloneNode: true,
						moveOnEnd: false,
					});

					instance.drop = new A.DD.Drop({
						bubbleTarget: delegate,
						groups: delegate.dd.get('groups'),
						node: instance.get(STR_CONT),
					});

					instance.drop.on(
						'drop:enter',
						A.bind('_onDropEnter', instance)
					);

					instance._setDragMethod();

					delegate.on({
						'drag:drag': A.bind('_dragMethod', instance),
						'drag:end': A.bind('_onDragEnd', instance),
						'drag:over': A.bind('_onDragOver', instance),
						'drag:start': A.bind('_onDragStart', instance),
					});

					instance.delegate = delegate;

					Sortable.reg(instance, id);
				},
			},
			true
		);
	},
	'',
	{
		requires: ['liferay-dd-proxy', 'sortable'],
	}
);
