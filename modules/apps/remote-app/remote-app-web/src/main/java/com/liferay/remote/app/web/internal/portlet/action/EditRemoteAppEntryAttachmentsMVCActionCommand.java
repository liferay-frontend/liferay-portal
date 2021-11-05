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

package com.liferay.remote.app.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.service.RemoteAppEntryService;
import com.liferay.remote.app.web.internal.constants.RemoteAppAdminPortletKeys;
import com.liferay.remote.app.web.internal.upload.RemoteAppEntryAttachmentUploadFileEntryHandler;
import com.liferay.upload.UploadHandler;
import com.liferay.upload.UploadResponseHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(
	property = {
		"javax.portlet.name=" + RemoteAppAdminPortletKeys.REMOTE_APP_ADMIN,
		"mvc.command.name=/remote_app_admin/edit_remote_app_entry_attachments"
	},
	service = MVCActionCommand.class
)
public class EditRemoteAppEntryAttachmentsMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addTempAttachment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_remoteAppEntryAttachmentUploadFileEntryHandler,
			_multipleUploadResponseHandler, actionRequest, actionResponse);
	}

	protected void deleteTempAttachment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String fileName = ParamUtil.getString(actionRequest, "fileName");
		long remoteAppEntryId = ParamUtil.getLong(
			actionRequest, "remoteAppEntryId");

		_remoteAppEntryService.deleteTempAttachment(
			remoteAppEntryId, fileName, RemoteAppConstants.RESOURCE_NAME);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD_TEMP)) {
			addTempAttachment(actionRequest, actionResponse);
		}
		else if (cmd.equals(Constants.DELETE_TEMP)) {
			deleteTempAttachment(actionRequest, actionResponse);
		}

		if (Validator.isNotNull(cmd)) {
			String redirect = ParamUtil.getString(actionRequest, "redirect");

			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	@Reference(target = "(upload.response.handler=multiple)")
	private UploadResponseHandler _multipleUploadResponseHandler;

	@Reference
	private Portal _portal;

	@Reference
	private RemoteAppEntryAttachmentUploadFileEntryHandler
		_remoteAppEntryAttachmentUploadFileEntryHandler;

	@Reference
	private RemoteAppEntryService _remoteAppEntryService;

	@Reference
	private UploadHandler _uploadHandler;

}