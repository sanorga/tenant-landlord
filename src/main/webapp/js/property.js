	
	//	Property Util JS Code Starts here
	//	invoke this method on focus exit.
	function getProperty(subscriberId){
		var selectedProperty = document.getElementById("selectedProperty");
		if (selectedProperty.value == "-1") {
			document.getElementById('rentalBuildingName').value = "";
			document.getElementById('rentalHouseNo').value = "";
			document.getElementById('rentalAddress').value = "";
			document.getElementById('rentalCity').value = "";
			document.getElementById('rentalState').value = "";
			document.getElementById('rentalZipcode').value = "";
			document.getElementById('rentalApartmentNo').value = "";
			document.getElementById('rentalAmount').value = "";
			return;
		}
		// need some validation before invoking ajax call.
//		PropertyUtil.findPropertyById(selectedProperty.value, populatePropertyCallBack);
		
		var jqxhr = $.ajax({
			url: "lookup/"+subscriberId+"/propertyById.json",
			dataType: "json",
			timeout: 5000
		})
		.done(function(property){
			if (property == null) {
				alert("Not a valid Property Key");
				return; 
			}
			document.getElementById('rentalBuildingName').value = property.name;
			document.getElementById('rentalHouseNo').value = property.houseNo;
			document.getElementById('rentalAddress').value = property.street;
			document.getElementById('rentalCity').value = property.city;
			document.getElementById('rentalState').value = property.state;
			document.getElementById('rentalZipcode').value = property.zipcode;
			document.getElementById('rentalApartmentNo').value = property.apartmentNo;
			if (property.rentalAmount > 0){
				document.getElementById('rentalAmount').value = property.rentalAmount;
			}
			
		})
	}

//	var populatePropertyCallBack = function (property) {
//		if (property == null) {
//			alert("Not a valid Property Key");
//			return; 
//		}
//		document.getElementById('rentalBuildingName').value = property.name;
//		document.getElementById('rentalHouseNo').value = property.houseNo;
//		document.getElementById('rentalAddress').value = property.street;
//		document.getElementById('rentalCity').value = property.city;
//		document.getElementById('rentalState').value = property.state;
//		document.getElementById('rentalZipcode').value = property.zipcode;
//		document.getElementById('rentalApartmentNo').value = property.apartmentNo;
//		document.getElementById('rentalAmount').value = property.rentalAmount;
//	}
