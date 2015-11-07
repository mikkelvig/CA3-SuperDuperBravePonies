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
import javax.persistence.TypedQuery;
import org.xml.sax.helpers.DefaultHandler;
import security.EntityNotFoundException;

/**
 *
 * @author steffen
 */
public class CurrencyFacade extends DefaultHandler
{

    EntityManagerFactory emf;

    public CurrencyFacade()
    {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void saveCurrency(List<Currency> currencyList)
    {

        EntityManager em = getEntityManager();

        try
        {
            em.getTransaction().begin();
            for (int i = 2; i < currencyList.size(); i++)
            {
                em.persist(currencyList.get(i));

            }
            em.getTransaction().commit();

        } finally
        {
            em.close();
        }
    }

    public List<Currency> getAllCurrency() throws EntityNotFoundException
    {
        EntityManager em = getEntityManager();
        List<Currency> currencyList;
        try
        {
            currencyList = em.createQuery("SELECT c FROM Currency c", Currency.class).getResultList();
        } finally
        {
            em.close();
        }
        if (currencyList == null)
        {
            throw new EntityNotFoundException("No Currency found in the database");
        } else
        {
            return currencyList;
        }
    }

    public Currency getCurrencyByCode(String code) throws EntityNotFoundException
    {
        EntityManager em = getEntityManager();
        Currency c;
        try
        {
            TypedQuery<Currency> qu = em.createQuery("SELECT c FROM Currency c WHERE c.code = :code", Currency.class);
            qu.setParameter("code", code);
            qu.setMaxResults(1);

            c = qu.getSingleResult();
        } finally
        {
            em.close();
        }
        if (c == null)
        {
            throw new EntityNotFoundException("No currency found with requested code");
        } else
        {
            return c;
        }
    }

    public double currencyConverter(double amount, String from, String to) throws EntityNotFoundException
    {
        Currency currencyFrom = getCurrencyByCode(from);
        Currency currencyTo = getCurrencyByCode(to);

        if (currencyTo == null || currencyFrom == null)
        {
            throw new EntityNotFoundException("Currency not found");
        } else
        {
             double result = amount * currencyFrom.getRate() / currencyTo.getRate();
             return result;
        }
    }

}
