package jpa;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import entities.Cliente;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ClienteJpaController implements Serializable {
    @PersistenceUnit(unitName = "WIFITicketSystemPU")
    private EntityManagerFactory emf=null;

    public ClienteJpaController()
    {
        if(emf==null) {
            emf = Persistence.createEntityManagerFactory("WIFITicketSystemPU");
        }        
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws Exception {
        EntityManager em = null;
        try {            
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception ex) {
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cliente = em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new Exception("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new Exception("The cliente with id " + id + " no longer exists.", enfe);
            }
            em.getTransaction().begin();
            em.remove(cliente);
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

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Cliente as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

      
    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Cliente as o").getSingleResult()).intValue();
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

    public List<Cliente> findClienteEntities_from_den(String strSearch) {
        EntityManager em = getEntityManager();
        try {
            strSearch+="%";
            Query q = em.createQuery("select object(o) from Cliente as o where (o.denominazione LIKE '"+strSearch+"');");            
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean checkExistClienteEntities(Cliente cliente) {
        EntityManager em = getEntityManager();
        try {            
            Query q = em.createQuery("select object(o) from Cliente as o where ("
                    + "o.codiceFiscale='"+cliente.getCodiceFiscale()+"' OR "
                    + "o.partitaIva='"+cliente.getPartitaIva()+"');");            
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

}
