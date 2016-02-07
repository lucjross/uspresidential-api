
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
            .when('/events', {
                templateUrl: "vote.html",
                controller: 'voteCtrlr'
            })
            .when('/about', {
                templateUrl: "about.html",
                controller: 'aboutCtrlr'
            })
            .otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
});

prezModule
.factory('Page', function() {
    var titleSuffix = " — Presidential Ranker";
    var title = 'default';
    return {
        title: function () { return title; },
        setTitle: function (newTitle) { title = newTitle + titleSuffix; }
    };
})
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
})
.directive('nxEventStats', function () {
    return {
        restrict: 'A',
        scope: true, // new child scope that prototypally inherits from the parent scope
        link: function (scope) {

            /*
             * statsByEventId is inherited by the ng-repeat for eventsAndVotes, which is on the controller scope;
             * eventAndVote.event.id is on the scope of the ng-repeat for eventsAndVotes;
             * scope.stats will be on this directive's scope.
             */

            function updateStats(newStats) {
                scope.stats = newStats;
            }

            updateStats(scope.statsByEventId[scope.eventAndVote.event.id]);

            scope.$watch(function () {
                return scope.statsByEventId[scope.eventAndVote.event.id];
            }, updateStats);
        }
    };
});

prezModule
.filter('ordinal', function () {
    return function (num) {
        if (num >= 4 && num <= 20)
            return num + 'th';

        var mod = num % 10;
        if (mod == 0 || mod >= 4)
            return num + 'th';
        else if (mod == 1)
            return num + 'st';
        else if (mod == 2)
            return num + 'nd';
        else
            return num + 'rd';
    }; 
})
.filter('percentage', function () {
    return function (fraction) {
        return Math.round(fraction * 100);
    }
});

