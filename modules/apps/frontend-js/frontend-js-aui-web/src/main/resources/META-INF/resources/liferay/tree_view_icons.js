/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
AUI.add(
	'liferay-tree-view-icons',
	(A) => {
		const ICON_DEPRECATED_CLASSES = [
			'glyphicon',
			'glyphicon-check',
			'glyphicon-file',
			'glyphicon-folder',
			'glyphicon-folder-close',
			'glyphicon-folder-open',
			'glyphicon-minus',
			'glyphicon-plus',
			'icon-file',
			'icon-minus',
			'icon-plus',
		];

		const clearIconClasses = function (element) {
			ICON_DEPRECATED_CLASSES.forEach((className) =>
				element.removeClass(className)
			);
		};

		const originalSyncIconUIFn = A.TreeNode.prototype._syncIconUI;

		A.TreeNode.prototype._syncIconUI = function (args) {
			originalSyncIconUIFn.call(this, args);

			const hasChildren = !this.get('leaf');
			const expanded = this.get('expanded');
			const hitAreaEl = this.get('hitAreaEl');
			const iconEl = this.get('iconEl');

			const hitAreaContent = hasChildren
				? Liferay.Util.getLexiconIconTpl(expanded ? 'hr' : 'plus')
				: '<span class="tree-hitarea"></span>';

			hitAreaEl.setHTML(hitAreaContent);

			clearIconClasses(hitAreaEl);

			const expandedIcon = expanded ? 'move-folder' : 'folder';
			const icon = hasChildren ? expandedIcon : 'page';

			iconEl.setHTML(Liferay.Util.getLexiconIconTpl(icon));

			clearIconClasses(iconEl);
		};

		const originalSyncIconCheckUIFn =
			A.TreeNodeCheck.prototype._syncIconCheckUI;

		A.TreeNodeCheck.prototype._syncIconCheckUI = function (args) {
			originalSyncIconCheckUIFn.call(this, args);

			const checked = this.isChecked();
			const checkContainerEl = this.get('checkContainerEl');

			checkContainerEl.setHTML(
				Liferay.Util.getLexiconIconTpl(
					checked ? 'check-square' : 'square-hole'
				)
			);

			clearIconClasses(checkContainerEl);
		};
	},
	'',
	{
		requires: ['aui-tree-view'],
	}
);
