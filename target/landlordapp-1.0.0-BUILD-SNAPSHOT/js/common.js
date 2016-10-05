/*
	These functions are for setting and 
	getting the cookie value on a page.
*/

function getexpirydate( nodays){
var UTCstring;
Today = new Date();
nomilli=Date.parse(Today);
Today.setTime(nomilli+nodays*24*60*60*1000);
UTCstring = Today.toUTCString();
return UTCstring;
}
function getcookie(cookiename) {
 var cookiestring=""+document.cookie;
 var index1=cookiestring.indexOf(cookiename);
 if (index1==-1 || cookiename=="") return ""; 
 var index2=cookiestring.indexOf(';',index1);
 if (index2==-1) index2=cookiestring.length; 
 return unescape(cookiestring.substring(index1+cookiename.length+1,index2));
}
function setcookie(name,value,duration){
cookiestring=name+"="+escape(value)+";EXPIRES="+getexpirydate(duration);
document.cookie=cookiestring;
if(!getcookie(name)){
return false;
}
else{
return true;
}
}

/*
 * This method is used to submit the form using a regular text link on the page.
 * The form on the page has to have name="form" and there must also be a hidden
 * field on the page somewhere within the form with the name="linkto". The text
 * link that will submit the form should have the following code: <a
 * href="javascript: submitForm('myvalue');">link</a> OR <a href="javascript:
 * void(0);" onclick="submitForm('myvalue');">link</a> Where 'myvalue' is the
 * value of the button/link you want to pass to the next page. Therefore the
 * request will have linkto=myvalue as one of its paramteres.
 */
function submitForm(selectedValue) {
	document.form.confirmation.value = selectedValue ;
    document.form.submit();
}

/**
 * Set checkboxs' checked values
 */ 
function toggle_cb(theFormId, theChecked)
{
	var theForm = document.getElementById(theFormId);
	var cb = null;
	
	for (var i = 0; i < theForm.elements.length; i++) 
	{
    	cb = theForm.elements[i];
	
		if (cb && cb.type == 'checkbox')
		{
			cb.checked = theChecked;
		}
	}
}

// This function is used to hide a row in a table by checking a check box
function toggleRow(cbObj, blockId, idName) {
	var blockObj = document.getElementById(blockId);
	if (blockObj != null && cbObj != null) {
    	rows = blockObj.getElementsByTagName("table");
        if (rows != null) {
        	for(var i = 0; i < rows.length; i++){
            	var row = rows[i];
                if (row.id ==idName) {
                	if(cbObj.checked == true) {
							      row.style.display = "block";
                  } else{
							      row.style.display = "none";

                  }
                }
            }
        }
    }
}

function enableRow(cbObj, blockId, idName) {
	var blockObj = document.getElementById(blockId);
	if (blockObj != null && cbObj != null) {
    	rows = blockObj.getElementsByTagName("table");
        if (rows != null) {
        	for(var i = 0; i < rows.length; i++){
            	var row = rows[i];
                if (row.id ==idName) {
                	if(cbObj.checked == true) {
						row.style.display = "block";
                  } 
                }
            }
        }
    }
}

function disableRow(cbObj, blockId, idName) {
	var blockObj = document.getElementById(blockId);
	if (blockObj != null && cbObj != null) {
    	rows = blockObj.getElementsByTagName("table");
        if (rows != null) {
        	for(var i = 0; i < rows.length; i++){
            	var row = rows[i];
                if (row.id ==idName) {
                	if(cbObj.checked == false) {
						row.style.display = "none";
                  }
                }
            }
        }
    }
}


function disableField(field) {
	var fieldObj = returnObject(field);

	if(fieldObj != null && typeof fieldObj.disabled != 'undefined') {
		fieldObj.disabled = true;
		if (!(/radio|checkbox|undefined/.test(fieldObj.type))) {
			fieldObj.style.backgroundColor = '#dddddd';
		}
	}
}
/**
 * doesn't actually disable
 */
