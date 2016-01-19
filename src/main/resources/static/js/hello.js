
var helloModule = angular.module('hello', ['ngRoute']);

/*
calling order:

1. app.config()
1. app.run()
1. directive's compile functions (if they are found in the dom)
1. app.controller()
1. directive's link functions (again, if found)
*/

helloModule.config(function ($routeProvider, $httpProvider) {
	$routeProvider
			.when('/', {
				templateUrl: "home.html",
				controller: 'homeCtrlr'
			})
			.when('/loginPage', {
				templateUrl: "login.html",
				controller: 'loginCtrlr'
			})
			.when('/registerPage', {
				templateUrl: "register.html",
				controller: 'registrationCtrlr'
			})
			.otherwise('/');

	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
});

helloModule
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
			return function (obj) { return Object.keys(obj)[0]; }
		})
		.factory('oneVal', function () {
			return function (obj) { return obj[Object.keys(obj)[0]]; }
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
			}
		})
		.factory('isRequiredAndTouched', function () {
			return function (formEl) {
				return formEl.$touched && formEl.$error.required;
			}
		});

helloModule
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
				model.$setValidity('nxUsername', (! model.$modelValue) || newUsernames.indexOf(model.$modelValue) === -1);
			}, true /* use angular.equals, not !== */);

			model.$parsers.push(function (val) {
				var pass = (! val) || scope.unavailableUsernames.indexOf(val) === -1;
				model.$setValidity('nxUsername', pass);
				return pass ? val : undefined;
			});
		}
	};
});

helloModule
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
				$http.post('logout', {}).then(function () {
					$rootScope.authenticated = false;
			    	$location.path("/");
				}, function () {
					$rootScope.authenticated = false;
				});
			};
		
		}])
		.controller('loginCtrlr',
				['authenticate', '$rootScope', '$scope', '$http', '$location',
				function (authenticate, $rootScope, $scope, $http, $location) {

			$http.post('logout', {}).finally(function () {
				$rootScope.authenticated = false;
			});

			$scope.credentials = {};
			$scope.login = function () {
				authenticate($scope.credentials, function () {
					if ($rootScope.authenticated) {
						$location.path("/");
						$scope.error = false;
					}
					else {
						$location.path("/loginPage");
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
				}).then(function (resp) {
					resp = resp;
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
		}]);

helloModule.run(['$anchorScroll', function ($anchorScroll) {
	$anchorScroll.yOffset = 50;
}]);


