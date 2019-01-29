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
}]).controller('ObjectivesListController', function($scope, $state, $stateParams, $resource, ItineraryDto) {
	//showMuseums
	var Museum = $resource('/itineraries/showMuseums?localityName=:localityName', {localityName: '@localityName'});
	$scope.museums = Museum.query({localityName: $stateParams.localityName});
	//showOtherCulturalAttractions
	var Oca = $resource('/itineraries/showOtherCulturalAttractions?localityName=:localityName', {localityName: '@localityName'});
	$scope.cans = Oca.query({localityName: $stateParams.localityName});
	//showRestaurants
	var Restaurant = $resource('/itineraries/showRestaurants?localityName=:localityName', {localityName: '@localityName'});
	$scope.restaurants = Restaurant.query({localityName: $stateParams.localityName});
	//showMountains
	var Mountain = $resource('/itineraries/showMountains?localityName=:localityName', {localityName: '@localityName'});
	$scope.mountains = Mountain.query({localityName: $stateParams.localityName});
	//showLakes
	var Lake = $resource('/itineraries/showLakes?localityName=:localityName', {localityName: '@localityName'});
	$scope.lakes = Lake.query({localityName: $stateParams.localityName});
	//showOtherNaturalAttractions
	var Ona = $resource('/itineraries/showOtherNaturalAttractions?localityName=:localityName', {localityName: '@localityName'});
	$scope.onas = Ona.query({localityName: $stateParams.localityName});
	//showEntertainmentObjectives
	var Fun = $resource('/itineraries/showEntertainmentObjectives?localityName=:localityName', {localityName: '@localityName'});
	$scope.funObjs = Fun.query({localityName: $stateParams.localityName});
	
	$scope.currentUsername = getCookie("username");
	$scope.submitObjectives = function() { 
		console.log($scope.museums);
		$scope.itineraryDto = new ItineraryDto();
		$scope.itineraryDto.itineraryName = $scope.itinerary;
		$scope.itineraryDto.museums = $scope.museums; 
		$scope.itineraryDto.otherCulturalAttractions = $scope.cans;
		$scope.itineraryDto.restaurants = $scope.restaurants;
		$scope.itineraryDto.mountains = $scope.mountains;
		$scope.itineraryDto.lakes = $scope.lakes;
		$scope.itineraryDto.otherNaturalAttractions = $scope.onas;
		$scope.itineraryDto.entertainmentObjectives = $scope.funObjs;
		
		$scope.itineraryDto.$save(function() {
		      $state.go('showItineraries'); 
		    });
	};
}).controller('ItinerariesListController', function($scope, $state, $stateParams, $resource) {
	$scope.currentUsername = getCookie("username");
	var Itinerary = $resource('/itineraries/getAll');
	$scope.itineraries =Itinerary.query();
});
