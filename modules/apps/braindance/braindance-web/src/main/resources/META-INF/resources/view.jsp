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

<clay:container-fluid>
	<clay:row>
		<clay:col
			cssClass="text-center"
		>
			<h1>BRAINDANCE PLAYER</h1>

			<p>With our brand new BrainDance Player (BDP&trade;) you'll live an experience like no other, with better graphics, better sound and better sensations.</p>
		</clay:col>
	</clay:row>

	<clay:row>
		<clay:col
			size="3"
		>
			<clay:vertical-card
				verticalCard="<%= new SightVerticalCard(renderRequest, renderResponse) %>"
			/>
		</clay:col>

		<clay:col
			size="3"
		>
			<clay:vertical-card
				verticalCard="<%= new HearingVerticalCard(renderRequest, renderResponse) %>"
			/>
		</clay:col>

		<clay:col
			size="3"
		>
			<clay:vertical-card
				verticalCard="<%= new SmellVerticalCard(renderRequest, renderResponse) %>"
			/>
		</clay:col>

		<clay:col
			size="3"
		>
			<clay:vertical-card
				verticalCard="<%= new TouchVerticalCard(renderRequest, renderResponse) %>"
			/>
		</clay:col>
	</clay:row>
</clay:container-fluid>