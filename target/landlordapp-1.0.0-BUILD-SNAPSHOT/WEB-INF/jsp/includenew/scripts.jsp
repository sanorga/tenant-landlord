<script src="jsnew/app.js"></script>
<% // <script src="js_old/jquery/jquery.jBreadCrumb.1.1.js"></script>
// <c:set var="thePageTitle" value="Sales rep" />
// <c:set var="theBodyID" value="admin" />
// <c:set var="addFormValidator" value="true" />
// <c:set var="zipChecker" value="true" />
%>
<%
// Existing
// var datepicker           = require('jquery-ui/datepicker')
// var autocomplete         = require('jquery-ui/autocomplete')
// var bootstrap            = require('bootstrap')
// var select2              = require('select2')
// var psb                  = require('perfect-scrollbar/jquery')($)
// var numberInput          = require('./numeric.only.js')
// var tablesorter          = require('tablesorter')
// var tablesorterWidgets   = require('./jquery.tablesorter.widgets.js')
// var tablesorterPager     = require('./jquery.tablesorter.pager.js')
// var fancybox             = require('fancybox')($)
// var jQueryValidation     = require('jquery-validation')
// var jsHashtable          = require('./hashtable.js')
// var jQueryValidationAM   = require('./jquery-validation-additional-methods.js')
// var maskedinput          = require('./jquery.maskedinput.min.js')
// var formatDateTime       = require('jquery-formatdatetime')
%>

<c:if test="${zipChecker == true}">         <script src='jsnew/zip2CityState.js'></script></c:if>
<c:if test="${activatePopUpWindow == true}"><script src='jsnew/popupwindow.js'></script></c:if>
<c:if test="${activateQuickSearch == true}"><script src='jsnew/quicksearch.js'></script></c:if>
<c:if test="${activateToolTips == true}">   <script src="jsnew/qtip.js"></script></c:if>
<c:if test="${activateSearchTable == true}"><script src="jsnew/searchtable.min.js"></script></c:if>
<c:if test="${moveApplications == true}">   <script src="jsnew/searchtable_application.js"></script></c:if>
<c:if test="${blockUI == true}">            <script src="jsnew/jquery.blockUI.js"></script></c:if>
<c:if test="${jqGrid == true}">             <script src="jsnew/jqGrid.locale-EN.js"></script><script src="jsnew/jquery.jqGrid.min.js"></script></c:if>

<c:if test="${numerFormat == true}">
  <script src="jsnew/jquery.maskedinput.js"></script>
  <script src="jsnew/jquery.numeric.only.js"></script>
  <script src="jsnew/hashtable.js"></script>
  <script src="jsnew/jquery.numberformatter.js"></script>
</c:if>

<c:if test="${kendoImport == true}"> 
  <script src="//da7xgjtj801h2.cloudfront.net/2014.2.716/jsnew/kendo.core.min.js"></script>
  <script src="//da7xgjtj801h2.cloudfront.net/2014.2.716/jsnew/kendo.userevents.min.js"></script>
  <script src="//da7xgjtj801h2.cloudfront.net/2014.2.716/jsnew/kendo.draganddrop.min.js"></script>
  <script src="//da7xgjtj801h2.cloudfront.net/2014.2.716/jsnew/kendo.window.min.js"></script>
</c:if>

<script>
function doAjax(url) {var jqxhr = $.ajax({ url: url, dataType: "json",timeout: 5000}).done(function(po) { getPropertyMapCallBack(po)})};


