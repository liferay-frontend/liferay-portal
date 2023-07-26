/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.servlet.NullSession;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * @author Brian Wing Shun Chan
 */
public class SharedSessionWrapper implements HttpSession {

	public SharedSessionWrapper(
		HttpSession portalHttpSession, HttpSession portletHttpSession) {

		if (portalHttpSession == null) {
			_portalHttpSession = new NullSession();

			if (_log.isWarnEnabled()) {
				_log.warn("Wrapped portal session is null");
			}
		}
		else {
			_portalHttpSession = portalHttpSession;
		}

		_portletHttpSession = portletHttpSession;
	}

	@Override
	public Object getAttribute(String name) {
		HttpSession httpSession = getSessionDelegate(name);

		return httpSession.getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		HttpSession httpSession = getSessionDelegate();

		Enumeration<String> namesEnumeration = httpSession.getAttributeNames();

		if (httpSession == _portletHttpSession) {
			List<String> namesList = Collections.list(namesEnumeration);

			Enumeration<String> portalHttpSessionNamesEnumeration =
				_portalHttpSession.getAttributeNames();

			while (portalHttpSessionNamesEnumeration.hasMoreElements()) {
				String name = portalHttpSessionNamesEnumeration.nextElement();

				if (containsSharedAttribute(name)) {
					namesList.add(name);
				}
			}

			namesEnumeration = Collections.enumeration(namesList);
		}

		return namesEnumeration;
	}

	@Override
	public long getCreationTime() {
		HttpSession httpSession = getSessionDelegate();

		return httpSession.getCreationTime();
	}

	@Override
	public String getId() {
		HttpSession httpSession = getSessionDelegate();

		return httpSession.getId();
	}

	@Override
	public long getLastAccessedTime() {
		HttpSession httpSession = getSessionDelegate();

		return httpSession.getLastAccessedTime();
	}

	@Override
	public int getMaxInactiveInterval() {
		HttpSession httpSession = getSessionDelegate();

		return httpSession.getMaxInactiveInterval();
	}

	@Override
	public ServletContext getServletContext() {
		HttpSession httpSession = getSessionDelegate();

		return httpSession.getServletContext();
	}

	/**
	 * @deprecated As of Paton (6.1.x)
	 */
	@Deprecated
	@Override
	public HttpSessionContext getSessionContext() {
		HttpSession httpSession = getSessionDelegate();

		return httpSession.getSessionContext();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public Object getValue(String name) {
		return getAttribute(name);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public String[] getValueNames() {
		List<String> names = ListUtil.fromEnumeration(getAttributeNames());

		return names.toArray(new String[0]);
	}

	@Override
	public void invalidate() {
		HttpSession httpSession = getSessionDelegate();

		httpSession.invalidate();
	}

	@Override
	public boolean isNew() {
		HttpSession httpSession = getSessionDelegate();

		return httpSession.isNew();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		HttpSession httpSession = getSessionDelegate(name);

		httpSession.removeAttribute(name);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public void removeValue(String name) {
		removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		HttpSession httpSession = getSessionDelegate(name);

		httpSession.setAttribute(name, value);
	}

	@Override
	public void setMaxInactiveInterval(int maxInactiveInterval) {
		HttpSession httpSession = getSessionDelegate();

		httpSession.setMaxInactiveInterval(maxInactiveInterval);
	}

	protected boolean containsSharedAttribute(String name) {
		for (String sharedName : PropsValues.SESSION_SHARED_ATTRIBUTES) {
			if (name.startsWith(sharedName)) {
				return true;
			}
		}

		return false;
	}

	protected HttpSession getSessionDelegate() {
		if (_portletHttpSession != null) {
			return _portletHttpSession;
		}

		return _portalHttpSession;
	}

	protected HttpSession getSessionDelegate(String name) {
		if (_portletHttpSession == null) {
			return _portalHttpSession;
		}

		if (_sharedSessionAttributesExcludes.containsKey(name)) {
			return _portletHttpSession;
		}
		else if (containsSharedAttribute(name)) {
			return _portalHttpSession;
		}

		return _portletHttpSession;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharedSessionWrapper.class);

	private static final Map<String, String> _sharedSessionAttributesExcludes =
		new HashMap<String, String>() {
			{
				for (String name :
						PropsValues.SESSION_SHARED_ATTRIBUTES_EXCLUDES) {

					put(name, name);
				}
			}
		};

	private final HttpSession _portalHttpSession;
	private HttpSession _portletHttpSession;

}