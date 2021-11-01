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

import '@testing-library/jest-dom/extend-expect';
import {
	act,
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import Modal from '../../../src/main/resources/META-INF/resources/js/modals/ImportDataDefinitionModal';

const ImportDataDefinitionModal = () => <Modal portletNamespace="test" />;

describe('Import Structure Modal', () => {
	let file;
	let openModal;

	beforeEach(() => {
		cleanup();
		Liferay.component = jest.fn((_component, {open}) => {
			openModal = () => act(() => open());
		});
		Liferay.destroyComponent = jest.fn();
		file = new File(['testing file upload'], 'Testing.lar', {
			type: '.lar',
		});
	});

	it('renders the title modal', async () => {
		const {getByText} = render(<ImportDataDefinitionModal />);

		openModal();

		const title = await waitForElement(() => getByText('import-structure'));

		expect(title).toBeInTheDocument();
	});

	it('renders the info alert', async () => {
		const {queryByText} = render(<ImportDataDefinitionModal />);

		openModal();

		const infoAlert = await waitForElement(() =>
			queryByText(
				'the-import-process-will-run-in-the-background-and-may-take-a-few-minutes'
			)
		);

		expect(infoAlert).toBeInTheDocument();
	});

	it('renders name input', async () => {
		const {queryByText} = render(<ImportDataDefinitionModal />);

		openModal();

		const inputName = await waitForElement(() => queryByText('name'));

		expect(inputName).toBeInTheDocument();
	});

	it('input name has correct name prop', async () => {
		const {getByLabelText} = render(<ImportDataDefinitionModal />);

		openModal();

		const inputName = await waitForElement(() => getByLabelText('name'));

		expect(inputName).toHaveAttribute('name', 'testname');
	});

	it('renders json input', async () => {
		const {queryByText} = render(<ImportDataDefinitionModal />);

		openModal();

		const jsonInput = await waitForElement(() => queryByText('json-file'));

		expect(jsonInput).toBeInTheDocument();
	});

	it('renders the attribute name as the portlet name appended by `jsonFile`', async () => {
		const {getAllByRole} = render(<ImportDataDefinitionModal />);

		openModal();

		const inputFile = (
			await waitForElement(() => getAllByRole('textbox'))
		)[2];

		expect(inputFile).toHaveAttribute('name', 'testjsonFile');
	});

	it('renders cancel button in footer', async () => {
		const {queryByText} = render(<ImportDataDefinitionModal />);

		openModal();

		const buttonCancel = await waitForElement(() => queryByText('cancel'));

		expect(buttonCancel).toBeInTheDocument();
	});

	it('the cancel button closes modal', async () => {
		const {queryByText} = render(<ImportDataDefinitionModal />);

		openModal();

		const buttonCancel = await waitForElement(() => queryByText('cancel'));

		userEvent.click(buttonCancel);

		const body = document.getElementsByTagName('body').item(0);

		expect(body).not.toHaveClass();
	});

	it('renders import button in footer', async () => {
		const {queryByText} = render(<ImportDataDefinitionModal />);

		openModal();

		const buttonImport = await waitForElement(() => queryByText('import'));

		expect(buttonImport).toBeInTheDocument();
	});

	it('when exists a file selected, the file name appear in disabled input file', async () => {
		const {getAllByRole} = render(<ImportDataDefinitionModal />);

		openModal();

		const inputFile = (
			await waitForElement(() => getAllByRole('textbox'))
		)[2];
		const inputFileName = document.getElementById('testjsonFile');

		fireEvent.change(inputFile, {
			target: {files: [file]},
			value: 'C://downloads/Testing.lar',
		});

		expect(inputFileName).toHaveValue('Testing.lar');
		expect(inputFileName).toBeDisabled();
	});

	it('the clear button dont appear, when not exists value in input file name', async () => {
		const {getByText, queryByText} = render(<ImportDataDefinitionModal />);

		openModal();

		await waitForElement(() => getByText('import-structure'));
		const clearButton = queryByText('clear');

		expect(clearButton).not.toBeInTheDocument();
	});

	it('the clear button appear when exists value in input file name', async () => {
		const {getAllByRole, queryByText} = render(
			<ImportDataDefinitionModal />
		);

		openModal();

		const inputFile = (
			await waitForElement(() => getAllByRole('textbox'))
		)[2];
		const inputFileName = document.getElementById('testjsonFile');

		fireEvent.change(inputFile, {target: {files: [file]}});

		const clearButton = queryByText('clear');

		expect(inputFileName).toHaveValue('Testing.lar');
		expect(clearButton).toBeInTheDocument();
	});

	it('the click in clear button erases the file', async () => {
		const {getAllByRole, getByText} = render(<ImportDataDefinitionModal />);

		openModal();

		const inputFile = (
			await waitForElement(() => getAllByRole('textbox'))
		)[2];
		const inputFileName = document.getElementById('testjsonFile');

		fireEvent.change(inputFile, {target: {files: [file]}});

		userEvent.click(getByText('clear'));

		expect(inputFileName).toHaveValue('');
	});

	it('the import button is disabled when the input arent filled', async () => {
		const {queryByText} = render(<ImportDataDefinitionModal />);

		openModal();

		const buttonImport = await waitForElement(() => queryByText('import'));

		expect(buttonImport).toBeDisabled();
	});

	it('the import button isnt disabled when the inputs are filled', async () => {
		const {getByLabelText, getByText} = render(
			<ImportDataDefinitionModal />
		);

		openModal();

		const inputName = await waitForElement(() => getByLabelText('name'));
		const inputFile = document.getElementsByName('testjsonFile')[0];
		const inputFileName = getByLabelText('json-file');
		const buttonImport = getByText('import');

		fireEvent.change(inputFile, {target: {files: [file]}});
		userEvent.type(inputName, 'structure test');

		expect(inputFileName).toHaveValue('Testing.lar');
		expect(inputName).toHaveValue('structure test');
		expect(buttonImport).toBeEnabled();
	});

	it('the button import has submit property', async () => {
		const {queryByText} = render(<ImportDataDefinitionModal />);

		openModal();

		const buttonImport = await waitForElement(() => queryByText('import'));

		expect(buttonImport).toHaveAttribute('type', 'submit');
	});
});
