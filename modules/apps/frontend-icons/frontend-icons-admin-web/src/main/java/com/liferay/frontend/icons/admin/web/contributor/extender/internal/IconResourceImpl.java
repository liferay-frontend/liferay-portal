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

package com.liferay.frontend.icons.admin.web.contributor.extender.internal;

import com.liferay.frontend.icons.admin.web.contributor.extender.IconResource;
import com.liferay.petra.string.StringPool;

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

		Pattern patternViewBox = Pattern.compile("(?i)(viewBox.*\"(?=[\\s, >]))");
		Matcher matcherViewBox = patternViewBox.matcher(svg);
		if (matcherViewBox.find()) {
			viewBox = matcherViewBox.group(1);
		}

		String svgContent = "";

		Pattern patternSvgContent = Pattern.compile("(?ims)<svg.*?>(.*)</svg>");
		Matcher matcherSvgContent = patternSvgContent.matcher(svg);
		if (matcherSvgContent.find()) {
			svgContent = matcherSvgContent.group(1);
		}

		svgContent = "<symbol id=" + StringPool.QUOTE + id + StringPool.QUOTE + StringPool.SPACE + viewBox + ">" + svgContent + "</symbol>";

		_id = id;
		_svgContent = svgContent;
		_svg = svg;
		_priority = priority;
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

	private String _id;
	private int _priority;
	private String _svg;
	private String _svgContent;

}