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

public class UserFacade {

    EntityManagerFactory emf;

    public UserFacade() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User saveUser(User u) throws NoSuchAlgorithmException, InvalidKeySpecException {
        EntityManager em = getEntityManager();

        String hashedPassword = PasswordHash.createHash(u.getPassword());
        u.setPassword(hashedPassword);

        try {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return u;
    }

    public User getUserByUserId(String id) {
        EntityManager em = getEntityManager();
        User u;
        try {

            u = em.find(User.class, id);

        } finally {
            em.close();
        }

        return u;
    }

    public List<User> getUsers() {
        EntityManager em = getEntityManager();
        List<User> userList;
        try {
            userList = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            return userList;
        } finally {
            em.close();
        }
    }

    public User deleteUser(String id) {
        EntityManager em = getEntityManager();
        try {
            User u = em.find(User.class, id);
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
            return u;
        } finally {
            em.close();
        }
    }

    public List<String> authenticateUser(String userName, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        EntityManager em = getEntityManager();
        User user;
        try {
            user = em.find(User.class, userName);
        } finally {
            em.close();
        }
        return user != null && PasswordHash.validatePassword(password, user.getPassword()) ? user.getRoles() : null;
    }

}
