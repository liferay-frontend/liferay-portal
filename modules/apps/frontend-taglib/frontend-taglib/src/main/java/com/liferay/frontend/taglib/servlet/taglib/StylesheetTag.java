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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolverUtil;
import com.liferay.frontend.js.module.launcher.JSModuleResolver;
import com.liferay.frontend.taglib.internal.util.ServicesProvider;
import com.liferay.taglib.util.AttributesTagSupport;

import java.io.IOException;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class StylesheetTag extends AttributesTagSupport {

	@Override
	public int doEndTag() throws JspException {
		JspWriter jspWriter = pageContext.getOut();

		try {
			Map<String, Bundle> bundleMap = ServicesProvider.getBundleMap();

			Bundle bundle = bundleMap.get(_bundle);

			if (bundle == null) {
				throw new JspException("Unable to find bundle " + _bundle);
			}

			NPMResolver npmResolver = NPMResolverUtil.getNPMResolver(bundle);

			JSModuleResolver jsModuleResolver =
				ServicesProvider.getJSModuleResolver();

			String moduleName = jsModuleResolver.resolveModule(bundle, _css);

			String resolvedModuleName = npmResolver.resolveModuleName(
				moduleName);

			jspWriter.print("<script>Liferay.Loader.require('");
			jspWriter.print(resolvedModuleName);
			jspWriter.print("');</script>");
		}
		catch (IOException ioException) {
			throw new JspException(ioException);
		}

		return EVAL_PAGE;
	}

	public void setBundle(String bundle) {
		_bundle = bundle;
	}

	public void setCss(String css) {
		_css = css;
	}

	private String _bundle;
	private String _css;

}