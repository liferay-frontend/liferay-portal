/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTMessageLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class CTMessageBusInterceptorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_testMessageListener = new TestMessageListener();

		Destination destination = _messageBus.getDestination(
			DestinationNames.SUBSCRIPTION_SENDER);

		_originalMessageListeners = destination.getMessageListeners();

		destination.unregisterMessageListeners();

		destination.register(_testMessageListener);

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			CTMessageBusInterceptorTest.class.getSimpleName(), null);
	}

	@After
	public void tearDown() {
		Destination destination = _messageBus.getDestination(
			DestinationNames.SUBSCRIPTION_SENDER);

		destination.unregisterMessageListeners();

		for (MessageListener messageListener : _originalMessageListeners) {
			destination.register(messageListener);
		}
	}

	@Test
	public void testInterceptSubscriptionSenderMessage() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setCompanyId(companyId);
		subscriptionSender.setMailId(
			CTMessageBusInterceptorTest.class.getName(), "test");

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			subscriptionSender.flushNotificationsAsync();
		}

		Assert.assertNull(_testMessageListener.getReceivedMessage());

		List<Message> messages = _ctMessageLocalService.getMessages(
			_ctCollection.getCtCollectionId());

		Assert.assertSame(messages.toString(), 1, messages.size());

		Message deserializedMessage = messages.get(0);

		Assert.assertEquals(
			DestinationNames.SUBSCRIPTION_SENDER,
			deserializedMessage.getDestinationName());

		SubscriptionSender deserializedSubscriptionSender =
			(SubscriptionSender)deserializedMessage.getPayload();

		Assert.assertEquals(
			companyId, deserializedSubscriptionSender.getCompanyId());
	}

	@Test
	public void testPublishSubscriptionSenderMessage() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Message message = new Message();

		message.setDestinationName(DestinationNames.SUBSCRIPTION_SENDER);

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setCompanyId(companyId);
		subscriptionSender.setMailId(
			CTMessageBusInterceptorTest.class.getName(), "test");

		message.setPayload(subscriptionSender);

		_ctMessageLocalService.addCTMessage(
			_ctCollection.getCtCollectionId(), message);

		Assert.assertNull(_testMessageListener.getReceivedMessage());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_ctProcessLocalService.addCTProcess(
				_ctCollection.getUserId(), _ctCollection.getCtCollectionId());
		}

		Message receivedMessage = _testMessageListener.getReceivedMessage();

		SubscriptionSender deserializedSubscriptionSender =
			(SubscriptionSender)receivedMessage.getPayload();

		Assert.assertEquals(
			companyId, deserializedSubscriptionSender.getCompanyId());

		List<Message> messages = _ctMessageLocalService.getMessages(
			_ctCollection.getCtCollectionId());

		Assert.assertSame(messages.toString(), 1, messages.size());

		Message deserializedMessage = messages.get(0);

		Assert.assertEquals(
			DestinationNames.SUBSCRIPTION_SENDER,
			deserializedMessage.getDestinationName());

		deserializedSubscriptionSender =
			(SubscriptionSender)receivedMessage.getPayload();

		Assert.assertEquals(
			companyId, deserializedSubscriptionSender.getCompanyId());

		_ctCollectionLocalService.deleteCTCollection(_ctCollection);

		messages = _ctMessageLocalService.getMessages(
			_ctCollection.getCtCollectionId());

		Assert.assertTrue(messages.toString(), messages.isEmpty());

		_ctCollection = null;
	}

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTMessageLocalService _ctMessageLocalService;

	@Inject
	private static CTProcessLocalService _ctProcessLocalService;

	@Inject
	private static MessageBus _messageBus;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	private Set<MessageListener> _originalMessageListeners;
	private TestMessageListener _testMessageListener;

	private static class TestMessageListener implements MessageListener {

		public Message getReceivedMessage() {
			return _message;
		}

		@Override
		public void receive(Message message) {
			_message = message;
		}

		private Message _message;

	}

}