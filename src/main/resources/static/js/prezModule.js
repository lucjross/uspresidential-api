
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
})
.factory('eventDatesText', function () {
    return function (eventStart, eventEnd) {
        var startYear = eventStart && eventStart.substr(0, eventStart.indexOf('-'));
        var endYear = eventEnd && eventEnd.substr(0, eventEnd.indexOf('-'));
        if (startYear && endYear) {
            if (startYear === endYear) {
                return "In " + startYear;
            }
            else {
                return "Between " + startYear + " and " + endYear;
            }
        }
        else if (startYear) {
            return "After " + startYear;
        }
        else if (endYear) {
            return "By " + endYear;
        }
        else {
            return "During the President's term of office"
        }
    }
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
.controller('homeCtrlr', ['$scope', '$http', function ($scope, $http) {
    $http.get('/home-auth').then(function (response) {
        $scope.user = response.data.user;
    }, null);
}])
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

    $http.post('/logout', {}).finally(function () {
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

    $http.post('/logout', {}).finally(function () {
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

        // omit "passwordConfirm"
        var data = angular.copy($scope.user);
        data.passwordConfirm = undefined;

        $http({
            url: '/public-api/register',
            method: 'POST',
            headers: {
                'Content-Type': "application/x-www-form-urlencoded; charset=utf-8"
            },
            transformRequest: transformRequestAsFormPost,
            data: data
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
    $http.get('/public-api/userDetailLabels', { cache: true })
    .then(function (resp) {
        $scope.userDetailLabels = resp.data;
    }, function (error) {
        console.error(error);
    });
}])
.controller('voteCtrlr', [
        '$scope', '$rootScope', '$location', '$http', 'RestApiConfig', 'showStartBtn', 'eventDatesText',
        function ($scope, $rootScope, $location, $http, RestApiConfig, showStartBtn, eventDatesText) {

    if (! $rootScope.authenticated) {
        $location.path("/login");
        return;
    }

    $scope.eventDatesText = eventDatesText;

    $scope.votingOptions = {};
    $scope.eventsAndVotes = []; // todo - should be an array of maps:

    var showEventsByOptionFuncs = { // todo - not sure this is needed anymore
        byPresident: function () {},
        byTimePeriod: function () {},
        all: function () {}
    }
    $scope.showEventsByOptions = function () {
        showEventsByOptionFuncs[$scope.votingOptions.showEventsBy]();
    };

    $scope.getEventsFuncs = {
        byPresident: function () {
            var params = {
                presidentId: $scope.votingOptions.byPresident,
                getAlreadyVoted: $scope.votingOptions.showAlreadyVoted,
                offset: 0 // todo - should equal the number of events voted on in the last batch.
                // if 10 events were obtained last time and 6 were voted on, only 6 more need to be obtained
                // in order to maintain an in-client collection size of 10 events.
            };
            return $http.get(RestApiConfig.BASE_URI + '/event/by-president', { params: params });
        },
        byTimePeriod: function () {
            var params = {
                startDate: $scope.byTimePeriodFrom,
                endDate: $scope.byTimePeriodTo,
                getAlreadyVoted: $scope.votingOptions.showAlreadyVoted,
                offset: 0 // todo
            };
            return $http.get(RestApiConfig.BASE_URI + '/event/for-period', { params: params });
        },
        all: function () {} // todo
    }
    $scope.start = function () {

        angular.forEach($scope.eventsAndVotes, function (eventAndVote, key) {
            eventAndVote.deregisterWatch && eventAndVote.deregisterWatch();
        });
        $scope.eventsAndVotes = [];

        var promise = $scope.getEventsFuncs[$scope.votingOptions.showEventsBy]();
        promise.then(function (resp) {
            angular.forEach(resp.data, function (eventAndVote, key) {
                eventAndVote.vote = eventAndVote.vote || {};

                eventAndVote.deregisterWatch = $scope.$watch(function () {
                    return eventAndVote.vote;
                }, function (newVal, oldVal) {
                    if (newVal.response && newVal.voteWeight && !angular.equals(newVal, oldVal)) {

                        var voteData = angular.copy(newVal);
                        voteData.event_id = eventAndVote.event.id;

                        eventAndVote.posting = true;

                        if (!oldVal.response || !oldVal.voteWeight) {
                            // insert

                            $http.post(RestApiConfig.BASE_URI + '/vote/submit', voteData)
                            .then(function () {
                                eventAndVote.voteSubmitted = true;
                                eventAndVote.posting = false;
                            }, httpErrorFn);
                        }
                        else {
                            // update

                            $http.post(RestApiConfig.BASE_URI + '/vote/update', voteData)
                            .then(function () {
                                eventAndVote.voteUpdated = true;
                                eventAndVote.posting = false;
                            }, httpErrorFn);
                        }
                    }

                }, true);
            });
            $scope.eventsAndVotes = resp.data;

            $scope.showStartBtn = false;
            $scope.showEvents = true;
        }, function (error) {
            console.error(error);
        });
    }

    $scope.today = function () { return new Date(); };

    var yearRegExp = /^[12][7890]\d{2}$/;
    $scope.$watch(function () {
        return $scope.votingOptions;
    }, function (newVal, oldVal) {
        $scope.showStartBtn = showStartBtn(newVal, oldVal);
        $scope.showEvents = false;
        // $scope.enableCarousel = false;
    }, true);


    getAndAssignToScope(RestApiConfig.BASE_URI + '/president/all-by-id', 'presidentsById');
    getAndAssignToScope('/public-api/voteResponseLabels', 'voteResponseLabels');
    getAndAssignToScope('/public-api/eventImportanceLabels', 'eventImportanceLabels');
    getAndAssignToScope('/public-api/eventCategoryLabels', 'eventCategoryLabels');

    function getAndAssignToScope(uri, property) {
        $http.get(uri, { cache: true })
        .then(function (resp) {
            $scope[property] = resp.data;
        }, function (error) {
            console.error(error);
        });
    }

    function httpErrorFn(error) {
        console.error(error);
    }
}]);

prezModule.run([
        '$anchorScroll', '$rootScope', '$location', '$http', '$templateCache',
        function ($anchorScroll, $rootScope, $location, $http, $templateCache) {

    $anchorScroll.yOffset = 50;

    $rootScope.$watch(function () {
        return $location.path();
    }, function (newVal) {
        $rootScope.activePath = newVal;
    });

    $rootScope.$on('$routeChangeSuccess', function () {
        $rootScope.navCollapsed = true;
    });

    $http.get('/template/rating.html').then(function (resp) {
        $templateCache.put('uib/template/rating/rating.html', resp.data);
    });
}]);











