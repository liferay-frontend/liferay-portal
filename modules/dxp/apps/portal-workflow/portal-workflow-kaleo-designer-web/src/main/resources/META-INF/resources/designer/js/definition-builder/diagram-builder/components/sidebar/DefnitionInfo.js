/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayTabs from '@clayui/tabs';
import {format} from 'date-fns';
import React, {useContext, useState} from 'react';

import {DefinitionBuilderContext} from '../../../DefinitionBuilderContext';
import {
	publishDefinitionRequest,
	retrieveDefinitionRequest,
	saveDefinitionRequest,
} from '../../../util/fetchUtil';
import lang from '../../../util/lang';

const Details = ({definitionInfo}) => {
	const titleCreated = Liferay.Language.get('created');
	const titleLastModified = Liferay.Language.get('last-modified');
	const titleTotalModifications = Liferay.Language.get('total-modifications');

	const dateCreated = format(
		new Date(definitionInfo.dateCreated),
		'MMM dd, yyyy, p'
	);

	const dateModified = format(
		new Date(definitionInfo.dateModified),
		'MMM dd, yyyy, p'
	);

	const totalModifications = definitionInfo.totalModifications;

	const revisionMessage =
		totalModifications > 1
			? lang.sub(Liferay.Language.get('x-revisions'), [
					totalModifications,
			  ])
			: `${totalModifications} ${Liferay.Language.get('revision')}`;

	return (
		<>
			<div className="info-group">
				<label className="text-secondary">
					{titleCreated.toUpperCase()}
				</label>

				<span>{dateCreated}</span>
			</div>

			<div className="info-group">
				<label className="text-secondary">
					{titleLastModified.toUpperCase()}
				</label>

				<span>{dateModified}</span>
			</div>

			<div className="info-group">
				<label className="text-secondary">
					{titleTotalModifications.toUpperCase()}
				</label>

				<span>{revisionMessage}</span>
			</div>
		</>
	);
};

const VersionRow = ({versionNumber}) => {
	const {
		definitionId,
		setAlertMessage,
		setAlertType,
		setDefinitionId,
		setShowAlert,
		setVersion,
	} = useContext(DefinitionBuilderContext);

	const restoreSuccess = (response) => {
		const alertMessage = lang.sub(
			Liferay.Language.get('restored-to-revision-x'),
			[versionNumber]
		);

		setAlertMessage(alertMessage);
		setAlertType('success');

		setShowAlert(true);

		response.json().then(({name, version}) => {
			setDefinitionId(name);
			setVersion(parseInt(version, 10));
		});
	};

	const restoreFailed = () => {
		const alertMessage = Liferay.Language.get(
			'unable-to-restore-this-item'
		);

		setAlertMessage(alertMessage);
		setAlertType('danger');

		setShowAlert(true);
	};

	return (
		<div className="info-group">
			<div className="version-row">
				<label className="text-secondary">
					{Liferay.Language.get('version')} {versionNumber}
				</label>

				<ClayButtonWithIcon
					className="text-secondary"
					displayType="unstyled"
					onClick={() => {
						retrieveDefinitionRequest(definitionId, versionNumber)
							.then((response) => response.json())
							.then(
								({
									active,
									content,
									title,
									title_i18n,
									version,
								}) => {
									if (active) {
										publishDefinitionRequest({
											active,
											content,
											name: definitionId,
											title,
											title_i18n,
											version,
										}).then((response) => {
											if (response.ok) {
												restoreSuccess(response);
											}
											else {
												restoreFailed();
											}
										});
									}
									else {
										saveDefinitionRequest({
											active,
											content,
											name: definitionId,
											title,
											version,
										}).then((response) => {
											if (response.ok) {
												restoreSuccess(response);
											}
											else {
												restoreFailed();
											}
										});
									}
								}
							);
					}}
					symbol="restore"
					title={Liferay.Language.get('restore')}
				/>
			</div>

			<div className="sheet-subtitle" />
		</div>
	);
};

const RevisionHistory = ({version}) => {
	const otherVersions = [];

	for (let i = version - 1; i > 0; i--) {
		otherVersions.push({versionNumber: i});
	}

	return (
		<>
			<div className="info-group">
				<label>
					{Liferay.Language.get('current-version')}: {version}
				</label>

				<div className="sheet-subtitle" />
			</div>

			{otherVersions.map(({versionNumber}, index) => (
				<VersionRow key={index} versionNumber={versionNumber} />
			))}
		</>
	);
};

const TABS = [
	{
		Component: Details,
		label: Liferay.Language.get('details'),
	},
	{
		Component: RevisionHistory,
		label: Liferay.Language.get('revision-history'),
	},
];

export default function DefinitionInfo() {
	const [activeIndex, setActiveIndex] = useState(0);

	const {definitionInfo, version} = useContext(DefinitionBuilderContext);

	return (
		<>
			<ClayTabs>
				{TABS.map(({label}, index) => (
					<ClayTabs.Item
						active={activeIndex === index}
						key={index}
						onClick={() => setActiveIndex(index)}
					>
						{label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content activeIndex={activeIndex} fade>
				{TABS.map(({Component}, index) => (
					<ClayTabs.TabPane key={index}>
						<Component
							definitionInfo={definitionInfo}
							version={version}
						/>
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
}
