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

export interface ThemeDisplay {
	readonly getBCP47LanguageId: () => string;
	readonly getCanonicalURL: () => string;
	readonly getCDNBaseURL: () => string;
	readonly getCDNDynamicResourcesHost: () => string;
	readonly getCDNHost: () => string;
	readonly getCompanyGroupId: () => string;
	readonly getCompanyId: () => string;
	readonly getDefaultLanguageId: () => string;
	readonly getDoAsUserIdEncoded: () => string;
	readonly getLanguageId: () => string;
	readonly getLayoutId?: () => string;
	readonly getLayoutRelativeControlPanelURL?: () => string;
	readonly getLayoutRelativeURL?: () => string;
	readonly getLayoutURL?: () => string;
	readonly getParentLayoutId?: () => string;
	readonly isControlPanel?: () => boolean;
	readonly isPrivateLayout?: () => boolean;
	readonly isVirtualLayout?: () => boolean;
	readonly getParentGroupId: () => string;
	readonly getPathContext: () => string;
	readonly getPathImage: () => string;
	readonly getPathJavaScript: () => string;
	readonly getPathMain: () => string;
	readonly getPathThemeImages: () => string;
	readonly getPathThemeRoot: () => string;
	readonly getPlid: () => string;
	readonly getPortalURL: () => string;
	readonly getScopeGroupId: () => string;
	readonly getScopeGroupIdOrLiveGroupId: () => string;
	readonly getSessionId: () => string;
	readonly getSiteAdminURL: () => string;
	readonly getSiteGroupId: () => string;
	readonly getURLControlPanel: () => string;
	readonly getURLHome: () => string;
	readonly getUserEmailAddress: () => string;
	readonly getUserId: () => string;
	readonly getUserName: () => string;
	readonly isAddSessionIdToURL: () => boolean;
	readonly isImpersonated: () => boolean;
	readonly isSignedIn: () => boolean;
	readonly isStateExclusive: () => boolean;
	readonly isStateMaximized: () => boolean;
	readonly isStatePopUp: () => boolean;
}
