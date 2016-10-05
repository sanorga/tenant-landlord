	//	invoke this method on focus exit.
	
	// it call from financial page. It is displayed zip code and state for country is US
	function getStateCityScript2(countryId, zipcodeId, cityId, stateId){
		var country = document.getElementById(countryId).value;
		if(country == ""){
			alert("Select country");
		} else if (country == "US") {
			getStateCityScript(zipcodeId, cityId, stateId);
		}
	}
	
	function clearStateCity(zipcodeId, cityId, stateId) {
		document.getElementById(zipcodeId).value = "";
		document.getElementById(cityId).value = "";
		document.getElementById(stateId).value = "";
	}

	function getStateCityScript(zipcodeId, cityId, stateId){
		var cityObj = document.getElementById(cityId);
		var stateObj = document.getElementById(stateId);
		
		var zip = document.getElementById(zipcodeId).value;
		
		if (zip) {
			if (zip.length > 0) {
				

				// need some validation before invoking ajax call.
				var res = $.ajax({
					url: "lookup/" + zip + "/cityStateByZip.json",
					dataType: "json",
					timeout: 5000
				})
				.done(function(result){
					if (result == null) {
						cityObj.value = '';
						stateObj.value = '';
						alert("Not a valid Zipcode");
						return; 
					}
					cityObj.value = result.city;
					stateObj.value = result.state;
				})
				.fail(function(){
					cityObj.value = '';
					stateObj.value = '';
					alert("City lookup failed.");
					return;
				})
		

			}
		}
	}
