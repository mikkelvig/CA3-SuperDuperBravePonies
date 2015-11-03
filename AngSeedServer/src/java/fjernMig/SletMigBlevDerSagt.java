/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fjernMig;

import entity.User;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 *
 * @author Mikkel
 */
public class SletMigBlevDerSagt {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        UserFacade f = new UserFacade();
        
        User user = new User("user","test");
        user.AddRole("User");
        
        User admin = new User("admin","test");
        admin.AddRole("Admin");
        
        f.saveUser(user);
        f.saveUser(admin);
    }
    
}
