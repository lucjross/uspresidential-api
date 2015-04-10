// Set the require.js configuration for your application.
require.config({

    shim: {
        'underscore': {
            exports: '_'
        },
        'backbone': {
            deps: [
                'underscore',
                'jquery'
            ],
            exports: 'Backbone'
        },
        'bootstrap': {
            deps: [
                'jquery'
            ]
        }
    },

    paths: {
        jquery: 'lib/jquery.min',
        underscore: 'lib/underscore-min',
        backbone: 'lib/backbone-min',
        text: 'lib/text',
        i18next: 'lib/i18next.min',
        'bootstrap': 'lib/bootstrap',
        template: '../template',
        mustache: 'lib/mustache.min',
        stache: 'lib/stache'
    }
});

// Load our app module and pass it to our definition function
require(['app']);
