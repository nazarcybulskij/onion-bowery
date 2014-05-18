define([
  'jquery',
  'underscore',
  'backbone',
  'jsTree',
  
  'text!templates/content/content-multiple-view.html'
], function($, _, Backbone, jstree, homeView){ 
  var HomeView = Backbone.View.extend({
    el: $("body"),

    render: function(){
    	console.info("Content page rendering");
        var data = {};
               
        var compiledTemplate = _.template(homeView, data);
        
        this.$el.children().remove();
        this.$el.append(compiledTemplate);
        
        $(document).ready(function(){
			$("#jstreeselector").jstree({
					"json_data" : {
						"data" : [ {
							"data" : "Search engines"
						}, {
							"data" : "Networking sites"
						} ]
					},
					"plugins" : []
			});
            
        });
    }
  });
  
  return HomeView;
});