angular.module('sparkker', [ 'ngRoute','chart.js','ngMaterial' ])
.config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'navigation'
	}).when('/indicators', {
		templateUrl : 'indicators.html',
		controller : 'indicatorsController'
	}).when('/jobs', {
		templateUrl : 'jobs.html',
		controller : 'jobsController'
	})
	.otherwise('/');

  $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

})
.controller('navigation', function($rootScope, $scope, $http, $location, $window) {

	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};
})
.controller('jobsController', function($scope, $http) 
{
    $scope.myForm = {};
    $scope.myForm.stop_loss_perc  = 3;
    $scope.myForm.take_profit_perc  = 15;
    $scope.myForm.sma_sliding_window = 200;
    
    $scope.getTotalYield = function(){
        var total = 0;
        for(var i = 0; i < $scope.positions.length; i++){
            var pos = $scope.positions[i];
            total += (pos.yield);
        }
        return total;
    }
    
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
	          		     ticker: 'V'}
	          		  ];
	           

  $scope.myForm.submitTheForm = function(item, event) {
    var jobConfig = {
       stopLossPerc : $scope.myForm.stop_loss_perc,
       takeProfitPerc  : $scope.myForm.take_profit_perc,
       targetStock : $scope.myForm.targetStock.ticker,
       smaWindow : $scope.myForm.sma_sliding_window
    };
    console.log("--> Submitting form"+ JSON.stringify(jobConfig));

    var responsePromise = $http.post("/jobs/submit", jobConfig, {});
    responsePromise.success(function(dataFromServer, status, headers, config) {
       $scope.positions = dataFromServer.positions;
    });
     responsePromise.error(function(data, status, headers, config) {
       alert("Submitting form failed!");
    });
  }
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
});



