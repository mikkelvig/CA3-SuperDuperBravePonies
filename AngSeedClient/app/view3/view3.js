'use strict';

angular.module('myApp.view3', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view3', {
                    templateUrl: 'view3/view3.html',
                    controller: 'View3Ctrl'
                });
            }])

        .controller('View3Ctrl', function ($http, $scope) {
            $http.get('api/demoadmin')
                    .success(function (data, status, headers, config) {
                        $scope.data = data;
                    })
                    .error(function (data, status, headers, config) {

                    });

            var url = 'api/demoadmin/users';
            alert("Jeg er inde get users");
            $http.get(url).then(function successCallback(res) {
                $scope.userList = res.data;
            }, function errorCallback(res) {
            });

            $scope.deleteUser = function (userName) {
                var url = 'api/demoadmin/user/' + userName;
                alert("Jeg er inde i delete");
                $http.delete(url).then(function successCallback(res) {
                    $scope.deleteMsg = userName + "Has been deleted";
                }, function errorCallback(res) {
                    $scope.deleteMsg = userName + ";-( Was not deleted";
                });
            };


        });