function readonlyDisableField(field) {
	var fieldObj = returnObject(field);

	if(fieldObj != null && typeof fieldObj.disabled != 'undefined') {
		if (!(/radio|checkbox|undefined/.test(fieldObj.type))) {
			fieldObj.style.backgroundColor = '#dddddd';
		}
	}
}

function enableField(field) {
	var fieldObj = returnObject(field);

	if(fieldObj != null && typeof fieldObj.disabled != 'undefined') {
		fieldObj.disabled = false;
		if (!(/radio|checkbox|undefined/.test(fieldObj.type))) {
			fieldObj.style.backgroundColor = "#ffffff";
		}
	}
}


// Function for closing multiple popup windows
// Tweaked this function to fix bug 9825, the popups that are closed manually by
// the users
// shouldn't get the confimation to close all the popups
  function closePopupArr() {
  if (newWin.length > 0) // Check to see if there's a popup window open
	for(i=0; i< newWin.length; i++)
        if (newWin[i] != null) { // Only open popups should be closed
          	if (newWin[i] && newWin[i].closed) {
          		// do nothing;
          	} else {
          	    var agree=confirm("Do you want to close popup window(s)?"); 
          	    // Ask the user if they	 want to close all popup windows
    			if (agree)
           			for(i=0; i< newWin.length; i++) // we close all open windows
           				if (newWin[i] != null) {
           					newWin[i].close();
          				}           
          	}
       }
  }

  function NewWindowArr(popupArr,mypage, myname, w, h,scroll) {
    winproperty = 'height='+h+',width='+w+',scrollbars='+scroll+',resizable'
    newWin[popupArr] = window.open(mypage, myname, winproperty)
    if (parseInt(navigator.appVersion) >= 4) { newWin[popupArr].window.focus(); }
  }


//
// Function that closes the popup window after user confirmation,
// it works when the user clicks on logout and any other link on
// the page. This function is best implemented on an onunload event.
//
function closePopup() {
    if (newWin && newWin.open && !newWin.closed)
      var agree=confirm("Do you want to close popup window(s)?");
      if (agree)
      newWin.close();
}

//
// Function to open a new window on an onclick event
//
function NewWindow(mypage, myname, w, h,scroll) {
    winproperty = 'height='+h+',width='+w+',scrollbars='+scroll+',resizable'
    newWin = window.open(mypage, myname, winproperty)
    if (parseInt(navigator.appVersion) >= 4) { newWin.window.focus(); }
    }


// Function to update datefield value to hold today's date

function updatetotoday(datefield){
    var mydate = new Date();
    today = leadingZero(mydate.getMonth()+1) + "/" + leadingZero(mydate.getDate()) + "/" + mydate.getFullYear();
    datefield.value = today;
}


// Function to help prepend leading zeros to numbers less than 10.
// Used in date and month conversions.


function leadingZero(nr)
{
	if (nr < 10) nr = "0" + nr;
	return nr;
}


function confirmMessage(msg, myForm) {
	if(confirm(msg)) {
		alert("Deleted!");
		myForm.submit();
	}
	else {
		alert("Canceled.");
	}
}

