define([
  'jquery',
  'underscore',
  'backbone',
  'security',
  
  'text!templates/login/login-template.html'
], function($, _, Backbone, security, homeView){ 
  var LoginView = Backbone.View.extend({
    el: $("body"),

    events: {
	      'click #signinBtn': 'signin'
	},
    
    render: function(){
        var data = {};
        var compiledTemplate = _.template(homeView, data);
        
        this.$el.children().remove();
        this.$el.append(compiledTemplate);
    },
    signin: function() {
    	var login = $('#authLogin').val();
    	var password = $('#authPassword').val();
    	
    	this.setAuthorizationHeader(login, password);
    	this.checkAuthorization();
    },
    setAuthorizationHeader: function(login, password) {
    	var token = login + ':' + password;
    	var hash = 'Basic ' + btoa(token);
    	document.cookie = 'Authorization=' + hash;
    },
    checkAuthorization: function() {
    	var user = security.getUser();

    	if(user.isValid) {
        	Backbone.history.navigate('home', {trigger:true});
        } else {
        	Backbone.history.navigate('login', {trigger:true});
        }
    	
    }
  });
  
  return LoginView;
});