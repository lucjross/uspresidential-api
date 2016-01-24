
var prezModule = angular.module('prezModule', ['ngRoute', 'ui.bootstrap', 'ngTouch', 'ngAnimate']);

prezModule.constant('RestApiConfig', {
    BASE_URI: "/presidents-api"
});

/*
calling order:

1. app.config()
2. app.run()
3. directive's compile functions (if they are found in the dom)
4. app.controller()
5. directive's link functions (again, if found)
*/

prezModule.config(function ($routeProvider, $httpProvider) {
    $routeProvider
            .when('/', {
                templateUrl: "home.html",
                controller: 'homeCtrlr'
            })
            .when('/login', {
                templateUrl: "login.html",
                controller: 'loginCtrlr'
            })
            .when('/register', {
                templateUrl: "register.html",
                controller: 'registrationCtrlr'
            })
            .when('/vote', {
                templateUrl: "vote.html",
                controller: 'voteCtrlr'
            })
            .otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
});

prezModule
.factory('authenticate', ['$rootScope', '$http', function ($rootScope, $http) {
    return function (credentials, callback) {

        var headers = credentials ?
                { authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)} :
                {};

        $http.get('user', {headers: headers}).then(function () { 
            // success cb
            $rootScope.authenticated = true;
            callback && callback();
        }, function () {
            // error cb
            $rootScope.authenticated = false;
            callback && callback();
        });
    };
}])
.factory('oneKey', function () {
    return function (obj) { return Object.keys(obj)[0]; };
})
.factory('oneVal', function () {
    return function (obj) { return obj[Object.keys(obj)[0]]; };
})
.factory('transformRequestAsFormPost', function () {
    return function (data, getHeaders) {
        var headers = getHeaders();
        headers["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
        return serialize(data);
    };

    function serialize(data) {
        if (! angular.isObject(data)) {
            return data == null ? "" : data.toString();
        }

        var buffer = [];
        for (var name in data) {
            if (! data.hasOwnProperty(name)) {
                continue;
            }

            var value = data[name];
            if (value) {
                buffer.push(encodeURIComponent(name) + "=" + encodeURIComponent(value));
            }
        }

        return buffer.join("&").replace(/%20/g, "+");
    };
})
.factory('isRequiredAndTouched', function () {
    return function (formEl) {
        return formEl.$touched && formEl.$error.required;
    };
})
.factory('showStartBtn', function () {

    var yearRegExp = /^[12][7890]\d{2}$/;

    /**
     * For VoteCtrlr
     * 
     * @param newVal  new votingOptions
     * @param oldVal  old votingOptions
     */
    return function (newVal, oldVal) {
        var show = false;

        if (newVal.showEventsBy !== oldVal.showEventsBy) {
            // then reset the sub-options
            newVal.byPresident = newVal.byTimePeriodFrom = newVal.byTimePeriodTo = undefined;
        }

        if (newVal.showEventsBy === 'byPresident') {
            show = newVal.byPresident && newVal.byPresident.length > 0;
        }
        else if (newVal.showEventsBy === 'byTimePeriod') {
            // the fields must be either empty or correct
            show = (function () {
                var from = null, to = null;
                if (newVal.byTimePeriodFrom) {
                    from = newVal.byTimePeriodFrom.match(yearRegExp);
                    if (! from) return false;
                }

                if (newVal.byTimePeriodTo) {
                    to = newVal.byTimePeriodTo.match(yearRegExp);
                    if (! to) return false;
                }

                if (from && to && parseInt(from, 10) >= parseInt(to, 10)) {
                    return false;
                }
                
                return true;
            })();
        }
        else if (newVal.showEventsBy === 'all') {
            show = true;
        }

        return show;
    };
});

prezModule
.directive('nxEqual', function() {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function (scope, elem, attrs, model) {
            if (! attrs.nxEqual) {
                console.error('nxEqual expects a model as an argument!');
                return;
            }

            scope.$watch(attrs.nxEqual, function (value) {
                // Only compare values if the second ctrl has a value.
                if (model.$viewValue !== undefined && model.$viewValue !== '') {
                    model.$setValidity('nxEqual', value === model.$viewValue);
                }
            });

            model.$parsers.push(function (value) {
                // Mute the nxEqual error if the second ctrl is empty.
                if (value === undefined || value === '') {
                    model.$setValidity('nxEqual', true);
                    return value;
                }
                var isValid = value === scope.$eval(attrs.nxEqual);
                model.$setValidity('nxEqual', isValid);
                return isValid ? value : undefined;
            });
        }
    };
})
.directive('nxUsername', function () {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function (scope, elem, attrs, model) {
            
            scope.unavailableUsernames = [];
            scope.$watch('unavailableUsernames', function (newUsernames) {
                var available = newUsernames.indexOf(model.$modelValue) === -1;
                model.$setValidity('nxUsername', (! model.$modelValue) || available);
            }, true /* use angular.equals, not !==, since it's an array */);

            model.$parsers.push(function (val) {
                var available = scope.unavailableUsernames.indexOf(val) === -1;
                var pass = (! val) || available;
                model.$setValidity('nxUsername', pass);
                return pass ? val : undefined;
            });
        }
    };
});

