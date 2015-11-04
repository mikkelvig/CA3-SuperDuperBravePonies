'use strict';

angular.module('myApp.view2', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view2', {
                    templateUrl: 'view2/view2.html',
                    controller: 'View2Ctrl'
                });
            }])

        .controller('View2Ctrl', function ($http, $scope) {
            $http({
                method: 'GET',
                url: 'api/demouser'
            }).then(function successCallback(res) {
                $scope.data = res.data.message;
            }, function errorCallback(res) {
                $scope.error = res.status + ": " + res.data.statusText;
            });
          
            $scope.getCompany = function () {
                var url = 'api/company/'+$scope.feedback.option + "/" + $scope.feedback.search+ "/" + $scope.feedback.country;
                alert("Jeg er inde i getcompany"+url);
                $http.get(url).then(function successCallback(res) {
                    $scope.companylist = res.data;
                }, function errorCallback(res) {
                $scope.error = res.status + ": " + res.data.statusText;
            });
            };
         
            
        });