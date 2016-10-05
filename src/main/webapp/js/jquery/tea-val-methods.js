 var separator= "/";
 var minYear=1900;
 var maxYear=2050;
 var shortYearCutoff = 10;

 function isInteger(s){
 var i;
 for (i = 0; i < s.length; i++){ 
 // Check that current character is a number or not.
 var c = s.charAt(i);
 if (((c < "0") || (c > "9"))) return false;
 }
 // All characters are numbers.
 return true;
 }

 function stripCharsInBag(s, bag){
 var i;
 var returnString = "";
 // Search through string's characters one by one.
 // If character is not in bag, append to returnString.
 for (i = 0; i < s.length; i++){ 
 var c = s.charAt(i);
 if (bag.indexOf(c) == -1) returnString += c;
 }
 return returnString;
 }

 function daysInFebruary (year){
 // February has 29 days in any year evenly divisible by four,
 // EXCEPT for centurial years which are not also divisible by 400.
 return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
 }
 function DaysArray(n) {
 for (var i = 1; i <= n; i++) {
 this[i] = 31;
 if (i==4 || i==6 || i==9 || i==11) {this[i] = 30;}
 if (i==2) {this[i] = 29;}
 } 
 return this;
 }

 function isDate(dtStr, element){
 if (dtStr.length == 0) return true;
 var daysInMonth = DaysArray(12);
 var pos1=dtStr.indexOf(separator);
 var pos2=dtStr.indexOf(separator,pos1+1);
 var strMonth=dtStr.substring(0,pos1);
 var strDay=dtStr.substring(pos1+1,pos2);
 var strYear=dtStr.substring(pos2+1);
 strYr=strYear;
 if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1);
 if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1);
 for (var i = 1; i <= 3; i++) {
 if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1);
 }
 month=parseInt(strMonth);
 day=parseInt(strDay);
 year=parseInt(strYr);
 if (year < 20){
	 year += 2000;
 } else if (year < 100) {
	 year += 1900;
 }
 if (pos1==-1 || pos2==-1){
 //alert("The date format should be : MM/DD/YYYY");
 return false;
 }
 if (strMonth.length<1 || month<1 || month>12){
 //alert("Please enter a valid month");
 return false;
 }
 if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
 //alert("Please enter a valid day");
 return false;
 }
 if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
 //alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear);
 return false;
 }
 if (dtStr.indexOf(separator,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, separator))==false){
 //alert("Please enter a valid date");
 return false;
 }
 return true;
 }
 
 jQuery.validator.addMethod("dateMYD", function(value, element){ return isDate(value);}, "Must be in mm/dd/yyyy format.");