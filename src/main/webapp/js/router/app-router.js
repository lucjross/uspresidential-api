define([
    'backbone',
    'view/home-view',
    'view/about_faq-view',
    'view/samples-view',
    'view/signin-view'
], function (Backbone, HomeView, About_FAQView, SamplesView, SigninView) {
    
    var AppRouter = Backbone.Router.extend({

        initialize: function() {
            Backbone.history.start({ root: "/" });
        },

        routes: {
            '': 'home',
            'home': 'home',
            'about/faq': 'about_faq',
            'signin': 'signin'
        },

        home: function () {
            new HomeView();
        },

        about_faq: function () {
            new About_FAQView();
        },

        signin: function () {
            new SigninView();
        }
        
    });

    return AppRouter;

});