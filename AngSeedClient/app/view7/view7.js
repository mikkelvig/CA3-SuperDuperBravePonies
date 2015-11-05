'use strict';

angular.module('myApp.view7', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view7', {
                    templateUrl: 'view7/view7.html',
                    controller: 'View7Ctrl'
                });
            }])

        .controller('View7Ctrl', function ($http, $scope) {
            $http({
                method: 'GET',
                url: 'api/demouser'
            }).then(function successCallback(res) {
                $scope.data = res.data.message;
            }, function errorCallback(res) {
                $scope.error = res.status + ": " + res.data.statusText;
            });

            var url = 'api/currency/dailyrates'
            $http.get(url).then(function successCallback(res) {
                $scope.currencylist = res.data;
            }, function errorCallback(res) {
                $scope.error = res.status + ": " + res.data.statusText;
            });


            $scope.calculate = function () {
                var url = 'api/currency/calculator/' + $scope.amount + "/" + $scope.fromcurrency + "/" + $scope.tocurrency;
                $http.get(url).then(function successCallback(res) {
                    $scope.result = res.data;
                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;

                });
            };


        });