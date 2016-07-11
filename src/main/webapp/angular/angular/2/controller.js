angular.module('test', []).controller('firstController', function($scope){
	$scope.date = new Date();
	setInterval(function(){
		$scope.$apply(function(){
			$scope.date = new Date();
		});
	},500);
})