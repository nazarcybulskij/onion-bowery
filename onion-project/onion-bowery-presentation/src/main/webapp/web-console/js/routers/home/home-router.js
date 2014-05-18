// Filename: router.js
define([
  'jquery',
  'underscore',
  'backbone',
  
  'js/views/home/home-view.js'
], function($, _, Backbone, HomeView) {
  
  var HomeRouter = Backbone.Router.extend({
	  routes : {
			'':'init',
			'home':'init'
		},
	    init: function() {
	    	var homeView = new HomeView();
	    	homeView.render();
	    }
  });
  
  var initialize = function(){
	
	  var home_router = new HomeRouter;

  };
  
  return { 
    initialize: initialize
  };
});