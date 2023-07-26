/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gislayne Vitorino
 */
@Component(service = CTDisplayRenderer.class)
public class JournalArticleResourceCTDisplayRenderer
	extends BaseCTDisplayRenderer<JournalArticleResource> {

	@Override
	public Class<JournalArticleResource> getModelClass() {
		return JournalArticleResource.class;
	}

	@Override
	public String getTitle(
			Locale locale, JournalArticleResource journalArticleResource)
		throws PortalException {

		return StringBundler.concat(
			journalArticleResource.getModelClassName(), " ",
			journalArticleResource.getResourcePrimKey());
	}

	@Override
	public boolean isHideable(JournalArticleResource journalArticleResource) {
		return true;
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<JournalArticleResource> displayBuilder) {

		JournalArticleResource journalArticleResource =
			displayBuilder.getModel();

		displayBuilder.display(
			"mvcc-version", journalArticleResource.getMvccVersion()
		).display(
			"ct-collection-id", journalArticleResource.getCtCollectionId()
		).display(
			"uuid", journalArticleResource.getUuid()
		).display(
			"resource-prim-key", journalArticleResource.getResourcePrimKey()
		).display(
			"group-id", journalArticleResource.getGroupId()
		).display(
			"company-id", journalArticleResource.getCompanyId()
		).display(
			"article-id", journalArticleResource.getArticleId()
		);
	}

}