define(['router/app-router'], function(AppRouter) {
    new AppRouter;

    $(document).ready(function () {
        $('.nav a').on('click', function () {
            $('.nav').find('.active').removeClass('active');
            $(this).parent().addClass('active');
        });
    });
});