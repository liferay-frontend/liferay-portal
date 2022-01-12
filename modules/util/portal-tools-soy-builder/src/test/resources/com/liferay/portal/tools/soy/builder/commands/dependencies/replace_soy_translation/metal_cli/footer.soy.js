/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

Liferay.Loader.define(
	'@liferay/hello-soy-web@1.0.4/Footer.soy',
	['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'],
	(exports, _Component2, _Soy) => {
		'use strict';

		Object.defineProperty(exports, '__esModule', {
			value: true,
		});
		exports.templates = exports.Footer = undefined;

		var _Component3 = _interopRequireDefault(_Component2);

		var _Soy2 = _interopRequireDefault(_Soy);

		function _interopRequireDefault(obj) {
			return obj && obj.__esModule
				? obj
				: {
						default: obj,
				  };
		}

		function _classCallCheck(instance, Constructor) {
			if (!(instance instanceof Constructor)) {
				throw new TypeError('Cannot call a class as a function');
			}
		}

		function _possibleConstructorReturn(self, call) {
			if (!self) {
				throw new ReferenceError(
					"this hasn't been initialised - super() hasn't been called"
				);
			}

			return call &&
				(typeof call === 'object' || typeof call === 'function')
				? call
				: self;
		}

		function _inherits(subClass, superClass) {
			if (typeof superClass !== 'function' && superClass !== null) {
				throw new TypeError(
					'Super expression must either be null or a function, not ' +
						typeof superClass
				);
			}

			subClass.prototype = Object.create(
				superClass && superClass.prototype,
				{
					constructor: {
						value: subClass,
						enumerable: false,
						writable: true,
						configurable: true,
					},
				}
			);
			if (superClass) {
				Object.setPrototypeOf
					? Object.setPrototypeOf(subClass, superClass)
					: (subClass.__proto__ = superClass);
			}
		}

		var templates;
		goog.loadModule((exports) => {

			// This file was automatically generated from Footer.soy.
			// Please don't edit this file by hand.

			/**
			 * @fileoverview Templates in namespace Footer.
			 * @public
			 */

			goog.module('Footer.incrementaldom');

			/** @suppress {extraRequire} */
			var soy = goog.require('soy');

			/** @suppress {extraRequire} */
			var soydata = goog.require('soydata');

			/** @suppress {extraRequire} */
			goog.require('goog.i18n.bidi');

			/** @suppress {extraRequire} */
			goog.require('goog.asserts');
			var IncrementalDom = goog.require('incrementaldom');
			var ie_open = IncrementalDom.elementOpen;
			var ie_close = IncrementalDom.elementClose;
			var ie_void = IncrementalDom.elementVoid;
			var ie_open_start = IncrementalDom.elementOpenStart;
			var ie_open_end = IncrementalDom.elementOpenEnd;
			var itext = IncrementalDom.text;
			var iattr = IncrementalDom.attr;

			/**
			 * @param {Object<string, *>=} opt_data
			 * @param {(null|undefined)=} opt_ignored
			 * @param {Object<string, *>=} opt_ijData
			 * @return {void}
			 * @suppress {checkTypes}
			 */
			function $render(opt_data, opt_ignored, opt_ijData) {

				/** @desc  */
				var MSG_EXTERNAL_2986127772961742460 = goog.getMsg(
					'this-portlet-was-written-using-soy-templates'
				);
				var msg_s136 = MSG_EXTERNAL_2986127772961742460;
				ie_open('blockquote', null, null, 'class', 'blockquote-xs');
				ie_open('small');
				itext(msg_s136);
				ie_close('small');
				ie_close('blockquote');
			}
			exports.render = $render;
			if (goog.DEBUG) {
				$render.soyTemplateName = 'Footer.render';
			}

			exports.render.params = [];
			exports.render.types = {};
			exports.templates = templates = exports;

			return exports;
		});

		var Footer = (function (_Component) {
			_inherits(Footer, _Component);

			function Footer() {
				_classCallCheck(this, Footer);

				return _possibleConstructorReturn(
					this,
					(Footer.__proto__ || Object.getPrototypeOf(Footer)).apply(
						this,
						arguments
					)
				);
			}

			return Footer;
		})(_Component3.default);

		_Soy2.default.register(Footer, templates);
		exports.Footer = Footer;
		exports.templates = templates;
		exports.default = templates;
	}
);

// # sourceMappingURL=Footer.soy.js.map
