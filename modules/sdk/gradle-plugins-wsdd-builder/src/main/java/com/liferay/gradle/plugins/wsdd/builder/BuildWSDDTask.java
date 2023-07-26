/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.wsdd.builder;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.portal.tools.wsdd.builder.WSDDBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class BuildWSDDTask extends JavaExec {

	public BuildWSDDTask() {
		setMain("com.liferay.portal.tools.wsdd.builder.WSDDBuilder");
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	@Input
	public String getBuilderClasspath() {
		return GradleUtil.toString(_builderClasspath);
	}

	@InputFile
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getInputFile() {
		return GradleUtil.toFile(getProject(), _inputFile);
	}

	@Input
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getOutputDir() {
		return GradleUtil.toFile(getProject(), _outputDir);
	}

	@Input
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getServerConfigFile() {
		return GradleUtil.toFile(getProject(), _serverConfigFile);
	}

	@Input
	public String getServiceNamespace() {
		return GradleUtil.toString(_serviceNamespace);
	}

	public void setBuilderClasspath(Object builderClasspath) {
		_builderClasspath = builderClasspath;
	}

	public void setInputFile(Object inputFile) {
		_inputFile = inputFile;
	}

	public void setOutputDir(Object outputDir) {
		_outputDir = outputDir;
	}

	public void setServerConfigFile(Object serverConfigFile) {
		_serverConfigFile = serverConfigFile;
	}

	public void setServiceNamespace(Object serviceNamespace) {
		_serviceNamespace = serviceNamespace;
	}

	protected List<String> getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("wsdd.class.path=" + getBuilderClasspath());
		args.add("wsdd.input.file=" + FileUtil.getAbsolutePath(getInputFile()));
		args.add(
			"wsdd.output.path=" + FileUtil.getAbsolutePath(getOutputDir()) +
				"/");
		args.add(
			"wsdd.server.config.file=" +
				FileUtil.getAbsolutePath(getServerConfigFile()));
		args.add("wsdd.service.namespace=" + getServiceNamespace());

		return args;
	}

	private Object _builderClasspath;
	private Object _inputFile;
	private Object _outputDir;
	private Object _serverConfigFile = WSDDBuilderArgs.SERVER_CONFIG_FILE_NAME;
	private Object _serviceNamespace = WSDDBuilderArgs.SERVICE_NAMESPACE;

}