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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import React, {useState} from 'react';

import Occurrences from './Occurrences';
import PanelNavigator from './PanelNavigator';
import Rule from './Rule';

// const response = {
// 	category: [],
// 	impact: [],
// };

const IMPACT_FILTER_OPTIONS = [
	{label: Liferay.Language.get('critical'), value: 'critical'},
	{label: Liferay.Language.get('serious'), value: 'serious'},
	{label: Liferay.Language.get('moderate'), value: 'moderate'},
	{label: Liferay.Language.get('minor'), value: 'minor'},
];

const CATEGORY_FILTER_OPTIONS = [
	{label: 'WCAG 2.0A', value: 'wcag2a'},
	{label: 'WCAG 2.0 Level AA', value: 'wcag2aa'},
	{label: 'WCAG 2.1 Level A', value: 'wcag21a'},
	{label: 'WCAG 2.1 Level AA', value: 'wcag21aa'},
	{label: 'Best Practices', value: 'best-practice'},
];

function ViolationsFilter({active, onViolationsFilterChange}) {
	const [selectedCategories, setSelectedCategories] = useState(['critical']);
	const [selectedImpact, setSelectedImpact] = useState(['wcag2a']);

	return (
		<ClayDropDown
			active={active}
			onActiveChange={() =>
				onViolationsFilterChange({
					category: selectedCategories,
					impact: selectedImpact,
				})
			}
		>
			<ClayDropDown.ItemList>
				<ClayDropDown.Group
					header={Liferay.Language.get('filter-by-impact')}
				>
					{IMPACT_FILTER_OPTIONS.map((item) => {
						const existsImpact = selectedImpact.findIndex(element => element === item.value) !== -1;

						return (
							<ClayDropDown.Section key={item.value}>
								<ClayCheckbox
									active={existsImpact}
									label={item.label}
									onChange={() => {
										if (existsImpact) {
											return setSelectedImpact(
												selectedImpact.filter(
													(item) =>
														item !== item.value
												)
											);
										}

										return setSelectedImpact([
											...selectedImpact,
											item.value,
										]);
									}}
								></ClayCheckbox>
							</ClayDropDown.Section>
						);
					})}
				</ClayDropDown.Group>
				<ClayDropDown.Group
					header={Liferay.Language.get('filter-by-category')}
				>
					{CATEGORY_FILTER_OPTIONS.map((item) => {
						const existsCategory = selectedCategories.findIndex(
							(element) => element === item.value
						) !== -1;

						return (
							<ClayDropDown.Section key={item.value}>
								<ClayCheckbox
									active={existsCategory}
									label={item.label}
									onChange={() => {
										if (existsCategory) {
											return setSelectedCategories(
												selectedCategories.filter(
													(item) =>
														item !== item.value
												)
											);
										}

										return setSelectedCategories([
											...selectedCategories,
											item.value,
										]);
									}}
								></ClayCheckbox>
							</ClayDropDown.Section>
						);
					})}
				</ClayDropDown.Group>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

function ViolationsPanelHeaderTitle() {
	const [dropdownActive, setDropdownActive] = useState(false);

	return (
		<div className="page-accessibility-tool__sidebar--violations-panel-header-title">
			<div className="inline-item">
				<ClayIcon className="text-danger" symbol="info-circle" />
			</div>
			<div className="inline-item inline-item-after">
				<span className="list-group-title">
					{Liferay.Language.get('accessibility-violations')}
				</span>
			</div>
			<ClayButtonWithIcon
				displayType="unstyled"
				onClick={() => setDropdownActive(!dropdownActive)}
				small
				symbol="filter"
			/>
			<ViolationsFilter
				active={dropdownActive}
				// eslint-disable-next-line no-console
				onViolationsFilterChange={(newValues) => console.log(newValues)}
			/>
		</div>
	);
}

function Panel({violations}) {
	const [violationSelected, setViolationSelected] = useState(null);

	if (!violationSelected) {
		return (
			<>
				<div className="sidebar-section">
					<ViolationsPanelHeaderTitle />
				</div>
				<div className="page-accessibility-tool__sidebar--violations-panel-header-description">
					{Liferay.Language.get(
						'set-of-rules-violated-by-the-highlighted-issues'
					)}
				</div>
				<ClayList className="list-group-flush">
					{violations.map(({id, impact, nodes, ...props}) => {
						return (
							<Rule
								id={id}
								impact={impact}
								key={id}
								nodes={nodes}
								onListItemClick={(violation) =>
									setViolationSelected(violation)
								}
								quantity={nodes.length}
								subtext={impact}
								title={id}
								{...props}
							/>
						);
					})}
				</ClayList>
			</>
		);
	}

	const {description, helpUrl, id, impact, nodes} = violationSelected;

	return (
		<ViolationDescription
			description={description}
			helpUrl={helpUrl}
			id={id}
			impact={impact}
			nodes={nodes}
			onBack={() => setViolationSelected(null)}
		/>
	);
}

function PanelSection({children, title}) {
	return (
		<ClayPanel
			displayTitle={title}
			displayType="unstyled"
			showCollapseIcon={false}
		>
			<ClayPanel.Body>{children}</ClayPanel.Body>
		</ClayPanel>
	);
}

function ViolationDescription({
	description,
	helpUrl,
	id,
	impact,
	nodes,
	onBack,
}) {
	const [occurrenceSelected, setOccurrenceSelected] = useState(null);

	if (!occurrenceSelected) {
		return (
			<>
				<PanelNavigator
					helpUrl={helpUrl}
					impact={impact}
					onBack={onBack}
					title={id}
				/>
				<div className="page-accessibility-tool__sidebar--occurrences-panel-wrapper">
					<ClayPanel.Group flush small>
						<PanelSection title={Liferay.Language.get('details')}>
							{description}
						</PanelSection>
						<PanelSection
							title={Liferay.Language.get('occurrences')}
						>
							<Occurrences.List
								nodes={nodes}
								onOccurrenceClicked={(occurrence) =>
									setOccurrenceSelected(occurrence)
								}
							/>
						</PanelSection>
					</ClayPanel.Group>
				</div>
			</>
		);
	}

	const {html, target, title} = occurrenceSelected;

	return (
		<Occurrences.Description
			html={html}
			id={id}
			impact={impact}
			nodes={nodes}
			onBack={() => setOccurrenceSelected(null)}
			target={target}
			title={title}
		/>
	);
}

function EmptyState() {
	return (
		<div className="sidebar-body">
			<div className="align-self-center sidebar-header">
				{Liferay.Language.get(
					'there-are-no-accessibility-violations-in-this-page'
				)}
			</div>
		</div>
	);
}

const Violations = {
	EmptyState,
	Panel,
};
export default Violations;
