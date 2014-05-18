// Filename: router.js
define([
  'jquery',
  'underscore',
  'backbone',
  
  'js/views/login/login-view.js',
], function($, _, Backbone, LoginView) {
  
  var LoginRouter = Backbone.Router.extend({
	  routes : {
		  'login':'login'
	  },
	  login: function(offset) {
    	var loginView = new LoginView();
    	loginView.render();
	  }
  });
  
  var initialize = function(){

	  var loginRouter = new LoginRouter;
	   
  };
  
  return { 
    initialize: initialize
  };
});