function showConfirmPage(show, url) {
	/*
	 * no more popup confirmation page. please refer to the includes/_error.jsp
	 * to find out how we display the confirmation message
	 */
	/*
	 * if(show == true) { var winWidth = 400; var winHeight = 100; var winName =
	 * 'confirm'; var posLeft = (screen.width - winWidth) / 2; var posTop =
	 * (screen.height - winHeight) / 2; features = 'height=' + winHeight +
	 * ',width=' + winWidth + ',top=' + posTop + ',left=' + posLeft +
	 * ',scrollbars=no'; newWin = window.open(url, winName, features); }
	 */
}
function showSubmitPage(url) {
	var winWidth = 400;
	var winHeight = 200;
	var winName = 'submit';
	var posLeft = (screen.width - winWidth) / 2;
	var posTop = (screen.height - winHeight) / 2;
	features = 'height=' + winHeight + ',width=' + winWidth + ',top=' + posTop + ',left=' + posLeft + ',scrollbars=no';
	newWin = window.open(url, winName, features);
	return true;
}
function pdfWindow(url) {
	var winWidth = 720;
	var winHeight = 550;
	var winName = 'pdf';
	var posLeft = (screen.width - winWidth) / 2;
	var posTop = 50;
	features = 'height=' + winHeight + ',width=' + winWidth + ',top=' + posTop + ',left=' + posLeft + ',scrollbars=no, resizable=yes';
	newWin = window.open(url, winName, features);
}
function popupFeatureCodes(url) {
	var winWidth = 400;
	var winHeight = 500;
	var winName = 'popup';
	var posLeft = (screen.width - winWidth) / 2;
	var posTop = 200;
	features = 'height=' + winHeight + ',width=' + winWidth + ',top=' + posTop + ',left=' + posLeft + ',scrollbars=yes, resizable=yes';
	newWin = window.open(url, winName, features);
}
function popupMergeCodes(url) {
	var winWidth = 800;
	var winHeight = 300;
	var winName = 'popup';
	var posLeft = (screen.width - winWidth) / 2;
	var posTop = 200;
	features = 'height=' + winHeight + ',width=' + winWidth + ',top=' + posTop + ',left=' + posLeft + ',scrollbars=yes, resizable=yes';
	newWin = window.open(url, winName, features);
}

function popupRecommendations(url) {
	var winWidth = 480;
	var winHeight = 400;
	var winName = 'popup';
	var posLeft = (screen.width - winWidth) / 2;
	var posTop = 100;
	features = 'height=' + winHeight + ',width=' + winWidth + ',top=' + posTop + ',left=' + posLeft + ',scrollbars=yes, resizable=yes';
	newWin = window.open(url, winName, features);
}

function TrackCount(fieldObj,countFieldName,maxChars) {
	// var countField = eval("fieldObj.form."+countFieldName);
	var diff = maxChars - fieldObj.value.length;

	// Need to check & enforce limit here also in case user pastes data
	if (diff < 0) {
		fieldObj.value = fieldObj.value.substring(0,maxChars);
		diff = maxChars - fieldObj.value.length;
	}
	// countField.value = diff;
	// also, make it safe
	makeSafe(fieldObj);
}

function LimitText(fieldObj,maxChars){
	var result = true;
	if (fieldObj.value.length >= maxChars) {
		result = false;
	}
	if (window.event) {
		window.event.returnValue = result;
	}
	return result;
}

function textCounter(field, countfield, maxlimit) {
	if (field.value.length > maxlimit) { // if too long...trim it!
		field.value = field.value.substring(0, maxlimit);
	}
	// otherwise, update 'characters left' counter
	else {
		countfield.value = maxlimit - field.value.length;
	}
}




/*
 * This method stolen from CS3 - sets the value of the 'zipChangedId' field to
 * 'foo', then submits as in submitForm. This is done so that it can tab back to
 * the proper place
 */
function submitForm(value, zipChangedId) {
	zipChangedField = document.getElementById(zipChangedId);

	if(zipChangedField != null) {
        zipChangedField.value = "foo";
	}

	document.form.confirmation.value = value;
    document.form.submit() ;
}


// Highlight form element- � Dynamic Drive (www.dynamicdrive.com)
var highlightcolor="#ffffee"
var ns6=document.getElementById&&!document.all
var previous=''
var eventobj

// Regular expression to highlight only form elements
var intended=/INPUT|TEXTAREA|SELECT|OPTION/

// Function to check whether element clicked is form element
function checkel(which) {
	if (which.style&&intended.test(which.tagName)) {
		if (ns6&&eventobj.nodeType==3)
			eventobj=eventobj.parentNode.parentNode
		return true
	}
	else
		return false
}

// Function to highlight form element
function highlight(e) {
	eventobj=ns6? e.target : event.srcElement
	if (previous!='') {
		if (checkel(previous))
			previous.style.backgroundColor=''
		previous=eventobj
		if (checkel(eventobj))
			eventobj.style.backgroundColor=highlightcolor
	}
	else {
		if (checkel(eventobj))
			eventobj.style.backgroundColor=highlightcolor
		previous=eventobj
	}
}

var temp = "";