$(function() {

  $('.fancyoverlay').fancybox({
    maxWidth  : 800,
    maxHeight : 600,
        beforeLoad : function() {         
            this.width  = parseInt(this.element.data('fancybox-width'));  
            this.height = parseInt(this.element.data('fancybox-height'));
        }
  });

  <c:if test="${activateQuickSearch == true}">
    $("#id_search").quicksearch("ul#clientListTableId li", {
        noResults: '#noresults',
        stripeRows: ['odd', 'even'],
        loader: 'span.loading'
     });
     function sendHeartbeat() { $.get("heartbeat.htm", function(result){ $('#contactTime').html(result); });}
     setInterval(function(){ sendHeartbeat(); }, 50000);
  </c:if>

  <c:if test="${numerFormat == true}">
    // $('.phone_format').mask("(999) 999-9999");
    // $('.format_currency').mask('000,000,000,000,000', {reverse: true});
    // $('.format_currency_decimal_one').mask('000,000,000,000,000.0', {reverse: true});
    // $('.format_currency_decimal').mask('000,000,000,000,000.00', {reverse: true});
    // $('.format_percent').mask('000,00%', {reverse: true});
    // $(".format_currency_decimal").currencyInput().format({format:"#,###.00", locale:"us"});
    // $(".format_currency").currencyInput().format({format:"#,###", locale:"us"});
  </c:if>

  // for the Spring Radiobuttons
  $('.radio-inline-combo>span, .checkboxer>span, .radiobuttoner>span').addClass('divver');
  $('<i></i>').prependTo('.divver>label');

    var pagerOptions = {
        container: $('.pager'),
        output: '{page:input} of {totalPages}',
        page: 0,
        size: 10,
        removeRows: false,
        cssNext: '.next',
        cssPrev: '.prev',
        cssFirst: '.first',
        cssLast: '.last',
        cssGoto: '.gotoPage',
        cssPageDisplay: '.pagedisplay',
        cssPageSize: '.pagesize',
        cssDisabled: 'disabled',
        cssErrorRow: 'tablesorter-errorRow'
    };

    $('.sortableTable').tablesorter({
        widthFixed: true,
         widgets: ['zebra']
    }).tablesorterPager(pagerOptions);


<c:if test="${addFormValidator == true}">
      $.validator.addMethod("email2", 
            function(value, element){
               return this.optional(element) || isEmail(value);
            }, 
            "Please enter a valid email address"
      );
      
      $('form').validate({
         rules: {
            name: {required: true},
            email: {email2: true},
            phone: {phoneUS: true},
            address: {maxlength: 80},
            address2: {maxlength: 80},
            city: {maxlength: 50},
            state: {maxlength: 2},
            zipcode: {maxlength: 10}
         }
      });
</c:if>

<c:if test="${activateToolTip == true}">
  $('.toolTip2').each(function()
            { $(this).qtip({
               content: {text: 'Loading...'}, 
               show: {show: 'mouseover',solo: true},
               position: {
                  corner: {target: 'bottomMiddle', tooltip: 'topMiddle'},
                  adjust: {screen: true}
               },
               api: {
                     // Retrieve the content when tooltip is first rendered
                     onRender: function() {
                     var self = this;
                     var selectedAttributes = $(self.elements.target).attr('id');
                     var valueTip = $('#showhidesrvs'+selectedAttributes).html();
                     self.updateContent(valueTip);
                  }
               }, 
               style: { border: { width: 0, radius:2 }, tip: true, name: 'dark', width: 450
            }
         });
      });
</c:if>

<c:if test="${quickBookScripts == true}">

   function validateCheckbox() {
      subscribers = document.getElementsByName('clientId'); 
      for (i=0; i<subscribers.length; i++)
         if (subscribers[i].checked) return true;
            alert("Please select client");
      return false;   
    }

   var tzOffset = new Date().getTimezoneOffset();
   $('#timezoneOffset').val(tzOffset);

   function sendHeartbeat() {
      $.get("heartbeat.htm", function(result){ $('#contactTime').html(result);});
   }

   function getPaymentAdjustmentReport(){
      var adjDateVal = $.formatDateTime('yy-mm-dd', $('#adjDate').datepicker("getDate"));
      var tgt = "paymentJournalAdjustment.xls?adjDt=" + adjDateVal;
      window.open(tgt);
   }
</c:if>


<c:if test="${moveApplications == true}">
   
   function delbtncheck(){
       var tbl = document.getElementById('searchTable');
       var rows = tbl.tBodies[0].rows.length;       
       if (rows < 1) {
         alert("Unable to search without search value(s)");         
         addRowToTable();    
         return false;
       }
       return true;     
   }

   function sortColumn(columnIndex) {
      var sortType = 'Desc';
      if (sortTypeArr[columnIndex] == 'Desc') {
         sortType = 'Asc';
      } else if (sortTypeArr[columnIndex] == 'Asc') {
         sortType = 'Desc';
      }
      location.href='moveapplications.htm?pageNo=0&sortBy=' + columnIndex + '&sortType=' + sortType;
   }
   function clickFirst(){
      document.getElementById('search').action = "searchapplications.htm?pageNo=0&sortBy=${sortBy}&sortType=${sortType}";
      document.getElementById('search').submit();
      }
   function clickNext(){
      document.getElementById('search').action = "searchapplications.htm?pageNo=${pageNo+1}&sortBy=${sortBy}&sortType=${sortType}";
      document.getElementById('search').submit();
      }

   function clickPrevious(){
      document.getElementById('search').action = "searchapplications.htm?pageNo=${pageNo-1}&sortBy=${sortBy}&sortType=${sortType}";
      document.getElementById('search').submit();
   }
   function clickLast(){
      document.getElementById('search').action = "searchapplications.htm?pageNo=${pageCount-1}&sortBy=${sortBy}&sortType=${sortType}";
      document.getElementById('search').submit();
    }

  // populate the search params of previous search, if exists 
  var criteriasArr = [];
  var modifiersArr = [];
  var valuesArr = [];
  var logic = '${logic}';
    <c:forEach var="criteria" items="${criterias}">criteriasArr.push("${criteria}");</c:forEach>
    <c:forEach var="modifier" items="${modifiers}">modifiersArr.push("${modifier}");</c:forEach>
    <c:forEach var="value" items="${values}">valuesArr.push("${value}");</c:forEach>


  // pagination sort variables
  var sortByArr = [];
  var sortTypeArr = [];

    fillInRows();
    populatePreviousSearchValues(criteriasArr, modifiersArr, valuesArr);
    if (logic == 'AND') { document.getElementById('and').checked = true; } 
    else if (logic == 'OR') { document.getElementById('or').checked = true; }

    // default sort variables initialization
   for (var i = 0; i < 12; i++) {
      sortByArr.push(i);
      if (i == '${sortBy}') {
         sortTypeArr.push('${sortType}');
         if ('${sortType}' == 'Desc') {
            $('#th_' + i).attr("class", "sortingDesc");
         } else {
            $('#th_' + i).attr("class", "sortingAsc");
         }
      } else {
         sortTypeArr.push('');
      }
   }

   function goToPage(object){
      window.location='moveapplications.htm?pageNo='+object.value+'&sortBy=${sortBy}&sortType=${sortType}';
   }

   function goToPageForSearch(object){
      document.getElementById('search').action = "searchapplications.htm?pageNo="+object.value+"&sortBy=${sortBy}&sortType=${sortType}";
      document.getElementById('search').submit();
   }
</c:if>

<c:if test="${populateJobResults == true}">
  function populateJobResults() {
    var userType = $('#userTypeHidden').val();
    $.ajax({
             type: "GET",
             url: "monitoringresult.htm?userType="+userType,
             cache: false,
             success: function(ajaxResultHtml){
              $('#monitoringResult').html(ajaxResultHtml);
              $('.sortableTable').tablesorter({
                    widthFixed: true,
                     widgets: ['zebra']
                }).tablesorterPager(pagerOptions);
             },
             error: function(){
              alert("Communication Error.");
             }
      });
  }
  window.onload=populateJobResults;
</c:if>



<c:if test="${clientApplicationScripts == true}">

  var pageModel=new Object();
  pageModel.colModel=[];
  pageModel.colNames=[];
  var gridCols=JSON.parse(document.getElementById("colModel").value);

    for(var i=0;i<gridCols.length;i++){
        pageModel.colModel[i]={};
        if(gridCols[i]!="signingStatus" || gridCols[i]!="viewEdit")
          pageModel.colModel[i].index="a."+gridCols[i];
          switch(gridCols[i]){
            case "signingStatus":
              pageModel.colNames[i]="";
              pageModel.colModel[i].width=50;
              pageModel.colModel[i].sortable=false;
              pageModel.colModel[i].search=false; 
              pageModel.colModel[i].align="center";
              break;
            case "viewEdit":
              pageModel.colNames[i]="";
              pageModel.colModel[i].width=70;
              pageModel.colModel[i].sortable=false;
              pageModel.colModel[i].search=false; 
              pageModel.colModel[i].align="center";
              break;
            case "communityName":
              pageModel.colNames[i]="Community Name"
              pageModel.colModel[i].align="center";
              pageModel.colModel[i].search=true; 
              pageModel.colModel[i].searchoptions= {sopt: ['eq','cn']};
              break;
            case "startDate":
              pageModel.colNames[i]="Start Date";
              pageModel.colModel[i].width=110;
              pageModel.colModel[i].align="center";
              pageModel.colModel[i].search=true; 
              pageModel.colModel[i].searchoptions= {sopt: ['eq','cn']};
              break;
            case "applicantType":
                pageModel.colNames[i]="Applicant Type";
                pageModel.colModel[i].align="center";
                pageModel.colModel[i].search=true; 
                pageModel.colModel[i].searchoptions= {sopt: ['eq','cn']};
                break;
            case "city":
              pageModel.colNames[i]="City";
              pageModel.colModel[i].align="center";
              pageModel.colModel[i].search=true; 
              pageModel.colModel[i].searchoptions= {sopt: ['eq','cn']};
              break;
            case "state":
              pageModel.colNames[i]="State";
              pageModel.colModel[i].width=50;
              pageModel.colModel[i].align="center";
              pageModel.colModel[i].search=true; 
              pageModel.colModel[i].searchoptions= {sopt: ['eq','cn']};
              break;
            case "numberOfUnits":
              pageModel.colNames[i]="# of Units";
              pageModel.colModel[i].width=80;
              pageModel.colModel[i].align="center";
              pageModel.colModel[i].search=true; 
              pageModel.colModel[i].searchoptions= {sopt: ['eq','cn']};
              break;
            case "signatureFullName":
              pageModel.colNames[i]="Signature Full Name";
              pageModel.colModel[i].align="center";
              pageModel.colModel[i].search=true; 
              pageModel.colModel[i].searchoptions= {sopt: ['eq','cn']};
              break;
            case "pdf":
              pageModel.colNames[i]="";
              pageModel.colModel[i].align="center";
              pageModel.colModel[i].width=50;
              pageModel.colModel[i].sortable=false;
              pageModel.colModel[i].search=false; 
              break;
          }
    }

    $("#grid_clientapplication").jqGrid({
          url:"clientapplications.json",
          datatype: "json",
          height: "100%",
          colNames:pageModel.colNames,
          colModel:pageModel.colModel,
          rownumbers: false,
          rowNum: 10,
          pager: '#pager_clientapplication', 
          sortname: 'a.startDate', 
          viewrecords: true, 
          sortorder: "desc", 
          caption:"Client Applications",
          loadComplete: function() {
              $("tr.jqgrow:even").addClass('even');
              $("tr.jqgrow:odd").addClass('odd');
              attachToolTip();
          }
    }); 



    
    $("#grid_clientapplication").jqGrid('navGrid','#pager_clientapplication',{
        edit:false,
        add:false,
        del:false,
        excel: true},
        {},
        {},
        {},
        {
          multipleSearch:true,
          groupOps: [{ op: "AND", text: "All matching"}, { op: "OR", text: "Any matching"}]
    });



  function attachToolTip() {
    $('.toolTip3').each(function() {
      
      $(this).qtip({
          content: {
            text: 'Loading...'
          },  
          show: {
            show: 'mouseover', // Only show one tooltip at a time
            solo: true // And hide all other tooltips
          },
          position: {
            corner: {
              target: 'bottomMiddle', // Position the tooltip above the link
              tooltip: 'topMiddle'
            },
            adjust: {
              screen: true // Keep the tooltip on-screen at all times
            }
          },
          api: {
            // Retrieve the content when tooltip is first rendered
            onRender: function() {
            var self = this;
            var selectedAttributes = $(self.elements.target).attr('id').split("_")[1];
            var valueTip = $('#signingserviceTip'+selectedAttributes).html();
            self.updateContent(valueTip);
          }
        },  
        style: {
          border: {
            width: 0,
            radius:2
          },
        tip: true,
        name: 'dark', // Use the default light style
        width: 450 // Set the tooltip width
        }
      });
      
    });
  }

   $("#_new").click(function(){ window.location='clientapplication.htm';});
   $("#_cancel").click(function(){ window.location='home.htm'; });

</c:if>




// EO SCRIPTS
});
</script>