prezModule
.controller('pageCtrlr', ['$scope', 'Page', function ($scope, Page) {
    $scope.Page = Page;
}])
.controller('homeCtrlr', ['$scope', '$http', 'Page', function ($scope, $http, Page) {
    Page.setTitle("Home");
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
        ['authenticate', '$rootScope', '$scope', '$http', '$location', 'Page',
        function (authenticate, $rootScope, $scope, $http, $location, Page) {

    Page.setTitle("Login");

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
        '$rootScope', '$scope', '$http', '$location', '$anchorScroll', 'Page',
        function (oneKey, oneVal, transformRequestAsFormPost, isRequiredAndTouched,
                $rootScope, $scope, $http, $location, $anchorScroll, Page) {

    Page.setTitle("Register");

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
        '$scope', '$rootScope', '$location', '$http', '$q', 'RestApiConfig', 'showStartBtn', 'eventDatesText', 'Page',
        function ($scope, $rootScope, $location, $http, $q, RestApiConfig, showStartBtn, eventDatesText, Page) {

    if (! $rootScope.authenticated) {
        $location.path("/login");
        return;
    }

    Page.setTitle("Events");

    var votingOptionsPromises = [getAndAssignToScope(RestApiConfig.BASE_URI + '/president/all-by-id', 'presidentsById')];
    preLoad(votingOptionsPromises, function () {
        $scope.votingOptionsDataLoaded = true;
    })();

    var startPromises = [
            getAndAssignToScope('/public-api/voteResponseLabels', 'voteResponseLabels'),
            getAndAssignToScope('/public-api/eventImportanceLabels', 'eventImportanceLabels'),
            getAndAssignToScope('/public-api/eventCategoryLabels', 'eventCategoryLabels')];
    preLoad(startPromises, function () {
        $scope.startDataLoaded = true;
    })();

    $scope.eventDatesText = eventDatesText;
    $scope.today = function () { return new Date(); };
    $scope.votingOptions = {
        showAlreadyVoted: true
    };
    $scope.eventsAndVotes = [];
    $scope.modes = {
        vote: { active: true },
        stats: { active: false }
    }; // for the vote/stats tabset

    $scope.getEventsFuncs = {
        byPresident: function (offset) {
            var params = {
                presidentId: $scope.votingOptions.byPresident,
                getAlreadyVoted: $scope.votingOptions.showAlreadyVoted,
                offset: offset
            };
            return $http.get(RestApiConfig.BASE_URI + '/event/by-president', { params: params });
        },
        byTimePeriod: function (offset) {
            var params = {
                startYear: $scope.votingOptions.byTimePeriodFrom,
                endYear: $scope.votingOptions.byTimePeriodTo,
                getAlreadyVoted: $scope.votingOptions.showAlreadyVoted,
                offset: offset
            };
            return $http.get(RestApiConfig.BASE_URI + '/event/for-period', { params: params });
        },
        all: function (offset) {} // todo
    };

    $scope.start = function (concat, offset) {

        $scope.lastSlideWatchDeregister && $scope.lastSlideWatchDeregister();

        if (!concat) {
            // then the votingOptions were just changed and the carousel should get new content

            angular.forEach($scope.eventsAndVotes, function (eventAndVote, key) {
                eventAndVote.watchDeregister && eventAndVote.watchDeregister();
            });
            $scope.eventsAndVotes = [];
            $scope.statsByEventId = {}; // a map of eventIds to statistic objects
            $scope.modes.vote.active = true;
        }

        var mergedEAVs;
        var promise = $scope.getEventsFuncs[$scope.votingOptions.showEventsBy](offset || 0);
        promise.then(function (resp) {

            if (resp.data.length === 0) {
                // todo - handle "no more events"
            }

            var newEventsAndVotes = resp.data;
            angular.forEach(newEventsAndVotes, function (eventAndVote, key) {
                eventAndVote.vote = eventAndVote.vote || {};
                eventAndVote.voteSubmitted = !!eventAndVote.vote.created;

                eventAndVote.watchDeregister = $scope.$watch(function () {
                    return eventAndVote.vote;
                }, function (newVal, oldVal) {
                    if (newVal.response && newVal.voteWeight && !angular.equals(newVal, oldVal)) {

                        var voteData = angular.copy(newVal);
                        voteData.event_id = eventAndVote.event.id;

                        eventAndVote.posting = true;

                        var promise;
                        if (!oldVal.response || !oldVal.voteWeight) {
                            // insert

                            promise = $http.post(RestApiConfig.BASE_URI + '/vote/submit', voteData)
                            .then(function () {
                                eventAndVote.voteSubmitted = true;
                                return $q.resolve();
                            }, httpErrorFn);
                        }
                        else {
                            // update

                            promise = $http.post(RestApiConfig.BASE_URI + '/vote/update', voteData)
                            .then(function () {
                                eventAndVote.voteUpdated = true;
                                return $q.resolve();
                            }, httpErrorFn);
                        }

                        // get the updated stats for this event
                        promise && promise.then(function () {
                            eventAndVote.posting = false;
                            return $http.get(RestApiConfig.BASE_URI + '/stats/by-events',
                                    { params: { eventIds: [eventAndVote.event.id] } });
                        })
                        .then(function (resp) {
                            $scope.statsByEventId[eventAndVote.event.id] = resp.data[eventAndVote.event.id];
                        }, httpErrorFn);
                    }

                }, true);
            });
            mergedEAVs = $scope.eventsAndVotes.concat(newEventsAndVotes);

            var lastEventAndVote = mergedEAVs[mergedEAVs.length - 1];
            $scope.lastSlideWatchDeregister = $scope.$watch(function () {
                return lastEventAndVote.active;
            }, function (newVal, oldVal) {
                if (newVal && ! oldVal) {
                    /*
                     * i get 10 more events when the user lands on the last event slide.
                     * if i am showing events already voted on, i simply get more events
                     * starting at the row number equal to the current events array length,
                     * since in that case a limitless result set wouldn't change from call to call.
                     *
                     * if i am showing only events not voted on, i get more events
                     * starting at the row number equal to how many events are not voted on.
                     * (if i have `n` events and `m` are voted on, the next unvoted event
                     * that has not already been obtained will be at offset `n - m`.)
                     */
                    var offset =
                            $scope.votingOptions.showAlreadyVoted ?
                            mergedEAVs.length :
                            mergedEAVs.filter(function (eventAndVote) {
                                return !eventAndVote.voteSubmitted;
                            }).length;

                    $scope.start(true, offset); // recursive
                }
            });

            $scope.showStartBtn = false;
            $scope.showEvents = true;

            // now get the stats for the new events and chain the promise
            var eventIds = [];
            angular.forEach(newEventsAndVotes, function (eventAndVote) {
                eventIds.push(eventAndVote.event.id);
            });

            return $http.get(RestApiConfig.BASE_URI + '/stats/by-events',
                    { params: { eventIds: eventIds } });

        }, httpErrorFn) // -- end "got events" setup
        .then(function (resp) {

            angular.merge($scope.statsByEventId, resp.data);

            // now that the stats are available, set eventsAndVotes.
            // that way statsByEventId isn't accessed by event id before any event ids are available
            $scope.eventsAndVotes = mergedEAVs;

        }, httpErrorFn); // -- end "got stats" setup
    };

    $scope.$watch(function () {
        return $scope.votingOptions;
    }, function (newVal, oldVal) {
        $scope.showStartBtn = showStartBtn(newVal, oldVal);
        $scope.showEvents = false;
    }, true);



    function preLoad(promises, success) {
        return function () {
            if (promises.length > 0) {
                promises.shift().then(preLoad(promises, success));
            }
            else {
                success();
            }
        };
    }

    function getAndAssignToScope(uri, property) {
        return $http.get(uri, { cache: true })
        .then(function (resp) {
            $scope[property] = resp.data;
            return $q.resolve();
        }, httpErrorFn);
    }

    function httpErrorFn(error) {
        console.error(error);
        return $q.reject();
    }
}])
.controller('aboutCtrlr', [function () {

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
}]);











