package jpa;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import entities.Cliente;
import entities.User;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;


/**
 *
 * @author torsello
 */
public class UserJpaController implements Serializable {
    @PersistenceUnit(unitName = "WIFITicketSystemPU")
    private EntityManagerFactory emf=null;

    public UserJpaController()
    {
        if(emf==null) {
            emf = Persistence.createEntityManagerFactory("WIFITicketSystemPU");
        }        
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws Exception {
        EntityManager em = null;
        try {            
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.merge(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = user.getId();
                if (findUser(id) == null) {
                    throw new Exception("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new Exception("The user with id " + id + " no longer exists.", enfe);
            }
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        } 
        finally 
        {
            if (em != null) {em.close();}
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from User as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public User findUser(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

      
    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from User as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public boolean checkExistUtenteEntities(User user) {
        EntityManager em = getEntityManager();
        try {            
            Query q = em.createQuery("select object(o) from User as o where ("                    
                    + "o.utente='"+user.getUtente()+"');");            
            List lisres=q.getResultList();
            if(lisres.size()==1) {
                return true;
            }
            else {
                return false;
            }
        } finally {
            em.close();
        }        
    }
    /**
     * @param emf the emf to set
     */
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * @return the emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }

    public User CheckPassword(String user, String password) {
        EntityManager em = getEntityManager();
        try {            
            Query q = em.createQuery("select object(o) from User as o where ("                    
                    + "o.utente='"+user+"' AND "            
                    + "o.password='"+password+"');");            
            List lisres=q.getResultList();
            if(lisres.size()==1) {
                User usr=(User) lisres.get(0);            
                return usr;
            }
            else {
                return null;
            }
        } finally {
            em.close();
        }        
    }

    public List<User> findUserEntities_by_cliente(Cliente cliente) {
        EntityManager em = getEntityManager();
        try {            
            Query q = em.createQuery("select object(o) from User as o where ("                    
                    + "o.cliente.id='"+cliente.getId()+"');");            
            return q.getResultList();            
        } finally {
            em.close();
        }        
    }

}
