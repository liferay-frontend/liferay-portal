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

package com.liferay.portal.vulcan.graphql.dto.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOContributor;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOProperty;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier de Arcos
 */
@RunWith(Arquillian.class)
public class GraphQLDTOContributorTest {

	// Needed?

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_testGraphQLDTOContributor = new TestGraphQLDTOContributor();

		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			GraphQLDTOContributor.class, _testGraphQLDTOContributor,
			HashMapBuilder.<String, Object>put(
				"dto.name", TestGraphQLDTOContributor.class.getName()
			).build());
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
		_testGraphQLDTOContributor._clear();
	}

	@Test
	public void testGraphQLDTOContributorCreateDTO() throws Exception {
		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"mutation",
				new GraphQLField(
					"createGraphQLDTO",
					HashMapBuilder.<String, Object>put(
						"GraphQLDTO", "{name: \"test\"}"
					).put(
						"siteId", TestPropsValues.getGroupId()
					).build(),
					new GraphQLField("id"), new GraphQLField("name"))));

		Assert.assertEquals(
			"test",
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/createGraphQLDTO",
				"Object/name"));
	}

	@Test
	public void testGraphQLDTOContributorDeleteDTO() throws Exception {
		_testGraphQLDTOContributor.addDTO(1, "test");

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteGraphQLDTO",
				HashMapBuilder.<String, Object>put(
					"id", 1
				).build()));

		JSONObject jsonObject = _invoke(graphQLField);

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				jsonObject, "JSONObject/data", "Object/deleteGraphQLDTO"));

		jsonObject = _invoke(graphQLField);

		Assert.assertFalse(
			JSONUtil.getValueAsBoolean(
				jsonObject, "JSONObject/data", "Object/deleteGraphQLDTO"));
	}

	@Test
	public void testGraphQLDTOContributorGetDTO() throws Exception {
		_testGraphQLDTOContributor.addDTO(1, "test");

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					"graphQLDTO",
					HashMapBuilder.<String, Object>put(
						"id", 1
					).build(),
					new GraphQLField("id"), new GraphQLField("name"))));

		Assert.assertEquals(
			"test",
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/graphQLDTO",
				"Object/name"));
	}

	@Test
	public void testGraphQLDTOContributorGetDTOs() throws Exception {
		_testGraphQLDTOContributor.addDTO(1, "test1");
		_testGraphQLDTOContributor.addDTO(2, "test2");

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					"graphQLDTOs", Collections.emptyMap(),
					new GraphQLField("totalCount"),
					new GraphQLField("items", new GraphQLField("name")))));

		Assert.assertEquals(
			2,
			JSONUtil.getValueAsLong(
				jsonObject, "JSONObject/data", "JSONObject/graphQLDTOs",
				"Object/totalCount"));

		Assert.assertEquals(
			"test1",
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/graphQLDTOs",
				"JSONArray/items", "Object/0", "Object/name"));

		Assert.assertEquals(
			"test2",
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/graphQLDTOs",
				"JSONArray/items", "Object/1", "Object/name"));
	}

	@Test
	public void testGraphQLDTOContributorUpdateDTO() throws Exception {
		_testGraphQLDTOContributor.addDTO(1, "test");

		JSONObject jsonObject = _invoke(
			new GraphQLField(
				"query",
				new GraphQLField(
					"graphQLDTO",
					HashMapBuilder.<String, Object>put(
						_testGraphQLDTOContributor.getIdName(), 1
					).build(),
					new GraphQLField("id"), new GraphQLField("name"))));

		Assert.assertEquals(
			"test",
			JSONUtil.getValueAsString(
				jsonObject, "JSONObject/data", "JSONObject/graphQLDTO",
				"Object/name"));
	}

	public static class GraphQLDTO {

		public GraphQLDTO() {
		}

		public GraphQLDTO(long id, String name) {
			_id = id;
			_name = name;
		}

		public long getId() {
			return _id;
		}

		public String getName() {
			return _name;
		}

		public void setId(long id) {
			_id = id;
		}

		public void setName(String name) {
			_name = name;
		}

		private long _id;
		private String _name;

	}

	private JSONObject _invoke(GraphQLField queryGraphQLField)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.addHeader(
			"Authorization",
			"Basic " + Base64.encode("test@liferay.com:test".getBytes()));
		options.setBody(
			JSONUtil.put(
				"query", queryGraphQLField.toString()
			).toJSONString(),
			ContentTypes.APPLICATION_JSON, "UTF-8");
		options.setLocation("http://localhost:8080/o/graphql");
		options.setPost(true);

		return JSONFactoryUtil.createJSONObject(HttpUtil.URLtoString(options));
	}

	private ServiceRegistration<GraphQLDTOContributor> _serviceRegistration;
	private TestGraphQLDTOContributor _testGraphQLDTOContributor;

	private static class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static class TestGraphQLDTOContributor
		implements GraphQLDTOContributor<GraphQLDTO, GraphQLDTO> {

		public void addDTO(long id, String name) {
			_inMemoryGraphQLDTOs.put(id, new GraphQLDTO(id, name));
		}

		@Override
		public GraphQLDTO createDTO(
			GraphQLDTO dto, DTOConverterContext dtoConverterContext) {

			long id = RandomTestUtil.randomLong();

			dto.setId(id);

			_inMemoryGraphQLDTOs.put(id, dto);

			return _inMemoryGraphQLDTOs.get(id);
		}

		@Override
		public boolean deleteDTO(long id) {
			if (!_inMemoryGraphQLDTOs.containsKey(id)) {
				throw new NoSuchElementException("No dto with id: " + id);
			}

			_inMemoryGraphQLDTOs.remove(id);

			return true;
		}

		@Override
		public GraphQLDTO getDTO(
			DTOConverterContext dtoConverterContext, long id) {

			return _inMemoryGraphQLDTOs.get(id);
		}

		@Override
		public Class<?> getDTOClass() {
			return GraphQLDTO.class;
		}

		@Override
		public Page<GraphQLDTO> getDTOs(
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			Filter filter, Pagination pagination, String search, Sort[] sorts) {

			return Page.of(_inMemoryGraphQLDTOs.values());
		}

		@Override
		public EntityModel getEntityModel() {
			return () -> HashMapBuilder.<String, EntityField>put(
				"id", new IdEntityField("id", locale -> "id", locale -> "id")
			).put(
				"name", new StringEntityField("name", locale -> "name")
			).build();
		}

		@Override
		public List<GraphQLDTOProperty> getGraphQLDTOProperties() {
			return Arrays.asList(
				new GraphQLDTOProperty("id", Long.class),
				new GraphQLDTOProperty("name", String.class));
		}

		@Override
		public String getIdName() {
			return "id";
		}

		@Override
		public String getResourceName() {
			return GraphQLDTO.class.getSimpleName();
		}

		@Override
		public GraphQLDTO updateDTO(
			GraphQLDTO dto, DTOConverterContext dtoConverterContext, long id) {

			GraphQLDTO updateDTO = _inMemoryGraphQLDTOs.get(id);

			updateDTO.setName(dto.getName());

			return updateDTO;
		}

		private void _clear() {
			_inMemoryGraphQLDTOs.clear();
		}

		private final Map<Long, GraphQLDTO> _inMemoryGraphQLDTOs =
			new LinkedHashMap<>();

	}

}