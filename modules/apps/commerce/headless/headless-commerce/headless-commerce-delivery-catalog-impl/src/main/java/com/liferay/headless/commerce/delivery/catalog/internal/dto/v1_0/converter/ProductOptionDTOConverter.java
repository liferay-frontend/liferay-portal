/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "dto.class.name=CPDefinitionOptionRel",
	service = DTOConverter.class
)
public class ProductOptionDTOConverter
	implements DTOConverter<CPDefinitionOptionRel, ProductOption> {

	@Override
	public String getContentType() {
		return ProductOption.class.getSimpleName();
	}

	@Override
	public ProductOption toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
				(Long)dtoConverterContext.getId());

		String languageId = _language.getLanguageId(
			dtoConverterContext.getLocale());

		return new ProductOption() {
			{
				description = cpDefinitionOptionRel.getDescription(languageId);
				fieldType = cpDefinitionOptionRel.getDDMFormFieldTypeName();
				id = cpDefinitionOptionRel.getCPDefinitionOptionRelId();
				key = cpDefinitionOptionRel.getKey();
				name = cpDefinitionOptionRel.getName(languageId);
				optionId = cpDefinitionOptionRel.getCPOptionId();
				productOptionValues = _toProductOptionValues(
					cpDefinitionOptionRel, languageId);
				required = cpDefinitionOptionRel.isRequired();
				skuContributor = cpDefinitionOptionRel.isSkuContributor();
			}
		};
	}

	private ProductOptionValue _toProductOptionValue(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
		String languageId) {

		return new ProductOptionValue() {
			{
				id =
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId();
				key = cpDefinitionOptionValueRel.getKey();
				name = cpDefinitionOptionValueRel.getName(languageId);
				preselected = cpDefinitionOptionValueRel.isPreselected();
				priority = cpDefinitionOptionValueRel.getPriority();
			}
		};
	}

	private ProductOptionValue[] _toProductOptionValues(
		CPDefinitionOptionRel cpDefinitionOptionRel, String languageId) {

		int total =
			_cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRelsCount(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId());

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			_cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRels(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId(), 0,
					total);

		List<ProductOptionValue> productOptionValues = new ArrayList<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			productOptionValues.add(
				_toProductOptionValue(cpDefinitionOptionValueRel, languageId));
		}

		return productOptionValues.toArray(new ProductOptionValue[0]);
	}

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private Language _language;

}