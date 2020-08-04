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

AUI.add(
	'liferay-editor-image-uploader',
	(A) => {
		var Lang = A.Lang;

		var CSS_UPLOADING_IMAGE = 'uploading-image';

		var STR_BLANK = '';

		var TPL_IMAGE_CONTAINER =
			'<div class="uploading-image-container"></div>';

		var TPL_PROGRESS_BAR = '<div class="progressbar"></div>';

		var EditorImageUploader = A.Component.create({
			ATTRS: {
				editorName: {
					validator: Lang.isString,
					value: STR_BLANK,
				},

				strings: {
					validator: Lang.isObject,
					value: {
						uploadingFileError: Liferay.Language.get(
							'an-unexpected-error-occurred-while-uploading-your-file'
						),
					},
				},

				timeout: {
					validator: Lang.isNumber,
					value: 10000,
				},

				uploadItemReturnType: {
					validator: Lang.isString,
					value: STR_BLANK,
				},

				uploadUrl: {
					validator: Lang.isString,
					value: STR_BLANK,
				},
			},

			NAME: 'editorimageupload',

			NS: 'editorimageupload',

			prototype: {
				_createProgressBar(image) {
					var imageContainerNode = A.Node.create(TPL_IMAGE_CONTAINER);
					var progressBarNode = A.Node.create(TPL_PROGRESS_BAR);

					image.wrap(imageContainerNode);

					imageContainerNode.appendChild(progressBarNode);

					var progressbar = new A.ProgressBar({
						boundingBox: progressBarNode,
					}).render();

					return progressbar;
				},

				_getAlert() {
					var instance = this;

					if (!instance._alert) {
						instance._alert = new A.Alert({
							animated: true,
							closeable: true,
							cssClass: null,
							duration: instance.get('timeout'),
							render: true,
						});
					}

					return instance._alert;
				},

				_getUploader() {
					var instance = this;

					var uploader = instance._uploader;

					if (!uploader) {
						uploader = new A.Uploader({
							fileFieldName: 'imageSelectorFileName',
							uploadURL: instance.get('uploadUrl'),
						});

						instance._uploader = uploader;
					}

					return uploader;
				},

				_onImageAdd(event) {
					var instance = this;

					var eventData = event.data;

					var file = eventData.file;
					var image = eventData.el.$;

					image = A.one(image);

					var randomId = eventData.randomId || A.guid();

					image.attr('data-random-id', randomId);

					image.addClass(CSS_UPLOADING_IMAGE);

					instance._tempImage = image;

					var uploader = eventData.uploader;

					if (uploader) {
						uploader.on(
							'uploadcomplete',
							instance._onUploadComplete,
							instance
						);
						uploader.on(
							'uploaderror',
							instance._onUploadError,
							instance
						);
						uploader.on(
							'uploadprogress',
							instance._onUploadProgress,
							instance
						);
					}
					else {
						file = new A.FileHTML5(file);

						instance._uploadImage(file, randomId);
					}

					file.progressbar = instance._createProgressBar(image);
				},

				_onUploadComplete(event) {
					var instance = this;

					var target = event.details[0].target;

					var progressbar = target.progressbar;

					if (progressbar) {
						progressbar.destroy();
					}

					var data = JSON.parse(event.data);

					if (data.success) {
						var image = instance._tempImage;

						if (image) {
							image.removeAttribute('data-random-id');
							image.removeClass(CSS_UPLOADING_IMAGE);

							image.attr(
								data.file.attributeDataImageId,
								data.file.fileEntryId
							);

							var editor = instance._editor;

							var imageSrc = editor.config.attachmentURLPrefix
								? editor.config.attachmentURLPrefix +
								  data.file.title
								: data.file.url;

							image.attr('src', imageSrc);

							var imageContainer = image.ancestor();

							image.unwrap(imageContainer);

							imageContainer.remove();

							editor.fire('imageUploaded', {
								el: image,
								fileEntryId: data.file.fileEntryId,
								uploadImageReturnType: instance.get(
									'uploadItemReturnType'
								),
							});
						}
					}
					else {
						instance._removeTempImage(data.file);
					}
				},

				_onUploadError(event) {
					var instance = this;

					instance._removeTempImage(event);
				},

				_onUploadProgress(event) {
					var percentLoaded = Math.round(event.percentLoaded);

					var target = event.details[0].target;

					var progressbar = target.progressbar;

					if (progressbar) {
						progressbar.set('label', percentLoaded + ' %');

						progressbar.set('value', Math.ceil(percentLoaded));
					}
				},

				_removeTempImage() {
					var instance = this;

					var image = instance._tempImage;

					if (image) {
						image.ancestor().remove();
					}

					var strings = instance.get('strings');

					Liferay.Util.openToast({
						message: strings.uploadingFileError,
						type: 'danger',
					});
				},

				_uploadImage(file, randomId) {
					var instance = this;

					var uploader = instance._getUploader();

					uploader.set('postVarsPerFile', {
						randomId,
					});

					uploader.upload(file);
				},

				destructor() {
					var instance = this;

					if (instance._uploader) {
						instance._uploader.destroy();
					}

					if (instance._alert) {
						instance._alert.destroy();
					}

					instance._editor.removeListener(
						'imageAdd',
						instance._uploadImage
					);

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					var editor = CKEDITOR.instances[instance.get('editorName')];

					editor.on('imageAdd', instance._onImageAdd, instance);

					instance._editor = editor;

					var uploader = instance._getUploader();

					instance._eventHandles = [
						uploader.on(
							'uploadcomplete',
							instance._onUploadComplete,
							instance
						),
						uploader.on(
							'uploaderror',
							instance._onUploadError,
							instance
						),
						uploader.on(
							'uploadprogress',
							instance._onUploadProgress,
							instance
						),
					];
				},
			},
		});

		Liferay.EditorImageUploader = EditorImageUploader;
	},
	'',
	{
		requires: ['aui-alert', 'aui-base', 'aui-progressbar', 'uploader'],
	}
);
