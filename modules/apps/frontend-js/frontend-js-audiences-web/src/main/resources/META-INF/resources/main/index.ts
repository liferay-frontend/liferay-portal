/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	clear,
	get,
	on,
	runDetection,
	runHandlers,
	setLogEnabled,
} from './implementation';

// JSON API

export interface AudiencesDefinition {
	audiences: Audience[];
}

export interface Audience {
	conjunction: Conjunction;
	id: string;
	retentionType: RetentionType;
	rules: Rule[];
}

export type Conjunction = 'AND' | 'OR';

export type RetentionType = 'BROWSER' | 'PAGE' | 'TAB';

export type Rule = LeafRule | RuleGroup;

export interface LeafRule {
	attribute: Attribute;
	operator: Operator;
	value: any;
}

export interface RuleGroup {
	conjunction: Conjunction;
	rules: Rule[];
}

export type Attribute =
	| 'browser_name'
	| 'browser_version'
	| 'cookies'
	| `custom:${string}`
	| 'hostname'
	| 'language'
	| 'local_date'
	| 'local_hour'
	| 'pathname'
	| 'referrer'
	| `request_parameters`
	| 'segments'
	| 'timezone'
	| 'url'
	| 'user_agent';
export type Operator =
	| 'eq'
	| 'gt'
	| 'gte'
	| 'includes'
	| 'lt'
	| 'lte'
	| 'not_eq'
	| 'not_includes';

// JavaScript API

export interface CustomAttribute<T> {
	name: string | undefined;
	(): Promise<T> | T;
}

export interface Handler {
	name: string | undefined;
	(): Promise<void> | void;
}

export interface RunHandlersOptions {

	/**
	 * Ordering of audiences for executing handlers. Any audience not in this array will be executed
	 * after the specified audiences in an indeterminate order.
	 */
	audiencesRunOrder?: string[];

	/**
	 * Whether to remove all registered handlers when finished. Default value is `true`.
	 */
	clearHandlers?: boolean;
}

export interface AudiencesAPI {
	clear(retentionType?: RetentionType): void;
	get(): Set<string>;
	on(audienceId: string, handler: Handler): void;
	runDetection(audiencesDefinitionURL: string): Promise<void>;
	runHandlers(options?: RunHandlersOptions): Promise<void>;
	setLogEnabled(enabled: boolean): void;
}

export const audiences: AudiencesAPI = {
	clear,
	get,
	on,
	runDetection,
	runHandlers,
	setLogEnabled,
};
