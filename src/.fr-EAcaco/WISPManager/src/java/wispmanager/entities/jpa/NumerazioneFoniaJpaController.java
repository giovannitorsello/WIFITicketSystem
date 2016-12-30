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
import wispmanager.entities.Cliente;
import wispmanager.entities.NumerazioneFonia;
import wispmanager.entities.jpa.exceptions.NonexistentEntityException;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;

/**
 *
 * @author torsello
 */
public class NumerazioneFoniaJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "WISPManagerPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NumerazioneFonia numerazioneFonia) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente cliente = numerazioneFonia.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId());
                numerazioneFonia.setCliente(cliente);
            }
            em.persist(numerazioneFonia);
            if (cliente != null) {
                cliente.getNumerazioniFonia().add(numerazioneFonia);
                cliente = em.merge(cliente);
            }
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

    public void edit(NumerazioneFonia numerazioneFonia) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            NumerazioneFonia persistentNumerazioneFonia = em.find(NumerazioneFonia.class, numerazioneFonia.getId());
            Cliente clienteOld = persistentNumerazioneFonia.getCliente();
            Cliente clienteNew = numerazioneFonia.getCliente();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
                numerazioneFonia.setCliente(clienteNew);
            }
            numerazioneFonia = em.merge(numerazioneFonia);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getNumerazioniFonia().remove(numerazioneFonia);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getNumerazioniFonia().add(numerazioneFonia);
                clienteNew = em.merge(clienteNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = numerazioneFonia.getId();
                if (findNumerazioneFonia(id) == null) {
                    throw new NonexistentEntityException("The numerazioneFonia with id " + id + " no longer exists.");
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
            NumerazioneFonia numerazioneFonia;
            try {
                numerazioneFonia = em.getReference(NumerazioneFonia.class, id);
                numerazioneFonia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The numerazioneFonia with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = numerazioneFonia.getCliente();
            if (cliente != null) {
                cliente.getNumerazioniFonia().remove(numerazioneFonia);
                cliente = em.merge(cliente);
            }
            em.remove(numerazioneFonia);
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

    public List<NumerazioneFonia> findNumerazioneFoniaEntities() {
        return findNumerazioneFoniaEntities(true, -1, -1);
    }

    public List<NumerazioneFonia> findNumerazioneFoniaEntities(int maxResults, int firstResult) {
        return findNumerazioneFoniaEntities(false, maxResults, firstResult);
    }

    private List<NumerazioneFonia> findNumerazioneFoniaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from NumerazioneFonia as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public NumerazioneFonia findNumerazioneFonia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NumerazioneFonia.class, id);
        } finally {
            em.close();
        }
    }

    public int getNumerazioneFoniaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from NumerazioneFonia as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
