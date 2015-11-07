/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import facades.UserFacade;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Bente
 */
@Path("company")
public class Company
{
    UserFacade uf = new UserFacade();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{option}/{search}/{country}")
    public String getCompany(@PathParam("option") String option, @PathParam("search") String search, @PathParam("country") String country) throws MalformedURLException, IOException {
        
        String urlToUse = "http://cvrapi.dk/api?" + option + "=" + search + "&country=" + country;

        URL url = new URL(urlToUse);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();

        return jsonStr;
    }
}
