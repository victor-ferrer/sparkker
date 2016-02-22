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
	$scope.windowsize = 200;
	$scope.stocks = [
          {
          label: 'Abertis Infrastructuras',
          ticker: 'ABE.MC'},
          {
		  label: 'Banco Santander',
		  ticker: 'SAN.MC'},
		  {
          label: 'Bolsas y Mercados',
          ticker: 'BME.MC'},
          {
		  label: 'Mapfre',
		  ticker: 'MAP.MC'},
		  {
		  label: 'Red Eléctrica Corporación',
		  ticker: 'REE.MC'},
		  {
		  label: 'Repsol',
		  ticker: 'REP.MC'},		  
		  {
		  label: 'Walmart',
		  ticker: 'WMT'},
		  {
		  label: 'Visa',
		  ticker: 'V'}];
	
	$scope.targetStock = $scope.stocks[1];
	
	$scope.config = {
		datasetFill : false,
		bezierCurve : false,
		animation: false,
		pointDot : false
	}
	
	$scope.configVoting = {
			datasetFill : false,
			bezierCurve : false,
			animation: false,
			pointDot : false,
		    showTooltips: false
	}
	
	$scope.loadData = function (ticker) {
		$http.get('/analyzeStock?ticker=' + ticker + "&windowSize=" + $scope.windowsize).success(function(newData) {
			$scope.labels = newData.labels;
			$scope.series = newData.series;
			$scope.data = newData.datasets;
			$scope.labelsVoting = newData.labelsVoting;
			$scope.seriesVoting = newData.seriesVoting;
			$scope.dataVoting = newData.datasetsVoting;
			$scope.operations = newData.operations;
			
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



