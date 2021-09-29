import {ClayIconSpriteContext} from '@clayui/icon';

const getIconSpriteMap = () => {
	try {
		// eslint-disable-next-line no-undef
		if (!Liferay.Icons) {
			new Error('Liferay.Icons is not defined');
		}

		// eslint-disable-next-line no-undef
		return Liferay.Icons.getSpritemapPath('clay');
	}
	catch (error) {
		console.warn(error.message);

		// eslint-disable-next-line no-undef
		return require('@clayui/css/lib/images/icons/icons.svg').default;
	}
};

const ClayIconProvider = ({children}) => (
	<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
		{children}
	</ClayIconSpriteContext.Provider>
);

export default ClayIconProvider;
