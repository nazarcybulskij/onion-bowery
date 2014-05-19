define([
  'jquery',
  'underscore',
  'backbone',
  
  'text!templates/content/content-multiple-view.html'
], function($, _, Backbone, homeView){ 
  var HomeView = Backbone.View.extend({
    el: $("body"),

    render: function(){
    	console.info("Content page rendering");
        var data = {};
               
        var compiledTemplate = _.template(homeView, data);
        
        this.$el.children().remove();
        this.$el.append(compiledTemplate);
    }
  });
  
  return HomeView;
});