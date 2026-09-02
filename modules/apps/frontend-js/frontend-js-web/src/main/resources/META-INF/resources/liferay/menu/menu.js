/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import debounce from '../debounce/debounce.es';
import isPhone from '../util/is_phone';
import isTablet from '../util/is_tablet';
import {all, create, guid, toElement} from './dom';
import FocusManager from './focus_manager';
import MenuFilter from './menu_filter';
import Overlay from './overlay';

const CSS_BTN_PRIMARY = 'btn-primary';

const CSS_EXTENDED = 'lfr-extended';

const CSS_OPEN = 'open';

const CSS_PORTLET = '.portlet';

const CSS_SHOW = 'show';

const DEFAULT_ALIGN_POINTS = ['tl', 'bl'];

const STR_BOTTOM = 'b';

const STR_LEFT = 'l';

const STR_LTR = 'ltr';

const STR_RIGHT = 'r';

const STR_RTL = 'rtl';

const STR_TOP = 't';

const MAP_ALIGN_DOWN = {
	downleft: ['tr', 'br'],
	downright: DEFAULT_ALIGN_POINTS,
};

const MAP_ALIGN_HORIZONTAL_OVERLAY = {
	left: STR_RIGHT,
	right: STR_LEFT,
};

const MAP_ALIGN_HORIZONTAL_OVERLAY_RTL = {
	left: STR_LEFT,
	right: STR_RIGHT,
};

const MAP_ALIGN_HORIZONTAL_TRIGGER = {
	left: STR_LEFT,
	right: STR_RIGHT,
};

const MAP_ALIGN_HORIZONTAL_TRIGGER_RTL = {
	left: STR_RIGHT,
	right: STR_LEFT,
};

const MAP_ALIGN_VERTICAL_OVERLAY = {
	down: STR_TOP,
	up: STR_BOTTOM,
};

const MAP_ALIGN_VERTICAL_TRIGGER = {
	down: STR_BOTTOM,
	up: STR_TOP,
};

const REGEX_DIRECTION = /\bdirection-(downleft|downright|down|left|right|up)\b/;

const REGEX_MAX_DISPLAY_ITEMS = /max-display-items-(\d+)/;

const SELECTOR_ANCHOR = 'a';

const SELECTOR_LIST_ITEM = 'li';

const SELECTOR_SEARCH_CONTAINER = '.lfr-menu-list-search-container';

const TPL_MENU = '<div class="open"></div>';

const alignPointsCache = new Map();

const data = new WeakMap();

const liveSearches = new Map();

/**
 * Replaces the state AlloyUI kept through <code>Node.setData()</code>.
 */
function getData(element) {
	let elementData = data.get(element);

	if (!elementData) {
		elementData = {};

		data.set(element, elementData);
	}

	return elementData;
}

/**
 * While AlloyUI is on the page it augments <code>Liferay</code> with its own
 * event target, so <code>Liferay.on</code> hands back a YUI handle that
 * unsubscribes through <code>detach</code>. Without AlloyUI the handle is a
 * frontend-js-web <code>EventHandle</code>, which spells the same thing
 * <code>removeListener</code>. Both have to work, because taking AlloyUI off
 * the page is the point of the exercise.
 */
function detachLiferayHandle(handle) {
	if (typeof handle.detach === 'function') {
		handle.detach();
	}
	else {
		handle.removeListener();
	}
}

/**
 * Reproduces <code>A.Node.next(selector)</code>: the closest following sibling
 * matching <code>selector</code>, rather than only the immediately next one.
 * The distinction matters because the icon menu taglib writes an inline
 * <code>script</code> element between the trigger and its list.
 */
function next(element, selector) {
	let sibling = element.nextElementSibling;

	while (sibling && !sibling.matches(selector)) {
		sibling = sibling.nextElementSibling;
	}

	return sibling;
}

export default class Menu {
	constructor() {
		this._activeMenu = null;
		this._activeTrigger = null;
		this._focusManager = null;
		this._handles = [];
		this._overlayMap = new Map();
		this._trigger = null;

		if (!Menu._INSTANCE) {
			Menu._INSTANCE = this;
		}
	}

	/**
	 * Attaches the hover behaviour that moves focus onto an item's anchor.
	 * Public API: the Dynamic Data Mapping translation manager calls it with an
	 * AlloyUI node, and the icon menu taglib used to call it with a selector.
	 */
	static handleFocus(elementOrSelector) {
		const element = toElement(elementOrSelector);

		if (!element) {
			return;
		}

		element.addEventListener('mouseover', (event) =>
			Menu._targetLink(event, 'focus')
		);
		element.addEventListener('mouseout', (event) =>
			Menu._targetLink(event, 'blur')
		);
	}

