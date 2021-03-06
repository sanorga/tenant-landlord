var CRITERIA_PREFIX = 'criteria'; // this is being set via script
var MODIFIER_PREFIX = 'modifier'; // this is being set via script
var INPUT_PREFIX  = 'value'; // this is being set via script
var TYPE_PREFIX = 'type'; //	this is being set via script
var NAME_PREFIX = 'name' // this is being set via script
var TABLE_NAME = 'searchTable'; // this should be named in the HTML
var ROW_BASE = 1; // first number (for display)
var hasLoaded = false;

var modifierOptions = new Array('Contains', 'Is');
var modifierValues = new Array('C', 'E');

function populatePreviousSearchValues(criterias, modifiers, values) {
	//	javascript index starts with 1.
	var jsIndex;
	for (var i = 0; i < criterias.length; i++) {
		jsIndex = i + 1;
		if (i != 0) addRowToTable();
		document.getElementById('criteria' + jsIndex).value = criterias[i];
		document.getElementById('modifier' +jsIndex).value = modifiers[i];
		document.getElementById('value' + jsIndex).value = values[i];
		document.getElementById('hid1' + jsIndex).value = criteriaTypes[document.getElementById('criteria' + jsIndex).selectedIndex];
		document.getElementById('hid2' + jsIndex).value = criteriaOptions[document.getElementById('criteria' + jsIndex).selectedIndex];
	}
}

function fillInRows() {
  hasLoaded = true;
  addRowToTable();
}

// myRowObject is an object for storing information about the table rows
function myRowObject(one, two, three, four, five) {
  this.one = one; // criteria object
  this.two = two; // modifier object
  this.three = three; // input text object
  this.four = four; // add button
  this.five = five; // delete button
}

// addRowToTable - Inserts at row 'num', or appends to the end if no arguments are passed in. Dont pass in empty strings.
function addRowToTable(num) {
  if (hasLoaded) {
    var tbl = document.getElementById(TABLE_NAME);
    var nextRow = tbl.tBodies[0].rows.length;
    var iteration = nextRow + ROW_BASE;

    if (num == null) { num = nextRow; } 
    else { iteration = num + ROW_BASE; }

    // add the row
    var row = tbl.tBodies[0].insertRow(num);

    var cell0 = row.insertCell(0);
    var criteria = document.createElement('select');
    criteria.setAttribute('name', CRITERIA_PREFIX);
    criteria.setAttribute('id', 'criteria' + iteration);
    
    for (i=0; i<criteriaOptions.length; i++) {
      var opt = document.createElement('option');
      opt.text = criteriaOptions[i];
      opt.value = criteriaValues[i];
      criteria.options.add(opt);
    }
    criteria.onchange = function () {typeListener(iteration);};
    cell0.appendChild(criteria);

    // cell 1 - select box
    var cell1 = row.insertCell(1);
    var modifier = document.createElement('select');
    modifier.setAttribute('name', MODIFIER_PREFIX);
    modifier.setAttribute('id', 'modifier' + iteration);
    
    for (i=0; i<modifierOptions.length; i++) {
      var opt = document.createElement('option');
      opt.text = modifierOptions[i];
      opt.value = modifierValues[i];
      modifier.options.add(opt);
    }
    cell1.appendChild(modifier);

    // cell 2 - input box
    var cell2 = row.insertCell(2);
    var txtInp = document.createElement('input');
    txtInp.setAttribute('type', 'text');
    txtInp.setAttribute('name', INPUT_PREFIX);
    txtInp.setAttribute('id', 'value' + iteration);
    txtInp.setAttribute('size', '40');
    txtInp.setAttribute('class', 'form-control form-control-sm');
   // txtInp.onclick = function () {autocomplete(iteration);}; 
    cell2.appendChild(txtInp);

    // cell 3 - add button
    var cell3 = row.insertCell(3);
    var addBtn = document.createElement('input');
    addBtn.setAttribute('type', 'button');
    addBtn.setAttribute('value', 'Add');
    addBtn.setAttribute('class', 'btn by_cr');
    addBtn.onclick = function () {addRowToTable();};
    cell3.appendChild(addBtn);

    // cell 4 - delete button
    var cell4 = row.insertCell(4);
    var delBtn = document.createElement('input');
    delBtn.setAttribute('type', 'button');
    delBtn.setAttribute('value', 'Delete');
    delBtn.setAttribute('class', 'btn by_cr');
    delBtn.onclick = function () {deleteCurrentRow(this)};
    cell4.appendChild(delBtn);

    //	In cell 4 add - hidden textbox
    var hidInp = document.createElement('input');
    hidInp.setAttribute('type', 'hidden');
    hidInp.setAttribute('id', 'hid1' + iteration);
    hidInp.setAttribute('name', TYPE_PREFIX);
    hidInp.setAttribute('value', criteriaTypes[0]);
    cell4.appendChild(hidInp);
    
    var hidInp2 = document.createElement('input');
    hidInp2.setAttribute('type', 'hidden');
    hidInp2.setAttribute('id', 'hid2' + iteration);
    hidInp2.setAttribute('name', NAME_PREFIX);
    hidInp2.setAttribute('value', criteriaOptions[0]);
    cell4.appendChild(hidInp2);

    // Store the myRow object in each row
    row.myRow = new myRowObject(criteria, modifier, txtInp, addBtn, delBtn, hidInp);
  }
}

