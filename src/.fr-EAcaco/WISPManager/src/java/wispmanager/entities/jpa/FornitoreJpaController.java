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
import wispmanager.entities.Fornitore;
import wispmanager.entities.jpa.exceptions.NonexistentEntityException;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;

/**
 *
 * @author torsello
 */
public class FornitoreJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "WISPManagerPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fornitore fornitore) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(fornitore);
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

    public void edit(Fornitore fornitore) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            fornitore = em.merge(fornitore);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = fornitore.getId();
                if (findFornitore(id) == null) {
                    throw new NonexistentEntityException("The fornitore with id " + id + " no longer exists.");
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
            Fornitore fornitore;
            try {
                fornitore = em.getReference(Fornitore.class, id);
                fornitore.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fornitore with id " + id + " no longer exists.", enfe);
            }
            em.remove(fornitore);
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

    public List<Fornitore> findFornitoreEntities() {
        return findFornitoreEntities(true, -1, -1);
    }

    public List<Fornitore> findFornitoreEntities(int maxResults, int firstResult) {
        return findFornitoreEntities(false, maxResults, firstResult);
    }

    private List<Fornitore> findFornitoreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Fornitore as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Fornitore findFornitore(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fornitore.class, id);
        } finally {
            em.close();
        }
    }

    public int getFornitoreCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Fornitore as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