function hideLevel( _levelId, _imgId ) 
{
	var thisLevel = document.getElementById( _levelId );
	var thisImg = document.getElementById( _imgId );
	
	if (thisLevel != null)
	{
		thisLevel.style.display = "none";
	}
	
	if (thisImg != null)
	{
		//thisImg.src = plusImg.src;
	}
}

function showLevel( _levelId, _imgId ) {
	var thisLevel = document.getElementById( _levelId );
	var thisImg = document.getElementById( _imgId );
	if (thisLevel.style.display == "none") {
		thisLevel.style.display = "block";
		//thisImg.src = minusImg.src;
	}
	else {
		hideLevel( _levelId, _imgId);
	}
}


function selectField(theField) {
	theField.focus();
	theField.select();
}

var dom=document.getElementById?1:0;
function showHide(obj) {
  var myObj;
  if (dom) {
     myObj = document.getElementById(obj);
     if(myObj.style.display && myObj.style.display != "none") {
        myObj.style.display="none";
     }
     else {
        myObj.style.display="block";
     }
  }
}

function moveAll(theForm, delimiter) {

	for (var i = 0; i < theForm.elements.length; i++) 
	{
		// Case for input fields
        field = theForm.elements[i];
        
		
		if ((typeof field.name) == 'undefined')
		{
		// alert('undefined');
			continue;
		}
              
		if (field.name.indexOf(delimiter) == 0) 
		{
        	hiddenField = document.getElementsByName(field.name + '_h');

			if (hiddenField != null && hiddenField.length > 0) 
			{       
				moveValue(hiddenField[0], field);
			}
		}
	}
}

function moveValue(moveFromField, moveToField) 
{
	if (moveToField.type == 'checkbox')
	{
		moveToField.checked = (moveFromField.value == 'true');
	}
	else
	{
		moveToField.value = moveFromField.value;
	}
}

function removeAll(theForm, delimiter) 
{
	for(var i=0; i < theForm.elements.length; i++) 
	{
                field = theForm.elements[i];

		if ((typeof field.name) == 'undefined')
		{
			continue;
		}
		
		if(field.type != 'hidden' && field.name.indexOf(delimiter) == 0) 
		{
			if (field.type.indexOf("select") > -1) 
			{
				field.selectedIndex = 0;
			}
			else 
			{
				field.value = '';
			}
		}
	}
}


// Funtion to scroll to top. Necessary for scrolling
// as opposed to bookmark because base h ref of
// resource aliasing clicks to index.
ns = (document.layers)? true:false
ie = (document.all)? true:false
function goTop(sAnchor) {
 if (ns) {
   window.scrollTo(0, document.anchors[sAnchor].y)
 }
 if (ie) {
   eval('document.all.' + sAnchor + '.scrollIntoView()')
 }
}

function scrollToTop() {
    if (document.body.scrollTop) { 
        document.body.scrollTop = 0;
    } else if(document.body.scrollTo) {
		document.body.scrollTo(0,0);
	} else {
		location.href = "#top";
	}
}


/*
 * This small bit of javascript is used to set the values for some hidden fields
 * needed to do the custom validation for the Address Type fields.
 */
function checkAddressTypes(addrType) {
    if(addrType == null) return;
    typeStreetHiddenField = document.getElementById('typeStreetHidden');
    typePOBoxHiddenField = document.getElementById('typePOBoxHidden');
    typeRouteHiddenField = document.getElementById('typeRouteHidden');

    if(addrType == "0"){
        typeStreetHiddenField.value = addrType;
    } else {
        typeStreetHiddenField.value = '';
    }

    if(addrType == "1"){
        typePOBoxHiddenField.value = addrType;
    } else {
        typePOBoxHiddenField.value = '';
    }

    if(addrType == "2" || addrType == "3") {
        typeRouteHiddenField.value = addrType;
    }else{
        typeRouteHiddenField.value = '';
    }
}


/*
 * this method takes dates entered in 'short hand' and converts it to the
 * appropriate format required by the application.
 * 
 * Non-valid short-hand dates will not be formatted.
 * 
 * valid short-hand dates: M/d/yy - 1/1/00 MMddyy - 010100 MM/dd/yy - 01/01/00
 * valid date delimiters: / -
 * 
 * Short-hand years <= 30 will default to 20xx ; years > 30 will default to 19xx
 * 
 * ending format: MM/dd/yyyy - 01/01/2000
 */
