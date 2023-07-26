/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.util.comparator;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AMDistanceComparator;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;

import java.util.Collections;
import java.util.Map;

/**
 * @author Sergio González
 */
public class AMAttributeDistanceComparator
	implements AMDistanceComparator<AdaptiveMedia<AMImageProcessor>> {

	public AMAttributeDistanceComparator(
		AMAttribute<AMImageProcessor, ?> amAttribute) {

		this(
			Collections.singletonMap(
				amAttribute, AMImageQueryBuilder.SortOrder.ASC));
	}

	public AMAttributeDistanceComparator(
		AMAttribute<AMImageProcessor, ?> amAttribute,
		AMImageQueryBuilder.SortOrder sortOrder) {

		this(Collections.singletonMap(amAttribute, sortOrder));
	}

	public AMAttributeDistanceComparator(
		Map<AMAttribute<AMImageProcessor, ?>, AMImageQueryBuilder.SortOrder>
			sortCriteria) {

		_sortCriteria = (Map)sortCriteria;
	}

	@Override
	public long compare(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia1,
		AdaptiveMedia<AMImageProcessor> adaptiveMedia2) {

		for (Map.Entry
				<AMAttribute<AMImageProcessor, Object>,
				 AMImageQueryBuilder.SortOrder> sortCriterion :
					_sortCriteria.entrySet()) {

			AMAttribute<AMImageProcessor, Object> amAttribute =
				sortCriterion.getKey();

			Object value1 = adaptiveMedia1.getValue(amAttribute);
			Object value2 = adaptiveMedia2.getValue(amAttribute);

			if ((value1 != null) && (value2 != null)) {
				AMImageQueryBuilder.SortOrder sortOrder =
					sortCriterion.getValue();

				long result = sortOrder.getSortValue(
					amAttribute.compare(value1, value2));

				if (result != 0) {
					return result;
				}
			}
		}

		return 0L;
	}

	private final Map
		<AMAttribute<AMImageProcessor, Object>, AMImageQueryBuilder.SortOrder>
			_sortCriteria;

}