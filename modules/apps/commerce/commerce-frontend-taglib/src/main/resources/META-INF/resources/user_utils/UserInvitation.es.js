/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {debounce, fetch} from 'frontend-js-web';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './UserInvitation.soy';

import 'clay-modal';

import './UserListItem.es';

import './UserInputItem.es';

const EMAIL_REGEX = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

class UserInvitation extends Component {
	attached() {
		this._debouncedFetchUsers = debounce(this._fetchUsers.bind(this), 300);
	}

	created() {
		this._fetchUsers();
	}

	shouldUpdate(changes) {
		if (changes.events) {
			return false;
		}

		return true;
	}

	testAddedUsers(_e) {
		const contentWrapper = this.element.querySelector(
			'.autocomplete-input__content'
		);
		this.element.querySelector('.autocomplete-input__box').focus();
		if (contentWrapper.scrollTo) {
			contentWrapper.scrollTo(0, contentWrapper.offsetHeight);
		}

		return this.emit('updateUsers', this.addedUsers);
	}

	testQuery() {
		this._loading = true;

		return this._debouncedFetchUsers();
	}

	_handleFormSubmit(event) {
		event.preventDefault();

		if (this.query.match(EMAIL_REGEX)) {
			this.addedUsers = [
				...this.addedUsers,
				{
					email: this.query,
				},
			];

			this.query = '';

			this.testAddedUsers();
			this.testQuery();

			return true;
		}

		return false;
	}

	_handleInputBox(event) {
		event.preventDefault();

		if (event.keyCode === 8 && !this.query.length) {
			this.addedUsers = this.addedUsers.slice(0, -1);

			this.testAddedUsers();

			return false;
		}

		this.query = event.target.value;

		this.testQuery();

		return true;
	}

	_toggleInvitation(userToBeToggled) {
		if (!userToBeToggled.id) {
			this.query = '';
		}

		const userAlreadyAdded = this.addedUsers.reduce(
			(alreadyAdded, user) =>
				alreadyAdded || user.email === userToBeToggled.email,
			false
		);

		this.addedUsers = userAlreadyAdded
			? this.addedUsers.filter(
					(user) => user.email !== userToBeToggled.email
			  )
			: [...this.addedUsers, userToBeToggled];

		this.testAddedUsers();

		return this.addedUsers;
	}

	_fetchUsers() {
		return fetch(this.usersAPI + '&q=' + this.query)
			.then((response) => response.json())
			.then((response) => {
				this._loading = false;

				this.users = response.users;

				return this.users;
			});
	}
}

Soy.register(UserInvitation, template);

const USER_SCHEMA = Config.shapeOf({
	email: Config.string().required(),
	name: Config.string().required(),
	thumbnail: Config.string().required(),
	userId: Config.oneOfType([Config.number(), Config.string()]).required(),
});

UserInvitation.STATE = {
	_loading: Config.bool().internal().value(false),
	addedUsers: Config.array(USER_SCHEMA).value([]),
	query: Config.string().value(''),
	spritemap: Config.string(),
	users: Config.array(USER_SCHEMA).value([]),
	usersAPI: Config.string().value(''),
};

export {UserInvitation};
export default UserInvitation;
