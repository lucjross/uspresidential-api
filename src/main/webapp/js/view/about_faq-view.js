define(['backbone', 'stache!template/about_faq'],
function (Backbone, Template) {
    
    var About_FAQView = Backbone.View.extend({

        el: '#main',
        
        initialize:function () {
            // Render the view
            this.render();
        },

        render: function () {
            this.$el.html(Template({
                something: 'SOMETHING'
            }));
        }

    });
    return About_FAQView;
});