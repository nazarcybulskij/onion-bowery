define([
  'jquery', 
  'underscore', 
  'backbone',
  'security',
  
  'routers/home/home-router',
  'routers/login/login-router',
  'routers/content/content-router',
], function($, _, backbone, security, homeRouter, loginRouter, contentRouter){
	
  var initialize = function(){
	presetup();  
	  
	console.log('Starting routers');
	
	homeRouter.initialize();
	loginRouter.initialize();
	contentRouter.initialize();
    
	Backbone.history.start();
  };
  
  function presetup() {
	   $.ajaxSetup({
		    statusCode: {
		        401: function(){
		        	console.info('Handling 401 error');
		        	Backbone.history.navigate('login', {trigger:true});
		        },
		        403: function() {
		        	console.info('Handling 403 error');
		        	Backbone.history.navigate('login', {trigger:true});
		        }
		    }
		});
	   
	   $.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		    if ( !options.beforeSend) {
		        options.beforeSend = function (xhr) { 
		        	 var base = security.getAuthCookie();
		        	 xhr.setRequestHeader('Authorization', base);
		        };
		    }
		});
  }
  
  return { 
    initialize: initialize
  };
});