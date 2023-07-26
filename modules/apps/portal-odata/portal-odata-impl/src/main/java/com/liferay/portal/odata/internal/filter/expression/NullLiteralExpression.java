/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.LiteralExpression;

/**
 * @author Cristina González
 */
public class NullLiteralExpression implements LiteralExpression {

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitLiteralExpression(this);
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public Type getType() {
		return Type.NULL;
	}

	@Override
	public String toString() {
		return Type.NULL.toString();
	}

}