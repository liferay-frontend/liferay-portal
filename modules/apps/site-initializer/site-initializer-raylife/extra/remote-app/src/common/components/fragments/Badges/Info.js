import ClayIcon from '@clayui/icon';
import React from 'react';

export function InfoBadge({children, ...props}) {
	return (
		<div {...props} className="badge badge-info">
			<ClayIcon symbol="info-circle" />

			{children}
		</div>
	);
}
