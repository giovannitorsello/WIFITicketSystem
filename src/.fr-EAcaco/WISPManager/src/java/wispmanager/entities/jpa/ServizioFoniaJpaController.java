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
import wispmanager.entities.ChiamateFonia;
import wispmanager.entities.jpa.exceptions.NonexistentEntityException;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;

/**
 *
 * @author torsello
 */
public class ServizioFoniaJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "WISPManagerPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ChiamateFonia servizioFonia) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(servizioFonia);
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

    public void edit(ChiamateFonia servizioFonia) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            servizioFonia = em.merge(servizioFonia);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = servizioFonia.getId();
                if (findServizioFonia(id) == null) {
                    throw new NonexistentEntityException("The servizioFonia with id " + id + " no longer exists.");
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
            ChiamateFonia servizioFonia;
            try {
                servizioFonia = em.getReference(ChiamateFonia.class, id);
                servizioFonia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servizioFonia with id " + id + " no longer exists.", enfe);
            }
            em.remove(servizioFonia);
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

    public List<ChiamateFonia> findServizioFoniaEntities() {
        return findServizioFoniaEntities(true, -1, -1);
    }

    public List<ChiamateFonia> findServizioFoniaEntities(int maxResults, int firstResult) {
        return findServizioFoniaEntities(false, maxResults, firstResult);
    }

    private List<ChiamateFonia> findServizioFoniaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from ServizioFonia as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ChiamateFonia findServizioFonia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ChiamateFonia.class, id);
        } finally {
            em.close();
        }
    }

    public int getServizioFoniaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from ServizioFonia as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
