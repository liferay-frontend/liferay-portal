/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alan Huang
 */
public class NestedFieldAnnotationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if ((detailAST.getParent() != null) ||
			!AnnotationUtil.containsAnnotation(detailAST, "Component")) {

			return;
		}

		String className = getName(detailAST);

		if (!className.endsWith("ResourceImpl")) {
			return;
		}

		DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
			detailAST, "Component");

		List<String> serviceNamesList = _getServiceNamesList(
			annotationDetailAST);

		boolean hasNestedFieldAnnotation = _hasNestedFieldAnnotation(
			getAllChildTokens(detailAST, true, TokenTypes.METHOD_DEF));

		if (hasNestedFieldAnnotation &&
			!serviceNamesList.contains("NestedFieldSupport")) {

			log(annotationDetailAST, _MSG_NEEDED_SERVICE);
		}
		else if (!hasNestedFieldAnnotation &&
				 serviceNamesList.contains("NestedFieldSupport")) {

			log(annotationDetailAST, _MSG_UNNEEDED_SERVICE);
		}
	}

	private List<String> _getServiceNamesList(DetailAST annotationDetailAST) {
		if (annotationDetailAST == null) {
			return Collections.emptyList();
		}

		DetailAST annotationMemberValuePairDetailAST =
			getAnnotationMemberValuePairDetailAST(
				annotationDetailAST, "service");

		if (annotationMemberValuePairDetailAST == null) {
			return Collections.emptyList();
		}

		DetailAST annotationArrayInitDetailAST =
			annotationMemberValuePairDetailAST.findFirstToken(
				TokenTypes.ANNOTATION_ARRAY_INIT);

		if (annotationArrayInitDetailAST == null) {
			return Collections.emptyList();
		}

		List<String> serviceNamesList = new ArrayList<>();

		List<DetailAST> expressionDetailASTList = getAllChildTokens(
			annotationArrayInitDetailAST, false, TokenTypes.EXPR);

		for (DetailAST expressionDetailAST : expressionDetailASTList) {
			DetailAST firstChildDetailAST = expressionDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.DOT) {
				continue;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
				continue;
			}

			DetailAST siblingDetailAST = firstChildDetailAST.getNextSibling();

			if (siblingDetailAST.getType() != TokenTypes.LITERAL_CLASS) {
				continue;
			}

			serviceNamesList.add(firstChildDetailAST.getText());
		}

		return serviceNamesList;
	}

	private boolean _hasNestedFieldAnnotation(List<DetailAST> detailASTList) {
		for (DetailAST detailAST : detailASTList) {
			DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
				detailAST, "NestedField");

			if (annotationDetailAST == null) {
				continue;
			}

			return true;
		}

		return false;
	}

	private static final String _MSG_NEEDED_SERVICE = "service.needed";

	private static final String _MSG_UNNEEDED_SERVICE = "service.unneeded";

}