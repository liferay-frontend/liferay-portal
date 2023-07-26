/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Sign-in modal
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */

AUI.add(
	'liferay-sign-in-modal',
	(A) => {
		const NAME = 'signinmodal';

		const WIN = A.config.win;

		const SignInModal = A.Component.create({
			ATTRS: {
				resetFormValidator: {
					value: true,
				},

				signInPortlet: {
					setter: A.one,
					value:
						'#p_p_id_com_liferay_login_web_portlet_LoginPortlet_',
				},
			},

			EXTENDS: A.Plugin.Base,

			NAME,

			NS: NAME,

			prototype: {
				_bindUI() {
					const instance = this;

					instance._host.on('click', A.bind('_load', instance));

					const destroyModal = function () {
						instance.destroy();

						Liferay.detach('screenLoad', destroyModal);
					};

					Liferay.on('screenLoad', destroyModal);
				},

				_load(event) {
					const instance = this;

					event.preventDefault();

					if (
						instance._signInPortletBody &&
						instance._hasSignInForm
					) {
						instance._loadDOM();
					}
					else {
						instance._loadIO();
					}
				},

				_loadDOM() {
					const instance = this;

					const signInPortletBody = instance._signInPortletBody;

					if (!instance._originalParentNode) {
						instance._originalParentNode = signInPortletBody.ancestor();
					}

					instance._setModalContent(signInPortletBody);

					Liferay.Util.focusFormField(
						signInPortletBody.one('input:text')
					);
				},

				_loadIO() {
					const instance = this;

					const modalSignInURL = Liferay.Util.addParams(
						'windowState=exclusive',
						instance._signInURL
					);

					Liferay.Util.fetch(modalSignInURL)
						.then((response) => response.text())
						.then((response) => {
							if (response) {
								instance._setModalContent(response);
							}
							else {
								instance._redirectPage();
							}
						})
						.catch(() => instance._redirectPage());
				},

				_redirectPage() {
					const instance = this;

					WIN.location.href = instance._signInURL;
				},

				_setModalContent(content) {
					const instance = this;

					const dialog = Liferay.Util.getWindow(NAME);

					if (!dialog) {
						Liferay.Util.openWindow(
							{
								dialog: {
									after: {
										visibleChange(event) {
											const signInPortletBody =
												instance._signInPortletBody;

											const formValidator =
												instance._formValidator;

											if (
												formValidator &&
												instance.get(
													'resetFormValidator'
												)
											) {
												formValidator.resetAllFields();
											}

											if (
												!event.newVal &&
												signInPortletBody
											) {
												const originalParentNode =
													instance._originalParentNode;

												if (originalParentNode) {
													originalParentNode.append(
														signInPortletBody
													);
												}
											}
										},
									},
									height: 450,
									width: 560,
								},
								id: NAME,
								title: Liferay.Language.get('sign-in'),
							},
							(dialogWindow) => {
								const bodyNode = dialogWindow.bodyNode;

								bodyNode.plug(A.Plugin.ParseContent);

								bodyNode.setContent(content);
							}
						);
					}
					else {
						dialog.bodyNode.setContent(content);

						dialog.show();
					}
				},

				destructor() {
					const dialog = Liferay.Util.getWindow(NAME);

					if (dialog) {
						dialog.destroy();
					}
				},

				initializer() {
					const instance = this;

					const signInPortlet = instance.get('signInPortlet');

					if (signInPortlet) {
						instance._signInPortletBody = signInPortlet.one(
							'.portlet-body'
						);
					}

					const host = instance.get('host');

					instance._host = host;
					instance._signInPortlet = signInPortlet;

					instance._signInURL = host.attr('href');

					if (signInPortlet) {
						const formNode = signInPortlet.one('form');

						if (formNode) {
							const form = Liferay.Form.get(formNode.attr('id'));

							instance._formValidator = '';

							if (form) {
								instance._formValidator = form.formValidator;
							}

							instance._hasSignInForm = formNode.hasClass(
								'sign-in-form'
							);
						}
					}

					instance._bindUI();
				},
			},
		});

		Liferay.SignInModal = SignInModal;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-component',
			'aui-parse-content',
			'liferay-form',
			'liferay-portlet-url',
			'liferay-util-window',
			'plugin',
		],
	}
);