	/**
	 * Public API. Registration is buffered and flushed on a trailing debounce,
	 * exactly as <code>Menu._registerTask</code> did, so that a page rendering
	 * a search container's worth of menus binds them in one pass.
	 */
	static register(id) {
		const menuElement = document.getElementById(id);

		if (!menuElement) {
			return;
		}

		if (!Menu._INSTANCE) {
			new Menu();
		}

		Menu._buffer.push(menuElement);

		Menu._registerTask();
	}

	static _targetLink(event, action) {
		const listItem = event.target.closest(SELECTOR_LIST_ITEM);

		if (!listItem || listItem.contains(event.relatedTarget)) {
			return;
		}

		const anchor = listItem.querySelector(SELECTOR_ANCHOR);

		if (anchor) {
			anchor[action]();
		}
	}

	/**
	 * Called by the live search once it has hidden or revealed items, so that
	 * arrow key navigation skips what is no longer on screen.
	 */
	refreshFocusManager() {
		if (this._focusManager) {
			this._focusManager.refresh();
		}
	}

	_closeActiveMenu() {
		const menu = this._activeMenu;

		if (!menu) {
			return;
		}

		for (const handle of this._handles) {
			handle.detach();
		}

		this._handles.length = 0;

		const trigger = this._activeTrigger;

		const overlay = this._overlayMap.get(guid(trigger));

		if (overlay) {
			overlay.destroy();

			this._overlayMap.clear();
		}

		this._activeMenu = null;
		this._activeTrigger = null;
		this._focusManager = null;

		trigger.setAttribute('aria-expanded', 'false');

		if (trigger.classList.contains(CSS_EXTENDED)) {
			trigger.classList.remove(CSS_BTN_PRIMARY);
		}
		else {
			trigger.parentNode.classList.remove(CSS_OPEN);

			const portlet = trigger.closest(CSS_PORTLET);

			if (portlet) {
				portlet.classList.remove(CSS_OPEN);
			}
		}
	}

	_getAlignPoints(cssClass) {
		if (alignPointsCache.has(cssClass)) {
			return alignPointsCache.get(cssClass);
		}

		let alignPoints = DEFAULT_ALIGN_POINTS;

		let defaultOverlayHorizontalAlign = STR_RIGHT;
		let defaultTriggerHorizontalAlign = STR_LEFT;

		let mapAlignHorizontalOverlay = MAP_ALIGN_HORIZONTAL_OVERLAY;
		let mapAlignHorizontalTrigger = MAP_ALIGN_HORIZONTAL_TRIGGER;

		const languageDirection =
			Liferay.Language.direction[Liferay.ThemeDisplay.getLanguageId()] ||
			STR_LTR;

		if (languageDirection === STR_RTL) {
			defaultOverlayHorizontalAlign = STR_LEFT;
			defaultTriggerHorizontalAlign = STR_RIGHT;

			mapAlignHorizontalOverlay = MAP_ALIGN_HORIZONTAL_OVERLAY_RTL;
			mapAlignHorizontalTrigger = MAP_ALIGN_HORIZONTAL_TRIGGER_RTL;
		}

		if (cssClass.indexOf('auto') === -1) {
			const directionMatch = cssClass.match(REGEX_DIRECTION);

			const direction = (directionMatch && directionMatch[1]) || 'auto';

			if (direction.startsWith('down')) {
				alignPoints =
					MAP_ALIGN_DOWN[direction] || MAP_ALIGN_DOWN.downright;
			}
			else {
				const overlayHorizontal =
					mapAlignHorizontalOverlay[direction] ||
					defaultOverlayHorizontalAlign;
				const overlayVertical =
					MAP_ALIGN_VERTICAL_OVERLAY[direction] || STR_TOP;

				const triggerHorizontal =
					mapAlignHorizontalTrigger[direction] ||
					defaultTriggerHorizontalAlign;
				const triggerVertical =
					MAP_ALIGN_VERTICAL_TRIGGER[direction] || STR_TOP;

				alignPoints = [
					overlayVertical + overlayHorizontal,
					triggerVertical + triggerHorizontal,
				];
			}
		}

		alignPointsCache.set(cssClass, alignPoints);

		return alignPoints;
	}

