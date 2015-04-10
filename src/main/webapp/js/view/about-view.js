define([ 'backbone', 'stache!template/about'],
function (Backbone, template) {
    
    var AboutView = Backbone.View.extend({

        el: '#main',
        
        initialize:function () {
            // Render the view
            this.render();
        },

        render: function () {
            this.$el.html(template({
                something: 'SOMETHING'
            }));
        }

    });
    return AboutView;
});