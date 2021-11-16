import React from 'react';
import {EMAIL_REGEX} from '~/common/utils/patterns';

import {ControlledInput} from '.';

export function EmailControlledInput({rules, ...props}) {
	return (
		<ControlledInput
			{...props}
			rules={{
				pattern: {
					message: 'Must be a valid email address.',
					value: EMAIL_REGEX,
				},
				...rules,
			}}
		/>
	);
}
