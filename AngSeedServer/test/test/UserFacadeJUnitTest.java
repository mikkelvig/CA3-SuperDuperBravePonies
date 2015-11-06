/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import deploy.DeploymentConfiguration;
import entity.User;
import facades.CurrencyFacade;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import static java.util.Collections.list;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steffen
 */
public class UserFacadeJUnitTest {

    EntityManagerFactory emf;
    UserFacade u;
    EntityManager em;

    public UserFacadeJUnitTest() {

        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        u = new UserFacade();
        em = emf.createEntityManager();
    }

    @Before
    public void setUpBeforTest() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        u = new UserFacade();
        em = emf.createEntityManager();
    }

    @Test 
    public void insertOneNewUserInDatabase() throws NoSuchAlgorithmException {

        User k = new User("ponye", "ponytale");

        try {
            u.saveUser(k);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(UserFacadeJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        User m = u.getUserByUserId("ponye");

        assertEquals(k.getUserName(), m.getUserName());

    }

    @Test
    public void getOneUserByName() {

        User m = u.getUserByUserId("pony");
        assertEquals(m.getUserName(), "pony");

    }

    @Test 
    public void getAllUsersFromTheDatabase() {

        List<User> c = u.getUsers();
        assertEquals(c.size(), 3);
    }

    @Test(expected = Exception.class)
    public void deleteOneUserFromTheDatabase() throws InterruptedException {

       Thread.sleep(8000);
        u.deleteUser("ponye");
        User deluser = u.getUserByUserId("ponye");
        

    }
//    @
//    Test
//    public void authenticateOneUser() throws NoSuchAlgorithmException{
//   
//        try {
//            
//            User user = (User) u.authenticateUser("sd","ponytale");
//            System.out.println("hvad sker der her" + user.getUserName() + user.getPassword());
//        } catch (InvalidKeySpecException ex) {
//            Logger.getLogger(UserFacadeJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

}
