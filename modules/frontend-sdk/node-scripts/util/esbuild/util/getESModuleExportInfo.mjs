/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import module from 'module';

import getProjectAlias from '../../configuration/getProjectAlias.mjs';
import resolveWithEsbuild from './resolveWithEsbuild.mjs';

const require = module.createRequire(import.meta.url);

/**
 * Tell whether `moduleName` resolves to an ES module and, when it does, whether
 * it declares a `default` export.
 *
 * This is what lets an export bridge re-export an ES module with `export *`
 * instead of naming every symbol: esbuild reads the names out of the module
 * itself, so nothing here has to. It only works for ES modules. A CommonJS
 * module has no statically readable export list, which is why `export *` over
 * one yields nothing and its bridge still has to name each symbol.
 *
 * Returns `null` for anything that is not an ES module, so the caller falls
 * back to the named form.
 */
export default async function getESModuleExportInfo(moduleName) {
	let modulePath;

	try {
		modulePath = await resolveWithEsbuild(moduleName);
	}
	catch (_error) {
		return null;
	}

	// Requiring the resolved file is the cheapest way to tell the two apart.
	// A CommonJS module loads and is a plain object. An ES module either fails
	// to load or, on Node.js 20.19 and above, loads as a module namespace,
	// which announces itself through its @@toStringTag.

	try {
		const loadedModule = require(modulePath);

		if (loadedModule[Symbol.toStringTag] !== 'Module') {
			return null;
		}

		return {hasDefault: 'default' in loadedModule};
	}
	catch (_error) {

		// An ES module Node cannot load. Its names still come from `export *`,
		// but whether it has a `default` has to be asked of esbuild.

		const hasDefault = await hasDefaultExport(moduleName);

		// Not being able to ask is not an answer. Guessing "no" here drops a
		// `default` the module really has, silently, which is the whole class
		// of bug this is meant to avoid. Fall back to the named form instead,
		// which reads the module a different way.

		if (hasDefault === null) {
			return null;
		}

		return {hasDefault};
	}
}

async function hasDefaultExport(moduleName) {
	const esbuild = require('esbuild');

	try {
		await esbuild.build({
			alias: getProjectAlias(),
			bundle: true,
			format: 'esm',
			logLevel: 'silent',
			stdin: {
				contents: `export {default} from ${JSON.stringify(moduleName)};`,
				resolveDir: '.',
				sourcefile: 'getESModuleExportInfo-probe.js',
			},
			write: false,
		});

		return true;
	}
	catch (error) {

		// A module with no `default` is reported as a missing export. Anything
		// else means the module could not be bundled here at all, which says
		// nothing about its exports.

		for (const {text} of error.errors || []) {
			if (text.includes('No matching export')) {
				return false;
			}
		}

		return null;
	}
}
