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

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClaySticker from '@clayui/sticker';
import ClayTabs from '@clayui/tabs';
import React, {useState} from 'react';

import Sidebar from './Sidebar';

const SidebarPanelInfoView = ({
	categories,
	classPK,
	creationDate,
	data,
	modifiedDate,
	subType,
	tags,
	title,
	userName,
	versions,
	viewURLs,
}) => {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);

	return (
		<>
			<Sidebar.Header subtitle={subType} title={title}>
				{versions.map((version) => (
					<div key={version}>
						<ClayLabel displayType="info">
							{`${Liferay.Language.get('version')} ${
								version.version
							}`}
						</ClayLabel>

						<ClayLabel displayType={version.statusStyle}>
							{version.statusLabel}
						</ClayLabel>
					</div>
				))}
			</Sidebar.Header>

			<Sidebar.Body>
				<ClayTabs modern>
					<ClayTabs.Item
						active={activeTabKeyValue === 0}
						innerProps={{
							'aria-controls': 'tabpanel-0',
						}}
						onClick={() => setActiveTabKeyValue(0)}
					>
						{Liferay.Language.get('details')}
					</ClayTabs.Item>
				</ClayTabs>

				<ClayTabs.Content activeIndex={activeTabKeyValue} fade>
					<ClayTabs.TabPane aria-labelledby="tab-1" className="mt-3">
						<div className="sidebar-dl sidebar-section">
							<dd className="sidebar-dd">
								<ClaySticker
									className="sticker-user-icon"
									size="sm"
								>
									{'BS'}
								</ClaySticker>

								<span className="ml-2">
									<b>{userName}</b>
								</span>
							</dd>
						</div>

						<div className="sidebar-dl sidebar-section">
							<dt className="sidebar-dt">
								{Liferay.Language.get(
									'languages-translated-into'
								)}
							</dt>

							<dd className="sidebar-dd">
								{viewURLs.map((language) => (
									<div
										className="autofit-row autofit-row-center"
										key={language.languageId}
									>
										<div className="autofit-col inline-item-before">
											<ClayIcon
												symbol={language.languageId.toLowerCase()}
											/>
										</div>

										<div className="autofit-col autofit-col-expand">
											<div className="autofit-row autofit-row-center">
												<div className="autofit-col inline-item-before small">
													{language.languageId}
												</div>
												<div className="autofit-col">
													{language.default && (
														<ClayLabel
															className="d-inline"
															displayType="info"
														>
															{Liferay.Language.get(
																'default'
															)}
														</ClayLabel>
													)}
												</div>
											</div>
										</div>

										<div className="autofit-col">
											<a href={language.viewURL}>
												<ClayIcon symbol="view" />
											</a>
										</div>
									</div>
								))}
							</dd>
						</div>

						<div className="sidebar-dl sidebar-section">
							<dt className="sidebar-dt">
								{Liferay.Language.get('tags')}
							</dt>

							<dd className="sidebar-dd">
								{tags.map((tag) => (
									<ClayLabel
										displayType="secondary"
										key={tag}
									>
										{tag}
									</ClayLabel>
								))}
							</dd>
						</div>

						<div className="sidebar-dl sidebar-section">
							<dt className="sidebar-dt">
								{Liferay.Language.get('categories')}
							</dt>

							<dd className="sidebar-dd">
								{categories.map((category) => (
									<ClayLabel
										displayType="secondary"
										key={category}
									>
										{category}
									</ClayLabel>
								))}
							</dd>
						</div>

						<div className="sidebar-dl sidebar-section">
							<dt className="sidebar-dt">
								{Liferay.Language.get('display-date')}
							</dt>
							<dd className="sidebar-dd">{data.displayDate}</dd>
						</div>

						<div className="sidebar-dl sidebar-section">
							<dt className="sidebar-dt">
								{Liferay.Language.get('creation-date')}
							</dt>
							<dd className="sidebar-dd">{creationDate}</dd>
						</div>

						<div className="sidebar-dl sidebar-section">
							<dt className="sidebar-dt">
								{Liferay.Language.get('modified-date')}
							</dt>
							<dd className="sidebar-dd">{modifiedDate}</dd>
						</div>

						<div className="sidebar-dl sidebar-section">
							<dt className="sidebar-dt">
								{Liferay.Language.get('expiration-date')}
							</dt>
							<dd className="sidebar-dd">
								{data.expirationDate}
							</dd>
						</div>

						<div className="sidebar-dl sidebar-section">
							<dt className="sidebar-dt">
								{Liferay.Language.get('review-date')}
							</dt>
							<dd className="sidebar-dd">{data.reviewDate}</dd>
						</div>

						<div className="sidebar-dl sidebar-section">
							<dt className="sidebar-dt">
								{Liferay.Language.get('id')}
							</dt>
							<dd className="sidebar-dd">{classPK}</dd>
						</div>
					</ClayTabs.TabPane>
				</ClayTabs.Content>
			</Sidebar.Body>
		</>
	);
};

export default SidebarPanelInfoView;
