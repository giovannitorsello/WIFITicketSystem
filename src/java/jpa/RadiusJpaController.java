package jpa;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import entities.Cliente;
import entities.Radius;
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
public class RadiusJpaController implements Serializable {
    @PersistenceUnit(unitName = "WIFITicketSystemPU")
    private EntityManagerFactory emf=null;

    public RadiusJpaController()
    {
        if(emf==null) {
            emf = Persistence.createEntityManagerFactory("WIFITicketSystemPU");
        }        
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Radius user) throws Exception {
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

    public void edit(Radius user) throws Exception {
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
                if (findRadius(id) == null) {
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
            Radius user;
            try {
                user = em.getReference(Radius.class, id);
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

    public List<Radius> findRadiusEntities() {
        return findRadiusEntities(true, -1, -1);
    }

    public List<Radius> findRadiusEntities(int maxResults, int firstResult) {
        return findRadiusEntities(false, maxResults, firstResult);
    }

    private List<Radius> findRadiusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Radius as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Radius findRadius(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Radius.class, id);
        } finally {
            em.close();
        }
    }

      
    public int getRadiusCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Radius as o").getSingleResult()).intValue();
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

    
}
