
var helloModule = angular.module('hello', ['ngRoute']);
helloModule.config(function ($routeProvider, $httpProvider) {
	$routeProvider
			.when('/', {
				templateUrl: "home.html",
				controller: 'home'
			})
			.when('/login', {
				templateUrl: "login.html",
				controller: 'navigation'
			})
			.otherwise('/');

	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
});

helloModule
		.controller('home', function ($scope, $http) {
		    $http.get('/page/').success(function (data) {
		    	$scope.greeting = data;
		    })
		})
		.controller('navigation', function ($rootScope, $scope, $http, $location) {

			var authenticate = function (credentials, callback) {

				var headers = credentials ?
						{ authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)} :
						{};

				$http.get('user', {headers: headers})
						.success(function () {
							$rootScope.authenticated = true;
						})
						.error(function () {
							$rootScope.authenticated = false;
						})
						.always(function () {
							callback && callback();
						});
			};

			authenticate();

			$scope.credentials = {};
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
			$scope.logout = function () {
				$http.post('logout', {})
						.success(function () {
					    	$location.path("/");
						})
						.always(function () {
							$rootScope.authenticated = false;
						})
			}
		});