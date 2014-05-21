require.config({
  paths: {
    jquery: 'libs/jquery/jquery-min',
    jqueryui: 'libs/jquery/jquery-ui-min',
    jqueryform: 'libs/jquery/jquery-form',
    jqueryvalidate: 'libs/jquery/jquery-validate-min',
    jsTree: 'libs/jstree/jstree',
    
    underscore: 'libs/underscore/underscore-min',
    
    backbone: 'libs/backbone/backbone-min',
    backboneroutefilter: 'libs/backbone/backbone-routefilter',

    spin: 'libs/spin/spin-min',
    
    templates: '../templates',

    settings: 'settings/pre-settings',
    security: 'settings/security',
    urls: 'settings/urls',

    ui: 'ui/ui',
    async: 'libs/require/async'
  }

});

require([
  'app'

], function(App){
  App.initialize();
});