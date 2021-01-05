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

<portlet:actionURL name="/currency_converter/edit_preferences" var="editPreferences" />

<aui:form action="<%= editPreferences %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="symbols" type="hidden" value="" />

	<%
	List<KeyValuePair> leftList = new ArrayList<>();

	for (int i = 0; i < symbols.length; i++) {
		leftList.add(new KeyValuePair(symbols[i], LanguageUtil.get(request, "currency." + symbols[i])));
	}

	List<KeyValuePair> rightList = new ArrayList<>();

	Arrays.sort(symbols);

	for (Map.Entry<String, String> entry : allSymbols.entrySet()) {
		String symbol = entry.getValue();
		String currencyValue = entry.getKey();

		if (Arrays.binarySearch(symbols, symbol) < 0) {
			rightList.add(new KeyValuePair(symbol, LanguageUtil.get(request, "currency." + currencyValue)));
		}
	}

	rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
	%>

	<liferay-ui:input-move-boxes
		leftBoxName="current_actions"
		leftList="<%= leftList %>"
		leftReorder="<%= Boolean.TRUE.toString() %>"
		leftTitle="current"
		rightBoxName="available_actions"
		rightList="<%= rightList %>"
		rightTitle="available"
	/>

	<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "saveCurrency();" %>' primary="<%= true %>" value="save" />
</aui:form>

<aui:script>
	function <portlet:namespace />saveCurrency() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var currentActions = form.querySelector(
				'#<portlet:namespace />current_actions'
			);
			var symbols = form.querySelector('#<portlet:namespace />symbols');

			if (currentActions && symbols) {
				symbols.setAttribute(
					'value',
					Liferay.Util.listSelect(currentActions)
				);

				submitForm(form);
			}
		}
	}
</aui:script>