// Filename: router.js
define([
  'jquery',
  'underscore',
  'backbone',
  
  'js/views/content/content-view.js'
], function($, _, Backbone, View) {
  
  var Router = Backbone.Router.extend({
	  routes : {
			'content':'content'
		},
	    content: function() {
	    	console.info("Content router: content");
	    	var view = new View();
	    	view.render();
	    }
  });
  
  var initialize = function(){
	  new Router();
  };
  
  return { 
    initialize: initialize
  };
});