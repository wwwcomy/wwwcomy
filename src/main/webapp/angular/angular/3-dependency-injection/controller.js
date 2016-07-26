angular.module('test', [], function($provide){
	// Use provider method
	$provide.provider('nameService',function(){
		this.$get = function() {
			return {name:'Neo',age:1};
		}
	})

	// Use factory method
	$provide.factory('nameFactory',function(){
		return {name:'Neo',age:1};
	})

	// Use service method, the return value must be a non-basic object
	$provide.service('nameService2',function(){
		return {name:'Neo',age:1};
	})
}).controller('firstController', function($scope,nameService,nameFactory,nameService2){
	$scope.object=nameService;
	console.log(nameFactory);
	console.log(nameService2);
})