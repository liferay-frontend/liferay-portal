/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.analytics.reports.web.internal.model.util.TrafficChannelUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author David Arques
 */
public class SocialTrafficChannelImpl implements TrafficChannel {

	public SocialTrafficChannelImpl(boolean error) {
		_error = error;

		_referringSocialMediaList = Collections.emptyList();
		_trafficAmount = 0;
		_trafficShare = 0;
	}

	public SocialTrafficChannelImpl(
		List<ReferringSocialMedia> referringSocialMediaList, long trafficAmount,
		double trafficShare) {

		_referringSocialMediaList = Optional.ofNullable(
			referringSocialMediaList
		).orElse(
			Collections.emptyList()
		).stream(
		).filter(
			referringSocialMedia -> referringSocialMedia.getTrafficAmount() > 0
		).collect(
			Collectors.toList()
		);
		_trafficAmount = trafficAmount;
		_trafficShare = trafficShare;

		_error = false;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SocialTrafficChannelImpl)) {
			return false;
		}

		SocialTrafficChannelImpl socialTrafficChannelImpl =
			(SocialTrafficChannelImpl)object;

		if (Objects.equals(_error, socialTrafficChannelImpl._error) &&
			Objects.equals(
				getHelpMessageKey(),
				socialTrafficChannelImpl.getHelpMessageKey()) &&
			Objects.equals(getName(), socialTrafficChannelImpl.getName()) &&
			Objects.equals(
				_referringSocialMediaList,
				socialTrafficChannelImpl._referringSocialMediaList) &&
			Objects.equals(
				_trafficAmount, socialTrafficChannelImpl._trafficAmount) &&
			Objects.equals(
				_trafficShare, socialTrafficChannelImpl._trafficShare)) {

			return true;
		}

		return false;
	}

	@Override
	public String getHelpMessageKey() {
		return "this-is-the-number-of-page-views-generated-by-people-coming-" +
			"to-your-page-from-social-sites";
	}

	@Override
	public String getName() {
		return "social";
	}

	public List<ReferringSocialMedia> getReferringSocialMediaList() {
		return _referringSocialMediaList;
	}

	@Override
	public long getTrafficAmount() {
		return _trafficAmount;
	}

	@Override
	public double getTrafficShare() {
		return _trafficShare;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_error, getHelpMessageKey(), getName(), _trafficAmount,
			_trafficShare);
	}

	@Override
	public JSONObject toJSONObject(
		Locale locale, ResourceBundle resourceBundle) {

		JSONObject jsonObject = TrafficChannelUtil.toJSONObject(
			_error,
			ResourceBundleUtil.getString(resourceBundle, getHelpMessageKey()),
			getName(), ResourceBundleUtil.getString(resourceBundle, getName()),
			_trafficAmount, _trafficShare);

		if (ListUtil.isNotEmpty(_referringSocialMediaList)) {
			jsonObject.put(
				"referringSocialMedia",
				_getReferringSocialMediaJSONArray(resourceBundle));
		}

		return jsonObject;
	}

	@Override
	public String toString() {
		return String.valueOf(
			TrafficChannelUtil.toJSONObject(
				_error, getHelpMessageKey(), getName(), getName(),
				_trafficAmount, _trafficShare));
	}

	private JSONArray _getReferringSocialMediaJSONArray(
		ResourceBundle resourceBundle) {

		if (ListUtil.isEmpty(_referringSocialMediaList)) {
			return JSONFactoryUtil.createJSONArray();
		}

		Stream<ReferringSocialMedia> stream =
			_referringSocialMediaList.stream();

		Comparator<ReferringSocialMedia> comparator = Comparator.comparingInt(
			ReferringSocialMedia::getTrafficAmount);

		return JSONUtil.putAll(
			stream.sorted(
				comparator.reversed()
			).map(
				referringSocialMedia -> referringSocialMedia.toJSONObject(
					resourceBundle)
			).toArray());
	}

	private final boolean _error;
	private final List<ReferringSocialMedia> _referringSocialMediaList;
	private final long _trafficAmount;
	private final double _trafficShare;

}