function formatDate(myDate) {
    var returnDate = myDate;
    turnDate = 30;  // implied year range is 1931 - 2030
    
    delim = '/';
    if(myDate.indexOf('-') > 0){
    	delim = '-';
    }
    
    // if a user enters delim with 2 digit year less than the turnDate, we
	// assume > 2000, otherwise use 1900
	y = myDate.substring(myDate.indexOf(delim, 3)+1, myDate.length);
	if((y.length == 2) && (Number(y) <= Number(turnDate))){
		myDate = myDate.substring(0, myDate.indexOf(delim,3)) + delim + '20' + y;
	}

    if ((Number(myDate) + "") != 'NaN') {
        if (myDate.length == 8 || myDate.length == 6) {
            m = myDate.substring(0,2);
            d = myDate.substring(2,4);
            y = myDate.substring(4,myDate.length);

			y = Number(y) <= Number(turnDate) ? '20' + y : y;

            myDate = m + "/" + d + "/" + y;
        }
    }

    d = new Date(myDate);

    if (d != 'NaN') {
        month = (d.getMonth() + 1) < 10 ? ('0' + (d.getMonth() + 1)) : (d.getMonth() + 1);
        day = (d.getDate() < 10) ? ('0' + d.getDate()) : d.getDate();
        year = d.getFullYear();

        returnDate = (month + "/" + day + "/" + year);
    } else {
        returnDate = myDate;
    }

	if(returnDate == 'NaN/NaN/NaN'){
		returnDate = 'MM/DD/YYYY';
	}

    return returnDate;
}


/*
 * this function takes a string representing a phone number and formats it to a
 * user friendly display.
 * 
 * acceptable string lengths to format are: 7, 3334444 -> 333-4444 10,
 * 2223334444 -> (222) 333-4444 11, 12223334444 -> 1 (222) 333-4444
 * 
 * other phone lengths will be ignored
 * 
 */
function formatPhone(pnum){
	if(pnum.length == 7){
		return pnum.substring(0,3) + "-" + pnum.substring(3,pnum.length);
	} else if (pnum.length == 10) {
		return "(" + pnum.substring(0,3) + ") " + pnum.substring(3,6) + "-" + pnum.substring(6,pnum.length);
	} else if (pnum.length == 11){
		return pnum.substring(0,1) + " (" + pnum.substring(1,4) + ") " + pnum.substring(4,7) + "-" + pnum.substring(7,pnum.length);
	}
	return pnum;
}


/*
 * this function takes a value and returns it in money format (ex: 12 -> 12.00)
 * by default, we do not allow blanks - blanks will be converted into 0.00
 */
function formatMoney(val) {
    return formatMoney(val, false);
}

/*
 * this function takes a value and returns it in money format (ex: 12 -> 12.00)
 * allowBlank - if true, then does not format if length of val = 0, and "" is
 * returned. if false, we format blanks; ex: if val = '', then we return 0.00
 */
function formatMoney(val, allowBlank){
    doFormat = true;

    // if we allow blanks, let's see if we should do the format
    if(allowBlank){
        // only format if the length of val is greater than 0
        if(val.length < 1){
            doFormat = false;
        }
    }

    if(doFormat){
        val = Number(val);
        if(isNaN(val)){
        	return "0.00";
        }else{
	        val = (Math.round(val*100))/100;
	        return (val == Math.floor(val)) ? val + '.00'
	                  :((val*10 == Math.floor(val*10)) ? val + '0' : val);
        }
    }
    return "";
}

/*
 * this function takes a string representing a zipcode and formats it for a user
 * friendly display. Valid entries to format are: 9, 222225555 -> 22222-5555
 * 
 */
function formatZipCode(znum){
	if(znum.length == 9){
		return znum.substring(0,5) + "-" + znum.substring(5,znum.length);
	}
	return znum;
}
/*
 * this function takes obj's value and replaces special chars with it's more
 * accepted counterpart param obj - the object to make safe. (curling single and
 * double quotes)
 */
