
var helloModule = angular.module('hello', ['ngRoute']);
helloModule.config(function ($routeProvider, $httpProvider) {
	$routeProvider
			.when('/', {
				templateUrl: "home.html",
				controller: 'home'
			})
			.when('/loginPage', {
				templateUrl: "login.html",
				controller: 'navigation'
			})
			.otherwise('/');

	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
});

helloModule
		.controller('home', function ($scope, $http) {
		    $http.get('/homePage/').then(function (response) {
		    	$scope.greeting = response.data;
		    }, function (what) {
		    	what = what;
		    });
		})
		.controller('navigation', function ($rootScope, $scope, $http, $location) {

			var authenticate = function (credentials, callback) {

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

			authenticate();

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
			$scope.logout = function () {
				$http.post('logout', {}).then(function () {
					$rootScope.authenticated = false;
			    	$location.path("/");
				}, function () {
					$rootScope.authenticated = false;
				});
			}
		});