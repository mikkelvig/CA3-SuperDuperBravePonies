describe('myApp.view2 view2Ctrl', function () {

    var scope, httpBackendMock, ctrl;
    var testResponse = {message: "Test af view 2"};
//    var testCompany = {name: "DataSteffensFirma", vat: "2233445566", adress: "Hyldevej 4", employees: "3", country: "dk"};
    beforeEach(module('myApp.view2'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        httpBackendMock.expectGET('api/demouser').respond(testResponse);
//        httpBackendMock.expectGET('api/company/Name' + "/" + testCompany.name + "/DK").respond(testCompany);
//        httpBackendMock.expectGET('api/company/""' + "/" + "/").respond("");
        scope = $rootScope.$new();
        ctrl = $controller('View2Ctrl', {$scope: scope});
    }));

    describe('view2 controller', function () {
        it('Should fetch a test message', function () {
            expect(scope.data).toBeUndefined();
            httpBackendMock.flush();
            expect(scope.data).toEqual("Test af view 2");
//            console.log("msg view 2: "+scope.data.message);
        });
    });

//    describe('view2 http get call though the controller', function () {
//        it('Should fetch a company from the list', function () {
//            scope.getCompany();
//            httpBackendMock.flush();
//            expect(scope.companylist.name).toBe("DataSteffensFirma");
//        });
//    });
});

