<%--
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
--%>

<%@ include file="/init.jsp" %>

<div id="<portlet:namespace />simulationDeviceContainer">
	<div class="devices">
		<ul class="default-devices">
			<li class="default-devices-item">
				<button class="btn btn-monospaced lfr-device-item selected" data-device="desktop" type="button">
					<span class="c-inner" tabindex="-1">
						<aui:icon cssClass="icon sticker sticker-lg" image="desktop" markupView="lexicon" />

						<small><%= LanguageUtil.get(resourceBundle, "desktop") %></small>
					</span>
				</button>
			</li>

			<li class="default-devices-item">
				<button class="btn btn-monospaced lfr-device-item" data-device="tablet" type="button">
					<span class="c-inner" tabindex="-1">
						<aui:icon cssClass="icon sticker sticker-lg" image="tablet-portrait" markupView="lexicon" />

						<aui:icon cssClass="hide icon icon-rotate sticker sticker-lg" image="tablet-landscape" markupView="lexicon" />

						<small><%= LanguageUtil.get(resourceBundle, "tablet") %></small>
					</span>
				</button>
			</li>

			<li class="d-block default-devices-item">
				<button class="btn btn-monospaced lfr-device-item" data-device="smartphone" type="button">
					<span class="c-inner" tabindex="-1">
						<aui:icon cssClass="icon sticker sticker-lg" image="mobile-portrait" markupView="lexicon" />

						<aui:icon cssClass="hide icon icon-rotate sticker sticker-lg" image="mobile-landscape" markupView="lexicon" />

						<small><%= LanguageUtil.get(resourceBundle, "mobile") %></small>
					</span>
				</button>
			</li>

			<li class="default-devices-item">
				<button class="btn btn-monospaced lfr-device-item" data-device="autosize" type="button">
					<span class="c-inner" tabindex="-1">
						<aui:icon cssClass="icon sticker sticker-lg" image="autosize" markupView="lexicon" />

						<small><%= LanguageUtil.get(resourceBundle, "autosize") %></small>
					</span>
				</button>
			</li>

			<li class="default-devices-item">
				<button class="btn btn-monospaced lfr-device-item" data-device="custom" type="button">
					<span class="c-inner" tabindex="-1">
						<aui:icon cssClass="icon sticker sticker-lg" image="custom-size" markupView="lexicon" />

						<small><liferay-ui:message key="custom" /></small>
					</span>
				</button>
			</li>
		</ul>

		<div class="custom-devices" hidden id='<%= liferayPortletResponse.getNamespace() + "customDeviceContainer" %>'>
			<div class="custom-devices-item">
				<aui:input cssClass="form-control-sm" inlineField="<%= true %>" label='<%= LanguageUtil.get(request, "width") + " (px):" %>' name="width" size="4" value="600" wrappedField="<%= true %>" />
			</div>

			<div class="custom-devices-item">
				<aui:input cssClass="form-control-sm" inlineField="<%= true %>" label='<%= LanguageUtil.get(request, "height") + " (px):" %>' name="height" size="4" value="600" wrappedField="<%= true %>" />

			</div>
		</div>
	</div>
</div>

<aui:script use="liferay-product-navigation-simulation-device">
	var simulationDevice = new Liferay.SimulationDevice({
		devices: {
			autosize: {
				skin: 'autosize',
			},
			custom: {
				height: '#<portlet:namespace />height',
				resizable: true,
				width: '#<portlet:namespace />width',
			},
			desktop: {
				height: 1050,
				selected: true,
				width: 1300,
			},
			smartphone: {
				height: 640,
				preventTransition: true,
				rotation: true,
				skin: 'smartphone',
				width: 400,
			},
			tablet: {
				height: 900,
				preventTransition: true,
				rotation: true,
				skin: 'tablet',
				width: 760,
			},
		},
		inputHeight: '#<portlet:namespace />height',
		inputWidth: '#<portlet:namespace />width',
		namespace: '<portlet:namespace />',
	});

	Liferay.once('screenLoad', function () {
		simulationDevice.destroy();
	});

	A.one('.devices').delegate(
		'click',
		function (event) {
			var currentTarget = event.currentTarget;

			var dataDevice = currentTarget.attr('data-device');

			var customDeviceContainer = A.one(
				'#<portlet:namespace />customDeviceContainer'
			);

			if (dataDevice === 'custom') {
				customDeviceContainer.show();
			}
			else {
				customDeviceContainer.hide();
			}
		},
		'.lfr-device-item'
	);
</aui:script>