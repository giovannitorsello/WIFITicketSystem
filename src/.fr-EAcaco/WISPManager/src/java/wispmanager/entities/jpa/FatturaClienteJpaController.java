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
import wispmanager.entities.FatturaCliente;
import wispmanager.entities.jpa.exceptions.NonexistentEntityException;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;

/**
 *
 * @author torsello
 */
public class FatturaClienteJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "WISPManagerPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FatturaCliente fatturaCliente) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(fatturaCliente);
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

    public void edit(FatturaCliente fatturaCliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            fatturaCliente = em.merge(fatturaCliente);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = fatturaCliente.getId();
                if (findFatturaCliente(id) == null) {
                    throw new NonexistentEntityException("The fatturaCliente with id " + id + " no longer exists.");
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
            FatturaCliente fatturaCliente;
            try {
                fatturaCliente = em.getReference(FatturaCliente.class, id);
                fatturaCliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fatturaCliente with id " + id + " no longer exists.", enfe);
            }
            em.remove(fatturaCliente);
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

    public List<FatturaCliente> findFatturaClienteEntities() {
        return findFatturaClienteEntities(true, -1, -1);
    }

    public List<FatturaCliente> findFatturaClienteEntities(int maxResults, int firstResult) {
        return findFatturaClienteEntities(false, maxResults, firstResult);
    }

    private List<FatturaCliente> findFatturaClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from FatturaCliente as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FatturaCliente findFatturaCliente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FatturaCliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getFatturaClienteCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from FatturaCliente as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
