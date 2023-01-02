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

import {format} from 'date-fns';
import React, {useContext} from 'react';
import {
	CartesianGrid,
	Line,
	LineChart,
	ResponsiveContainer,
	Tooltip,
	XAxis,
	YAxis,
} from 'recharts';

import {AppContext} from '../../AppContext.es';
import {
	formatMonthDate,
	formatWeekDateWithYear,
	formatXAxisDate,
	formatYearDate,
	getAxisMeasuresFromData,
} from './util/chart.es';
import {HOURS, MONTHS, WEEKS, YEARS} from './util/chartConstants.es';

function VelocityChart({timeRange, velocityData = {}, velocityUnit}) {
	const {isAmPm} = useContext(AppContext);

	const {key: unitKey, name: unitName} = velocityUnit;

	const {histograms = []} = velocityData;

	const dataValues = [[...histograms.map((item) => item.value)]];

	const {intervals, maxValue} = getAxisMeasuresFromData(dataValues);

	const dataChart = histograms.map((data) => {
		data.name = formatXAxisDate(
			new Date(data.key),
			isAmPm,
			unitKey,
			timeRange
		);

		return data;
	});

	return (
		<div className="velocity-chart">
			{!!histograms.length && (
				<ResponsiveContainer height="100%" width="100%">
					<LineChart
						data={dataChart}
						height={300}
						margin={{
							bottom: 5,
							left: 20,
							right: 20,
							top: 5,
						}}
						width={1180}
					>
						<CartesianGrid strokeDasharray="3 3" />

						<XAxis dataKey="name" interval="preserveStartEnd" />

						<YAxis
							domain={[intervals, maxValue]}
							interval="preserveStartEnd"
						/>

						<Tooltip
							content={
								<CustomTooltip
									isAmPm={isAmPm}
									timeRange={timeRange}
									unit={unitName}
									unitKey={unitKey}
								/>
							}
						/>

						<Line
							activeDot={{r: 8}}
							dataKey="value"
							type="monotone"
						/>
					</LineChart>
				</ResponsiveContainer>
			)}
		</div>
	);
}

const CLASSNAME = 'workflow-tooltip-chart';

const CustomTooltip = ({active, isAmPm, payload, timeRange, unit, unitKey}) => {
	const formatTooltipDate = (date, isAmPm, timeRange, unitKey) => {
		let datePattern = "ddd',' MMM d";

		if (unitKey === HOURS) {
			if (isAmPm) {
				datePattern = "MMM dd',' hh':'mm a";
			}
			else {
				datePattern = "MMM dd',' HH':'mm";
			}

			return format(new Date(date), datePattern);
		}
		else if (unitKey === WEEKS) {
			return formatWeekDateWithYear(date, timeRange);
		}
		else if (unitKey === MONTHS) {
			return formatMonthDate(date, timeRange);
		}
		else if (unitKey === YEARS) {
			return formatYearDate(date, timeRange);
		}

		return format(new Date(date), datePattern);
	};

	const getDateTitle = (date, isAmPm, timeRange, unitKey) => {
		if (!(date instanceof Date && !isNaN(date.getTime()))) {

			// is valid date

			return '';
		}

		return formatTooltipDate(date, isAmPm, timeRange, unitKey);
	};

	if (active && payload?.length) {
		const date = new Date(payload[0].payload.key);
		const label = getDateTitle(date, isAmPm, timeRange, unitKey);

		return (
			<div className={CLASSNAME}>
				<div className={`${CLASSNAME}-header`}>
					<div
						className={`${CLASSNAME}-content ${CLASSNAME}-column font-weight-semibold`}
					>
						{label}
					</div>
				</div>

				<div className={`${CLASSNAME}-body`}>
					<div className={`${CLASSNAME}-row`}>
						<div
							className={`${CLASSNAME}-content ${CLASSNAME}-column font-weight-normal`}
						>
							{payload[0].value} {unit}
						</div>
					</div>
				</div>
			</div>
		);
	}

	return null;
};

VelocityChart.Tooltip = CustomTooltip;

export default VelocityChart;
