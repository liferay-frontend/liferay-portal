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

package com.liferay.frontend.icons.admin.web.internal.model;

import com.liferay.frontend.icons.model.IconResource;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Bryce Osterhaus
 */
public class IconResourceImpl implements IconResource {

	public IconResourceImpl(String id, String content, String viewBox) {
		_id = id;
		_content = content;
		_viewBox = viewBox;
	}

	@Override
	public String asSVG() {
		return StringUtil.replace(
			_SVG_TMPL, new String[] {"[$NAME$]", "[$CONTENT$]", "[$VIEW_BOX$]"},
			new String[] {_id, _content, _viewBox});
	}

	@Override
	public String asSymbol() {
		return StringUtil.replace(
			_SYMBOL_TMPL,
			new String[] {"[$NAME$]", "[$CONTENT$]", "[$VIEW_BOX$]"},
			new String[] {_id, _content, _viewBox});
	}

	@Override
	public String getId() {
		return _id;
	}

	private static String _read(String fileName) {
		return StringUtil.read(
			IconResourceImpl.class,
			StringBundler.concat(
				"/com/liferay/frontend/icons/admin/web/internal/model",
				"/dependencies/", fileName));
	}

	private static final String _SVG_TMPL = _read("icon.svg");

	private static final String _SYMBOL_TMPL = _read("symbol.svg");

	private final String _content;
	private final String _id;
	private final String _viewBox;

}