function makeSafe(obj){
    var tempStr = obj.value;
        // do the replacing
        tempStr = tempStr.replace(/�|�/g,"\"");
        tempStr = tempStr.replace(/�|�/g,"'");
    obj.value = tempStr;
}


/*
 * Used to auto tab to another input field when current field reaches a certain
 * length
 * 
 * @param obj The object which use is typing into @param len The length to check
 * for to auto tab @param next_field The next field to tab to
 */
function TabNext(obj, len, next_field) {
    var field_length = obj.value.length;
	var range = "";

	if(document.selection) {
		range = document.selection.createRange();
		range = range.text;
	} else if(document.getSelection) {
		range = document.getSelection();
	}

    if(field_length == len) {
        if(next_field != null && range == "") {
   			if(next_field.focus) next_field.focus();
            if(next_field.select) next_field.select();
        }
    }
}
function validateAndSubmit(value) {

	var regExpression = /\.rtf$/i;
	
	if (regExpression.test(value)) {
		document.form.submit();
	} else {
		alert("Please choose a RTF file.");
		return false;
	}

}
function validateAndSubmitVerificationUpload(value, refer) {

	var regExpression = /\.rtf$/i;
	
	if (regExpression.test(value)) {
		document.form.target.value='/controller'; 
		document.form.encoding='multipart/form-data';
		document.form.confirmation.value = '/uploadverificationfile';
		document.form.submit();
	} else {
		alert("Please choose a RTF file.");
		return false;
	}

}


function displayPleaseWait() {
	scrollToTop();
	// we only display the please wait message
	// if no fValidate errors have occured.
	show('pleasewait-area');
}


function scrollToTop() {
    if (document.body.scrollTop) { 
        document.body.scrollTop = 0;
    } else if(document.body.scrollTo) {
		document.body.scrollTo(0,0);
	} else {
		location.href = "#top";
	}
}

function showElements(elType, elDisplayType) {
    if (elType == null) {
		elType = 'select';
	}
    var elements = document.getElementsByTagName(elType);
	var displayType = "block";
    
	if (elDisplayType != null && elDisplayType.length > 0)
	{
		displayType = elDisplayType;
	}

    for ( i = 0; i < elements.length; i++ ) {
		elements[i].style.display = displayType;
	}
}
function hideElements(elType) {
	if (elType == null) {
		elType = 'select';
	}
    var elements = document.getElementsByTagName(elType);
    
    for ( i = 0; i < elements.length; i++ ) {
		elements[i].style.display = "none";
	}
}

function visibleElements(elType) {
	if (elType == null) {
		elType = 'select';
	}
    var elements = document.getElementsByTagName(elType);
    
    for ( i = 0; i < elements.length; i++ ) {
		elements[i].style.visibility = "visible";
	}
}

function invisibleElements(elType) {
	if (elType == null) {
		elType = 'select';
	}
    var elements = document.getElementsByTagName(elType);
    
    for ( i = 0; i < elements.length; i++ ) {
		elements[i].style.visibility = "hidden";
	}
}

// simple function to show a container
function show(arg) {
    var myObj = returnObject(arg);
    if (myObj != null) {
        myObj.style.display = "block";
    }
}

// simple function to hide a container
function hide(arg) {
    var myObj = returnObject(arg);
    if (myObj != null) {
        myObj.style.display = "none";
    }
}
 
function returnObject(arg, formObj) {
    var myObj = null;
    if (formObj != null) {
    	myObj = formObj.elements[arg];
    } else if (typeof arg == 'string') {
        myObj = document.getElementById(arg);
    } else if (typeof arg == 'object') {
        myObj = arg;
    }

    return myObj;
}

function printWindow(){
   bV = parseInt(navigator.appVersion)
   if (bV >= 4) window.print()
}

function trimZipCode(value) {	
	if(value.length > 0) { // do not perform if empty input

		var numbers = ""; // store all the numbers here
		// only get the first 5 chars
		for(var i = 0; i < 5; i++) {
			numbers += value.charAt(i);
		}
	}
	return numbers; // output to form
}



