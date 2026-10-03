/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import zIndex from '../liferay/zIndex';
import {create} from './dom';

/**
 * Every class name the AlloyUI widget put on the page is reproduced here, and
 * so is the nesting that carried them. The widget was <code>A.Widget</code>
 * augmented with <code>WidgetPosition</code>, <code>WidgetPositionAlign</code>,
 * <code>WidgetPositionConstrain</code>, <code>WidgetStdMod</code>,
 * <code>WidgetModality</code> and <code>WidgetStack</code>, declaring
 * <code>CSS_PREFIX: 'overlay'</code>, which rendered as:
 *
 * <pre>
 * &lt;div class="yui3-widget overlay"&gt;   boundingBox
 *   &lt;div class="overlay-content"&gt;      contentBox
 *     &lt;div class="yui3-widget-bd"&gt;     WidgetStdMod BODY section
 * </pre>
 *
 * <code>_renderBoxClassNames</code> put <code>yui3-widget</code> on the
 * bounding box and the instance prefix on the content box, and
 * <code>StdMod.SECTION_CLASS_NAMES</code> named the body section from the
 * <code>Widget</code> prefix rather than the instance's, which is why only two
 * of the four names read as AlloyUI's.
 *
 * Two of them the product styles: <code>_aui.scss</code> positions
 * <code>.overlay</code> absolutely, and <code>_dropdowns.scss</code> reveals an
 * open menu only through <code>.overlay-content .open > .dropdown-menu</code>.
 * The other two nothing in the repository styles, and they are kept anyway.
 * See the commit message for why.
 */
const TPL_OVERLAY =
	'<div class="yui3-widget overlay"><div class="overlay-content">' +
	'<div class="yui3-widget-bd"></div></div></div>';

/**
 * <code>WidgetModality</code> named the mask <code>yui3-widget-mask</code>, set
 * its geometry inline from JavaScript and took its paint from
 * <code>.yui3-skin-sam .yui3-widget-mask</code> in
 * <code>widget-modality/assets/skins/sam/widget-modality.css</code>, which the
 * YUI loader linked into the head the first time the module was used. Every
 * theme matched that selector, because <code>init.ftl</code> appends
 * <code>yui3-skin-sam</code> to the body class.
 *
 * Nothing links that stylesheet once the menu stops going through the AlloyUI
 * loader, and this module ships no CSS of its own, so the paint is inlined
 * alongside the geometry. Overriding <code>background-color</code> or
 * <code>opacity</code> on the mask therefore now takes an
 * <code>!important</code>, where before it took a rule matching the selector.
 */
const TPL_MASK =
	'<div class="yui3-widget-mask" style="background-color: #000; bottom: 0; ' +
	'left: 0; opacity: 0.4; position: fixed; right: 0; top: 0"></div>';

export default class Overlay {
	constructor() {
		this.element = create(TPL_OVERLAY);

		this.contentElement = this.element.querySelector('.overlay-content');

		this._bodyElement = this.element.querySelector('.yui3-widget-bd');

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
	 * Mirrors <code>overlay.setStdModContent(A.WidgetStdMod.BODY, menu)</code>,
	 * which filled the body section rather than the content box itself.
	 */
	setBody(element) {
		this._bodyElement.replaceChildren(element);
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
