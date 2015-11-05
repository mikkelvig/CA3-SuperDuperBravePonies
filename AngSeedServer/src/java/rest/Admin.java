package rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entity.User;
import facades.UserFacade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("demoadmin")
@RolesAllowed("Admin")
public class Admin {

    UserFacade u;
    Gson gson;

    public Admin() {
        u = new UserFacade();
        gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSomething() {
        String now = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
        return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated ADMINS\",\n"
                + "\"serverTime\": \"" + now + "\"}";
    }

    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() {
        //Facaden henter alle brugere, og sætter dem ind i denne liste.
        List<User> userObjects = u.getUsers();
        //Denne liste kommer til at indeholde alle brugere i json-format.
        List<JsonObject> jsonUsers = new ArrayList();
        //her løber vi userObjects-listen igennem, laver et jsonObject for hver user,
        // - og sætter dem ind i listen jsonUsers.
        for (int i = 0; i < userObjects.size(); i++) {
            JsonObject jsonUser = new JsonObject();
            //Denne string indeholder alle roller tilknyttet et user-object.
            String userRoles = "";
            //Her bliver rollerne fra en user lagt ind i vores userRoles-string.
            for (String role : userObjects.get(i).getRoles()) {
                userRoles = "" + role;
            }
            //Her bliver attributter og deres attribut-værdier sat ind i et jsonObject.
            jsonUser.addProperty("username", userObjects.get(i).getUserName());
            jsonUser.addProperty("password", userObjects.get(i).getPassword());
            jsonUser.addProperty("roles", userRoles);
            //Det færdige jsonObject tilføjes til jsonUsers-listen.
            jsonUsers.add(jsonUser);
        }
        //jsonUsers returneres som String i json-format. Hilsen Mikkel aka Rainbow Dash...
        return gson.toJson(jsonUsers);
    }
    
    @DELETE
    @Path("user/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("id") String id) {
        u.deleteUser(id);
    }

}
