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

package com.liferay.portal.vulcan.internal.resource;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.extension.ExtensionProvider;
import com.liferay.portal.vulcan.extension.ExtensionProviderRegistry;
import com.liferay.portal.vulcan.extension.PropertyDefinition;
import com.liferay.portal.vulcan.openapi.OpenAPISchemaFilter;

import java.lang.reflect.Field;

import java.net.URI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Carlos Correa
 */
public class OpenAPIResourceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testOpenAPIResourceImplWithExtensions() throws Exception {
		OpenAPIResourceImpl openAPIResourceImpl = Mockito.spy(
			_setDefaultProperties(new OpenAPIResourceImpl()));

		Mockito.when(
			_uriBuilder.build()
		).thenReturn(
			URI.create(_BASE_PATH)
		);
		Mockito.when(
			_uriInfo.getBaseUriBuilder()
		).thenReturn(
			_uriBuilder
		);

		ExtensionProvider extensionProvider1 = Mockito.mock(
			ExtensionProvider.class);
		ExtensionProvider extensionProvider2 = Mockito.mock(
			ExtensionProvider.class);
		ExtensionProvider extensionProvider3 = Mockito.mock(
			ExtensionProvider.class);

		Mockito.when(
			_extensionProviderRegistry.getExtensionProviders(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Arrays.asList(extensionProvider1, extensionProvider2)
		).thenReturn(
			Arrays.asList(extensionProvider3)
		);

		Mockito.when(
			extensionProvider1.getExtendedPropertyDefinitions(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			HashMapBuilder.put(
				RandomTestUtil.randomString(),
				Mockito.mock(PropertyDefinition.class)
			).put(
				RandomTestUtil.randomString(),
				Mockito.mock(PropertyDefinition.class)
			).build()
		);
		Mockito.when(
			extensionProvider2.getExtendedPropertyDefinitions(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			HashMapBuilder.put(
				RandomTestUtil.randomString(),
				Mockito.mock(PropertyDefinition.class)
			).put(
				RandomTestUtil.randomString(),
				Mockito.mock(PropertyDefinition.class)
			).build()
		);
		Mockito.when(
			extensionProvider3.getExtendedPropertyDefinitions(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			HashMapBuilder.put(
				RandomTestUtil.randomString(),
				Mockito.mock(PropertyDefinition.class)
			).build()
		);

		Response responseMock = Mockito.mock(Response.class);

		Mockito.doReturn(
			responseMock
		).when(
			openAPIResourceImpl
		).getOpenAPI(
			Mockito.any(OpenAPISchemaFilter.class), Mockito.anySet(),
			Mockito.anyString(), Mockito.any(UriInfo.class)
		);

		Response response = openAPIResourceImpl.getOpenAPI(
			_COMPANY_ID, _resourceDTOClasses, _TYPE, _uriInfo);

		Assert.assertSame(responseMock, response);

		Mockito.verify(
			_uriBuilder
		).build();
		Mockito.verify(
			_uriInfo
		).getBaseUriBuilder();
		Mockito.verify(
			_extensionProviderRegistry
		).getExtensionProviders(
			_COMPANY_ID, ClassDTOTest1.class.getName()
		);
		Mockito.verify(
			_extensionProviderRegistry
		).getExtensionProviders(
			_COMPANY_ID, ClassDTOTest2.class.getName()
		);
		Mockito.verify(
			openAPIResourceImpl
		).getOpenAPI(
			Mockito.notNull(OpenAPISchemaFilter.class),
			Mockito.eq(
				SetUtil.fromArray(
					ClassTest1.class, ClassTest2.class, ClassTest3.class)),
			Mockito.eq(_TYPE), Mockito.eq(_uriInfo)
		);
	}

	@Test
	public void testOpenAPIResourceImplWithNoExtensions() throws Exception {
		OpenAPIResourceImpl openAPIResourceImpl = Mockito.spy(
			_setDefaultProperties(new OpenAPIResourceImpl()));

		Mockito.when(
			_uriBuilder.build()
		).thenReturn(
			URI.create(_BASE_PATH)
		);
		Mockito.when(
			_uriInfo.getBaseUriBuilder()
		).thenReturn(
			_uriBuilder
		);
		Mockito.when(
			_extensionProviderRegistry.getExtensionProviders(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			null
		);

		Response responseMock = Mockito.mock(Response.class);

		Mockito.doReturn(
			responseMock
		).when(
			openAPIResourceImpl
		).getOpenAPI(
			Mockito.any(OpenAPISchemaFilter.class), Mockito.anySet(),
			Mockito.anyString(), Mockito.any(UriInfo.class)
		);

		Response response = openAPIResourceImpl.getOpenAPI(
			_COMPANY_ID, _resourceDTOClasses, _TYPE, _uriInfo);

		Assert.assertSame(responseMock, response);

		Mockito.verify(
			_uriBuilder
		).build();
		Mockito.verify(
			_uriInfo
		).getBaseUriBuilder();
		Mockito.verify(
			_extensionProviderRegistry
		).getExtensionProviders(
			_COMPANY_ID, ClassDTOTest1.class.getName()
		);
		Mockito.verify(
			_extensionProviderRegistry
		).getExtensionProviders(
			_COMPANY_ID, ClassDTOTest2.class.getName()
		);
		Mockito.verify(
			openAPIResourceImpl
		).getOpenAPI(
			null,
			SetUtil.fromArray(
				ClassTest1.class, ClassTest2.class, ClassTest3.class),
			_TYPE, _uriInfo
		);
	}

	private void _set(Object object, String fieldName, Object value) {
		Class<?> clazz = object.getClass();

		try {
			Field field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			field.set(object, value);
		}
		catch (Exception exception) {
			throw new IllegalArgumentException(exception);
		}
	}

	private OpenAPIResourceImpl _setDefaultProperties(
		OpenAPIResourceImpl openAPIResourceImpl) {

		_set(
			openAPIResourceImpl, "_extensionProviderRegistry",
			_extensionProviderRegistry);

		return openAPIResourceImpl;
	}

	private static final String _BASE_PATH = RandomTestUtil.randomString();

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _TYPE = RandomTestUtil.randomString();

	private static final Map<Class<?>, Class<?>> _resourceDTOClasses =
		new HashMap<Class<?>, Class<?>>() {
			{
				put(ClassTest1.class, ClassDTOTest1.class);
				put(ClassTest2.class, ClassDTOTest2.class);
				put(ClassTest3.class, null);
			}
		};

	@Mock
	private ExtensionProviderRegistry _extensionProviderRegistry;

	@Mock
	private UriBuilder _uriBuilder;

	@Mock
	private UriInfo _uriInfo;

	private static class ClassDTOTest1 {
	}

	private static class ClassDTOTest2 {
	}

	private static class ClassTest1 {
	}

	private static class ClassTest2 {
	}

	private static class ClassTest3 {
	}

}