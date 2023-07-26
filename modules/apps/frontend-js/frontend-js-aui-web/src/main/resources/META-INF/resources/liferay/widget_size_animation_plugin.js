/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-widget-size-animation-plugin',
	(A) => {
		const Lang = A.Lang;

		const NAME = 'sizeanim';

		const STR_END = 'end';

		const STR_HOST = 'host';

		const STR_SIZE = 'size';

		const STR_START = 'start';

		const SizeAnim = A.Component.create({
			ATTRS: {
				align: {
					validator: Lang.isBoolean,
				},
				duration: {
					validator: Lang.isNumber,
					value: 0.3,
				},
				easing: {
					validator: Lang.isString,
					value: 'easeBoth',
				},
				preventTransition: {
					validator: Lang.isBoolean,
				},
			},

			EXTENDS: A.Plugin.Base,

			NAME,

			NS: NAME,

			prototype: {
				_alignWidget() {
					const instance = this;

					if (instance.get('align')) {
						instance.get(STR_HOST).align();
					}
				},

				_animWidgetSize(size) {
					const instance = this;

					const host = instance.get(STR_HOST);

					instance._anim.stop();

					const attrs = {
						height: size.height,
						width: size.width,
					};

					if (!instance.get('preventTransition')) {
						instance._anim.set('to', attrs);

						instance._anim.run();
					}
					else {
						instance.fire(STR_START);

						host.setAttrs(attrs);

						instance._alignWidget();

						instance.fire(STR_END);
					}
				},

				destructor() {
					const instance = this;

					instance.get(STR_HOST).removeAttr(STR_SIZE);

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					const host = instance.get(STR_HOST);

					host.addAttr(STR_SIZE, {
						setter: A.bind('_animWidgetSize', instance),
					});

					instance._anim = new A.Anim({
						duration: instance.get('duration'),
						easing: instance.get('easing'),
						node: host,
					});

					const eventHandles = [
						instance._anim.after(
							STR_END,
							A.bind('fire', instance, STR_END)
						),
						instance._anim.after(
							STR_START,
							A.bind('fire', instance, STR_START)
						),
						instance._anim.after(
							'tween',
							instance._alignWidget,
							instance
						),
					];

					instance._eventHandles = eventHandles;
				},
			},
		});

		A.Plugin.SizeAnim = SizeAnim;
	},
	'',
	{
		requires: ['anim-easing', 'plugin', 'widget'],
	}
);
