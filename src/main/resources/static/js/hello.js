
var helloModule = angular.module('hello', ['ngRoute']);
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
		.controller('registrationCtrlr',
				['oneKey', 'oneVal', '$rootScope', '$scope', '$http', '$location',
				function (oneKey, oneVal, $rootScope, $scope, $http, $location) {

			$http.post('logout', {}).finally(function () {
				$rootScope.authenticated = false;
			});

			$scope.oneKey = oneKey;
			$scope.oneVal = oneVal;

			$scope.register = function (newUser) {
				// $http.post('register', {}); // ...
				newUser = newUser;
			}

			$scope.master = {};
			$scope.reset = function() {
	        	$scope.user = angular.copy($scope.master);
	      	};

	      	$scope.reset();

	      	$scope.user = {};
			$http.get('/public-api/userDetailLabels').then(function (response) {
				$scope.userDetailLabels = response.data;
				// angular.forEach($scope.userDetailLabels, function (value, key) {
				// 	// add a blank "no answer" value to each select.
				// 	value.noAnswer = "";

				// 	// set the initial value of each option to noAnswer.
				// 	$scope.user[key] = value.noAnswer;
				// });
			});
		}]);

