	function getServices(pkge, targetDiv) {
		var indexId = targetDiv.substring(11);
		$.ajax({ 
	  	type: "GET", 
	  	url: "getServicesOfPkg.htm?indexId="+indexId,
	  	data: "packageId=" + pkge.value,
	  	cache: false,
	  	success: function(ajaxHtml){ 
			$(targetDiv).hide();
			
			$('#alacarteDiv').hide();
			var pkgDiv = $(ajaxHtml).find('#selPkgDivId');

			$(targetDiv).html($(pkgDiv).html());
			var tabDiv = $(ajaxHtml).find('#tableDivId');
	    	$('#alacarteDiv').html($(tabDiv).html());

	    	$(targetDiv).slideDown("slow");
	    	$('#alacarteDiv').slideDown("slow");
	  	},
	  	error: function() {
		  alert("Communication Error.");
	  	}
		});
	}

	function noStep2Package(pkge, targetDiv) {
		var indexId = targetDiv.substring(11);
		$.ajax({ 
		  	type: "GET", 
		  	url: "getServicesOfPkg.htm?indexId="+indexId, 
		  	data: "packageId=" + pkge.value,
		  	cache: false,
		  	success: function(ajaxHtml){ 
				$(targetDiv).hide();
				$('#alacarteDiv').hide();
				var pkgDiv = $(ajaxHtml).find('#selPkgDivId');
		    	$(targetDiv).html($(pkgDiv).html());
				var tabDiv = $(ajaxHtml).find('#tableDivId');
		    	$('#alacarteDiv').html($(tabDiv).html());
		    	$(targetDiv).slideDown("slow");
		    	$('#alacarteDiv').slideDown("slow");
		    	$(targetDiv).slideUp("slow");
				$(targetDiv).html(""); 
				$(targetDiv).hide();
		  	},
		  	error: function() {
			  alert("Communication Error.");
		  	}
			});
		
	}	
	function noPackage(targetDiv) {
		$(targetDiv).slideUp("slow");
		$(targetDiv).html(""); 
		$(targetDiv).hide();
	}	
	
	function disableAllInputs(els){
		for(i=0; i<els.length; i++){
			//els[i].disabled = true;
			switch(els[i].type){
				case "select-one" :
					els[i].disabled = true;
					break;
				case "text":
					els[i].disabled = true;
					break;
				case "textarea":
					els[i].disabled = true;
					break;
				case "checkbox":
					els[i].disabled = true;
					break;
				case "radio":
					els[i].disabled = true;
					break;					
			}
		}
	}
	
	
	
	
	/**** services load within tool tip***/
	$(function() {
		$('.toolTip1').each(function()
				{ $(this).qtip({
					content: {
						// Set the text to an image HTML string with the correct src URL to the loading image you want to use
						text: 'Loading...',
						title: {
							text: $(this).attr('lang') // Give the tooltip a title using each elements text
						}
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
					show: {
						show: 'mouseover', // Only show one tooltip at a time
						solo: true // And hide all other tooltips
					},
					api: {
						// Retrieve the content when tooltip is first rendered
						onRender: function() {
						var self = this;
						var selectedAttributes = $(self.elements.target).attr('id');
						$.ajax({
							type: "GET",
							url: "getServicesOfPkgForTooltip.htm?packageId=" + selectedAttributes,
							cache: false,
							success: function(html){
							self.updateContent(html);
							},
							error: function() {
								brightleafAjaxError();
							}
						});
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
	});