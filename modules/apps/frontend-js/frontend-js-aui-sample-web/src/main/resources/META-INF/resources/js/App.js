/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

// Every AlloyUI module the LPD-515xx campaign removes is resurrected behind a
// deprecation feature flag, registered from its own AlloyUI group in
// frontend-js-aui-web/src/main/resources/META-INF/resources/liferay
// /modules_deprecated.js. Add one entry here per removal. The checker asserts
// both directions: with the flag on the module must load, with the flag off it
// must be gone.
//
// Set `preloaded` only for a module the portal itself used to load on every
// page, so its globals were there without anyone calling AUI().use(). The
// checker then also asserts that they still arrive on their own.

const CHECKS = [
	{
		globals: ['Liferay.AutoFields'],
		moduleKey: 'liferay-auto-fields',
		removedBy: 'LPD-51537',
		title: 'AutoFields',
	},
	{
		globals: ['Liferay.Menu'],
		moduleKey: 'liferay-menu',
		preloaded: true,
		removedBy: 'LPD-51552',
		title: 'Menu',
	},
	{
		globals: ['Liferay.MenuFilter'],
		moduleKey: 'liferay-menu-filter',
		removedBy: 'LPD-51552',
		title: 'MenuFilter',
	},
];

const EXPECTED_GROUP = 'liferaydeprecated';

const FEATURE_FLAG_KEY = 'LPD-57347';

const PRELOAD_POLL_INTERVAL = 50;

const PRELOAD_TIMEOUT = 10000;

const USE_TIMEOUT = 10000;

export function App() {
	const [results, setResults] = useState(null);

	useEffect(() => {
		let cancelled = false;

		const run = async () => {
			const preloadedGlobals = await getPreloadedGlobals();

			const nextResults = [];

			for (const check of CHECKS) {
				nextResults.push(await runCheck(check, preloadedGlobals));
			}

			if (!cancelled) {
				setResults(nextResults);
			}
		};

		run();

		return () => {
			cancelled = true;
		};
	}, []);

	if (!results) {
		return (
			<div
				className="container-fluid container-fluid-max-xl py-3"
				data-qa-id="auiDeprecationChecker"
			>
				<p>Checking&hellip;</p>
			</div>
		);
	}

	const failed = results.filter((result) => !result.passed);

	return (
		<div
			className="container-fluid container-fluid-max-xl py-3"
			data-qa-id="auiDeprecationChecker"
			data-status={failed.length ? 'fail' : 'pass'}
		>
			<div
				className={
					'alert ' +
					(failed.length ? 'alert-danger' : 'alert-success')
				}
				data-qa-id="auiDeprecationCheckerSummary"
				data-status={failed.length ? 'fail' : 'pass'}
				role="alert"
			>
				{failed.length
					? failed.length +
						' of ' +
						results.length +
						' modules behaved unexpectedly'
					: 'All ' + results.length + ' modules behaved as expected'}
			</div>

			<p className="text-secondary">
				{'AlloyUI on this page: '}

				<code>{typeof window.AUI === 'function' ? 'yes' : 'no'}</code>

				{', '}

				<code>modules_deprecated.js</code>

				{' loaded: '}

				<code data-qa-id="auiDeprecationCheckerOverlay">
					{isOverlayLoaded() ? 'yes' : 'no'}
				</code>
			</p>

			{results.map((result) => (
				<Module key={result.check.moduleKey} result={result} />
			))}
		</div>
	);
}

function Assertion({assertion}) {
	return (
		<li
			className="list-group-item list-group-item-flex"
			data-qa-id="auiDeprecationCheckerAssertion"
			data-status={assertion.passed ? 'pass' : 'fail'}
		>
			<div className="autofit-col">
				<span
					className={
						'label ' +
						(assertion.passed ? 'label-success' : 'label-danger')
					}
				>
					{assertion.passed ? 'Pass' : 'Fail'}
				</span>
			</div>

			<div className="autofit-col autofit-col-expand">
				{assertion.label}

				{!assertion.passed && (
					<>
						{' — expected '}

						<code>{String(assertion.expected)}</code>

						{', got '}

						<code>{String(assertion.actual)}</code>
					</>
				)}

				{assertion.note ? <em>{' (' + assertion.note + ')'}</em> : null}
			</div>
		</li>
	);
}

function Module({result}) {
	return (
		<div
			className="card"
			data-feature-flag-enabled={String(result.enabled)}
			data-module-key={result.check.moduleKey}
			data-qa-id="auiDeprecationCheckerModule"
			data-status={result.passed ? 'pass' : 'fail'}
		>
			<div className="card-body">
				<h4 className="card-title">
					{result.check.title}{' '}

					<span
						className={
							'label ' +
							(result.enabled ? 'label-info' : 'label-secondary')
						}
					>
						{FEATURE_FLAG_KEY}

						{result.enabled ? ' enabled' : ' disabled'}
					</span>
				</h4>

				<p className="card-subtitle text-secondary">
					<code>{result.check.moduleKey}</code>

					{', removed by ' + result.check.removedBy}
				</p>
			</div>

			<ul className="list-group">
				{result.assertions.map((assertion, index) => (
					<Assertion assertion={assertion} key={index} />
				))}
			</ul>
		</div>
	);
}

