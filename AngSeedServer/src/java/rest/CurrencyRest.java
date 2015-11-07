package rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Currency;
import facades.CurrencyFacade;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import security.EntityNotFoundException;

/**
 *
 * @author Mikkel
 */
@Path("currency")
@RolesAllowed("User")
public class CurrencyRest {

    CurrencyFacade cf;
    Gson gson;

    public CurrencyRest() {
        cf = new CurrencyFacade();
        gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    }

    @GET
    @Path("dailyrates")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCurrency() throws EntityNotFoundException {
        //Vi henter alle currency-objekter fra databasen og lægger dem i currencyList
        List<Currency> currencyList = cf.getAllCurrency();
        //Dette er en ny liste som skal indeholde alle Currency-objekter i json-format
        List<JsonObject> jsonCurrencyList = new ArrayList();
        //Her går vi alle Currency-objekter i currencyList igennem, og laver dem til json
        for (int i = 0; i < currencyList.size(); i++) {
            //Dette jsonObjekt skal rumme alle attributter fra Currency-objektet
            JsonObject jsonCurrency = new JsonObject();
            //Her bliver attributterne hevet ud og lagt ind som properties i jsonObjektet
            jsonCurrency.addProperty("id", currencyList.get(i).getId());
            jsonCurrency.addProperty("code", currencyList.get(i).getCode());
            jsonCurrency.addProperty("description", currencyList.get(i).getDescription());
            jsonCurrency.addProperty("rate", currencyList.get(i).getRate());
            jsonCurrency.addProperty("date", currencyList.get(i).getDag());
            //Det færdige objekt bliver tilføjet til listen
            jsonCurrencyList.add(jsonCurrency);
        }
        //Når listen er loopet igennem, returneres jsonCurrencyList som en String i json-format
        return gson.toJson(jsonCurrencyList);
        //Hilsen Mikkel aka Rainbow Dash

    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("calculator/{amount}/{from}/{to}")
    public String calculateRates(@PathParam("amount")double amount,@PathParam("from") String fromCurrency, @PathParam("to") String toCurrency) throws EntityNotFoundException {
        //Først beder vi vores facade om at konvertere, ud fra de indsendte parametre
        double facadeResult = cf.currencyConverter(amount, fromCurrency, toCurrency);
        //Derefter lægger vi facadens resultat ind i et json-objekt
        JsonObject jsonResult = new JsonObject();
        jsonResult.addProperty("result", facadeResult);
        
        return gson.toJson(jsonResult);
    }

}