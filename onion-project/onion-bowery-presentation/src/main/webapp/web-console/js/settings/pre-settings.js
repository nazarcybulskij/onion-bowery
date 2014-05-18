$.ajaxSetup({
    statusCode: {
        401: function(){
        	//app.controller.navigate('login', {trigger:true});
        },
        403: function() {
        	alert('403 Access denied');
        }
    }
});


$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
	options.url = '..' + options.url;
});