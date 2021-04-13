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

declare const imageSelectorCoverImageAtom: {
	readonly default: {
		readonly imageData: {
			readonly attributeDataImageId?: string | undefined;
			readonly coverImageFileEntryId?: string | undefined;
			readonly entryId?: string | undefined;
			readonly fileEntryId?: string | undefined;
			readonly groupId?: string | undefined;
			readonly mimeType?: string | undefined;
			readonly randomId?: string | undefined;
			readonly title?: string | undefined;
			readonly type?: string | undefined;
			readonly url?: string | undefined;
			readonly uuid?: string | undefined;
		};
	} | null;
	readonly key: string;
	readonly 'Liferay.State.ATOM': true;
};
export default imageSelectorCoverImageAtom;
