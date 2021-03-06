'use strict';

angular.module('myApp.view2', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view2', {
                    templateUrl: 'view2/view2.html',
                    controller: 'View2Ctrl'
                });
            }])

        .controller('View2Ctrl', function ($http, $scope) {
            $scope.getCvrMessage = "";
                    $http({
                method: 'GET',
                url: 'api/demouser'
            }).then(function successCallback(res) {
                $scope.data = res.data.message;
            }, function errorCallback(res) {
                $scope.error = res.status + ": " + res.data.statusText;
            });
          
            $scope.getCompany = function () {
                $scope.getCvrMessage = "";
                var url = 'api/company/'+$scope.feedback.option + "/" + $scope.feedback.search+ "/" + $scope.feedback.country;
                $http.get(url).then(function successCallback(res) {
                    $scope.companylist = res.data;
                }, function errorCallback(res) {
                $scope.error = res.status + ": " + res.data.statusText;
                $scope.getCvrMessage = "No company found";
            });
            };
         
         $scope.getInfoOnSubCompany = function (name) {
               for(var i =0; i<$scope.companylist.productionunits.length; i++){
                 if( $scope.companylist.productionunits[i].name === name){
                     $scope.subUnit = angular.copy($scope.companylist.productionunits[i])
                     return;
                 }     
               }
            }
            
        });