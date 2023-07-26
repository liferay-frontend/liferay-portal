/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ProjectList from './components/ProjectsList';
import SearchHeader from './components/SearchHeader';
import useHasManyProjects from './hooks/useHasManyProjects';

import './app.scss';

import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import useKoroneikiAccounts from '~/common/hooks/useKoroneikiAccounts';
import ProjectsNavbar from './components/ProjectsNavbar/ProjectsNavbar';

const THRESHOLD_COUNT = 4;

const Home = () => {
	const {
		data,
		fetchMore,
		fetching,
		loading,
		search,
		searching,
	} = useKoroneikiAccounts();
	const {featureFlags} = useAppPropertiesContext();
	const koroneikiAccounts = data?.c?.koroneikiAccounts;

	const hasManyProjects = useHasManyProjects(
		koroneikiAccounts?.totalCount,
		THRESHOLD_COUNT
	);

	return (
		<>
			{featureFlags.includes('LPS-191380') && (
				<ProjectsNavbar loading={loading} />
			)}

			<ClayLayout.ContainerFluid
				className="cp-home-wrapper"
				size={hasManyProjects && !loading ? 'md' : 'xl'}
			>
				<ClayLayout.Row>
					<ClayLayout.Col>
						{hasManyProjects && !loading && (
							<SearchHeader
								count={koroneikiAccounts?.totalCount}
								loading={searching}
								onSearchSubmit={search}
							/>
						)}

						<ProjectList
							compressed={hasManyProjects && !loading}
							fetching={fetching}
							koroneikiAccounts={koroneikiAccounts}
							loading={loading || searching}
							maxCardsLoading={THRESHOLD_COUNT}
							onIntersect={(currentPage) =>
								fetchMore({
									variables: {
										page: currentPage + 1,
									},
								})
							}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</>
	);
};

export default Home;
