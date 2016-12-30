package jpa;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import entities.Cliente;
import entities.WebSurfer;
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
public class WebSurferJpaController implements Serializable {
    @PersistenceUnit(unitName = "WIFITicketSystemPU")
    private EntityManagerFactory emf=null;

    public WebSurferJpaController()
    {
        if(emf==null) {
            emf = Persistence.createEntityManagerFactory("WIFITicketSystemPU");
        }        
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(WebSurfer webSurfer) throws Exception {
        EntityManager em = null;
        try {            
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(webSurfer);
            em.getTransaction().commit();
        } catch (Exception ex) {
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(WebSurfer webSurfer) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            webSurfer = em.merge(webSurfer);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = webSurfer.getId();
                if (findWebSurfer(id) == null) {
                    throw new Exception("The webSurfer with id " + id + " no longer exists.");
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
            WebSurfer webSurfer;
            try {
                webSurfer = em.getReference(WebSurfer.class, id);
                webSurfer.getId();
            } catch (EntityNotFoundException enfe) {
                throw new Exception("The webSurfer with id " + id + " no longer exists.", enfe);
            }
            em.getTransaction().begin();
            em.remove(webSurfer);
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

    public List<WebSurfer> findWebSurferEntities() {
        return findWebSurferEntities(true, -1, -1);
    }

    public List<WebSurfer> findWebSurferEntities(int maxResults, int firstResult) {
        return findWebSurferEntities(false, maxResults, firstResult);
    }

    private List<WebSurfer> findWebSurferEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from WebSurfer as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public WebSurfer findWebSurfer(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(WebSurfer.class, id);
        } finally {
            em.close();
        }
    }

    public long checkExistWebSurferEntities(WebSurfer webSurfer) {
        EntityManager em = getEntityManager();
        try {            
            Query q = em.createQuery("select object(o) from WebSurfer as o where ("                    
                    + "o.denominazione='"+webSurfer.getDenominazione()+"');");            
            List lisres=q.getResultList();
            if(lisres.size()>=1) {
                return ((WebSurfer) lisres.get(0)).getId();                
            }
            else {
                return 0;
            }
        } finally {
            em.close();
        }        
    }  
    
    
    public int getWebSurferCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from WebSurfer as o").getSingleResult()).intValue();
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

    public List findWebSurferEntities_from_den(String query) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from WebSurfer as o where ("                    
                    + "o.denominazione='"+query+"');");    
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    
}
