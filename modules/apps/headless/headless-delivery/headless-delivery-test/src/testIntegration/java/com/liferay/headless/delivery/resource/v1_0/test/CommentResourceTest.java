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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.headless.delivery.client.dto.v1_0.Comment;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class CommentResourceTest extends BaseCommentResourceTestCase {

	@Override
	@Test
	public void testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode()
		throws Exception {

		super.
			testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			Comment comment =
				testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_addComment();

			// Non existing document

			assertHttpResponseStatusCode(
				404,
				commentResource.
					deleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCodeHttpResponse(
						testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId(),
						RandomTestUtil.randomString(),
						comment.getExternalReferenceCode()));
		}

		// Non existing comment

		assertHttpResponseStatusCode(
			204,
			commentResource.
				deleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCodeHttpResponse(
					testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId(),
					testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getDocumentExternalReferenceCode(),
					RandomTestUtil.randomString()));

		// Comment associated to a different document

		FileEntry prevFileEntry = _fileEntry;

		Comment comment =
			testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_addComment();

		assertHttpResponseStatusCode(
			204,
			commentResource.
				deleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCodeHttpResponse(
					testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId(),
					prevFileEntry.getExternalReferenceCode(),
					comment.getExternalReferenceCode()));
	}

	@Override
	@Test
	public void testGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode()
		throws Exception {

		super.
			testGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			Comment comment =
				testGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_addComment();

			// Non existing document

			assertHttpResponseStatusCode(
				404,
				commentResource.
					getSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCodeHttpResponse(
						testGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId(),
						RandomTestUtil.randomString(),
						comment.getExternalReferenceCode()));
		}

		// Non existing comment

		assertHttpResponseStatusCode(
			404,
			commentResource.
				getSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCodeHttpResponse(
					testGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId(),
					testGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getDocumentExternalReferenceCode(),
					RandomTestUtil.randomString()));

		// Comment associated to a different document

		FileEntry prevFileEntry = _fileEntry;

		Comment comment =
			testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_addComment();

		assertHttpResponseStatusCode(
			404,
			commentResource.
				getSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCodeHttpResponse(
					testGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId(),
					prevFileEntry.getExternalReferenceCode(),
					comment.getExternalReferenceCode()));
	}

	@Override
	@Test
	public void testGraphQLGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCodeNotFound()
		throws Exception {

		super.
			testGraphQLGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCodeNotFound();

		// Existing Document but not existing Comment

		testGraphQLGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_addComment();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"documentByExternalReferenceCodeDocument" +
							"ExternalReferenceCode" +
								"CommentByExternalReferenceCode",
						HashMapBuilder.<String, Object>put(
							"documentExternalReferenceCode",
							"\"" +
								testGraphQLGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getDocumentExternalReferenceCode() +
									"\""
						).put(
							"externalReferenceCode",
							"\"" + RandomTestUtil.randomString() + "\""
						).put(
							"siteKey",
							"\"" +
								testGraphQLGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId() +
									"\""
						).build(),
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Override
	@Test
	public void testPutSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode()
		throws Exception {

		super.
			testPutSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode();

		// Existing comment with an ERC associated to a different type of parent

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Comment postComment =
			testPutSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_addComment();

		Comment otherComment = _addBlogPostingComment();

		Comment randomComment = randomComment();

		randomComment.setExternalReferenceCode(
			otherComment.getExternalReferenceCode());

		assertHttpResponseStatusCode(
			400,
			commentResource.
				putSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCodeHttpResponse(
					testPutSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId(),
					testPutSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getDocumentExternalReferenceCode(),
					randomComment.getExternalReferenceCode(), randomComment));
	}

	@Override
	protected boolean equals(Comment comment1, Comment comment2) {
		if (Objects.equals(_formatHTML(comment1), _formatHTML(comment2))) {
			return true;
		}

		return false;
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"text"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId"};
	}

	@Override
	protected Comment testDeleteComment_addComment() throws Exception {
		return _addBlogPostingComment();
	}

	@Override
	protected Comment
			testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_addComment()
		throws Exception {

		return _addDocumentComment();
	}

	@Override
	protected String
			testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getDocumentExternalReferenceCode()
		throws Exception {

		return _fileEntry.getExternalReferenceCode();
	}

	@Override
	protected Long
			testDeleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	@Override
	protected Long testGetBlogPostingCommentsPage_getBlogPostingId()
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry();

		return blogsEntry.getEntryId();
	}

	@Override
	protected Comment testGetComment_addComment() throws Exception {
		return _addBlogPostingComment();
	}

	@Override
	protected Long testGetCommentCommentsPage_getParentCommentId()
		throws Exception {

		return _addBlogPostingComment().getId();
	}

	@Override
	protected Long testGetDocumentCommentsPage_getDocumentId()
		throws Exception {

		FileEntry fileEntry = _addFileEntry();

		return fileEntry.getFileEntryId();
	}

	@Override
	protected Comment
			testGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_addComment()
		throws Exception {

		return _addDocumentComment();
	}

	@Override
	protected String
			testGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getDocumentExternalReferenceCode()
		throws Exception {

		return _fileEntry.getExternalReferenceCode();
	}

	@Override
	protected Long
			testGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	@Override
	protected Long testGetStructuredContentCommentsPage_getStructuredContentId()
		throws Exception {

		JournalArticle journalArticle = _addJournalArticle();

		return journalArticle.getResourcePrimKey();
	}

	@Override
	protected Comment testGraphQLComment_addComment() throws Exception {
		return testGetComment_addComment();
	}

	@Override
	protected Comment
			testGraphQLGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_addComment()
		throws Exception {

		return _addDocumentComment();
	}

	@Override
	protected String
			testGraphQLGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getDocumentExternalReferenceCode()
		throws Exception {

		return _fileEntry.getExternalReferenceCode();
	}

	@Override
	protected Long
			testGraphQLGetSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	@Override
	protected Comment testPutComment_addComment() throws Exception {
		return commentResource.postCommentComment(
			_addBlogPostingComment().getId(), randomComment());
	}

	@Override
	protected Comment
			testPutSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_addComment()
		throws Exception {

		return _addDocumentComment();
	}

	@Override
	protected String
		testPutSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getDocumentExternalReferenceCode() {

		return _fileEntry.getExternalReferenceCode();
	}

	@Override
	protected Long
			testPutSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	private Comment _addBlogPostingComment() throws Exception {
		_blogsEntry = _addBlogsEntry();

		return commentResource.postBlogPostingComment(
			_blogsEntry.getEntryId(), randomComment());
	}

	private BlogsEntry _addBlogsEntry() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		return BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	private Comment _addDocumentComment() throws Exception {
		_fileEntry = _addFileEntry();

		return commentResource.postDocumentComment(
			_fileEntry.getFileEntryId(), randomComment());
	}

	private FileEntry _addFileEntry() throws Exception {
		return DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), testGroup.getGroupId(), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			new ServiceContext());
	}

	private JournalArticle _addJournalArticle() throws Exception {
		return JournalTestUtil.addArticle(testGroup.getGroupId(), 0);
	}

	private String _formatHTML(Comment comment) {
		String text = HtmlUtil.stripHtml(comment.getText());

		if (!text.startsWith("<p>")) {
			return StringBundler.concat("<p>", text, "</p>");
		}

		return text;
	}

	private BlogsEntry _blogsEntry;
	private FileEntry _fileEntry;

}