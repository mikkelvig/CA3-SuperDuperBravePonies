/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import facades.UserFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 *
 * @author Mikkel
 */
@Path("adduser")
public class AddUser {
    UserFacade f;

    public AddUser() {
        f = new UserFacade();
    }

    @POST
    @Consumes("application/json")
    public void saveUser(String user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        
        JsonObject jsonUser = new JsonParser().parse(user).getAsJsonObject();
        String username = jsonUser.get("username").getAsString();
        String password = jsonUser.get("password").getAsString();
        
        User u = new User(username,password);
        u.AddRole("User");
        f.saveUser(u);
        
    }
    
}
