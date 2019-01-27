angular.module('app.services', []).factory('LoginDto', function($resource) {
  return $resource('/accounts/login/:id', { id: '@id' }, {
    update: {
      method: 'PUT'
    }
  });
}).service('popupService',function($window){
    this.showPopup=function(message){
        return $window.confirm(message);
    }
});
