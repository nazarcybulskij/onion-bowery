define([
  'jquery',
  'spin'
], function($, Spinner){
	var opts = {
			  lines: 17, // The number of lines to draw
			  length: 13, // The length of each line
			  width: 5, // The line thickness
			  radius: 30, // The radius of the inner circle
			  corners: 1, // Corner roundness (0..1)
			  rotate: 22, // The rotation offset
			  direction: 1, // 1: clockwise, -1: counterclockwise
			  color: '#000', // #rgb or #rrggbb or array of colors
			  speed: 1.4, // Rounds per second
			  trail: 85, // Afterglow percentage
			  shadow: false, // Whether to render a shadow
			  hwaccel: false, // Whether to use hardware acceleration
			  className: 'spinner', // The CSS class to assign to the spinner
			  zIndex: 2e9, // The z-index (defaults to 2000000000)
			  top: 'auto', // Top position relative to parent in px
			  left: 'auto' // Left position relative to parent in px
			};

	var progressBar = function(element) {
		
		var target = document.getElementById(element);
		if(target !== null) {
			target.innerHTML = "<div class=\"modal-body\" ><div style=\"height:400px\"><span id=\"searching_spinner_center\" style=\"position: absolute;display: block;top: 50%;left: 50%;\"></span></div></div>";
			target = document.getElementById('searching_spinner_center');
			var spinner = new Spinner(opts).spin(target);
			
		}
	};
	
	return {
		progressBar:progressBar
	};
});

