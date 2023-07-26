/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {debounce, fetch} from 'frontend-js-web';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './AddAccountModal.soy';

import 'clay-modal';

import '../user_utils/UserListItem.es';

import '../user_utils/UserInputItem.es';

import '../css/main.scss';

const EMAIL_REGEX = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

class AddAccountModal extends Component {
	created() {
		this._debouncedFetchUser = debounce(this._fetchUsers.bind(this), 300);
	}

	attached() {
		this._fetchUsers();
	}

	syncAddedUsers() {
		const contentWrapper = this.element.querySelector(
			'.autocomplete-input__content'
		);
		this.element.querySelector('.autocomplete-input__box').focus();
		if (contentWrapper.scrollTo) {
			contentWrapper.scrollTo(0, contentWrapper.offsetHeight);
		}
	}

	_handleCloseModal(event) {
		event.preventDefault();
		this._modalVisible = false;

		return event;
	}

	syncQuery() {
		this._loading = true;

		return this._debouncedFetchUser();
	}

	_handleFormSubmit(event) {
		event.preventDefault();
		let result = false;
		if (this.query.match(EMAIL_REGEX)) {
			this.addedUsers = [
				...this.addedUsers,
				{
					email: this.query,
				},
			];
			this.query = '';
			result = true;
		}

		return result;
	}

	_handleInputBox(event) {
		if (event.keyCode === 8 && !this.query.length) {
			this.addedUsers = this.addedUsers.slice(0, -1);
		}
		else {
			this.query = event.target.value;
		}

		return event;
	}

	_handleInputName(event) {
		this.accountName = event.target.value;

		return event;
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

		return this.addedUsers;
	}

	_fetchUsers() {
		return fetch(
			this.usersAPI +
				'?groupId=' +
				themeDisplay.getScopeGroupId() +
				'&q=' +
				this.query
		)
			.then((response) => response.json())
			.then((response) => {
				this._loading = false;
				this.users = response.users;

				return this.users;
			});
	}

	_handleCreateAccount() {
		if (!this.addedUsers.length || !this.accountName) {
			return false;
		}

		const data = {
			accountName: this.accountName,
			administratorsEmail: this.addedUsers,
		};

		return this.emit('AddAccountModalSave', data);
	}

	toggle() {
		this._modalVisible = !this._modalVisible;

		return this._modalVisible;
	}

	open() {
		this._modalVisible = true;

		return this._modalVisible;
	}

	close() {
		this._modalVisible = false;

		return this._modalVisible;
	}
}

Soy.register(AddAccountModal, template);

const USER_SCHEMA = Config.shapeOf({
	email: Config.string().required(),
	name: Config.string().required(),
	thumbnail: Config.string().required(),
	userId: Config.oneOfType([Config.string(), Config.number()]).required(),
});

AddAccountModal.STATE = {
	_loading: Config.bool().internal().value(false),
	_modalVisible: Config.bool().internal().value(false),
	accountName: Config.string().value(''),
	addedUsers: Config.array(USER_SCHEMA).value([]),
	query: Config.string().value(''),
	spritemap: Config.string(),
	users: Config.array(USER_SCHEMA).value([]),
	usersAPI: Config.string().value(''),
};

export {AddAccountModal};
export default AddAccountModal;
