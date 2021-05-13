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

package com.liferay.portal.vulcan.aggregation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 */
@JacksonXmlRootElement(localName = "Facet")
public class Facet {

	public Facet() {
	}

	public Facet(String facetCriteria, List<FacetValue> facetValues) {
		this.facetCriteria = facetCriteria;
		this.facetValues = facetValues;
	}

	public String getFacetCriteria() {
		return facetCriteria;
	}

	public List<FacetValue> getFacetValues() {
		return facetValues;
	}

	public void setFacetCriteria(String facetCriteria) {
		this.facetCriteria = facetCriteria;
	}

	public void setFacetValues(List<FacetValue> facetValues) {
		this.facetValues = facetValues;
	}

	@JacksonXmlRootElement(localName = "FacetValue")
	public static class FacetValue {

		public FacetValue() {
		}

		public FacetValue(Integer numberOfOccurrences, String term) {
			this.numberOfOccurrences = numberOfOccurrences;
			this.term = term;
		}

		public Integer getNumberOfOccurrences() {
			return numberOfOccurrences;
		}

		public String getTerm() {
			return term;
		}

		@JsonProperty("numberOfOccurrences")
		protected Integer numberOfOccurrences;

		@JsonProperty("term")
		protected String term;

	}

	@JsonProperty("facetCriteria")
	protected String facetCriteria;

	@JsonProperty("facetValues")
	protected List<FacetValue> facetValues = new ArrayList<>();

}