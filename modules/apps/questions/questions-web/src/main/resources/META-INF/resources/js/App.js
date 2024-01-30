/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClientContext} from 'graphql-hooks';
import React from 'react';
import {BrowserRouter, HashRouter, Route, Switch} from 'react-router-dom';

import {AppContextProvider} from './AppContext';
import {ErrorBoundary} from './components/ErrorBoundary';
import ProtectedRoute from './components/ProtectedRoute';
import useLazy from './hooks/useLazy';
import NavigationBar from './pages/NavigationBar';
import {client} from './utils/client';
import {getFullPath} from './utils/utils';

export function App(props) {
	redirectForNotifications(props);

	const Component = useLazy();

	const Router = props.historyRouterBasePath ? BrowserRouter : HashRouter;

	const packageName = props.npmResolvedPackageName;

	const questionsComponent = `${packageName}/js/pages/questions/Questions`;

	let path = props.historyRouterBasePath;

	if (path && props.i18nPath) {
		path = props.i18nPath + path;
	}

	if (path && location.pathname.includes(path)) {
		path = location.pathname.slice(
			0,
			location.pathname.indexOf(path) + path.length
		);
	}

	return (
		<ClientContext.Provider value={client}>
			<AppContextProvider {...props}>
				<Router basename={path}>
					<ErrorBoundary>
						<div>
							<NavigationBar />

							<Switch>
								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/pages/home/Home`}
											props={{...props, isHomePath: true}}
										/>
									)}
									exact
									path="/"
								/>

								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/pages/home/Home`}
											props={props}
										/>
									)}
									exact
									path="/questions"
								/>

								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/components/ForumsToQuestion`}
											props={props}
										/>
									)}
									exact
									path="/questions/question/:questionId"
								/>

								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/pages/home/UserActivity`}
											props={props}
										/>
									)}
									exact
									path="/questions/activity/:creatorId"
								/>

								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/pages/home/UserSubscriptions`}
											props={props}
										/>
									)}
									exact
									path="/questions/subscriptions/:creatorId"
								/>

								<Route
									component={(props) => (
										<Component
											module={questionsComponent}
											props={props}
										/>
									)}
									exact
									path="/questions/tag/:tag"
								/>

								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/pages/tags/Tags`}
											props={props}
										/>
									)}
									exact
									path="/tags"
								/>

								<Route
									path="/questions/:sectionTitle"
									render={({match: {path}}) => (
										<>
											<Switch>
												<ProtectedRoute
													component={(props) => (
														<Component
															module={`${packageName}/js/pages/answers/EditAnswer`}
															props={props}
														/>
													)}
													exact
													path={`${path}/:questionId/answers/:answerId/edit`}
												/>

												<Route
													component={(props) => (
														<Component
															module={
																questionsComponent
															}
															props={props}
														/>
													)}
													exact
													path={`${path}/creator/:creatorId`}
												/>

												<Route
													component={(props) => (
														<Component
															module={
																questionsComponent
															}
															props={props}
														/>
													)}
													exact
													path={`${path}/tag/:tag`}
												/>

												<ProtectedRoute
													component={(props) => (
														<Component
															module={`${packageName}/js/pages/questions/NewQuestion`}
															props={props}
														/>
													)}
													exact
													path={`${path}/new`}
												/>

												<Route
													component={(props) => (
														<Component
															module={`${packageName}/js/pages/questions/Question`}
															props={props}
														/>
													)}
													exact
													path={`${path}/:questionId`}
												/>

												<ProtectedRoute
													component={(props) => (
														<Component
															module={`${packageName}/js/pages/questions/EditQuestion`}
															props={props}
														/>
													)}
													exact
													path={`${path}/:questionId/edit`}
												/>

												<Route
													component={(props) => (
														<Component
															module={
																questionsComponent
															}
															props={props}
														/>
													)}
													exact
													path={`${path}/`}
												/>
											</Switch>
										</>
									)}
								/>
							</Switch>
						</div>
					</ErrorBoundary>
				</Router>
			</AppContextProvider>
		</ClientContext.Provider>
	);

	function redirectForNotifications(props) {
		if (window.location.search && !props.historyRouterBasePath) {
			const urlSearchParams = new URLSearchParams(window.location.search);

			const redirectTo = urlSearchParams.get('redirectTo');
			if (redirectTo) {
				window.history.replaceState(
					{},
					document.title,
					getFullPath() + decodeURIComponent(redirectTo)
				);
			}
		}
	}
}
