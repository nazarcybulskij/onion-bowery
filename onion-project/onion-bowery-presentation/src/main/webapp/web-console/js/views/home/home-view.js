define([
  'jquery',
  'underscore',
  'backbone',
  'security',  
  'text!templates/home/home.html'
], function($, _, Backbone, security, homeView){ 
  var HomeView = Backbone.View.extend({
    el: $("body"),

    render: function(){
    	console.info("Home View rendering");
        var data = {};
               
        var compiledTemplate = _.template(homeView, data);
        
        this.$el.children().remove();
        this.$el.append(compiledTemplate);
    }
  });
  
  return HomeView;
});