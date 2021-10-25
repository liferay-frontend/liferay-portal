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

package com.liferay.frontend.icons.admin.web.internal.util;

import com.liferay.frontend.icons.admin.web.internal.model.IconResourceImpl;
import com.liferay.frontend.icons.model.IconResource;
import com.liferay.frontend.icons.model.IconResourcePack;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Víctor Galán
 */
public class SVGUtil {

	public static List<IconResource> getIconResources(
		String svgContent, String name) {

		List<IconResource> iconResources = new ArrayList<>();

		try {
			Document document = SAXReaderUtil.read(svgContent);

			Element rootElement = document.getRootElement();

			List<Element> symbols = rootElement.elements("symbol");

			if (ListUtil.isNotEmpty(symbols)) {
				for (Element symbol : symbols) {
					iconResources.add(
						new IconResourceImpl(
							symbol.attributeValue("id", name),
							_getInnerSVG(symbol),
							symbol.attributeValue("viewBox")));
				}
			}
			else {
				return Collections.singletonList(
					new IconResourceImpl(
						rootElement.attributeValue("id", name),
						_getInnerSVG(rootElement),
						rootElement.attributeValue("viewBox")));
			}
		}
		catch (DocumentException documentException) {
			return iconResources;
		}

		return iconResources;
	}

	public static String getSVGSpritemap(IconResourcePack iconResourcePack) {
		StringBundler sb = new StringBundler();

		for (IconResource iconResource : iconResourcePack.getIconResources()) {
			sb.append(iconResource.asSymbol());
		}

		return StringUtil.replace(
			_SPRITEMAP_TMPL, "[$CONTENT$]", sb.toString());
	}

	private static String _getInnerSVG(Element rootElement) {
		List<Element> elements = rootElement.elements();

		StringBundler sb = new StringBundler(elements.size());

		for (Element element : elements) {
			sb.append(element.asXML());
		}

		return sb.toString();
	}

	private static final String _SPRITEMAP_TMPL = StringUtil.read(
		IconResourceImpl.class,
		"/com/liferay/frontend/icons/admin/web/internal/model/dependencies" +
			"/spritemap.svg");

}