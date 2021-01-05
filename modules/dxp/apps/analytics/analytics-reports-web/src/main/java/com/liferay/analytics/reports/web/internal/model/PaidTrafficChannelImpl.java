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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * @author David Arques
 */
public class PaidTrafficChannelImpl implements TrafficChannel {

	public PaidTrafficChannelImpl(boolean error) {
		_error = error;

		_countrySearchKeywordsList = Collections.emptyList();
		_trafficAmount = 0;
		_trafficShare = 0;
	}

	public PaidTrafficChannelImpl(
		List<CountrySearchKeywords> countrySearchKeywordsList,
		long trafficAmount, double trafficShare) {

		_countrySearchKeywordsList = countrySearchKeywordsList;
		_trafficAmount = trafficAmount;
		_trafficShare = trafficShare;

		_error = false;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PaidTrafficChannelImpl)) {
			return false;
		}

		PaidTrafficChannelImpl paidTrafficChannelImpl =
			(PaidTrafficChannelImpl)object;

		if (Objects.equals(
				_countrySearchKeywordsList,
				paidTrafficChannelImpl._countrySearchKeywordsList) &&
			Objects.equals(
				getHelpMessageKey(),
				paidTrafficChannelImpl.getHelpMessageKey()) &&
			Objects.equals(getName(), paidTrafficChannelImpl.getName()) &&
			Objects.equals(
				_trafficAmount, paidTrafficChannelImpl._trafficAmount) &&
			Objects.equals(
				_trafficShare, paidTrafficChannelImpl._trafficShare)) {

			return true;
		}

		return false;
	}

	public List<CountrySearchKeywords> getCountrySearchKeywordsList() {
		return _countrySearchKeywordsList;
	}

	@Override
	public String getHelpMessageKey() {
		return "this-is-the-number-of-page-views-generated-by-people-that-" +
			"find-your-page-through-google-adwords";
	}

	@Override
	public String getName() {
		return "paid";
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
			_countrySearchKeywordsList, getHelpMessageKey(), getName(),
			_trafficAmount, _trafficShare);
	}

	@Override
	public JSONObject toJSONObject(
		Locale locale, ResourceBundle resourceBundle) {

		JSONObject jsonObject = TrafficChannelUtil.toJSONObject(
			_error,
			ResourceBundleUtil.getString(resourceBundle, getHelpMessageKey()),
			getName(), ResourceBundleUtil.getString(resourceBundle, getName()),
			_trafficAmount, _trafficShare);

		if (!ListUtil.isEmpty(_countrySearchKeywordsList)) {
			jsonObject.put(
				"countryKeywords", _getCountryKeywordsJSONArray(locale));
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

	private JSONArray _getCountryKeywordsJSONArray(Locale locale) {
		Stream<CountrySearchKeywords> stream =
			_countrySearchKeywordsList.stream();

		return JSONUtil.putAll(
			stream.map(
				countrySearchKeywords -> countrySearchKeywords.toJSONObject(
					locale)
			).toArray());
	}

	private final List<CountrySearchKeywords> _countrySearchKeywordsList;
	private final boolean _error;
	private final long _trafficAmount;
	private final double _trafficShare;

}