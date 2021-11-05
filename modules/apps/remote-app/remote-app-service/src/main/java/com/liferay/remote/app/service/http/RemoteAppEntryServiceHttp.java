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

package com.liferay.remote.app.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.remote.app.service.RemoteAppEntryServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>RemoteAppEntryServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntryServiceSoap
 * @generated
 */
public class RemoteAppEntryServiceHttp {

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addAttachment(
				HttpPrincipal httpPrincipal, long remoteAppEntryId,
				String fileName, java.io.InputStream inputStream,
				String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "addAttachment",
				_addAttachmentParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId, fileName, inputStream, mimeType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			addCustomElementRemoteAppEntry(
				HttpPrincipal httpPrincipal, String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class,
				"addCustomElementRemoteAppEntry",
				_addCustomElementRemoteAppEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, customElementCSSURLs, customElementHTMLElementName,
				customElementURLs, instanceable, nameMap, portletCategoryName,
				properties);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			addCustomElementRemoteAppEntry(
				HttpPrincipal httpPrincipal, String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				java.util.Map<java.util.Locale, String> descriptionMap,
				boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String[] selectedFileNames, String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class,
				"addCustomElementRemoteAppEntry",
				_addCustomElementRemoteAppEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, customElementCSSURLs, customElementHTMLElementName,
				customElementURLs, descriptionMap, instanceable, nameMap,
				portletCategoryName, properties, selectedFileNames,
				sourceCodeURL);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			addIFrameRemoteAppEntry(
				HttpPrincipal httpPrincipal, String iFrameURL,
				boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "addIFrameRemoteAppEntry",
				_addIFrameRemoteAppEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, iFrameURL, instanceable, nameMap,
				portletCategoryName, properties);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			addIFrameRemoteAppEntry(
				HttpPrincipal httpPrincipal, String iFrameURL,
				java.util.Map<java.util.Locale, String> descriptionMap,
				boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String[] selectedFileNames, String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "addIFrameRemoteAppEntry",
				_addIFrameRemoteAppEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, iFrameURL, descriptionMap, instanceable, nameMap,
				portletCategoryName, properties, selectedFileNames,
				sourceCodeURL);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void addTempAttachment(
			HttpPrincipal httpPrincipal, long remoteAppEntryId, String fileName,
			String tempFolderName, java.io.InputStream inputStream,
			String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "addTempAttachment",
				_addTempAttachmentParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId, fileName, tempFolderName,
				inputStream, mimeType);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			deleteRemoteAppEntry(
				HttpPrincipal httpPrincipal, long remoteAppEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "deleteRemoteAppEntry",
				_deleteRemoteAppEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteTempAttachment(
			HttpPrincipal httpPrincipal, long remoteAppEntryId, String fileName,
			String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "deleteTempAttachment",
				_deleteTempAttachmentParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId, fileName, tempFolderName);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry getRemoteAppEntry(
			HttpPrincipal httpPrincipal, long remoteAppEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "getRemoteAppEntry",
				_getRemoteAppEntryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			updateCustomElementRemoteAppEntry(
				HttpPrincipal httpPrincipal, long remoteAppEntryId,
				String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String[] selectedFileNames, long[] removeFileEntryIds,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class,
				"updateCustomElementRemoteAppEntry",
				_updateCustomElementRemoteAppEntryParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId, customElementCSSURLs,
				customElementHTMLElementName, customElementURLs, descriptionMap,
				nameMap, portletCategoryName, properties, selectedFileNames,
				removeFileEntryIds, sourceCodeURL);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			updateCustomElementRemoteAppEntry(
				HttpPrincipal httpPrincipal, long remoteAppEntryId,
				String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class,
				"updateCustomElementRemoteAppEntry",
				_updateCustomElementRemoteAppEntryParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId, customElementCSSURLs,
				customElementHTMLElementName, customElementURLs, nameMap,
				portletCategoryName, properties);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			updateIFrameRemoteAppEntry(
				HttpPrincipal httpPrincipal, long remoteAppEntryId,
				String iFrameURL,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String[] selectedFileNames, long[] removeFileEntryIds,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "updateIFrameRemoteAppEntry",
				_updateIFrameRemoteAppEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId, iFrameURL, descriptionMap, nameMap,
				portletCategoryName, properties, selectedFileNames,
				removeFileEntryIds, sourceCodeURL);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			updateIFrameRemoteAppEntry(
				HttpPrincipal httpPrincipal, long remoteAppEntryId,
				String iFrameURL,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "updateIFrameRemoteAppEntry",
				_updateIFrameRemoteAppEntryParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId, iFrameURL, nameMap,
				portletCategoryName, properties);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		RemoteAppEntryServiceHttp.class);

	private static final Class<?>[] _addAttachmentParameterTypes0 =
		new Class[] {
			long.class, String.class, java.io.InputStream.class, String.class
		};
	private static final Class<?>[]
		_addCustomElementRemoteAppEntryParameterTypes1 = new Class[] {
			String.class, String.class, String.class, boolean.class,
			java.util.Map.class, String.class, String.class
		};
	private static final Class<?>[]
		_addCustomElementRemoteAppEntryParameterTypes2 = new Class[] {
			String.class, String.class, String.class, java.util.Map.class,
			boolean.class, java.util.Map.class, String.class, String.class,
			String[].class, String.class
		};
	private static final Class<?>[] _addIFrameRemoteAppEntryParameterTypes3 =
		new Class[] {
			String.class, boolean.class, java.util.Map.class, String.class,
			String.class
		};
	private static final Class<?>[] _addIFrameRemoteAppEntryParameterTypes4 =
		new Class[] {
			String.class, java.util.Map.class, boolean.class,
			java.util.Map.class, String.class, String.class, String[].class,
			String.class
		};
	private static final Class<?>[] _addTempAttachmentParameterTypes5 =
		new Class[] {
			long.class, String.class, String.class, java.io.InputStream.class,
			String.class
		};
	private static final Class<?>[] _deleteRemoteAppEntryParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteTempAttachmentParameterTypes7 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _getRemoteAppEntryParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[]
		_updateCustomElementRemoteAppEntryParameterTypes9 = new Class[] {
			long.class, String.class, String.class, String.class,
			java.util.Map.class, java.util.Map.class, String.class,
			String.class, String[].class, long[].class, String.class
		};
	private static final Class<?>[]
		_updateCustomElementRemoteAppEntryParameterTypes10 = new Class[] {
			long.class, String.class, String.class, String.class,
			java.util.Map.class, String.class, String.class
		};
	private static final Class<?>[]
		_updateIFrameRemoteAppEntryParameterTypes11 = new Class[] {
			long.class, String.class, java.util.Map.class, java.util.Map.class,
			String.class, String.class, String[].class, long[].class,
			String.class
		};
	private static final Class<?>[]
		_updateIFrameRemoteAppEntryParameterTypes12 = new Class[] {
			long.class, String.class, java.util.Map.class, String.class,
			String.class
		};

}