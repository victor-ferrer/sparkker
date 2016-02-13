angular.module('sparkker', [ 'ngRoute','chart.js' ])
.config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'navigation'
	}).when('/indicators', {
		templateUrl : 'indicators.html',
		controller : 'indicatorsController'
	})
	.otherwise('/');

  $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

})
.controller('navigation',

  function($rootScope, $scope, $http, $location, $window) {

	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};
})
.controller('indicatorsController', function($scope, $http) 
{
	$scope.stocks = [{
		  label: 'Mapfre Seguros',
		  ticker: 'MAP.MC'},
		  {
		  label: 'Banco Santander',
		  ticker: 'SAN.MC'},
		  {
		  label: 'Repsol',
		  ticker: 'REP.MC'}];
	
	$scope.targetStock = $scope.stocks[1];
	
	$scope.config = {
		datasetFill : false,
		bezierCurve : false,
		animation: false,
		pointDot : false

	}
	
	$scope.loadData = function (ticker) {
		$http.get('/analyzeStock?ticker=' + ticker).success(function(newData) {
			$scope.labels = newData.labels;
			$scope.series = newData.series;
			$scope.data = newData.datasets;
			
		});
	}
	
	$scope.updateChart = function() {
		$scope.loadData($scope.targetStock.ticker);
	}
	
    $scope.loadData('SAN.MC');
	
	$scope.onClick = function (points, evt) {
	  console.log(points, evt);
	};
})



