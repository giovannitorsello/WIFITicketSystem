/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.entities.jpa;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;
import wispmanager.entities.NetworkInterface;
import wispmanager.entities.jpa.exceptions.NonexistentEntityException;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;

/**
 *
 * @author torsello
 */
public class NetworkInterfaceJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "WISPManagerPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NetworkInterface networkInterface) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(networkInterface);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NetworkInterface networkInterface) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            networkInterface = em.merge(networkInterface);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = networkInterface.getId();
                if (findNetworkInterface(id) == null) {
                    throw new NonexistentEntityException("The networkInterface with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            NetworkInterface networkInterface;
            try {
                networkInterface = em.getReference(NetworkInterface.class, id);
                networkInterface.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The networkInterface with id " + id + " no longer exists.", enfe);
            }
            em.remove(networkInterface);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NetworkInterface> findNetworkInterfaceEntities() {
        return findNetworkInterfaceEntities(true, -1, -1);
    }

    public List<NetworkInterface> findNetworkInterfaceEntities(int maxResults, int firstResult) {
        return findNetworkInterfaceEntities(false, maxResults, firstResult);
    }

    private List<NetworkInterface> findNetworkInterfaceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from NetworkInterface as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public NetworkInterface findNetworkInterface(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NetworkInterface.class, id);
        } finally {
            em.close();
        }
    }

    public int getNetworkInterfaceCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from NetworkInterface as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
