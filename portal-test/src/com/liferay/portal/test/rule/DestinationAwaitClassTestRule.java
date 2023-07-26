/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.ClassTestRule;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class DestinationAwaitClassTestRule
	extends ClassTestRule<Set<CountDownLatch>> {

	public static final DestinationAwaitClassTestRule INSTANCE =
		new DestinationAwaitClassTestRule(DestinationNames.HOT_DEPLOY);

	public DestinationAwaitClassTestRule(String... destinationNames) {
		_destinationNames = destinationNames;
	}

	@Override
	public void afterClass(
			Description description, Set<CountDownLatch> endCountDownLatches)
		throws Throwable {

		endCountDownLatches.forEach(CountDownLatch::countDown);
	}

	@Override
	public Set<CountDownLatch> beforeClass(Description description)
		throws InterruptedException {

		Set<CountDownLatch> endCountdownLatches = new HashSet<>();

		for (String destinationName : _destinationNames) {
			Destination destination = MessageBusUtil.getDestination(
				destinationName);

			if (destination == null) {
				if (System.getenv("JENKINS_HOME") != null) {
					throw new IllegalStateException(
						destinationName + " is not available");
				}

				continue;
			}

			final CountDownLatch startCountDownLatch = new CountDownLatch(1);

			final CountDownLatch endCountDownLatch = new CountDownLatch(1);

			final Message countDownMessage = new Message();

			destination.register(
				new MessageListener() {

					@Override
					public void receive(Message message) {
						if (countDownMessage == message) {
							startCountDownLatch.countDown();

							try {
								endCountDownLatch.await();

								destination.unregister(this);
							}
							catch (InterruptedException interruptedException) {
								ReflectionUtil.throwException(
									interruptedException);
							}
						}
					}

				});

			destination.send(countDownMessage);

			startCountDownLatch.await();

			endCountdownLatches.add(endCountDownLatch);
		}

		return endCountdownLatches;
	}

	private final String[] _destinationNames;

}