/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.Currency;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author steffen
 */
public class CurrencyFacade extends DefaultHandler {

    EntityManagerFactory emf;

    public CurrencyFacade() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void saveCurrency(List<Currency> currencyList) {

        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            for (int i = 2; i < currencyList.size(); i++) {
                em.persist(currencyList.get(i));

            }
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
    
    public List<Currency> getAllCurrency() {
        EntityManager em = getEntityManager();
        List<Currency>currencyList;
        try {
            currencyList = em.createNamedQuery("SELECT c FROM Currency c", Currency.class).getResultList();
            return currencyList;
        } finally {
            em.close();
        }
    }

}
