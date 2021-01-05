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

package com.liferay.dispatch.talend.web.internal.archive;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.File;

import java.util.List;

/**
 * @author Igor Beslic
 */
public class TalendArchive {

	public String getClassPath() {
		return _classPath;
	}

	public String getContextName() {
		return _contextName;
	}

	public String getJobDirectory() {
		return _jobDirectory;
	}

	public String getJobJarPath() {
		return _jobJarPath;
	}

	public String getJobMainClassFQN() {
		return _jobMainClassFQN;
	}

	public static class Builder {

		public TalendArchive build() {
			return new TalendArchive(this);
		}

		public Builder classPathEntries(List<String> classPathEntries) {
			_classPathEntries = classPathEntries;

			return this;
		}

		public Builder contextName(String contextName) {
			_contextName = contextName;

			return this;
		}

		public Builder jobDirectory(String jobDirectory) {
			_jobDirectory = jobDirectory;

			return this;
		}

		public Builder jobJarPath(String jobJarPath) {
			_jobJarPath = jobJarPath;

			return this;
		}

		public Builder jobMainClassFQN(String jobMainClassFQN) {
			_jobMainClassFQN = jobMainClassFQN;

			return this;
		}

		private String _buildClassPath() {
			if (_classPathEntries == null) {
				return StringPool.BLANK;
			}

			StringBundler sb = new StringBundler(
				(_classPathEntries.size() * 2) + 1);

			for (String classPathEntry : _classPathEntries) {
				sb.append(classPathEntry);
				sb.append(File.pathSeparatorChar);
			}

			sb.append(_jobJarPath);

			return sb.toString();
		}

		private List<String> _classPathEntries;
		private String _contextName;
		private String _jobDirectory;
		private String _jobJarPath;
		private String _jobMainClassFQN;

	}

	private TalendArchive(Builder builder) {
		_classPath = builder._buildClassPath();
		_contextName = builder._contextName;
		_jobDirectory = builder._jobDirectory;
		_jobJarPath = builder._jobJarPath;
		_jobMainClassFQN = builder._jobMainClassFQN;
	}

	private final String _classPath;
	private final String _contextName;
	private final String _jobDirectory;
	private final String _jobJarPath;
	private final String _jobMainClassFQN;

}