prezModule
.controller('homeCtrlr', function ($scope, $http) {
    $http.get('/homePage/').then(function (response) {
        $scope.user = response.data.user;
    }, null);
})
.controller('navigationCtrlr',
        ['authenticate', '$rootScope', '$scope', '$http', '$location',
        function (authenticate, $rootScope, $scope, $http, $location) {

    authenticate();

    $scope.logout = function () {
        $http.post('logout', {})
        .finally(function () {
            $rootScope.authenticated = false;
            $rootScope.registeredAs = undefined;
            $location.path("/");
        });
    };

}])
.controller('loginCtrlr',
        ['authenticate', '$rootScope', '$scope', '$http', '$location',
        function (authenticate, $rootScope, $scope, $http, $location) {

    $http.post('logout', {}).finally(function () {
        $rootScope.authenticated = false;
    });

    $scope.registeredAs = $rootScope.registeredAs;

    $scope.credentials = { username: $scope.registeredAs };
    $scope.login = function () {
        authenticate($scope.credentials, function () {
            if ($rootScope.authenticated) {
                $location.path("/");
                $scope.error = false;
            }
            else {
                $location.path("/login");
                $scope.error = true;
            }
        });
    };
}])
.controller('registrationCtrlr', [
        'oneKey', 'oneVal', 'transformRequestAsFormPost', 'isRequiredAndTouched',
        '$rootScope', '$scope', '$http', '$location', '$anchorScroll',
        function (oneKey, oneVal, transformRequestAsFormPost, isRequiredAndTouched,
                $rootScope, $scope, $http, $location, $anchorScroll) {

    $http.post('logout', {}).finally(function () {
        $rootScope.authenticated = false;
    });

    $rootScope.registeredAs = undefined;

    $scope.oneKey = oneKey;
    $scope.oneVal = oneVal;
    $scope.isRequiredAndTouched = isRequiredAndTouched;
    $scope.unavailableUsernames = [];

    $scope.register = function () {

        var firstErrorId = null;
        if ($scope.form.$invalid) {
            angular.forEach($scope.form.$error, function (fields) {
                angular.forEach(fields, function (field) {
                    field.$setTouched();

                    // depends on both "id" and "name" having the same value on the element
                    firstErrorId = firstErrorId || field.$name;
                });
            });

            $anchorScroll(firstErrorId);

            return;
        }

        $http({
            url: '/public-api/register',
            method: 'POST',
            transformRequest: transformRequestAsFormPost,
            data: $scope.user
        }).then(function () {
            $rootScope.registeredAs = $scope.user.username;

            $location.path('/login');
        }, function (error) {
            if (error.status === 409 /* CONFLICT (username exists) */) {
                $scope.unavailableUsernames.push($scope.form.username.$modelValue);
                $anchorScroll('username');
            }
        });
    }

    $scope.master = {};
    $scope.reset = function() {
        $scope.user = angular.copy($scope.master);
    };

    $scope.reset();

    $scope.user = {};
    $http.get('/public-api/userDetailLabels', { cache: true }).then(function (response) {
        $scope.userDetailLabels = response.data;
    });
}])
.controller('voteCtrlr', [
        '$scope', '$rootScope', '$location', '$http', 'RestApiConfig', 'showStartBtn',
        function ($scope, $rootScope, $location, $http, RestApiConfig, showStartBtn) {

    if (! $rootScope.authenticated) {
        $location.path("/login");
        return;
    }

    $scope.votingOptions = {};

    $scope.showEventsByFuncs = {};
    $scope.showEventsByFuncs.byPresident = function () {
        $http.get(RestApiConfig.BASE_URI + '/president/list', { cache: true }).then(function (resp) {
            $scope.presidents = resp.data;
        });
    };
    $scope.showEventsByFuncs.byTimePeriod = function () {};
    $scope.showEventsByFuncs.all = function () {} // ?

    $scope.today = function () { return new Date(); };

    var yearRegExp = /^[12][7890]\d{2}$/;
    $scope.$watch(function () {
        return $scope.votingOptions;
    }, function (newVal, oldVal) {
        $scope.showStartBtn = showStartBtn(newVal, oldVal);
        $scope.enableCarousel = false;
    }, true);

    var slides = $scope.slides = [];
    var currIndex = 0;
    $scope.addSlide = function() {
        var newWidth = 600 + slides.length + 1;
        slides.push({
            image: '//lorempixel.com/' + newWidth + '/300',
            text: ['Nice image','Awesome photograph','That is so cool','I love that'][slides.length % 4],
            id: currIndex++
        });
    };

    for (var i = 0; i < 4; i++) {
        $scope.addSlide();
    }
}]);

prezModule.run([
        '$anchorScroll', '$rootScope', '$location', '$templateCache',
        function ($anchorScroll, $rootScope, $location, $templateCache) {

    $anchorScroll.yOffset = 50;

    $rootScope.$watch(function () {
        return $location.path();
    }, function (newVal) {
        $rootScope.activePath = newVal;
    });

    // $templateCache.put("uib/template/carousel/carousel.html", "<div></div>");
    // $templateCache.put("uib/template/carousel/slide.html", "<div></div>");
}]);











