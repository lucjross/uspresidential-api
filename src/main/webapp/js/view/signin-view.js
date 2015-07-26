define(['backbone', 'stache!template/signin'],
function (Backbone, Template) {

    var SigninModalView = Backbone.View.extend({

        id: 'base-modal',
        className: 'modal fade',

        events: {
            'hidden.bs.modal': 'teardown'
        },

        initialize: function () {
            this.render();
        },

        render: function () {
            this.$el.html(Template());
            this.$el.modal('show');
            return this;
        },

        teardown: function() {
            this.$el.data('modal', null);
            this.remove();
        }

    });
    return SigninModalView;
});