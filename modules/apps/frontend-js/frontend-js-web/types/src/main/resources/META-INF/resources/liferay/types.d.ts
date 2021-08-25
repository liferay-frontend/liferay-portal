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
export interface ILiferay {
    AUI: {
        readonly getCombine: () => boolean;
        readonly getComboPath: () => string;
        readonly getDateFormat: () => string;
        readonly getEditorCKEditorPath: () => string;
        readonly getFilter: () => string;
        readonly getFilterConfig: () => string;
        readonly getJavaScriptRootPath: () => string;
        readonly getPortletRootPath: () => string;
        readonly getStaticResourceURLParams: () => string;
    };
    Browser: {
        readonly acceptsGzip: () => boolean;
        readonly getMajorVersion: () => number;
        readonly getRevision: () => string;
        readonly getVersion: () => string;
        readonly isAir: () => boolean;
        readonly isChrome: () => boolean;
        readonly isEdge: () => boolean;
        readonly isFirefox: () => boolean;
        readonly isGecko: () => boolean;
        readonly isIe: () => boolean;
        readonly isIphone: () => boolean;
        readonly isLinux: () => boolean;
        readonly isMac: () => boolean;
        readonly isMobile: () => boolean;
        readonly isMozilla: () => boolean;
        readonly isOpera: () => boolean;
        readonly isRtf: () => boolean;
        readonly isSafari: () => boolean;
        readonly isSun: () => boolean;
        readonly isWebKit: () => boolean;
        readonly isWindows: () => boolean;
    };
    Data: {
        readonly ICONS_INLINE_SVG: boolean;
        readonly NAV_SELECTOR: string;
        readonly NAV_SELECTOR_MOBILE: string;
        readonly notices: [{
            title?: string;
            message: string;
            type?: string;
        }];
        readonly isCustomizationView: () => boolean;
    };
    Language: {
        readonly available: {
            [languageId: string]: string;
        };
        readonly direction: {
            [languageId: string]: 'ltr' | 'rtl' | null;
        };
        get: (key: string) => string;
    };
    PortletKeys: {
        readonly DOCUMENT_LIBRARY: string;
        readonly DYNAMIC_DATA_MAPPING: string;
        readonly ITEM_SELECTOR: string;
        [key: string]: string | null;
    };
    PropsValues: {
        readonly JAVASCRIPT_SINGLE_PAGE_APPLICATION_TIMEOUT: number;
        readonly NTLM_AUTH_ENABLED: boolean;
        readonly UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE: number;
        [key: string]: any;
    };
    Service: {
        (serviceName: string, payload: any, callback: (...args: any[]) => void): void;
    };
    ThemeDisplay: {
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
    };
    readonly authToken: string;
    readonly currentURL: string;
    readonly currentURLEncoded: string;
}
export declare type Callback = (...args: any[]) => void;
export interface ILiferay {
    after(eventName: string, callback: Callback): void;
    before(eventName: string, callback: Callback): void;
    on(eventName: string, callback: Callback): void;
    once(eventName: string, callback: Callback): void;
    onceAfter(eventName: string, callback: Callback): void;
}
export interface ILiferay {
    SPA?: {
        app?: {
            canNavigate: (url: string) => boolean;
            navigate: (url: string, replaceHistory?: boolean, event?: Event) => Promise<void>;
        };
    };
}
declare global {
    var Liferay: ILiferay;
    var submitForm: (form: HTMLFormElement, action?: string, singleSubmit?: boolean, validate?: boolean) => void;
    interface Window {
        Liferay: ILiferay;
    }
}
