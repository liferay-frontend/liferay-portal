/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.google.drive;

import java.io.File;

import java.util.function.Supplier;

/**
 * Represents a link between a Documents and Media {@code FileEntry} and a
 * Google Drive file. The methods in {@link DLOpenerGoogleDriveManager} return
 * instances of this class, which you should never create directly.
 *
 * @author     Adolfo Pérez
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class DLOpenerGoogleDriveFileReference {

	/**
	 * Creates a new {@code DLOpenerGoogleDriveFileReference}.
	 *
	 * @param fileEntryId the primary key of the file entry
	 * @param titleSupplier the supplier that provides the document's title when
	 *        invoked
	 * @param fileSupplier the supplier that provides the document's contents
	 *        when invoked
	 * @param backgroundTaskId the primary key of the background process that
	 *        uploads the original file contents to Google Drive. If {@code 0},
	 *        no upload task is in progress.
	 */
	public DLOpenerGoogleDriveFileReference(
		long fileEntryId, Supplier<String> titleSupplier,
		Supplier<File> fileSupplier, long backgroundTaskId) {

		_fileEntryId = fileEntryId;
		_titleSupplier = titleSupplier;
		_fileSupplier = fileSupplier;
		_backgroundTaskId = backgroundTaskId;
	}

	/**
	 * Returns the primary key of the background task that uploads the original
	 * file contents to Google Drive. If this method returns {@code 0}, no
	 * upload task is in progress.
	 *
	 * @return the primary key of the background task
	 */
	public long getBackgroundTaskId() {
		return _backgroundTaskId;
	}

	/**
	 * Returns a file with this Google Drive file reference's content.
	 *
	 * @return the file
	 */
	public File getContentFile() {
		return _fileSupplier.get();
	}

	/**
	 * Returns the primary key of the file entry linked to this reference.
	 *
	 * @return the primary key of the file entry
	 */
	public long getFileEntryId() {
		return _fileEntryId;
	}

	/**
	 * Returns this Google Drive file reference's title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return _titleSupplier.get();
	}

	private final long _backgroundTaskId;
	private final long _fileEntryId;
	private final Supplier<File> _fileSupplier;
	private final Supplier<String> _titleSupplier;

}