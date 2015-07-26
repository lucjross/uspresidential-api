define(['backbone'],
function (Backbone) {

    var HomeView = Backbone.View.extend({

        el: '#main',

        initialize:function () {
            // Render the view
            this.render();
        },

        render: function () {
            this.$el.html('<h1>Welcome</h1>');
        }

    });
    return HomeView;
});