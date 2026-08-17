/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import esbuild from 'esbuild';

import getProjectAlias from '../../configuration/getProjectAlias.mjs';

/**
 * Return the file esbuild resolves `moduleName` to.
 *
 * Node and esbuild do not agree on which file a package name means. Node reads
 * `main`, knows nothing about the `alias` given to esbuild and does not see the
 * linker plugin, while esbuild prefers `browser` and `module` and honours the
 * `import` condition of `exports`. Packages that ship both builds land on
 * opposite sides of that split: `@codemirror/state` names a CommonJS `main` and
 * an ES `module`, so Node loads the CommonJS file while esbuild bundles the ES
 * one. Symbols inferred from the wrong file describe a module that is not the
 * one being bundled.
 *
 * Asking esbuild removes the disagreement by construction, because the answer
 * comes from the resolver that will do the real work.
 *
 * The plugin returns empty contents from onLoad instead of letting esbuild read
 * the file. That stops traversal at the entry, so this costs a single
 * resolution rather than a bundle, and it is what lets the probe answer for
 * modules whose full bundle needs the linker plugin and a stylesheet loader.
 */
export default function resolveWithEsbuild(moduleName) {

	// Both bridges ask about the same packages, and exports are bundled
	// concurrently, so cache the promise rather than the path: that way
	// overlapping calls for one package share a single resolution instead of
	// racing to repeat it.

	if (!modulePaths.has(moduleName)) {
		modulePaths.set(moduleName, doResolveWithEsbuild(moduleName));
	}

	return modulePaths.get(moduleName);
}

const modulePaths = new Map();

async function doResolveWithEsbuild(moduleName) {
	let modulePath;

	await esbuild.build({
		alias: getProjectAlias(),
		bundle: true,
		logLevel: 'silent',
		plugins: [
			{
				name: 'resolve-only',
				setup(build) {
					build.onLoad({filter: /.*/}, ({path}) => {
						if (modulePath === undefined) {
							modulePath = path;
						}

						return {contents: '', loader: 'js'};
					});
				},
			},
		],
		stdin: {

			// Resolve from the project directory, the way the Node resolution
			// this replaces did. It cannot be the directory the export bridge
			// lives in: that only exists in a project that exports something,
			// and a project that merely imports has no such directory for
			// esbuild to resolve from.

			contents: `import ${JSON.stringify(moduleName)};`,
			resolveDir: '.',
			sourcefile: 'resolveWithEsbuild-probe.js',
		},
		write: false,
	});

	if (modulePath === undefined) {
		throw new Error(`Esbuild resolved ${moduleName} to nothing`);
	}

	return modulePath;
}
