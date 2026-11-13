/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.editor.configuration.internal;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Sanz
 */
@Component(
	property = {
		"editor.config.key=fragmentEntryLinkRichTextEditor",
		"jakarta.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"service.ranking:Integer=101"
	},
	service = EditorConfigContributor.class
)
public class RichTextFragmentEntryLinkAMEditorConfigContributor
	extends BaseAMEditorConfigContributor {

	@Override
	protected boolean isItemSelectorCriterionOverridable(
		ItemSelectorCriterion itemSelectorCriterion) {

		if (itemSelectorCriterion instanceof ImageItemSelectorCriterion) {
			return true;
		}

		return false;
	}

}