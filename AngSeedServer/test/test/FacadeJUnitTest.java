/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import deploy.DeploymentConfiguration;
import entity.Currency;
import facades.CurrencyFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.Attributes;

/**
 *
 * @author steffen
 */
public class FacadeJUnitTest {

    EntityManagerFactory emf;
    CurrencyFacade f;
    EntityManager em;

    public FacadeJUnitTest() {

        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        f = new CurrencyFacade();
        em = emf.createEntityManager();
    }


    @Before
    public void setUpBeforTest() {
       emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
       f = new CurrencyFacade(); 
       em = emf.createEntityManager();
    }
    
    @Test
    public void saveCurrencyInDatabase(){
        
        Currency cu0 = new Currency("SM", "SMARTIES KRONER", 100, "2015-11-05");
        Currency cu1 = new Currency("SM", "SMARTIES KRONER", 100, "2015-11-05");
        Currency cu = new Currency("SM", "SMARTIES KRONER", 100, "2015-11-05");
        List<Currency> c = new ArrayList();
        c.add(cu0);
        c.add(cu1);
        c.add(cu);
      
        f.saveCurrency(c);
        Currency d = f.getCurrencyByCode("SM");
        assertEquals(d.getCode(), "SM");

    }

    @Test
    public void getAllCurrencyfromCurrencyList(){
    
        List<Currency> c = f.getAllCurrency();
        assertEquals(c.size(),34);
    }
    @Test
    public void getOneCurrecyByCode(){
        String code ="myr";
        Currency c = f.getCurrencyByCode(code);
        assertEquals(c.getCode(), "MYR");
    }
    @Test
    public void valutaConvertUsdToMyr(){
    
        Double result = 214.9263091878332;
        
        String from ="usd";
        String to = "myr";
        
        Double c = f.currencyConverter(50, from, to);
        assertTrue(c.equals(result));
        
    }
}
