const warnMessage = 'Changes you made may not be saved.';

export function createExitAlert() {
	window.onbeforeunload = function (event) {
		event.preventDefault();
		event.returnValue = warnMessage;

		return warnMessage;
	};
}

export function clearExitAlert() {
	window.onbeforeunload = function () {};
}
