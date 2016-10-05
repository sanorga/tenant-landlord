function formatCurrency( num ) {
	var isNegative = false;
	num = num.toString().replace(/\\$|\\,/g,'');
	if( isNaN( num ) ) {
	  num = "0";
	}
	if ( num < 0 ) {
	  num = Math.abs( num );
	  isNegative = true;
	}
	cents = Math.floor( ( num * 100 + 0.5 ) % 100 );
	num = Math.floor( ( num * 100 + 0.5 ) / 100 ).toString();
	if ( cents < 10 ) {
	  cents = "0" + cents;
	}
	for ( i = 0; i < Math.floor( ( num.length - ( 1 + i ) ) / 3 ); i++) {
	  num = num.substring( 0 ,num.length - ( 4 * i + 3 ) ) + num.substring( num.length - ( 4 * i + 3 ));
	}

	var result = num + '.' + cents;
	if ( isNegative ) {
	  result = "-" + result;
	}
	return result;
}

function currencyFormatter(field)
{
	field.value = formatCurrency(field.value);
}

/*
 * this method takes dates entered in 'short hand' and converts it to the appropriate format
 * required by the application.
 *
 * Non-valid short-hand dates will not be formatted.
 *
 * valid short-hand dates:
 * M/d/yy - 1/1/00
 * MMddyy - 010100
 * MM/dd/yy - 01/01/00
 * valid date delimiters: / -
 *
 * Short-hand years <= 30 will default to 20xx ;  years > 30 will default to 19xx
 *
 * ending format:  MM/dd/yyyy - 01/01/2000
 */
function formatDate(myDate) {
    var returnDate;
    turnDate = 30;  //implied year range is 1931 - 2030
    
    delim = '/';
    if(myDate.indexOf('-') > 0){
    	delim = '-';
    }
    
    //if a user enters delim with 2 digit year less than the turnDate, we assume > 2000, otherwise use 1900
	y = myDate.substring(myDate.indexOf(delim, 3)+1, myDate.length);
	if((y.length == 2) && (Number(y) <= Number(turnDate))){
		myDate = myDate.substring(0, myDate.indexOf(delim,3)) + delim + '20' + y;
	}

    if (!isNaN(myDate)) {
        if (myDate.length == 8 || myDate.length == 6) {
            m = myDate.substring(0,2);
            d = myDate.substring(2,4);
            y = myDate.substring(4,myDate.length);

			y = Number(y) <= Number(turnDate) ? '20' + y : y;

            myDate = m + "/" + d + "/" + y;
        }
    }

    d = new Date(myDate);

    if (!isNaN(d)) {
        month = (d.getMonth() + 1) < 10 ? ('0' + (d.getMonth() + 1)) : (d.getMonth() + 1);
        day = (d.getDate() < 10) ? ('0' + d.getDate()) : d.getDate();
        year = d.getFullYear();

        returnDate = (month + "/" + day + "/" + year);
    } else {
        returnDate = myDate;
    }

    return returnDate;
}

function dateFormatter(field) {
	field.value = formatDate(field.value);
}

//***************** AUTHOR INFORMATION *************************
//* Phone/number filtering script made by,                     *
//* Øyvind Hansen, oyhansen@yahoo.no, Norway                   *
//* 14.04.2003                                                 *
//**************************************************************

// TESTED IN:

// Opera:	OK
// IE:		OK
// NS:		?
// Mozilla	?

//****************** SCRIPT INFO v1.1 **************************
//* Form validation of a phone number. Remove all illegal      *
//* characters and assign to a given format                    *
//**************************************************************

//************************* OTHER INFO *************************
//**> Free for use, but include author information header    <**
//* Please report any bugs & please rate it!                   *
//*                                                            *
//* http://www.hotscripts.com/Detailed/21683.html              *
//**************************************************************
function numberFormatter(input, format) {	
	var output = ""; //assign numbers here
	
	if(input.length > 0) { //do not perform if empty input

		var numbers = ""; //store all the numbers here

		//process to remove non-numbers and spaces
		for(var i = 0; i < input.length; i++) {
			var c = input.charAt(i);
			if(!(isNaN(c) || c == " ")) numbers += c;
		}

		//remove country code, if any
		//if(numbers.substring(0, 2) == "47") numbers = numbers.substring(2, numbers.length);

		//assign numbers to chosen format
		var n = 0, i = 0;
		while(i < format.length && n < numbers.length) {
			var c = format.charAt(i);
			if(c == "#") {
				output += numbers.charAt(n++)
			} else {
				output += c;
			}
			i++;
		}
	}
	return output; //output to form
}

function zipFormatter(field) {
	field.value = numberFormatter(field.value, '#####-####');
}

function phoneFormatter(field) {
	field.value = numberFormatter(field.value, '(###) ###-####');
}

function ssnFormatter(field) {
	field.value = numberFormatter(field.value, '###-##-####');
}

function sinFormatter(field) {
	field.value = numberFormatter(field.value, '###-###-###');
}

