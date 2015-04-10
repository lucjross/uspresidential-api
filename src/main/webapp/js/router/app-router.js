define(['backbone', 'view/about-view', 'view/samples-view'],
function (Backbone, AboutView, SamplesView) {
    
    var AppRouter = Backbone.Router.extend({

        initialize: function() {
            Backbone.history.start({ root: "/" });
            console.log('initialized ', this);
        },

        routes:{
            '': 'home',
            'home': 'home',
            'about': 'about'
        },

        home:function () {
            new SamplesView({ root: $('#main') });
        },
        about:function () {
            console.log('about');
            new AboutView();
        }
        
    });

    return AppRouter;

});