function typeListener(index) {
	document.getElementById('hid1' + index).value = criteriaTypes[document.getElementById('criteria' + index).selectedIndex];
	document.getElementById('hid2' + index).value = criteriaOptions[document.getElementById('criteria' + index).selectedIndex];
}

// If there isn't an element with an onclick event in your row, then this function can't be used.
function deleteCurrentRow(obj) {
  if (hasLoaded) {
    var delRow = obj.parentNode.parentNode;
    var tbl = delRow.parentNode.parentNode;
    var rIndex = delRow.sectionRowIndex;
    var rowArray = new Array(delRow);
    deleteRows(rowArray);
    //reorderRows(tbl, rIndex);	//	No need to re order the rows. - Jamal.
  }
}

function reorderRows(tbl, startingIndex) {
  if (hasLoaded) {
    if (tbl.tBodies[0].rows[startingIndex]) {
      var count = startingIndex + ROW_BASE;
      
      for (var i=startingIndex; i<tbl.tBodies[0].rows.length; i++) {
        tbl.tBodies[0].rows[i].myRow.one.name = CRITERIA_PREFIX + count; // criteria
        tbl.tBodies[0].rows[i].myRow.two.name = MODIFIER_PREFIX + count; // modifier
        tbl.tBodies[0].rows[i].myRow.three.name = INPUT_PREFIX + count; // input text
        var tempVal = tbl.tBodies[0].rows[i].myRow.three.value.split(' '); // for debug purposes
        tbl.tBodies[0].rows[i].myRow.three.value = tempVal[0]; // for debug purposes
        count++;
      }
    }
  }
}

function deleteRows(rowObjArray) {
  if (hasLoaded) {
    for (var i=0; i<rowObjArray.length; i++) {
      var rIndex = rowObjArray[i].sectionRowIndex;
      rowObjArray[i].parentNode.deleteRow(rIndex);
    }
  }
}

function doCheckAll() {
  with (document.searchResultsForm) {
    for (var i=0; i < elements.length; i++) {
      if (elements[i].type == 'checkbox' && elements[i].name.substr(0, 4) == 'cbox'){
        if (document.searchResultsForm.ckSelect.checked == true) {
          elements[i].checked = true;
		}else {
		  elements[i].checked = false;}
		}
      }
    }
}

function uncheckTop() {
  document.searchResultsForm.ckSelect.checked= false;
}

