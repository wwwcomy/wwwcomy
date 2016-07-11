angular.module('cart', []).controller('cartController', function($scope){
	$scope.cart = [
	{
		id:1,
		name:"iphone5",
		quantity:3,
		price:3000
	},
	{
		id:2,
		name:"iphone5s",
		quantity:22,
		price:4500
	},
	{
		id:3,
		name:"iMac",
		quantity:1,
		price:20000
	},
	{
		id:4,
		name:"ipad",
		quantity:10,
		price:5800
	}
	];

	/** Calculate the sum **/
	$scope.sumAll=function(){
		var sum = {
			price:0,
			quantity:0
		};
		angular.forEach($scope.cart,function(item){
			sum.price += item.quantity*item.price;
			sum.quantity += parseInt(item.quantity);
		});
		return sum;
	}

	/** Remove the row with a given id **/
	$scope.remove=function(id){
		if(id){
			var index = -1;
			angular.forEach($scope.cart,function(item, key){
				if(item.id===id){
					index=key;
					$scope.cart.splice(index,1);
					return;
				}
			});
		} else {
			$scope.cart = [];
		}
	}

	/** change the count of a product **/
	$scope.change=function(id,count){
		if(id){
			angular.forEach($scope.cart,function(item, key){
				if(item.id===id){
					item.quantity+=count;
					if(item.quantity<=0){
						var result = confirm("Do you want to remove this item?");
						if(result){
							$scope.remove(id);
						} else {
							item.quantity-=count;
						}
					}
				}
			});
		} else {
			alert("No row selected!!")
		}
	}

	$scope.$watch("cart",function(newVal, oldVal){
		angular.forEach(newVal,function(item, key){
			if(item.quantity<=0 || isNaN(item.quantity)){
				var result = confirm("Do you want to remove this item?");
				if(result){
					$scope.remove(item.id);
				} else {
					item.quantity = oldVal[key].quantity;
				}
				return;
			}
		});
	},true)

	
})