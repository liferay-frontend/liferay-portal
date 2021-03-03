<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<div class="lfr-segments-experiment-sidebar" id="segmentsExperimentSidebar">
	<div class="sidebar-header">
		<h1 class="sr-only"><liferay-ui:message key="ab-test-panel" /></h1>

		<span><liferay-ui:message key="ab-test" /></span>

		<aui:icon cssClass="icon-monospaced sidenav-close" image="times" markupView="lexicon" url="javascript:;" />
	</div>

	<div class="sidebar-body">
		<c:if test="<%= GetterUtil.getBoolean(request.getAttribute(SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_PANEL_STATE_OPEN)) %>">
			<liferay-util:include page="/segments_experiment_panel.jsp" servletContext="<%= application %>" />
		</c:if>
	</div>
</div>

<aui:script>
	var segmentsExperimentPanelToggle = document.getElementById(
		'<portlet:namespace />segmentsExperimentPanelToggleId'
	);

	var sidenavInstance = Liferay.SideNavigation.initialize(
		segmentsExperimentPanelToggle
	);

	var onSegmentsExperimentPanelToggle = (event) => {
		if (event.detail === sidenavInstance) {
			const state = event.type === 'open.lexicon.sidenav' ? 'open' : 'closed';

			Liferay.Util.Session.set(
				'com.liferay.segments.experiment.web_panelState',
				state
			);
		}
	};

	document.addEventListener(
		'closed.lexicon.sidenav',
		onSegmentsExperimentPanelToggle
	);

	document.addEventListener(
		'open.lexicon.sidenav',
		onSegmentsExperimentPanelToggle
	);

	Liferay.once('screenLoad', () => {
		Liferay.SideNavigation.destroy(segmentsExperimentPanelToggle);

		document.removeEventListener(
			'closed.lexicon.sidenav',
			onSegmentsExperimentPanelToggle
		);

		document.removeEventListener(
			'open.lexicon.sidenav',
			onSegmentsExperimentPanelToggle
		);
	});
</aui:script>