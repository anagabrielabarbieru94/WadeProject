angular.module('app.controllers', []).controller('LoginController', function($scope, $state, popupService, $window, LoginDto) {
  $scope.loginDto = new LoginDto();
  
  $scope.isCookieUndefined = function() {
	  var username = getCookie("username");
	    if (username === "") {
	        return true;
	    } else {
	        return false;
	    }
  };
  
  $scope.login = function(loginDto) { 
	  var name = $scope.loginDto.userName
	  $scope.loginDto.$save(function() {
		  document.cookie = "username=" + name + "; path=/";
	      $state.go('showCountries'); 
	    });
	  };
  $scope.logout = function(){ 
	  logout();
  };
}).controller('CountryListController', ['$scope', '$http', '$stateParams', 
	function($scope, $http, $stateParams, Employee) {
	$http.get("/itineraries/showCountries")
    						.then(function (response) { 
    							$scope.countries = response.data;
    							$scope.currentUsername = getCookie("username");
    						})
}]);
