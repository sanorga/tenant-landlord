
//Create the tooltips only on document load
$(document).ready(function() 
{
  // By suppling no content attribute, the library uses each elements title attribute by default
  $('.toolTipTitle, #saveapp, #toolTipTitle').qtip({
     content: {
        text: false // Use each elements title attribute
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
		style: {
			border: {
				width: 0,
				radius:2
			},
			tip: true,
			name: 'dark', // Use the default light style
			width: 300 // Set the tooltip width
		}
  });
  
  // NOTE: You can even omit all options and simply replace the regular title tooltips like so:
  // $('#content a[href]').qtip();
});