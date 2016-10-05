	function confirmPassword(passwordId, rePasswordId){
		if ((document.getElementById(passwordId) == null) || (document.getElementById(rePasswordId) == null))
			return true;
		
		if( (document.getElementById(passwordId).value == document.getElementById(rePasswordId).value) && (document.getElementById(passwordId).value != "") ){
			return true;
		}else
			document.getElementById(passwordId).value = "";
			document.getElementById(rePasswordId).value = "";
			alert("Passwords do not match.");
			return false;
	}