	/**
	 * AlloyUI cached the focus manager per trigger while destroying the overlay
	 * it was plugged into on every close, so arrow key navigation only ever
	 * worked the first time a given menu was opened. Binding it to the overlay
	 * instead fixes that.
	 */
	_getFocusManager(trigger, overlay) {
		if (!overlay.focusManager) {
			overlay.focusManager = new FocusManager(overlay.contentElement, {
				onExit: () => {
					this._closeActiveMenu();

					trigger.focus();
				},
			});
		}
		else {
			overlay.focusManager.refresh();
		}

		this._focusManager = overlay.focusManager;
	}

	_getLiveSearch(menu) {
		const id = guid(menu);

		if (!liveSearches.has(id)) {
			liveSearches.set(
				id,
				new MenuFilter({
					content: menu.querySelector('ul'),
					menu: this,
				})
			);
		}
	}

	_getMenu(trigger) {
		this._trigger = trigger;

		const triggerId = guid(trigger);

		let overlay = this._overlayMap.get(triggerId);

		if (!overlay) {
			overlay = new Overlay();

			Liferay.once('beforeScreenFlip', () => {
				overlay.destroy();

				this._overlayMap.clear();
			});

			this._overlayMap.set(triggerId, overlay);
		}

		const triggerData = getData(trigger);

		let listContainer = triggerData.menuListContainer;
		let menu = triggerData.menu;

		const liveSearch = menu && liveSearches.get(guid(menu));

		if (liveSearch) {
			liveSearch.reset();
		}

		let listItems;

		if (!menu || !listContainer) {
			listContainer = next(trigger, 'ul');

			listItems = all(listContainer, SELECTOR_LIST_ITEM);

			menu = create(TPL_MENU);

			menu.classList.add(CSS_SHOW);

			listContainer.before(menu);

			if (listItems.length) {
				listItems[listItems.length - 1].classList.add('last');
			}

			menu.append(listContainer);

			triggerData.menu = menu;
			triggerData.menuListContainer = listContainer;

			this._setARIARoles(trigger, menu);

			if (trigger.classList.contains('select')) {
				listContainer.addEventListener('click', (event) =>
					this._selectListItem(trigger, event)
				);
			}
		}

		overlay.setBody(menu);

		if (!triggerData.menuHeight) {
			const menuHeight = this._getMenuHeight(
				trigger,
				menu,
				listItems || all(listContainer, SELECTOR_LIST_ITEM)
			);

			triggerData.menuHeight = menuHeight;

			if (menuHeight !== 'auto') {
				listContainer.style.maxHeight = menuHeight + 'px';
			}
		}

		this._getFocusManager(trigger, overlay);

		return menu;
	}

	_getMenuHeight(trigger, menu, listItems) {
		const cssClass = trigger.className;

		let height = 'auto';

		if (cssClass.indexOf('lfr-menu-expanded') === -1) {
			const params = REGEX_MAX_DISPLAY_ITEMS.exec(cssClass);

			const maxDisplayItems = params && parseInt(params[1], 10);

			if (maxDisplayItems && listItems.length > maxDisplayItems) {
				this._getLiveSearch(menu);

				height = 0;

				for (const listItem of listItems.slice(0, maxDisplayItems)) {
					height += listItem.offsetHeight;
				}
			}
		}

		return height;
	}

	_positionActiveMenu() {
		const menu = this._activeMenu;
		const trigger = this._activeTrigger;

		if (!menu) {
			return;
		}

		const cssClass = trigger.className;

		const overlay = this._overlayMap.get(guid(trigger));

		const listElement = menu.querySelector('ul');

		overlay.show();

		menu.classList.add('lfr-icon-menu-open');

		const modal = isPhone() || isTablet();

		overlay.setModal(modal);
		overlay.setSize(listElement.offsetWidth, listElement.offsetHeight);
		overlay.alignTo(trigger, this._getAlignPoints(cssClass));

		if (!modal) {
			overlay.focusManager.focus(0);
		}

		if (cssClass.indexOf(CSS_EXTENDED) > -1) {
			trigger.classList.add(CSS_BTN_PRIMARY);
		}
		else {
			trigger.parentNode.classList.add(CSS_OPEN);

			const portlet = trigger.closest(CSS_PORTLET);

			if (portlet) {
				portlet.classList.add(CSS_OPEN);
			}
		}
	}

