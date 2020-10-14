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
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import axe from 'axe-core';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import ReactDOM from 'react-dom';

import './A11y.css';

function segmentAlertsByNode(alerts) {
	const nodes = alerts.flatMap((alert) =>
		alert.nodes.flatMap((node) => node.target)
	);

	return nodes.map((node) => {
		const alertsByNode = alerts.filter((alert) =>
			alert.nodes.some((n) => n.target.includes(node))
		);

		return {
			alerts: alertsByNode,
			node,
		};
	});
}

const Overlay = React.forwardRef(({style, ...othersProps}, ref) =>
	ReactDOM.createPortal(
		<div
			{...othersProps}
			className="a11y-overlay"
			ref={ref}
			style={style}
		/>,
		document.body
	)
);

function Alert({alerts, target}) {
	const [visible, setVisible] = useState(false);
	const [bounds, setBounds] = useState();

	useEffect(() => {
		const node = document.querySelector(target);

		if (!node) {
			return;
		}

		const bounds = node.getBoundingClientRect();

		if (bounds.x > 0 || bounds.y > 0) {
			const borderRadius = window
				.getComputedStyle(node, null)
				.getPropertyValue('border-radius');

			setBounds({
				'border-radius': borderRadius,
				height: bounds.height,
				left: bounds.left,
				top: bounds.top,
				width: bounds.width,
			});
		}
	}, [target, alerts]);

	return (
		<ClayPopover
			className="popover-a11y"
			header={
				<div className="autofit-row autofit-row-center">
					<div className="autofit-col autofit-col-expand">
						<span>Accessibility violation</span>
					</div>
					<div className="autofit-col">
						<ClayButtonWithIcon
							borderless
							displayType="secondary"
							onClick={() => setVisible(!visible)}
							small
							symbol="times"
						/>
					</div>
				</div>
			}
			onShowChange={setVisible}
			show={visible}
			trigger={<Overlay style={bounds} />}
		>
			{alerts.map((alert) => {
				const [{all, any}] = alert.nodes.filter((node) =>
					node.target.includes(target)
				);

				return (
					<article key={alert.id}>
						<div className="autofit-padded autofit-row">
							<div className="autofit-col">
								<ClayIcon
									className="a11y-icon"
									color={
										alert.impact === 'minor'
											? '#0B5FFF'
											: alert.impact === 'moderate'
											? '#FF8F39'
											: '#DA1414'
									}
									symbol={
										alert.impact === 'minor'
											? 'info-circle'
											: alert.impact === 'moderate'
											? 'warning-full'
											: 'exclamation-full'
									}
								/>
							</div>
							<div className="autofit-col">
								<a
									className="a11y-help"
									href={alert.helpUrl}
									rel="noopener noreferrer"
									target="_blank"
								>
									{alert.help}
								</a>
								<span className="text-muted">
									Fix {all.length > 0 ? 'all' : 'any'} of the
									following
								</span>
							</div>
						</div>
						{all.length > 0 && (
							<ul className="a11y-list">
								{all.map((check) => (
									<li key={check.id}>{check.message}</li>
								))}
							</ul>
						)}
						{any.length > 0 && (
							<ul className="a11y-list">
								{any.map((check) => (
									<li key={check.id}>{check.message}</li>
								))}
							</ul>
						)}
					</article>
				);
			})}
		</ClayPopover>
	);
}

export default function A11y({children, enable = true}) {
	const [alerts, setAlerts] = useState([]);
	const [disabled, setDisabled] = useState(!enable);
	const workInProgressRef = useRef(null);
	const idleIdRef = useRef(null);
	const childrenRef = useRef(null);

	const alertsByNode = useMemo(() => segmentAlertsByNode(alerts), [alerts]);

	useEffect(() => {
		if (!childrenRef.current || disabled) {
			return;
		}

		if (idleIdRef.current && window.cancelIdleCallback) {
			window.cancelIdleCallback(idleIdRef.current);
			idleIdRef.current = null;
		}

		const getAlerts = () =>
			axe
				.run(childrenRef.current, {reporter: 'v2'})
				.then((results) => setAlerts(results.violations))
				.catch(console.error);

		const workCallback = () => {
			if (!workInProgressRef.current) {
				workInProgressRef.current = getAlerts()
					.then(() => {
						workInProgressRef.current = null;
					})
					.catch(() => {
						workInProgressRef.current = null;
					});
			}
		};

		const mutationCallback = () => {
			if (window.requestIdleCallback) {
				if (idleIdRef.current) {
					window.cancelIdleCallback(idleIdRef.current);
				}

				idleIdRef.current = window.requestIdleCallback(workCallback);
			}
			else {
				workCallback();
			}
		};

		const observer = new MutationObserver(mutationCallback);

		observer.observe(childrenRef.current, {
			attributes: true,
			childList: true,
			subtree: true,
		});

		return () => {
			if (idleIdRef.current && window.cancelIdleCallback) {
				window.cancelIdleCallback(idleIdRef.current);
				idleIdRef.current = null;
				workInProgressRef.current = null;
			}
			observer.disconnect();
		};
	}, [disabled]);

	useEffect(() => {
		const handler = (event) => {
			if (event.ctrlKey && event.key === 'd' && enable) {
				setDisabled(!disabled);
			}
		};

		document.addEventListener('keydown', handler);

		return () => {
			document.removeEventListener('keydown', handler);
		};
	}, [disabled, enable]);

	return (
		<>
			<span ref={childrenRef}>{children}</span>
			{!disabled &&
				alertsByNode.map(({alerts, node}) => (
					<Alert alerts={alerts} key={node} target={node} />
				))}
		</>
	);
}