function getModuleInfo(moduleKey) {
	if (typeof window.AUI !== 'function') {
		return null;
	}

	const env = AUI().Env;

	const loader = (env && env._loader) || null;

	if (!loader || !loader.moduleInfo) {
		return null;
	}

	return loader.moduleInfo[moduleKey] || null;
}

// Snapshot which preloaded globals arrived on their own, and do it before any
// check calls AUI().use. It cannot wait: liferay-auto-fields requires
// liferay-menu, so the moment the AutoFields check runs the menu is attached
// either way and the question can no longer be asked.
//
// The preload is an AUI().use() issued from modules_deprecated.js in the top
// head, so it resolves on its own schedule rather than by the time this
// component mounts. Give it a bounded window instead of sampling once.

async function getPreloadedGlobals() {
	const paths = [];

	for (const check of CHECKS) {
		if (check.preloaded) {
			paths.push(...check.globals);
		}
	}

	if (isFeatureFlagEnabled()) {
		await waitForGlobals(paths);
	}

	const preloadedGlobals = {};

	for (const path of paths) {
		preloadedGlobals[path] = resolveGlobal(path) !== undefined;
	}

	return preloadedGlobals;
}

function isFeatureFlagEnabled() {
	return Boolean(
		window.Liferay &&
			Liferay.FeatureFlags &&
			Liferay.FeatureFlags[FEATURE_FLAG_KEY]
	);
}

function isOverlayLoaded() {
	return Array.prototype.some.call(
		document.querySelectorAll('script[src]'),
		(script) => script.src.indexOf('modules_deprecated.js') !== -1
	);
}

function resolveGlobal(path) {
	return path
		.split('.')
		.reduce(
			(value, name) =>
				value === undefined || value === null ? value : value[name],
			window
		);
}

// Resolves on timeout rather than rejecting, so a global that never arrives
// fails its assertion instead of stopping the run.

function waitForGlobals(paths) {
	return new Promise((resolve) => {
		if (!paths.length) {
			resolve();

			return;
		}

		const deadline = Date.now() + PRELOAD_TIMEOUT;

		const poll = () => {
			const arrived = paths.every(
				(path) => resolveGlobal(path) !== undefined
			);

			if (arrived || Date.now() >= deadline) {
				resolve();

				return;
			}

			setTimeout(poll, PRELOAD_POLL_INTERVAL);
		};

		poll();
	});
}

// Resolves rather than rejects, so one broken module cannot stop the run. A
// module that is not registered never calls back at all, hence the timeout.
//
// Do not trust the loader response: YUI keeps one cumulative Env._missed list
// for the whole page and _notify sets response.success to false whenever that
// list is not empty, even when the modules in it were requested by unrelated
// code. Ask Env._attached about this module instead.

function attachModule(moduleKey) {
	return new Promise((resolve) => {
		if (typeof window.AUI !== 'function') {
			resolve({loaded: false, reason: 'AUI is not on this page'});

			return;
		}

		let settled = false;

		const timeoutId = setTimeout(() => {
			if (!settled) {
				settled = true;

				resolve({
					loaded: false,
					reason: 'the loader never called back',
				});
			}
		}, USE_TIMEOUT);

		try {
			AUI().use(moduleKey, (A) => {
				if (settled) {
					return;
				}

				settled = true;

				clearTimeout(timeoutId);

				const env = A.Env || {};

				if (env._attached && env._attached[moduleKey]) {
					resolve({loaded: true});

					return;
				}

				const missed = env._missed || [];

				resolve({
					loaded: false,
					reason:
						missed.indexOf(moduleKey) === -1
							? 'the loader did not attach it'
							: 'the loader reported it missing',
				});
			});
		}
		catch (error) {
			settled = true;

			clearTimeout(timeoutId);

			resolve({loaded: false, reason: String(error)});
		}
	});
}

function assert(label, expected, actual) {
	return {
		actual,
		expected,
		label,
		passed: expected === actual,
	};
}

async function runCheck(check, preloadedGlobals) {
	const enabled = isFeatureFlagEnabled();

	const moduleInfo = getModuleInfo(check.moduleKey);

	const assertions = [
		assert(
			'Registered with the AlloyUI loader',
			enabled,
			Boolean(moduleInfo)
		),
	];

	if (enabled) {
		assertions.push(
			assert(
				'Registered in the deprecated group',
				EXPECTED_GROUP,
				moduleInfo ? moduleInfo.group : '(not registered)'
			)
		);
	}

	const result = await attachModule(check.moduleKey);

	assertions.push(
		assert(
			enabled
				? 'Loads through AUI().use()'
				: 'Refuses to load through AUI().use()',
			enabled,
			result.loaded
		)
	);

	if (!result.loaded && result.reason) {
		assertions[assertions.length - 1].note = result.reason;
	}

	check.globals.forEach((path) => {
		assertions.push(
			assert(
				enabled
					? 'Global ' + path + ' is installed'
					: 'Global ' + path + ' is absent',
				enabled,
				resolveGlobal(path) !== undefined
			)
		);

		if (check.preloaded) {
			assertions.push(
				assert(
					enabled
						? 'Global ' + path + ' arrives without an AUI().use()'
						: 'Global ' +
								path +
								' does not arrive without an AUI().use()',
					enabled,
					preloadedGlobals[path]
				)
			);
		}
	});

	return {
		assertions,
		check,
		enabled,
		passed: assertions.every((assertion) => assertion.passed),
	};
}
