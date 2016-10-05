	function popupWindow(formId){	
		f = document.getElementById(formId);
		f.target = 'pdfPopupwindow';
		window.open('',f.target,'menubar=no,scrollbars=no, width=800,height=800');
		f.submit();
		return false;
	}
	
	function sameWindowSaveAndComplete(formId) {
		$('#scDiv').hide();
		$('#scDivShow').show();
		sameWindow(formId);
	}
	
	function sameWindowMoveToPending(formId) {
		$('#mpDiv').hide();
		$('#mpDivShow').show();
		sameWindow(formId);
	}
	
	function sameWindowCancel(formId) {
		$('#cvDiv').hide();
		$('#cvDivShow').show();
		sameWindow(formId);
	}
	
	function sameWindow(formId){
		f = document.getElementById(formId);
		f.target = "mainwindow";
		window.open('',f.target,'menubar=no,scrollbars=no, width=800,height=800');
		f.submit();
		return false;
	}
	

	 //for direct call services verification only

	function sameWindowOtherSaveAndComplete() {
		$('#scDiv').hide();
		$('#scDivShow').show();
		return true;
	}

	function sameWindowOtherMoveToPending() {
		$('#mpDiv').hide();
		$('#mpDivShow').show();
		return true;
	}

	function sameWindowOtherCancel() {
		$('#cvDiv').hide();
		$('#cvDivShow').show();
		return true;
	}
