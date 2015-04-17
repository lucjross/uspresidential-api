define(['backbone', 'view/about-view', 'view/samples-view'],
function (Backbone, AboutView, SamplesView) {
    
    var AppRouter = Backbone.Router.extend({

        initialize: function() {
            Backbone.history.start({ root: "/" });

        },

        routes:{
            '': 'home',
            'home': 'home',
            'about': 'about'
        },

        about:function () {
            new AboutView();
        }
        
    });

    return AppRouter;

});