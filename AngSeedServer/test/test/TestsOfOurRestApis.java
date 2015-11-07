/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import static com.jayway.restassured.path.json.JsonPath.from;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.ApplicationConfig;
import static test.BackendTest.server;

/**
 *
 * @author Mikkel
 */
public class TestsOfOurRestApis {

    static Server server;

    public TestsOfOurRestApis() {
        baseURI = "http://localhost:8594";
        defaultParser = Parser.JSON;
        basePath = "/api";
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new Server(8594);
        ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
        servletHolder.setInitParameter("javax.ws.rs.Application", ApplicationConfig.class.getName());
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(servletHolder, "/api/*");
        server.setHandler(contextHandler);
        server.start();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        server.stop();
        //waiting for all the server threads to terminate so we can exit gracefully
        server.join();
    }
    
    @Test
    public void SaveUser() {
        given().
                contentType("application/json").
                body("{'username':'Bubba','password':'rainbowdash'}").
                when().
                post("/adduser").
                then().
                statusCode(204);
    }

    @Test
    public void testGetCurrency() {
        String json = given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();

        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/currency/dailyrates").
                then().
                statusCode(200);
    }

    @Test
    public void testGetCurrencyNoLogin() {
        given().
                contentType("application/json").
                when().
                get("/currency/dailyrates").
                then().
                statusCode(401);
    }

    @Test
    public void testGetCurrencyAsAdmin() {
        String json = given().
                contentType("application/json").
                body("{'username':'admin','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();

        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/currency/dailyrates").
                then().
                statusCode(403);
    }

    @Test
    public void testCalculateRates() {
        String json = given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();

        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/currency/calculator/50/USD/MYR").
                then().
                statusCode(200);

    }

    @Test
    public void testCalculateRatesAsAdmin() {
        String json = given().
                contentType("application/json").
                body("{'username':'admin','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();

        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/currency/calculator/50/USD/MYR").
                then().
                statusCode(403);
    }

    @Test
    public void testFindCompanyByCVR() {
        given().
                contentType("application/json").
                when().
                get("/company/vat/31678021/dk").
                then().
                statusCode(200);
    }

    @Test
    public void testFindCompanyByName() {
        given().
                contentType("application/json").
                when().
                get("/company/name/EA Copenhagen Business/dk").
                then().
                statusCode(200);
    }

    @Test
    public void testFindCompanyFail() {
        given().
                contentType("application/json").
                when().
                get("/company/name/scoobydoobydoo/dk").
                then().
                statusCode(500);
    }

    @Test
    public void testGetAllUsersAsAdmin() {
        String json = given().
                contentType("application/json").
                body("{'username':'admin','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();

        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/demoadmin/users").
                then().
                statusCode(200);
    }

    @Test
    public void testGetAllUsersAsUser() {
        String json = given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();

        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/demoadmin/users").
                then().
                statusCode(403);
    }

    @Test
    public void testDeleteUserAsAdmin() throws InterruptedException {
        Thread.sleep(90000);
        String json = given().
                contentType("application/json").
                body("{'username':'admin','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();

        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                delete("/demoadmin/user/Bubba").
                then().
                statusCode(204);
    }

    @Test
    public void testDeleteUserAsUser() {
        String json = given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();

        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                delete("/demoadmin/user/Bubba").
                then().
                statusCode(403);
    }

    @Test
    public void DeleteUserNoLogin() {
        given().
                contentType("application/json").
                when().
                delete("/demoadmin/user/Bubba").
                then().
                statusCode(401);
    }
    
    
}
