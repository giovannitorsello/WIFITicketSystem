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
import wispmanager.entities.exceptions.NonexistentEntityException;
import wispmanager.entities.exceptions.RollbackFailureException;
import wispmanager.entities.Cliente;
import wispmanager.entities.SerieTicket;

/**
 *
 * @author torsello
 */
public class SerieTicketJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "WISPManagerPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SerieTicket serieTicket) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente cliente = serieTicket.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId());
                serieTicket.setCliente(cliente);
            }
            em.persist(serieTicket);
            if (cliente != null) {
                cliente.getSerieTicket().add(serieTicket);
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

    public void edit(SerieTicket serieTicket) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SerieTicket persistentSerieTicket = em.find(SerieTicket.class, serieTicket.getId());
            Cliente clienteOld = persistentSerieTicket.getCliente();
            Cliente clienteNew = serieTicket.getCliente();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
                serieTicket.setCliente(clienteNew);
            }
            serieTicket = em.merge(serieTicket);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getSerieTicket().remove(serieTicket);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getSerieTicket().add(serieTicket);
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
                Long id = serieTicket.getId();
                if (findSerieTicket(id) == null) {
                    throw new NonexistentEntityException("The serieTicket with id " + id + " no longer exists.");
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
            SerieTicket serieTicket;
            try {
                serieTicket = em.getReference(SerieTicket.class, id);
                serieTicket.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The serieTicket with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = serieTicket.getCliente();
            if (cliente != null) {
                cliente.getSerieTicket().remove(serieTicket);
                cliente = em.merge(cliente);
            }
            em.remove(serieTicket);
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

    public List<SerieTicket> findSerieTicketEntities() {
        return findSerieTicketEntities(true, -1, -1);
    }

    public List<SerieTicket> findSerieTicketEntities(int maxResults, int firstResult) {
        return findSerieTicketEntities(false, maxResults, firstResult);
    }

    private List<SerieTicket> findSerieTicketEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from SerieTicket as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SerieTicket findSerieTicket(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SerieTicket.class, id);
        } finally {
            em.close();
        }
    }

    public int getSerieTicketCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from SerieTicket as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
