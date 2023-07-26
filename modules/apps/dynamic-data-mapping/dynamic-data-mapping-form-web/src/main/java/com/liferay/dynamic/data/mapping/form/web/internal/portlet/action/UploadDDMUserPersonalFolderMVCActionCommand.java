/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.web.internal.upload.DDMUserPersonalFolderUploadFileEntryHandler;
import com.liferay.dynamic.data.mapping.form.web.internal.upload.DDMUserPersonalFolderUploadResponseHandler;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.upload.UploadHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM,
		"mvc.command.name=/dynamic_data_mapping_form/upload_ddm_user_personal_folder"
	},
	service = MVCActionCommand.class
)
public class UploadDDMUserPersonalFolderMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_ddmUserPersonalFolderUploadFileEntryHandler,
			_ddmUserPersonalFolderUploadResponseHandler, actionRequest,
			actionResponse);
	}

	@Reference
	private DDMUserPersonalFolderUploadFileEntryHandler
		_ddmUserPersonalFolderUploadFileEntryHandler;

	@Reference
	private DDMUserPersonalFolderUploadResponseHandler
		_ddmUserPersonalFolderUploadResponseHandler;

	@Reference
	private UploadHandler _uploadHandler;

}