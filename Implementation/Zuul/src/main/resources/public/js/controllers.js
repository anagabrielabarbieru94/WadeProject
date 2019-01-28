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
}).controller('CountryListController', function($scope, $stateParams, Country) {
//	see services for definition of country
	$scope.countries = Country.query(); 
	$scope.currentUsername = getCookie("username");
	$scope.submitCountry = function(item) { 
		console.log(item);
		$state.go('showCountries'); 
	  };
});
