/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Parser} from 'acorn';
import tsPlugin from 'acorn-typescript';
import estraverse from 'estraverse';
import fs from 'fs/promises';

import projectScopeRequire from '../../projectScopeRequire.mjs';
import getSymbolsFromEsbuild from './getSymbolsFromEsbuild.mjs';
import resolveWithEsbuild from './resolveWithEsbuild.mjs';

export default async function getExportedSymbols(
	overridenPackageSymbols,
	moduleName
) {
	let symbols;

	try {
		if (overridenPackageSymbols[moduleName]) {
			symbols = {};

			overridenPackageSymbols[moduleName].forEach((symbol) => {
				symbols[symbol] = true;
			});

			if (symbols['*']) {
				delete symbols['*'];

				const loadedSymbols = await loadSymbols(moduleName);

				Object.keys(loadedSymbols).forEach((symbol) => {
					symbols[symbol] = true;
				});
			}
		}
		else {
			symbols = await loadSymbols(moduleName);

			// A CommonJS module has no `default` symbol of its own, so mimic
			// what Babel and webpack do and make `module.exports` the default
			// export. Modules tagged with `__esModule` are left alone because
			// they already export `default` when they have one.

			if (!symbols.__esModule) {
				symbols.default = true;
			}
		}
	}
	catch (error) {
		throw new Error(
			`Cannot infer exported symbols for ${moduleName}: ${error}`
		);
	}

	return symbols;
}

async function loadSymbols(moduleName) {

	// Inspect the file esbuild will bundle rather than the one Node would load,
	// because the two disagree for packages that ship a CommonJS `main` next to
	// an ES `module`.

	const modulePath = await resolveWithEsbuild(moduleName);

	let module;

	try {
		module = projectScopeRequire(modulePath);
	}
	catch (_error) {
		try {
			module = await parseESMExports(modulePath);
		}
		catch (_parseError) {

			// The module is an ES module that acorn cannot read: either it uses
			// export * or acorn-typescript rejects its source. Let esbuild link
			// it and report the symbols instead.

			return getSymbolsFromEsbuild(moduleName);
		}
	}

	const symbols = Object.keys(module).reduce((symbols, key) => {
		symbols[key] = true;

		return symbols;
	}, {});

	// Some modules config __esModule as non-enumerable, so we explicitly check
	// for it.
	//
	// Node.js 20.19 and above can require() native ES modules, returning a
	// module namespace object that carries no __esModule symbol. We detect
	// those through their @@toStringTag so that they are treated the same way
	// no matter which Node.js version runs the build.

	if (module.__esModule || module[Symbol.toStringTag] === 'Module') {
		symbols.__esModule = true;
	}

	return symbols;
}

/**
 * Record the names an `export` declares inline, as in `export function foo`,
 * `export class Foo` or `export const foo`, which name their symbol on the
 * declaration instead of listing it as a specifier.
 *
 * Only declarations that survive to runtime count. An `interface` or a `type`
 * is erased when the module is bundled, so exporting its name would describe a
 * symbol the bundle does not have. An `enum` is a real object and does count.
 *
 * A shape that is in neither list throws rather than being passed over. Ignoring
 * it would drop a symbol the module really exports and nothing would say so:
 * that is how `useDropzone` went missing from the react-dropzone bridge, from an
 * `export function` this function did not yet read. Throwing hands the module to
 * getSymbolsFromEsbuild(), which is slower but asks esbuild for the answer, so
 * an unread shape costs time instead of correctness.
 */
function addDeclaredSymbols(symbols, declaration) {
	if (!declaration) {
		return;
	}

	switch (declaration.type) {
		case 'ClassDeclaration':
		case 'FunctionDeclaration':
		case 'TSEnumDeclaration':
			symbols[declaration.id.name] = true;
			break;

		case 'VariableDeclaration':
			for (const {id} of declaration.declarations) {
				if (id.type !== 'Identifier') {
					throw new Error(
						`Cannot infer symbols from a ${id.type} in an export`
					);
				}

				symbols[id.name] = true;
			}
			break;

		// Erased when the module is bundled, so they export no symbol.

		case 'TSDeclareFunction':
		case 'TSInterfaceDeclaration':
		case 'TSTypeAliasDeclaration':
			break;

		default:
			throw new Error(
				`Cannot infer symbols from an exported ${declaration.type}`
			);
	}
}

async function parseESMExports(modulePath) {
	const ast = Parser.extend(tsPlugin()).parse(
		await fs.readFile(modulePath, 'utf-8'),
		{
			ecmaVersion: 2022,
			sourceType: 'module',
		}
	);

	const symbols = {};

	estraverse.traverse(ast, {
		enter: (node) => {
			switch (node.type) {
				case 'ExportAllDeclaration':

					// `export * as ns from` names one symbol and can be read
					// here, unlike a bare `export *`, whose names exist only in
					// the modules being re-exported.

					if (!node.exported) {
						throw new Error(
							'Cannot infer symbols if export * is used'
						);
					}

					symbols[node.exported.name] = true;
					break;

				case 'ExportDefaultDeclaration':
					symbols['default'] = true;
					break;

				case 'ExportNamedDeclaration':
					if (node.exportKind !== 'type') {
						for (const specifier of node.specifiers) {
							symbols[specifier.exported.name] = true;
						}

						addDeclaredSymbols(symbols, node.declaration);
					}
					break;

				default:
					break;
			}
		},

		fallback: 'iteration',
	});

	symbols.__esModule = true;

	return symbols;
}
