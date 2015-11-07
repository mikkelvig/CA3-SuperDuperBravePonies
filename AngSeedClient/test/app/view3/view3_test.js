describe('myApp.view3 view3Ctrl', function () {

    var scope, httpBackendMock, ctrl;
    var testResponse = {message: "I am in view 3", serverTime: "11-07-2015 07:35:21"};
    var testUserList = [{user: "tomBrady", password: "test", role: ["User"]}, {user: "ronGronkowski", password: "test", role: ["User"]}];
    beforeEach(module('myApp.view3'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        httpBackendMock.expectGET('api/demoadmin').respond(testResponse);
        httpBackendMock.expectGET('api/demoadmin/users').respond(testUserList);
        scope = $rootScope.$new();
        ctrl = $controller('View3Ctrl', {$scope: scope});
    }));

    describe('view3 controller', function () {
        it('Should fetch a test message', function () {
            expect(scope.data).toBeUndefined();
            httpBackendMock.flush();
            expect(scope.data.message).toEqual("I am in view 3");
            expect(scope.data.serverTime).toEqual("11-07-2015 07:35:21");
            console.log("msg view 3: "+scope.data.message);
            console.log("serverTime view 3: "+scope.data.serverTime);
        });
    });

    describe('view3 http get call though the controller', function () {
        it('Should fetch a user from the list and compare it to the one we said it should be', function () {
            expect(scope.info).toBeUndefined();
            httpBackendMock.flush();
            expect(scope.userList[0].user).toBe("tomBrady");
            expect(scope.userList[1].user).toBe("ronGronkowski");
            console.log("user 0: "+scope.userList[0].user);
            console.log("user 1: "+scope.userList[1].user);
        });
    });

    describe('view3 http get call though the controller', function () {
        it('should return a list with two users', function () {
            httpBackendMock.flush();
            expect(scope.userList.length).toBe(2);
            console.log("userlist length: "+scope.userList.length);
        });
    });

//    describe('view3 http delete call though the controller', function () {
//
//        var testDeleteOneUserInUserList = [{user: "billBelichick", password: "test", role: ["User"]}];
//
//        beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
//
//            httpBackendMock = $httpBackend;
//            httpBackendMock.expectGET('api/demoadmin').respond("");
//            httpBackendMock.expectGET('api/demoadmin/users').respond(testDeleteOneUserInUserList);
//            httpBackendMock.expectDELETE('api/demoadmin/user/' + "billBelichick").respond(testDeleteOneUserInUserList);
//            scope = $rootScope.$new();
//            ctrl = $controller('View3Ctrl', {$scope: scope});
//        }));
//
//
//        it('Should fetch a user from the list and and delete it', function () {
//            
//            httpBackendMock.flush();
//            expect(scope.userList.length).toBe(1);
//            scope.deleteUser("billBelichick");
//            scope.$digest();
//            console.log(scope.userList);
//            expect(scope.userList[0].user).toBeNull();
//        });
//    });

});