function confirmDelete() {
  var checkCount = 0;
  with (document.searchResultsForm) {

    for (var i=0; i < elements.length; i++) {
      if (elements[i].type == 'checkbox' && elements[i].name.substr(0, 4) == 'cbox'){
        if (elements[i].checked) {
          checkCount++;
        }
      }
    }

    if (checkCount == 0) {
      alert("Please select an application to delete");
      return false;
    }

    if (checkCount > 0) {
      var doDelete = false;
	  if (checkCount == 1) {doDelete = confirm("Do you wish to delete the selected applicant/application?");} 
	  else {doDelete = confirm("Do you wish to delete the " +  checkCount + " selected applicants/applications?"); }

	  if (doDelete) { document.searchResultsForm.submit(); return true;}
	  return false;
    }
  }
}

function confirmAbandon() {
  var checkCount = 0;
  with (document.searchResultsForm) {

    for (var i=0; i < elements.length; i++) {
      if (elements[i].type == 'checkbox' && elements[i].name.substr(0, 4) == 'cbox'){
        if (elements[i].checked) {
          checkCount++;
        }
      }
    }

    if (checkCount == 0) {
      alert("Please select an application to abandon");
      return false;
    }

    if (checkCount > 0) {
      var doDelete = false;
	  if (checkCount == 1) {doDelete = confirm("Do you wish to abandon the selected applicant/application?");} 
	  else {doDelete = confirm("Do you wish to abandon the " +  checkCount + " selected applicants/applications?"); }

	  if (doDelete) { document.searchResultsForm.submit(); return true;}
	  return false;
    }
  }
}

function confirmChecked() {
	  var checkCount = 0;
	  with (document.searchResultsForm) {

	    for (var i=0; i < elements.length; i++) {
	      if (elements[i].type == 'checkbox' && elements[i].name.substr(0, 4) == 'cbox'){
	        if (elements[i].checked) {
	          checkCount++;
	        }
	      }
	    }

	    if (checkCount == 0) {
	      alert("Please select atleast an application");
	      return false;
	    }

	    if (checkCount > 0) {
	      var doCheck = false;
		  if (checkCount == 1) {doCheck = confirm("Do you wish to change the status of  selected application?");} 
		  else {doCheck = confirm("Do you wish to change the status of " +  checkCount + " selected applications?"); }

		  if (doCheck) { document.searchResultsForm.submit(); return true;}
		  return false;
	    }
	  }
	}

function confirmInProcess() {
	  var checkCount = 0;
	  with (document.searchResultsForm) {

	    for (var i=0; i < elements.length; i++) {
	      if (elements[i].type == 'checkbox' && elements[i].name.substr(0, 4) == 'cbox'){
	        if (elements[i].checked) {
	          checkCount++;
	        }
	      }
	    }

	    if (checkCount == 0) {
	      alert("Please select an application to move to InProcess State");
	      return false;
	    }

	    if (checkCount > 0) {
	      var doMove = false;
		  if (checkCount == 1) {doMove = confirm("Do you wish to move the selected applicant/application?");} 
		  else {doMove = confirm("Do you wish to move the " +  checkCount + " selected applicants/applications?"); }

		  if (doMove) { document.searchResultsForm.submit(); return true;}
		  return false;
	    }
	  }
	}
function confirmDeleteInvoice() {
	  var checkCount = 0;
	  with (document.searchResultsForm) {

	    for (var i=0; i < elements.length; i++) {
	      if (elements[i].type == 'checkbox' && elements[i].name.substr(0, 4) == 'cbox'){
	        if (elements[i].checked) {
	          checkCount++;
	        }
	      }
	    }

	    if (checkCount == 0) {
	      alert("Please select an invoice to delete");
	      return false;
	    }

	    if (checkCount > 0) {
	      var doDelete = false;
		  if (checkCount == 1) {doDelete = confirm("Do you wish to delete the selected invoice?");} 
		  else {doDelete = confirm("Do you wish to delete the " +  checkCount + " selected invoices?"); }

		  if (doDelete) { document.searchResultsForm.submit(); return true;}
		  return false;
	    }
	  }
	}
