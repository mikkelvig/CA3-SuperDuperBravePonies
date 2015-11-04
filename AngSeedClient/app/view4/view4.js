'use strict';

angular.module('myApp.view4', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view4', {
    templateUrl: 'view4/view4.html',
    controller:'View4Ctrl'
  });
}])

.controller('View4Ctrl', function($scope, $http) {
  $scope.msg = "";
  
  $scope.saveUser = function() {
          $http.post("api/adduser", $scope.user).success(function() {
            $scope.msg = "User added!";
        });
    };                   
});