describe('myApp.view6 view6Ctrl', function () {

    var scope, httpBackendMock, ctrl;
    var testCurrencyList = [{description: "MonoppoliKroner", code: "MK", rate: "7,5", date: "2015-11-05"},{description: "MatadorPenge", code: "MP", rate: "700,5", date: "2015-11-05"}];
    beforeEach(module('myApp.view6'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        httpBackendMock.expectGET('api/demouser').respond("");
        httpBackendMock.expectGET('api/currency/dailyrates').respond(testCurrencyList);
        scope = $rootScope.$new();
        ctrl = $controller('View6Ctrl', {$scope: scope});
    }));

   describe('view6 http get call though the controller', function () {
        it('Should fetch a currency from the list and compare it to the one we said it should be', function () {
            expect(scope.info).toBeUndefined();
            httpBackendMock.flush();
            expect(scope.currencyList[0].description).toBe("MonoppoliKroner");
            expect(scope.currencyList[1].code).toBe("MP");
            console.log("currency 0: "+scope.currencyList[0].description);
            console.log("currency 1: "+scope.currencyList[1].code);
        });
    });

    describe('view6 http get call though the controller', function () {
        it('should return a list with two currencies', function () {
            httpBackendMock.flush();
            expect(scope.currencyList.length).toBe(2);
            console.log("currencyList length: "+scope.currencyList.length);
        });
    });
});