	_registerMenu(event) {

		// AlloyUI compared `event.key` against the numeric
		// `A.Event.KeyMap.SPACE`, so a space bar press never opened a menu.
		// Anchors used as triggers, which get no native click out of a space
		// bar press, were left unreachable from the keyboard.

		if (event.type === 'keydown' && event.key !== ' ') {
			return;
		}

		const trigger = event.currentTarget;

		const activeTrigger = this._activeTrigger;

		if (activeTrigger) {
			if (activeTrigger === trigger) {
				this._closeActiveMenu();

				return;
			}

			activeTrigger.classList.remove(CSS_BTN_PRIMARY);

			activeTrigger.parentNode.classList.remove(CSS_OPEN);

			const portlet = activeTrigger.closest(CSS_PORTLET);

			if (portlet) {
				portlet.classList.remove(CSS_OPEN);
			}

			this._closeActiveMenu();
		}

		if (trigger.classList.contains('disabled')) {
			return;
		}

		this._activeMenu = this._getMenu(trigger);
		this._activeTrigger = trigger;

		trigger.setAttribute('aria-expanded', 'true');

		if (!this._handles.length) {
			this._listen();
		}

		this._positionActiveMenu();

		Liferay.fire('dropdownShow', {
			src: 'LiferayMenu',
		});

		event.preventDefault();

		// Without this the document listener installed just above would see
		// this very click and close the menu again.

		event.stopPropagation();
	}

	/**
	 * The AlloyUI original also closed the menu on the drag manager's
	 * `ddm:start`, which is dropped here because it only ever fired when
	 * AlloyUI's drag and drop happened to be loaded on the page.
	 */
	_listen() {
		const closeActiveMenu = () => this._closeActiveMenu();

		const listContainer = getData(this._activeTrigger).menuListContainer;

		const onResize = debounce(() => this._positionActiveMenu(), 200);

		const onTouchEnd = (event) => {
			if (!listContainer.contains(event.target)) {
				event.preventDefault();

				closeActiveMenu();
			}
		};

		const dropdownShowHandle = Liferay.on('dropdownShow', (event) => {
			if (event.src !== 'LiferayMenu') {
				closeActiveMenu();
			}
		});

		document.addEventListener('click', closeActiveMenu);
		document.addEventListener('touchend', onTouchEnd);
		window.addEventListener('resize', onResize);

		this._handles.push(
			{
				detach: () =>
					document.removeEventListener('click', closeActiveMenu),
			},
			{
				detach: () =>
					document.removeEventListener('touchend', onTouchEnd),
			},
			{detach: () => window.removeEventListener('resize', onResize)},
			{detach: () => detachLiferayHandle(dropdownShowHandle)}
		);
	}

	_selectListItem(trigger, event) {
		const selectedListItem = event.target.closest(SELECTOR_LIST_ITEM);

		if (!selectedListItem) {
			return;
		}

		const selectedListItemIcon = selectedListItem.querySelector('i');

		const triggerIcon = trigger.querySelector('i');

		if (selectedListItemIcon && triggerIcon) {
			triggerIcon.setAttribute(
				'class',
				selectedListItemIcon.getAttribute('class')
			);
		}

		const selectedListItemMessage = selectedListItem.querySelector(
			'.lfr-icon-menu-text'
		);

		const triggerMessage = trigger.querySelector('.lfr-icon-menu-text');

		if (selectedListItemMessage && triggerMessage) {
			triggerMessage.textContent = selectedListItemMessage.textContent;
		}
	}

	_setARIARoles(trigger, menu) {

		// The AlloyUI selector was missing its closing bracket, which the
		// AlloyUI selector engine tolerated and `querySelectorAll` does not.

		const links = all(menu, SELECTOR_ANCHOR).filter(
			(link) => !link.matches('[aria-haspopup="dialog"]')
		);

		const searchContainer = menu.querySelector(SELECTOR_SEARCH_CONTAINER);

		const listElement = menu.querySelector('ul');

		let ariaLinksAttribute = 'menuitem';
		let ariaListElementAttribute = 'menu';

		if (searchContainer) {
			ariaLinksAttribute = 'option';
			ariaListElementAttribute = 'listbox';
		}

		if (links.length) {
			listElement.setAttribute('role', ariaListElementAttribute);

			for (const link of links) {
				link.setAttribute('role', ariaLinksAttribute);
			}
		}

		trigger.setAttribute('aria-haspopup', 'true');

		if (!trigger.classList.contains('input-localized-trigger')) {
			listElement.setAttribute('aria-labelledby', guid(trigger));
		}
	}
}

Menu._INSTANCE = null;

Menu._buffer = [];

Menu._registerTask = debounce(() => {
	const handler = (event) => Menu._INSTANCE._registerMenu(event);

	for (const menuElement of Menu._buffer) {
		menuElement.addEventListener('click', handler);
		menuElement.addEventListener('keydown', handler);
	}

	Menu._buffer.length = 0;
}, 100);
