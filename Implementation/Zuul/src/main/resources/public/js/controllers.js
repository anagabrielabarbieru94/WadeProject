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
}).controller('CountryListController', function($scope, $state, $stateParams, Country, Activity) {
//	see services for definition of country
	$scope.countries = Country.query(); 
	$scope.activities = Activity.query();
	$scope.currentUsername = getCookie("username");
	$scope.submitCountry = function(item,selectedActivity) { 
		$state.go('showLocalities', {"countryName": item.name, "activity": selectedActivity.activityName}); 
	  };
}).controller('LocalitiesListController', ['$scope', '$http', '$stateParams', 
	function($scope, $http, $stateParams) {
	$http.get("/itineraries/showLocalities", {params:{"countryName": $stateParams.countryName, 
		"activity": $stateParams.activity}})
		.then(function (response) { 
    			$scope.localities = response.data;
    			$scope.currentUsername = getCookie("username");
    })
}]);
