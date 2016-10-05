function Enter2TabFunctionality() {
	var jForm = $("form:first");
	var jInput = jForm.find("input");
	jInput.each(function(intI) {
		var jThis = $(this);

		jThis.keydown(function(objEvent) {
			return (InputKeyDownHandler(objEvent, jThis, jInput));
		});
	});
}

function InputKeyDownHandler(objEvent, jInput, jInputs) {
	if (objEvent.which != "13") {
		return (true);
	}
	var intI = jInputs.index(jInput);
	if (intI == (jInputs.length - 1)) {
		return (true);
	} else {
		jInputs.get(intI + 1).focus();
		return (false);

	}
}