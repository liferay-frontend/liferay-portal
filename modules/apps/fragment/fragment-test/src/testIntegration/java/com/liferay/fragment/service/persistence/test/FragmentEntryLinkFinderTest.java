/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.persistence.FragmentEntryLinkFinder;
import com.liferay.fragment.service.persistence.FragmentEntryLinkPersistence;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.SUPPORTS, "com.liferay.fragment.service"));

	@After
	public void tearDown() {
		for (FragmentEntryLink fragmentEntryLink : _fragmentEntryLinks) {
			_fragmentEntryLinkPersistence.remove(fragmentEntryLink);
		}
	}

	@Test
	@Transactional
	public void testCountByG_F() {
		_createFragmentEntryLink(0, 0);
		_createFragmentEntryLink(0, 0);
		_createFragmentEntryLink(0, 0);
		_createFragmentEntryLink(0, 1);
		_createFragmentEntryLink(0, 1);
		_createFragmentEntryLink(1, 2);
		_createFragmentEntryLink(1, 3);
		_createFragmentEntryLink(2, 3);
		_createFragmentEntryLink(2, 1);
		_createFragmentEntryLink(2, 4);

		Assert.assertEquals(
			7,
			_fragmentEntryLinkFinder.countByG_F(_GROUP_ID, _FRAGMENT_ENTRY_ID));
	}

	private void _createFragmentEntryLink(long classNameId, long classPK) {
		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkPersistence.create(
				_counterLocalService.increment());

		fragmentEntryLink.setGroupId(_GROUP_ID);
		fragmentEntryLink.setFragmentEntryId(_FRAGMENT_ENTRY_ID);
		fragmentEntryLink.setClassNameId(classNameId);
		fragmentEntryLink.setClassPK(classPK);
		fragmentEntryLink.setPlid(classPK);

		_fragmentEntryLinks.add(
			_fragmentEntryLinkPersistence.update(fragmentEntryLink));
	}

	private static final long _FRAGMENT_ENTRY_ID = 20;

	private static final long _GROUP_ID = 10;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private FragmentEntryLinkFinder _fragmentEntryLinkFinder;

	@Inject
	private FragmentEntryLinkPersistence _fragmentEntryLinkPersistence;

	private final List<FragmentEntryLink> _fragmentEntryLinks =
		new ArrayList<>();

}