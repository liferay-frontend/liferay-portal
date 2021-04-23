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

package com.liferay.dataset.custom.view.service.impl;

import com.liferay.dataset.custom.view.model.DatasetCustomViewEntry;
import com.liferay.dataset.custom.view.service.base.DatasetCustomViewEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the dataset custom view entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.dataset.custom.view.service.DatasetCustomViewEntryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DatasetCustomViewEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.dataset.custom.view.model.DatasetCustomViewEntry",
	service = AopService.class
)
public class DatasetCustomViewEntryLocalServiceImpl
	extends DatasetCustomViewEntryLocalServiceBaseImpl {

	public DatasetCustomViewEntry createDatasetCustomViewEntry(
		String jsonString) {

		DatasetCustomViewEntry datasetCustomViewEntry =
			datasetCustomViewEntryLocalService.createDatasetCustomViewEntry(
				counterLocalService.increment());

		datasetCustomViewEntry.setSettingsJSON(jsonString);

		return datasetCustomViewEntry;
	}

	public DatasetCustomViewEntry fetchDatasetCustomViewEntry(
		long userId, String datasetDisplayId, long plid, String portletId) {

		return datasetCustomViewEntryPersistence.fetchByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);
	}

}