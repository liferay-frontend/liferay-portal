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

package com.liferay.frontend.icons.admin.web.internal.contributor.extender;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bryce Osterhaus
 */
public class IconResourceImpl implements IconResource {

	public IconResourceImpl(String id, String svg) {
		this(id, svg, 0);
	}

	public IconResourceImpl(String id, String svg, int priority) {
		String viewBox = "";

		Matcher viewBoxMatcher = _viewBoxPattern.matcher(svg);

		if (viewBoxMatcher.find()) {
			viewBox = viewBoxMatcher.group(1);
		}

		String svgContent = "";

		Matcher svgContentMatcher = _svgContentPattern.matcher(svg);

		if (svgContentMatcher.find()) {
			svgContent = svgContentMatcher.group(1);
		}

		String symbolContent = StringUtil.replace(
			"<symbol id=\"[$NAME$]\" viewBox=\"[$VIEW_BOX$]\">" +
				"[$SYMBOL_CONTENT$]</symbol>",
			new String[] {"[$NAME$]", "[$SYMBOL_CONTENT$]", "[$VIEW_BOX$]"},
			new String[] {id, svgContent, viewBox});

		_id = id;
		_svg = svg;
		_priority = priority;

		_svgContent = symbolContent;
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public String getSVG() {
		return _svg;
	}

	@Override
	public String getSVGContent() {
		return _svgContent;
	}

	private static final Pattern _svgContentPattern = Pattern.compile(
		"(?ims)<svg.*?>(.*)</svg>");
	private static final Pattern _viewBoxPattern = Pattern.compile(
		"(?i)viewBox=\"(.*)\"(?=[\\s, >])");

	private String _id;
	private int _priority;
	private String _svg;
	private String _svgContent;

}