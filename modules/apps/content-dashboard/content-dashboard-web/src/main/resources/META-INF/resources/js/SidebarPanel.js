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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import ClayTabs from '@clayui/tabs';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useImperativeHandle, useState} from 'react';

import Sidebar from './components/Sidebar';

const noop = () => {};

const SidebarPanel = React.forwardRef(({fetchURL, onClose = noop}, ref) => {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);
	const [isOpen, setIsOpen] = useState(true);
	const [resourceData, setResourceData] = useState();

	const getData = (fetchURL) => {
		fetch(fetchURL, {
			method: 'GET',
		})
			.then((response) => response.json())
			.then((data) => {
				setResourceData(data);
			})
			.catch(() => {});
	};

	useEffect(() => {
		getData(fetchURL);
	}, [fetchURL]);

	useImperativeHandle(ref, () => ({
		close: () => {
			setIsOpen(false);
		},
		open: (fetchURL) => {
			getData(fetchURL);

			setIsOpen(true);
		},
	}));

	const onCloseHandle = () => {
		onClose ? onClose() : setIsOpen(false);
	};

	return (
		<Sidebar
			onClose={onCloseHandle}
			open={isOpen}
		>
			{resourceData ? (
				<>
					<Sidebar.Header
						subtitle={resourceData.subType}
						title={resourceData.title}
					>
						{resourceData.versions.map((version) => (
							<div>
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
							<ClayTabs.TabPane
								aria-labelledby="tab-1"
								className="mt-3"
							>
								<div className="sidebar-dl sidebar-section">
									<dd className="sidebar-dd">
										<ClaySticker
											className="sticker-user-icon"
											size="sm"
										>
											{'BS'}
										</ClaySticker>

										<span className="ml-2">
											<b>{resourceData.userName}</b>
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
										{resourceData.viewURLs.map(
											(language) => (
												<div className="autofit-row autofit-row-center">
													<div className="autofit-col inline-item-before">
														<ClayIcon
															symbol={language.languageId.toLowerCase()}
														/>
													</div>

													<div className="autofit-col autofit-col-expand">
														<div className="autofit-row autofit-row-center">
															<div className="autofit-col inline-item-before small">
																{
																	language.languageId
																}
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
														<a
															href={
																language.viewURL
															}
														>
															<ClayIcon symbol="view" />
														</a>
													</div>
												</div>
											)
										)}
									</dd>
								</div>

								<div className="sidebar-dl sidebar-section">
									<dt className="sidebar-dt">
										{Liferay.Language.get('tags')}
									</dt>

									<dd className="sidebar-dd">
										{resourceData.tags.map((tag) => (
											<ClayLabel displayType="secondary">
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
										{resourceData.categories.map(
											(category) => (
												<ClayLabel displayType="secondary">
													{category}
												</ClayLabel>
											)
										)}
									</dd>
								</div>

								<div className="sidebar-dl sidebar-section">
									<dt className="sidebar-dt">
										{Liferay.Language.get('display-date')}
									</dt>
									<dd className="sidebar-dd">
										{resourceData.data.displayDate}
									</dd>
								</div>

								<div className="sidebar-dl sidebar-section">
									<dt className="sidebar-dt">
										{Liferay.Language.get('creation-date')}
									</dt>
									<dd className="sidebar-dd">
										{resourceData.creationDate}
									</dd>
								</div>

								<div className="sidebar-dl sidebar-section">
									<dt className="sidebar-dt">
										{Liferay.Language.get('modified-date')}
									</dt>
									<dd className="sidebar-dd">
										{resourceData.modifiedDate}
									</dd>
								</div>

								<div className="sidebar-dl sidebar-section">
									<dt className="sidebar-dt">
										{Liferay.Language.get(
											'expiration-date'
										)}
									</dt>
									<dd className="sidebar-dd">
										{resourceData.data.expirationDate}
									</dd>
								</div>

								<div className="sidebar-dl sidebar-section">
									<dt className="sidebar-dt">
										{Liferay.Language.get('review-date')}
									</dt>
									<dd className="sidebar-dd">
										{resourceData.data.reviewDate}
									</dd>
								</div>

								<div className="sidebar-dl sidebar-section">
									<dt className="sidebar-dt">
										{Liferay.Language.get('id')}
									</dt>
									<dd className="sidebar-dd">
										{resourceData.classPK}
									</dd>
								</div>
							</ClayTabs.TabPane>
						</ClayTabs.Content>
					</Sidebar.Body>
				</>
			) : (
				<Sidebar.Body>
					<ClayLoadingIndicator />
				</Sidebar.Body>
			)}
		</Sidebar>
	);
});

export default SidebarPanel;
