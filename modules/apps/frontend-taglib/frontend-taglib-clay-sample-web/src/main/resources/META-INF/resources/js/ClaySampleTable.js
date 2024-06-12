/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const ClaySampleTable = () => {
	return (
		<div className="container-fluid-max-lg">
			<h3>Wrap / No wrap</h3>

			<table className="table table-autofit">
				<thead>
					<tr>
						<th>Description</th>

						<th>Title</th>

						<th>Modified Date</th>

						<th>Expect</th>
					</tr>
				</thead>

				<tbody>
					<tr className="table-divider">
						<td colSpan="5">Text samples</td>
					</tr>

					<tr>
						<td>Long text (default)</td>

						<td
							className="table-cell-expand"
							data-testid="longTextWrap"
						>
							<div className="table-list-title">
								table list title long enough to break the line
								(not a link)
							</div>
						</td>

						<td>2 Hours Ago</td>

						<td>Break line</td>
					</tr>

					<tr>
						<td>No break text (default)</td>

						<td
							className="table-cell-expand"
							data-testid="longUnbreakableTextWrap"
						>
							<div className="table-list-title">
								.table_list_title_long_enough_to_break_the_line_(not_a_link)
							</div>
						</td>

						<td>2 Hours Ago</td>

						<td>Break line</td>
					</tr>

					<tr>
						<td>Long text (no-wrap)</td>

						<td
							className="table-cell-expand table-cell-ws-nowrap"
							data-testid="longTextNoWrap"
						>
							<div className="table-list-title">
								.table_list_title_long_enough_to_break_the_line_(not_a_link)
							</div>
						</td>

						<td>2 Hours Ago</td>

						<td>Does not break line</td>
					</tr>

					<tr>
						<td>Text (truncate)</td>

						<td
							className="table-cell-expand"
							data-testid="longTextTruncate"
						>
							<div className="table-list-title text-truncate">
								<span className="text-truncate-inline">
									<span
										className="text-truncate"
										title=".table_list_title_long_enough_to_break_the_line_(not_a_link)"
									>
										.table_list_title_long_enough_to_break_the_line_(not_a_link)
									</span>
								</span>
							</div>
						</td>

						<td>2 Hours Ago</td>

						<td>Truncate text + tooltip</td>
					</tr>

					<tr className="table-divider">
						<td colSpan="5">Link examples</td>
					</tr>

					<tr>
						<td>Link (default)</td>

						<td
							className="table-cell-expand"
							data-testid="longLinkWrap"
						>
							<div className="table-list-title">
								<a href="#">
									table list title long enough to break the
									line (this is a link)
								</a>
							</div>
						</td>

						<td>2 Hours Ago</td>

						<td>Break line</td>
					</tr>

					<tr>
						<td>No break link (default)</td>

						<td
							className="table-cell-expand"
							data-testid="longUnbreakableLinkWrap"
						>
							<div className="table-list-title">
								<a href="#">
									table_list_title_long_enough_to_break_the_line_(this_is_a_link)
								</a>
							</div>
						</td>

						<td>2 Hours Ago</td>

						<td>Break line</td>
					</tr>

					<tr>
						<td>Link (no-wrap)</td>

						<td
							className="table-cell-expand table-cell-ws-nowrap"
							data-testid="longLinkNoWrap"
						>
							<div className="table-list-title">
								<a href="#">
									table_list_title_long_enough_to_break_the_line_(this_is_a_link)
								</a>
							</div>
						</td>

						<td>2 Hours Ago</td>

						<td>Does not break line</td>
					</tr>

					<tr>
						<td>Link (truncate)</td>

						<td
							className="table-cell-expand table-cell-ws-nowrap"
							data-testid="longLinkTruncate"
						>
							<div className="table-list-title text-truncate">
								<a href="#">
									<span
										className="text-truncate"
										title=".table_list_title_long_enough_to_break_the_line_(this_is_a_link)"
									>
										.table_list_title_long_enough_to_break_the_line_(this_is_a_link)
									</span>
								</a>
							</div>
						</td>

						<td>2 Hours Ago</td>

						<td>Truncate text + tooltip</td>
					</tr>
				</tbody>
			</table>
		</div>
	);
};

export default ClaySampleTable;
