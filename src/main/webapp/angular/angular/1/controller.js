angular.module('test', []).controller('firstController', function($scope){
	$scope.yourname = "What a world!";
	console.log($scope.yourname);
})