function getBaseHref(){
		return window.location.href.substring(0, window.location.href.lastIndexOf('/') + 1); 
}

// This function is used in the new framework for the ZipCode lookup
var req;

function zlu(zipId, cityId, stateId, countyId)
{
	var theZip = document.getElementById(zipId).value;   
	theZip = trimZipCode(theZip);

	if (window.XMLHttpRequest)
	{                                                                       
		req = new XMLHttpRequest();     
	}
	else if (window.ActiveXObject)
	{
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}

	if (req)
	{
		req.onreadystatechange = function()
		{
			var cityNode = document.getElementById(cityId);
			var stateNode = document.getElementById(stateId);
			var countyNode = document.getElementById(countyId);

			if (req.readyState == 4 && req.status == 200)
			{                                               
				if (req && req.responseXML)

					{
						cityNode.value = req.responseXML.documentElement.getAttribute("city");
						var stateVal = req.responseXML.documentElement.getAttribute("state");

						// stateNode.innerHTML = ;
						if (stateNode.type.indexOf("select") > -1) 
						{
							for (i=0; i < stateNode.options.length; i++)
							{
								if(stateNode.options[i].value == stateVal)
								{
									stateNode.options[i].selected=true;
									break;
								}
							}
							
						}

						countyNode.value = req.responseXML.documentElement.getAttribute("county");

					}
			}
		}

		req.open("GET", getBaseHref() + "ziplu?zipcode=" + theZip, true);

					
		if (window.XMLHttpRequest)
		{
			req.send(null);
		}
		else
		{
			req.send();
		}
	}
}


function ajaxGet(url, params, handleCompletedSuccess, handleCompletedError, handleFailure) {
	if (window.XMLHttpRequest) {                                                                       
		req = new XMLHttpRequest();     
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	if (req) {
		req.onreadystatechange = function() {
			if (req.readyState == 4) {
				if (req.status >= 200 && req.status <= 299) {
					if (handleCompletedSuccess) { handleCompletedSuccess(req); }
				} else {
					if (handleCompletedError) { handleCompletedError(req); }
				}
			}
		}

		var urlWithParams = url + "?";
		
		for (p in params) {
			urlWithParams += p + "=" + escape(params[p]) + "&";
		}
		
		req.open("GET", urlWithParams, true);

		if (window.XMLHttpRequest) {
			req.send(null);
		} else {
			req.send();
		}
	} else {
		if (handleFailure) { handleFailure(); }
	}
}

function focusFirstElement() {
	  // If there is a form in the doc...
	  if (document.forms != null) {	
	    var allForms = null;
		var theForm  = null;
		var allElems = null;
		var theElem  = null;
		var targElem = null;
			  
		allForms = document.forms;
		for (var i = 0; i < allForms.length && targElem == null; i++) {
	  	  theForm  = allForms[i];
		  allElems = theForm.elements;
					
	      for (var j = 0; j < allElems.length && targElem == null; j++) {
	        theElem = allElems[j];
			if (theElem.type != null && theElem.type == "text" ) {
			  targElem = theElem;
			}			
		  }		
		}
		
		if (targElem != null) {
	      targElem.focus();
		}
	  }
	}


/***Check all check box if checked th header check */
function checkAllCheckBoxes(checkboxname, checkAllchkboxId) {
	field = document.getElementsByName(checkboxname);
	headbox = document.getElementById(checkAllchkboxId);
	for (i = 0; i < field.length; i++)
		if (headbox.checked)
			field[i].checked = true;
		else
			field[i].checked = false;
}


/**hide and show by Id**/
function showDesc(descId) {	
	$('#'+descId).show();
}
function hideDesc(descId) {
	$('#'+descId).hide();
}

function loadFancyBox(){
	$('.fancyoverlay').fancybox({
		maxWidth	: 800,
		maxHeight	: 600,
		fitToView	: true,
		width		: '700',
		height		: '70%',
		autoSize	: true,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none'
	});
}

$(document).ready(function() {
	loadFancyBox();	
});