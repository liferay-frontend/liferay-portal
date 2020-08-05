if (Liferay.Browser.isIe()) {
	require('svg4everybody');
} else {
	const queue = [];

	window.svg4everybody = (args) => {
		queue.push(args);
	};

	const requestAnimationFrame = window.requestAnimationFrame || setTimeout;

	let useNodesBypassed = 0;

	const uses = document.getElementsByTagName('use');

	function oninterval() {
		if (useNodesBypassed && uses.length - useNodesBypassed <= 0) {
			return void requestAnimationFrame(oninterval, 67);
		}

		useNodesBypassed = 0;

		for (let index = 0; index < uses.length; index++) {
			const useNode = uses[index];

			const src =
				useNode.getAttribute('xlink:href') ||
				useNode.getAttribute('href');

			if (!src && useNode.hasAttribute('data-href')) {
				useNode.setAttribute('href', useNode.getAttribute('data-href'));
			}

			if (src && !src.match(location.origin)) {
				require('svg4everybody');

				for (let i = 0; i < queue.length; i++) {
					const args = queue[i];

					svg4everybody(args);
				}

				return;
			}

			++useNodesBypassed;
		}

		requestAnimationFrame(oninterval, 67);
	}

	oninterval();
}
