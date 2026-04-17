/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import childProcess from 'child_process';
import {execa} from 'execa';
import fs from 'fs/promises';
import path from 'path';

import {
	MODULES_DIR,
	YARN_LOCK_FILE,
	YARN_SCRIPT_FILE,
} from '../../locations.mjs';
import print from '../../print.mjs';

const VALID_REGISTRIES = process.env.CI
	? ['https://registry.yarnpkg.com/', 'http://mirrors.lax.liferay.com:4873/']
	: ['https://registry.yarnpkg.com/'];

export default async function formatYarnLock(check) {
	let checksPassed = true;

	print(
		1,
		print.subTitle(
			`> ${check ? 'Checking' : 'Formatting'} 'yarn.lock' file...\n`
		)
	);

	if (!(await updateYarnLock(check))) {
		checksPassed = false;
	}

	if (!(await checkInvalidReferences())) {
		checksPassed = false;
	}

	return checksPassed;
}

async function updateYarnLock(check) {
	let checksPassed = true;

	const originalYarnLock = await fs.readFile(YARN_LOCK_FILE, 'utf-8');

	try {
		if (process.env.CI) {
			try {
				await execa('git', ['checkout', YARN_LOCK_FILE]);
			}
			catch (error) {
				throw new Error(`Git execution failed: ${error.stderr}`);
			}
		}

		const baseYarnLock = await fs.readFile(YARN_LOCK_FILE, 'utf-8');

		await new Promise((resolve, reject) => {
			const child = childProcess.fork(
				YARN_SCRIPT_FILE,
				['--frozen-lockfile', '--offline'],
				{
					cwd: MODULES_DIR,
					stdio: 'inherit',
				}
			);

			child.on('exit', (code) => {
				if (code === 0) {
					resolve();
				}
				else {
					reject(
						new Error(`Yarn execution failed: exit code ${code}`)
					);
				}
			});

			child.on('error', (error) => {
				reject(new Error(`Yarn execution failed: ${error}`));
			});
		});

		const newYarnLock = await fs.readFile(YARN_LOCK_FILE, 'utf-8');

		const yarnRc = await fs.readFile(
			path.join(MODULES_DIR, '.yarnrc'),
			'utf-8'
		);
		console.log(
			`\n-- YARNRC ------------------------------\n${yarnRc}\n-----------------------------`
		);

		const modified = newYarnLock != baseYarnLock;

		// const baseLines = baseYarnLock.split('\n');
		// const newLines = newYarnLock.split('\n');
		// const modifiedLines = {};

		// for (let i = 0; i < newLines.length; i++) {
		// 	if (i >= baseLines.length) {
		// 		break;
		// 	}

		// 	if (newLines[i] !== baseLines[i]) {
		// 		modifiedLines[i] = {
		// 			newLine: newLines[i],
		// 			baseLine: baseLines[i],
		// 		};

		// 		modified = true;
		// 	}
		// }

		if (check) {
			await fs.writeFile(YARN_LOCK_FILE, originalYarnLock, 'utf-8');

			if (modified) {
				print(
					2,
					print.error('ERROR:'),
					`File 'yarn.lock' needs to be updated using 'yarn install'`,
					'\n'
				);

				// for (const [i, {baseLine, newLine}] of Object.entries(
				// 	modifiedLines
				// )) {
				// 	print(
				// 		3,
				// 		`${zeroPad(i, 6)}: ${newLine}\n        ${baseLine}`
				// 	);
				// }

				checksPassed = false;
			}
		}
		else if (modified) {
			print(
				2,
				print.success('SUCCESS:'),
				`Updated 'yarn.lock' file`,
				'\n'
			);
		}
	}
	catch (error) {
		await fs.writeFile(YARN_LOCK_FILE, originalYarnLock, 'utf-8');

		print(
			2,
			print.error('ERROR:'),
			`Unhandled error formatting 'yarn.lock' file`
		);
		print(3, error, '\n');

		checksPassed = false;
	}

	return checksPassed;
}

function zeroPad(str, spaces) {
	while (str.length < spaces) {
		str = ` ${str}`;
	}

	return str;
}

async function checkInvalidReferences() {
	let checksPassed = true;

	const errorLines = {};

	const yarnLock = await fs.readFile(YARN_LOCK_FILE, 'utf-8');

	const lines = yarnLock.split('\n');

	for (let i = 0; i < lines.length; i++) {
		const line = lines[i].trimStart();

		if (!line.startsWith('resolved ')) {
			continue;
		}

		if (
			!VALID_REGISTRIES.some((validRegistry) =>
				line.startsWith(`resolved "${validRegistry}`)
			)
		) {
			errorLines[i + 1] = line.substring(9);

			checksPassed = false;
		}
	}

	if (!checksPassed) {
		print(
			2,
			print.error('ERROR:'),
			'Global',
			print.underline(`'yarn.lock'`),
			'file contains invalid references to packages'
		);

		for (const [i, line] of Object.entries(errorLines)) {
			print(3, `(${i}:0) ${line}`);
		}

		print(2, '');
	}

	return checksPassed;
}
