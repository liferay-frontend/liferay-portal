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

package com.liferay.remote.app.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.remote.app.rest.client.dto.v1_0.RemoteAppEntry;
import com.liferay.remote.app.rest.client.http.HttpInvoker;
import com.liferay.remote.app.rest.client.pagination.Page;
import com.liferay.remote.app.rest.client.pagination.Pagination;
import com.liferay.remote.app.rest.client.resource.v1_0.RemoteAppEntryResource;
import com.liferay.remote.app.rest.client.serdes.v1_0.RemoteAppEntrySerDes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bruno Basto
 * @generated
 */
@Generated("")
public abstract class BaseRemoteAppEntryResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_remoteAppEntryResource.setContextCompany(testCompany);

		RemoteAppEntryResource.Builder builder =
			RemoteAppEntryResource.builder();

		remoteAppEntryResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		RemoteAppEntry remoteAppEntry1 = randomRemoteAppEntry();

		String json = objectMapper.writeValueAsString(remoteAppEntry1);

		RemoteAppEntry remoteAppEntry2 = RemoteAppEntrySerDes.toDTO(json);

		Assert.assertTrue(equals(remoteAppEntry1, remoteAppEntry2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		RemoteAppEntry remoteAppEntry = randomRemoteAppEntry();

		String json1 = objectMapper.writeValueAsString(remoteAppEntry);
		String json2 = RemoteAppEntrySerDes.toJSON(remoteAppEntry);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		RemoteAppEntry remoteAppEntry = randomRemoteAppEntry();

		remoteAppEntry.setUrl(regex);

		String json = RemoteAppEntrySerDes.toJSON(remoteAppEntry);

		Assert.assertFalse(json.contains(regex));

		remoteAppEntry = RemoteAppEntrySerDes.toDTO(json);

		Assert.assertEquals(regex, remoteAppEntry.getUrl());
	}

	@Test
	public void testGetRemoteAppEntriesPage() throws Exception {
		Page<RemoteAppEntry> page =
			remoteAppEntryResource.getRemoteAppEntriesPage(
				RandomTestUtil.randomString(), Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		RemoteAppEntry remoteAppEntry1 =
			testGetRemoteAppEntriesPage_addRemoteAppEntry(
				randomRemoteAppEntry());

		RemoteAppEntry remoteAppEntry2 =
			testGetRemoteAppEntriesPage_addRemoteAppEntry(
				randomRemoteAppEntry());

		page = remoteAppEntryResource.getRemoteAppEntriesPage(
			null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(remoteAppEntry1, remoteAppEntry2),
			(List<RemoteAppEntry>)page.getItems());
		assertValid(page);

		remoteAppEntryResource.deleteRemoteAppEntry(remoteAppEntry1.getId());

		remoteAppEntryResource.deleteRemoteAppEntry(remoteAppEntry2.getId());
	}

	@Test
	public void testGetRemoteAppEntriesPageWithPagination() throws Exception {
		RemoteAppEntry remoteAppEntry1 =
			testGetRemoteAppEntriesPage_addRemoteAppEntry(
				randomRemoteAppEntry());

		RemoteAppEntry remoteAppEntry2 =
			testGetRemoteAppEntriesPage_addRemoteAppEntry(
				randomRemoteAppEntry());

		RemoteAppEntry remoteAppEntry3 =
			testGetRemoteAppEntriesPage_addRemoteAppEntry(
				randomRemoteAppEntry());

		Page<RemoteAppEntry> page1 =
			remoteAppEntryResource.getRemoteAppEntriesPage(
				null, Pagination.of(1, 2), null);

		List<RemoteAppEntry> remoteAppEntries1 =
			(List<RemoteAppEntry>)page1.getItems();

		Assert.assertEquals(
			remoteAppEntries1.toString(), 2, remoteAppEntries1.size());

		Page<RemoteAppEntry> page2 =
			remoteAppEntryResource.getRemoteAppEntriesPage(
				null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<RemoteAppEntry> remoteAppEntries2 =
			(List<RemoteAppEntry>)page2.getItems();

		Assert.assertEquals(
			remoteAppEntries2.toString(), 1, remoteAppEntries2.size());

		Page<RemoteAppEntry> page3 =
			remoteAppEntryResource.getRemoteAppEntriesPage(
				null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(remoteAppEntry1, remoteAppEntry2, remoteAppEntry3),
			(List<RemoteAppEntry>)page3.getItems());
	}

	@Test
	public void testGetRemoteAppEntriesPageWithSortDateTime() throws Exception {
		testGetRemoteAppEntriesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, remoteAppEntry1, remoteAppEntry2) -> {
				BeanUtils.setProperty(
					remoteAppEntry1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetRemoteAppEntriesPageWithSortInteger() throws Exception {
		testGetRemoteAppEntriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, remoteAppEntry1, remoteAppEntry2) -> {
				BeanUtils.setProperty(
					remoteAppEntry1, entityField.getName(), 0);
				BeanUtils.setProperty(
					remoteAppEntry2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetRemoteAppEntriesPageWithSortString() throws Exception {
		testGetRemoteAppEntriesPageWithSort(
			EntityField.Type.STRING,
			(entityField, remoteAppEntry1, remoteAppEntry2) -> {
				Class<?> clazz = remoteAppEntry1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						remoteAppEntry1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						remoteAppEntry2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						remoteAppEntry1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						remoteAppEntry2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						remoteAppEntry1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						remoteAppEntry2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetRemoteAppEntriesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, RemoteAppEntry, RemoteAppEntry, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		RemoteAppEntry remoteAppEntry1 = randomRemoteAppEntry();
		RemoteAppEntry remoteAppEntry2 = randomRemoteAppEntry();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, remoteAppEntry1, remoteAppEntry2);
		}

		remoteAppEntry1 = testGetRemoteAppEntriesPage_addRemoteAppEntry(
			remoteAppEntry1);

		remoteAppEntry2 = testGetRemoteAppEntriesPage_addRemoteAppEntry(
			remoteAppEntry2);

		for (EntityField entityField : entityFields) {
			Page<RemoteAppEntry> ascPage =
				remoteAppEntryResource.getRemoteAppEntriesPage(
					null, Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(remoteAppEntry1, remoteAppEntry2),
				(List<RemoteAppEntry>)ascPage.getItems());

			Page<RemoteAppEntry> descPage =
				remoteAppEntryResource.getRemoteAppEntriesPage(
					null, Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(remoteAppEntry2, remoteAppEntry1),
				(List<RemoteAppEntry>)descPage.getItems());
		}
	}

	protected RemoteAppEntry testGetRemoteAppEntriesPage_addRemoteAppEntry(
			RemoteAppEntry remoteAppEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetRemoteAppEntriesPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"remoteAppEntries",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject remoteAppEntriesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/remoteAppEntries");

		Assert.assertEquals(0, remoteAppEntriesJSONObject.get("totalCount"));

		RemoteAppEntry remoteAppEntry1 =
			testGraphQLRemoteAppEntry_addRemoteAppEntry();
		RemoteAppEntry remoteAppEntry2 =
			testGraphQLRemoteAppEntry_addRemoteAppEntry();

		remoteAppEntriesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/remoteAppEntries");

		Assert.assertEquals(2, remoteAppEntriesJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(remoteAppEntry1, remoteAppEntry2),
			Arrays.asList(
				RemoteAppEntrySerDes.toDTOs(
					remoteAppEntriesJSONObject.getString("items"))));
	}

	@Test
	public void testPostRemoteAppEntry() throws Exception {
		RemoteAppEntry randomRemoteAppEntry = randomRemoteAppEntry();

		RemoteAppEntry postRemoteAppEntry =
			testPostRemoteAppEntry_addRemoteAppEntry(randomRemoteAppEntry);

		assertEquals(randomRemoteAppEntry, postRemoteAppEntry);
		assertValid(postRemoteAppEntry);
	}

	protected RemoteAppEntry testPostRemoteAppEntry_addRemoteAppEntry(
			RemoteAppEntry remoteAppEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteRemoteAppEntry() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		RemoteAppEntry remoteAppEntry =
			testDeleteRemoteAppEntry_addRemoteAppEntry();

		assertHttpResponseStatusCode(
			204,
			remoteAppEntryResource.deleteRemoteAppEntryHttpResponse(
				remoteAppEntry.getId()));

		assertHttpResponseStatusCode(
			404,
			remoteAppEntryResource.getRemoteAppEntryHttpResponse(
				remoteAppEntry.getId()));

		assertHttpResponseStatusCode(
			404, remoteAppEntryResource.getRemoteAppEntryHttpResponse(0L));
	}

	protected RemoteAppEntry testDeleteRemoteAppEntry_addRemoteAppEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteRemoteAppEntry() throws Exception {
		RemoteAppEntry remoteAppEntry =
			testGraphQLRemoteAppEntry_addRemoteAppEntry();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteRemoteAppEntry",
						new HashMap<String, Object>() {
							{
								put("remoteAppEntryId", remoteAppEntry.getId());
							}
						})),
				"JSONObject/data", "Object/deleteRemoteAppEntry"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"remoteAppEntry",
						new HashMap<String, Object>() {
							{
								put("remoteAppEntryId", remoteAppEntry.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetRemoteAppEntry() throws Exception {
		RemoteAppEntry postRemoteAppEntry =
			testGetRemoteAppEntry_addRemoteAppEntry();

		RemoteAppEntry getRemoteAppEntry =
			remoteAppEntryResource.getRemoteAppEntry(
				postRemoteAppEntry.getId());

		assertEquals(postRemoteAppEntry, getRemoteAppEntry);
		assertValid(getRemoteAppEntry);
	}

	protected RemoteAppEntry testGetRemoteAppEntry_addRemoteAppEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetRemoteAppEntry() throws Exception {
		RemoteAppEntry remoteAppEntry =
			testGraphQLRemoteAppEntry_addRemoteAppEntry();

		Assert.assertTrue(
			equals(
				remoteAppEntry,
				RemoteAppEntrySerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"remoteAppEntry",
								new HashMap<String, Object>() {
									{
										put(
											"remoteAppEntryId",
											remoteAppEntry.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/remoteAppEntry"))));
	}

	@Test
	public void testGraphQLGetRemoteAppEntryNotFound() throws Exception {
		Long irrelevantRemoteAppEntryId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"remoteAppEntry",
						new HashMap<String, Object>() {
							{
								put(
									"remoteAppEntryId",
									irrelevantRemoteAppEntryId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPutRemoteAppEntry() throws Exception {
		RemoteAppEntry postRemoteAppEntry =
			testPutRemoteAppEntry_addRemoteAppEntry();

		RemoteAppEntry randomRemoteAppEntry = randomRemoteAppEntry();

		RemoteAppEntry putRemoteAppEntry =
			remoteAppEntryResource.putRemoteAppEntry(
				postRemoteAppEntry.getId(), randomRemoteAppEntry);

		assertEquals(randomRemoteAppEntry, putRemoteAppEntry);
		assertValid(putRemoteAppEntry);

		RemoteAppEntry getRemoteAppEntry =
			remoteAppEntryResource.getRemoteAppEntry(putRemoteAppEntry.getId());

		assertEquals(randomRemoteAppEntry, getRemoteAppEntry);
		assertValid(getRemoteAppEntry);
	}

	protected RemoteAppEntry testPutRemoteAppEntry_addRemoteAppEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected RemoteAppEntry testGraphQLRemoteAppEntry_addRemoteAppEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		RemoteAppEntry remoteAppEntry1, RemoteAppEntry remoteAppEntry2) {

		Assert.assertTrue(
			remoteAppEntry1 + " does not equal " + remoteAppEntry2,
			equals(remoteAppEntry1, remoteAppEntry2));
	}

	protected void assertEquals(
		List<RemoteAppEntry> remoteAppEntries1,
		List<RemoteAppEntry> remoteAppEntries2) {

		Assert.assertEquals(remoteAppEntries1.size(), remoteAppEntries2.size());

		for (int i = 0; i < remoteAppEntries1.size(); i++) {
			RemoteAppEntry remoteAppEntry1 = remoteAppEntries1.get(i);
			RemoteAppEntry remoteAppEntry2 = remoteAppEntries2.get(i);

			assertEquals(remoteAppEntry1, remoteAppEntry2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<RemoteAppEntry> remoteAppEntries1,
		List<RemoteAppEntry> remoteAppEntries2) {

		Assert.assertEquals(remoteAppEntries1.size(), remoteAppEntries2.size());

		for (RemoteAppEntry remoteAppEntry1 : remoteAppEntries1) {
			boolean contains = false;

			for (RemoteAppEntry remoteAppEntry2 : remoteAppEntries2) {
				if (equals(remoteAppEntry1, remoteAppEntry2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				remoteAppEntries2 + " does not contain " + remoteAppEntry1,
				contains);
		}
	}

	protected void assertValid(RemoteAppEntry remoteAppEntry) throws Exception {
		boolean valid = true;

		if (remoteAppEntry.getDateCreated() == null) {
			valid = false;
		}

		if (remoteAppEntry.getDateModified() == null) {
			valid = false;
		}

		if (remoteAppEntry.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (remoteAppEntry.getCompanyId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (remoteAppEntry.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("url", additionalAssertFieldName)) {
				if (remoteAppEntry.getUrl() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (remoteAppEntry.getUserId() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<RemoteAppEntry> page) {
		boolean valid = false;

		java.util.Collection<RemoteAppEntry> remoteAppEntries = page.getItems();

		int size = remoteAppEntries.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.remote.app.rest.dto.v1_0.RemoteAppEntry.
						class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					ReflectionUtil.getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		RemoteAppEntry remoteAppEntry1, RemoteAppEntry remoteAppEntry2) {

		if (remoteAppEntry1 == remoteAppEntry2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						remoteAppEntry1.getCompanyId(),
						remoteAppEntry2.getCompanyId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						remoteAppEntry1.getDateCreated(),
						remoteAppEntry2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						remoteAppEntry1.getDateModified(),
						remoteAppEntry2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						remoteAppEntry1.getId(), remoteAppEntry2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals(
						(Map)remoteAppEntry1.getName(),
						(Map)remoteAppEntry2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("url", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						remoteAppEntry1.getUrl(), remoteAppEntry2.getUrl())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						remoteAppEntry1.getUserId(),
						remoteAppEntry2.getUserId())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_remoteAppEntryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_remoteAppEntryResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator,
		RemoteAppEntry remoteAppEntry) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("companyId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							remoteAppEntry.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							remoteAppEntry.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(remoteAppEntry.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							remoteAppEntry.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							remoteAppEntry.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(remoteAppEntry.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("url")) {
			sb.append("'");
			sb.append(String.valueOf(remoteAppEntry.getUrl()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected RemoteAppEntry randomRemoteAppEntry() throws Exception {
		return new RemoteAppEntry() {
			{
				companyId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				url = StringUtil.toLowerCase(RandomTestUtil.randomString());
				userId = RandomTestUtil.randomLong();
			}
		};
	}

	protected RemoteAppEntry randomIrrelevantRemoteAppEntry() throws Exception {
		RemoteAppEntry randomIrrelevantRemoteAppEntry = randomRemoteAppEntry();

		return randomIrrelevantRemoteAppEntry;
	}

	protected RemoteAppEntry randomPatchRemoteAppEntry() throws Exception {
		return randomRemoteAppEntry();
	}

	protected RemoteAppEntryResource remoteAppEntryResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseRemoteAppEntryResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.remote.app.rest.resource.v1_0.RemoteAppEntryResource
		_remoteAppEntryResource;

}