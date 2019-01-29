angular.module('app.services', []).factory('LoginDto', function($resource) {
  return $resource('/accounts/login/:id', { id: '@id' }, {
    update: {
      method: 'PUT'
    }
  });
}).factory('Country', function($resource) {
	  return $resource('/itineraries/showCountries/:id', { id: '@id' }, {
		    update: {
		      method: 'PUT'
		    }
		  });
}).factory('Activity', function($resource) {
	  return $resource('/itineraries/showActivities/:id', { id: '@id' }, {
		    update: {
		      method: 'PUT'
		    }
		  });
}).factory('Museum', function($resource) {
	  return $resource('/itineraries/showMuseums/:localityName', { localityName: '@localityName' }, {
		    update: {
		      method: 'PUT'
		    }
		  });
}).factory('ItineraryDto', function($resource) {
	  return $resource('/itineraries/itinerary/:id', { id: '@id' }, {
		    update: {
		      method: 'PUT'
		    }
		  });
}).service('popupService',function($window){
    this.showPopup=function(message){
        return $window.confirm(message);
    }
});
