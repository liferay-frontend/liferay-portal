/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.model.listener;

import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juanjo Fernandez
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class DataSetSnapshotObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return "L_DATA_SET_SNAPSHOT";
	}

	@Override
	public void onBeforeRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		if (PortalInstances.isCurrentCompanyInDeletionProcess()) {
			return;
		}

		try {
			_deleteStartupViews(
				objectEntry.getCompanyId(),
				objectEntry.getExternalReferenceCode());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to delete the startup views for the data set " +
						"snapshot " + objectEntry.getExternalReferenceCode(),
					exception);
			}
		}
	}

	private void _deleteStartupViews(long companyId, String dataSetSnapshotERC)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_SET_SNAPSHOT_STARTUP_VIEW", companyId);

		if (objectDefinition == null) {
			return;
		}

		ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(true);

		try {
			ObjectEntryManager objectEntryManager =
				DefaultObjectEntryManagerProvider.provide(
					_objectEntryManagerRegistry.getObjectEntryManager(
						objectDefinition.getCompanyId(),
						objectDefinition.getStorageType()));

			DTOConverterContext dtoConverterContext =
				new DefaultDTOConverterContext(
					false, null, null, null, null,
					LocaleUtil.getMostRelevantLocale(), null, null);

			Page<com.liferay.object.rest.dto.v1_0.ObjectEntry> page =
				objectEntryManager.getObjectEntries(
					companyId, objectDefinition, null, null,
					dtoConverterContext,
					StringBundler.concat(
						"dataSetSnapshotERC eq '",
						StringUtil.replace(dataSetSnapshotERC, '\'', "''"),
						"'"),
					null, null, null);

			for (com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry :
					page.getItems()) {

				objectEntryManager.deleteObjectEntry(
					companyId, dtoConverterContext,
					objectEntry.getExternalReferenceCode(), objectDefinition,
					null);
			}
		}
		finally {
			ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(false);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataSetSnapshotObjectEntryModelListener.class);

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

}