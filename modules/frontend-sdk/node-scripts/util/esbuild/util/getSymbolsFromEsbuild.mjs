/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import esbuild from 'esbuild';

import getProjectAlias from '../../configuration/getProjectAlias.mjs';

/**
 * Ask esbuild which symbols a module exports.
 *
 * getExportedSymbols() normally works out the symbol set by requiring the
 * module and reading its keys, falling back to an acorn parse when the require
 * fails because the module is an ES module. Both give up on modules that are
 * perfectly valid:
 *
 * - acorn cannot enumerate `export *` because the names live in the modules
 *   being re-exported, which is why `@clayui/core` and `@clayui/provider` are
 *   listed by hand.
 * - acorn-typescript rejects some TypeScript sources outright. It fails on
 *   `@clayui/date-picker` with `Export 'FirstDayOfWeek' is not defined`, even
 *   though the export is there.
 *
 * esbuild resolves and links the module exactly as the bundle will, so it can
 * answer both cases. The cost is that it bundles the module's whole dependency
 * graph to do it, which is far more expensive than a require or a parse, so
 * this is a last resort rather than the normal path.
 *
 * Two probes are needed because `export *` deliberately omits `default`, so a
 * module's own `default` has to be asked for separately.
 */
export default async function getSymbolsFromEsbuild(moduleName) {
	const symbols = {};

	for (const name of await getNamedExports(moduleName)) {
		symbols[name] = true;
	}

	if (await hasDefaultExport(moduleName)) {
		symbols.default = true;
	}

	// esbuild reports what the module really exports, `default` included, so
	// the CommonJS inference in getExportedSymbols() must not add one on top.

	symbols.__esModule = true;

	return symbols;
}

async function getNamedExports(moduleName) {
	const {metafile} = await runProbe(
		moduleName,
		`export * from ${JSON.stringify(moduleName)};`
	);

	for (const output of Object.values(metafile.outputs)) {
		if (output.exports) {
			return output.exports;
		}
	}

	return [];
}

async function hasDefaultExport(moduleName) {
	try {
		await runProbe(
			moduleName,
			`export {default} from ${JSON.stringify(moduleName)};`
		);

		return true;
	}
	catch (error) {

		// esbuild reports a module that has no `default` as a missing export
		// rather than as a resolution or syntax problem, so anything else is a
		// real failure and must not be read as "there is no default".

		for (const {text} of error.errors || []) {
			if (text.includes('No matching export')) {
				return false;
			}
		}

		throw error;
	}
}

function runProbe(moduleName, source) {
	return esbuild.build({
		alias: getProjectAlias(),
		bundle: true,
		format: 'esm',
		logLevel: 'silent',
		metafile: true,
		stdin: {
			contents: source,

			// Resolve from the project directory, not from the directory the
			// export bridge lives in: that only exists in a project that
			// exports something, and a project that merely imports has none.

			resolveDir: '.',
			sourcefile: 'getSymbolsFromEsbuild-probe.js',
		},
		write: false,
	});
}
