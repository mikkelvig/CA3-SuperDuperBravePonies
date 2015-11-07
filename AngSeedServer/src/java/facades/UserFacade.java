package facades;

import deploy.DeploymentConfiguration;
import security.PasswordHash;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import security.EntityNotFoundException;

public class UserFacade
{

    EntityManagerFactory emf;

    public UserFacade()
    {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public User saveUser(User u) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        EntityManager em = getEntityManager();

        String hashedPassword = PasswordHash.createHash(u.getPassword());
        u.setPassword(hashedPassword);

        try
        {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
        return u;
    }

    public User getUserByUserId(String id) throws EntityNotFoundException
    {
        EntityManager em = getEntityManager();
        User u;
        try
        {
            u = em.find(User.class, id);
        } finally
        {
            em.close();
        }
        if (u == null)
        {
            throw new EntityNotFoundException("No user found with requested Id");
        } else
        {
            return u;
        }
    }

    public List<User> getUsers() throws EntityNotFoundException
    {
        EntityManager em = getEntityManager();
        List<User> userList;
        try
        {
            userList = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        } finally
        {
            em.close();
        }
        if (userList == null)
        {
            throw new EntityNotFoundException("No user list found");
        } else
        {
            return userList;
        }
    }

    public User deleteUser(String id) throws EntityNotFoundException
    {
        EntityManager em = getEntityManager();
        try
        {
            User u = em.find(User.class, id);
            if (u == null)
            {
                throw new EntityNotFoundException("No user found with requested Id");
            } else
            {
                em.getTransaction().begin();
                em.remove(u);
                em.getTransaction().commit();
                return u;
            }
        } finally
        {
            em.close();
        }
    }

    public List<String> authenticateUser(String userName, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, EntityNotFoundException
    {
        EntityManager em = getEntityManager();
        User user;
        try
        {
            user = em.find(User.class, userName);
        } finally
        {
            em.close();
        }
        if (user == null)
        {
            throw new EntityNotFoundException("No user found with requested Id");
        } else
        {
            return user != null && PasswordHash.validatePassword(password, user.getPassword()) ? user.getRoles() : null;
        }
    }

}
