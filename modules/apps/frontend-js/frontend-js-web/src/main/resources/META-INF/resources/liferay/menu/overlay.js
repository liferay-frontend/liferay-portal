/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import zIndex from '../zIndex';
import {create} from './dom';

/**
 * The widget AlloyUI built out of <code>A.Widget</code> augmented with
 * <code>WidgetPosition</code>, <code>WidgetPositionAlign</code>,
 * <code>WidgetPositionConstrain</code>, <code>WidgetStdMod</code>,
 * <code>WidgetModality</code> and <code>WidgetStack</code> rendered as
 * <code>.overlay > .overlay-content</code>, because it declared
 * <code>CSS_PREFIX: 'overlay'</code>. Those two class names are a contract:
 * <code>_aui.scss</code> positions <code>.overlay</code> absolutely and
 * <code>_dropdowns.scss</code> only reveals a menu through
 * <code>.overlay-content .open > .dropdown-menu</code>.
 *
 * The <code>yui3-widget</code> and <code>yui3-widget-bd</code> class names the
 * widget also emitted are deliberately dropped: nothing in the product styles
 * them, and they name the very framework this component is leaving behind.
 */
const TPL_OVERLAY =
	'<div class="overlay"><div class="overlay-content"></div></div>';

/**
 * <code>WidgetModality</code> took its appearance from
 * <code>.yui3-skin-sam .yui3-widget-mask</code>, shipped with the AlloyUI CSS.
 * The styles are inlined here so that the mask survives the removal of that
 * stylesheet.
 */
const TPL_MASK =
	'<div class="overlay-mask" style="background-color: #000; bottom: 0; ' +
	'left: 0; opacity: 0.4; position: fixed; right: 0; top: 0"></div>';

export default class Overlay {
	constructor() {
		this.element = create(TPL_OVERLAY);

		this.contentElement = this.element.querySelector('.overlay-content');

		this.element.style.zIndex = zIndex.MENU;

		document.body.appendChild(this.element);

		this._maskElement = null;
	}

	/**
	 * Positions the overlay so that its own <code>overlayPoint</code> corner
	 * lands on the trigger's <code>triggerPoint</code> corner, then keeps it
	 * inside the viewport the way <code>WidgetPositionConstrain</code> did.
	 * Points are AlloyUI's two character corner names: vertical
	 * (<code>t</code>/<code>b</code>) followed by horizontal
	 * (<code>l</code>/<code>r</code>).
	 */
	alignTo(trigger, [overlayPoint, triggerPoint]) {
		const overlayRegion = this.element.getBoundingClientRect();
		const triggerRegion = trigger.getBoundingClientRect();

		let left =
			triggerPoint[1] === 'l' ? triggerRegion.left : triggerRegion.right;
		let top =
			triggerPoint[0] === 't' ? triggerRegion.top : triggerRegion.bottom;

		if (overlayPoint[1] === 'r') {
			left -= overlayRegion.width;
		}

		if (overlayPoint[0] === 'b') {
			top -= overlayRegion.height;
		}

		left = Math.max(
			0,
			Math.min(
				left,
				document.documentElement.clientWidth - overlayRegion.width
			)
		);
		top = Math.max(
			0,
			Math.min(
				top,
				document.documentElement.clientHeight - overlayRegion.height
			)
		);

		this._setPosition(left, top);
	}

	destroy() {
		this.setModal(false);

		this.element.remove();
	}

	/**
	 * Mirrors <code>overlay.setStdModContent(A.WidgetStdMod.BODY, menu)</code>.
	 */
	setBody(element) {
		this.contentElement.replaceChildren(element);
	}

	setModal(modal) {
		if (modal && !this._maskElement) {
			this._maskElement = create(TPL_MASK);

			this._maskElement.style.zIndex = zIndex.MENU - 1;

			document.body.appendChild(this._maskElement);
		}
		else if (!modal && this._maskElement) {
			this._maskElement.remove();

			this._maskElement = null;
		}
	}

	setSize(width, height) {
		this.element.style.height = height + 'px';
		this.element.style.width = width + 'px';
	}

	show() {
		this.element.classList.remove('overlay-hidden');
	}

	/**
	 * Turns viewport coordinates into the offset parent's coordinate system,
	 * which is what <code>left</code> and <code>top</code> mean for an
	 * absolutely positioned element. Going through the offset parent rather
	 * than adding the page scroll keeps the overlay in place when the theme
	 * gives <code>body</code> a margin, a border or a position of its own.
	 */
	_setPosition(left, top) {
		const offsetParent =
			this.element.offsetParent || document.documentElement;

		const offsetParentRegion = offsetParent.getBoundingClientRect();

		const computedStyle = window.getComputedStyle(offsetParent);

		this.element.style.left =
			left -
			offsetParentRegion.left -
			(parseFloat(computedStyle.borderLeftWidth) || 0) +
			offsetParent.scrollLeft +
			'px';
		this.element.style.top =
			top -
			offsetParentRegion.top -
			(parseFloat(computedStyle.borderTopWidth) || 0) +
			offsetParent.scrollTop +
			'px';
	}
}
