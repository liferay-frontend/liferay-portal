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

package com.liferay.portlet.test;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletContext;

import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletRequestDispatcher;

import javax.servlet.ServletContext;

/**
 * @author David Arques
 * @see com.liferay.portlet.internal.PortletContextImpl
 */
public class MockLiferayPortletContext implements LiferayPortletContext {

	public MockLiferayPortletContext(String path) {
		_path = path;
	}

	@Override
	public Object getAttribute(String name) {
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		return null;
	}

	@Override
	public Enumeration<String> getContainerRuntimeOptions() {
		return null;
	}

	@Override
	public String getContextPath() {
		return null;
	}

	@Override
	public int getEffectiveMajorVersion() {
		return 0;
	}

	@Override
	public int getEffectiveMinorVersion() {
		return 0;
	}

	@Override
	public String getInitParameter(String name) {
		return null;
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return null;
	}

	@Override
	public int getMajorVersion() {
		return 0;
	}

	@Override
	public String getMimeType(String path) {
		return null;
	}

	@Override
	public int getMinorVersion() {
		return 0;
	}

	@Override
	public PortletRequestDispatcher getNamedDispatcher(String name) {
		return null;
	}

	public Portlet getPortlet() {
		return null;
	}

	@Override
	public String getPortletContextName() {
		return null;
	}

	@Override
	public String getRealPath(String path) {
		return null;
	}

	@Override
	public PortletRequestDispatcher getRequestDispatcher(String path) {
		if (Objects.equals(_path, path)) {
			return new MockPortletRequestDispatcher();
		}

		throw new UnsupportedOperationException();
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		return null;
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		return null;
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		return null;
	}

	@Override
	public String getServerInfo() {
		return null;
	}

	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public void log(String message) {
	}

	@Override
	public void log(String message, Throwable throwable) {
	}

	@Override
	public void removeAttribute(String name) {
	}

	@Override
	public void setAttribute(String name, Object object) {
	}

	private final String _path;

}