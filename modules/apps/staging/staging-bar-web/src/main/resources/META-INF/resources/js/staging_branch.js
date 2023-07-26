/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-staging-branch',
	(A) => {
		const Lang = A.Lang;

		const StagingBar = Liferay.StagingBar;

		A.mix(StagingBar, {
			_getBranchDialog() {
				const instance = this;

				let branchDialog = instance._branchDialog;

				if (!branchDialog) {
					const namespace = instance._namespace;

					branchDialog = Liferay.Util.Window.getWindow({
						dialog: {
							bodyContent: A.one(
								'#' + namespace + 'addBranch'
							).show(),
						},
						title: Liferay.Language.get('branch'),
					});

					instance._branchDialog = branchDialog;
				}

				return branchDialog;
			},

			addBranch(dialogTitle) {
				const instance = this;

				const branchDialog = instance._getBranchDialog();

				if (Lang.isValue(dialogTitle)) {
					branchDialog.set('title', dialogTitle);
				}

				branchDialog.show();
			},
		});
	},
	'',
	{
		requires: ['liferay-staging'],
	}
);
