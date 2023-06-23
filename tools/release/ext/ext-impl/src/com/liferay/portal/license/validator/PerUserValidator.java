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

package com.liferay.portal.license.validator;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.CompanyMaxUsersException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.license.License;
import com.liferay.portal.license.LicenseConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Amos Fong
 */
public class PerUserValidator extends LicenseValidator {

	@Override
	public void doValidateVersion(License license) throws Exception {
		if (license.getMaxUsers() <= 0) {
			throw new Exception("A user limit must be set");
		}

		String productId = license.getProductId();

		if (productId.equals(LicenseConstants.PRODUCT_ID_PORTAL)) {
			validateServer(license);
		}

		if (license.getMaxUsers() > 0) {
			int userCount = getUserCount();

			if (userCount <= 0) {
				throw new Exception(
					"Unable to count number of users on server");
			}

			if (userCount > license.getMaxUsers()) {
				throw new CompanyMaxUsersException(
					"You have exceeded the maximum number of users allowed " +
						"for this server: " + license.getMaxUsers());
			}
		}
	}

	@Override
	public String[] getValidTypes() {
		return _VALID_TYPES;
	}

	protected int getUserCount() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from User_ where (defaultUser = ?) and " +
					"(status = ?)")) {

			preparedStatement.setBoolean(1, false);
			preparedStatement.setLong(2, WorkflowConstants.STATUS_APPROVED);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count > 0) {
						return count;
					}
				}
			}
		}

		return 0;
	}

	private static final String[] _VALID_TYPES = {
		LicenseConstants.TYPE_PER_USER
	};

}