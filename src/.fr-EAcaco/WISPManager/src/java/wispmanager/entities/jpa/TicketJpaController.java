/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.entities.jpa;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import wispmanager.entities.exceptions.NonexistentEntityException;
import wispmanager.entities.exceptions.RollbackFailureException;
import wispmanager.entities.Ticket;

/**
 *
 * @author torsello
 */
public class TicketJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "WISPManagerPU")
    private EntityManagerFactory emf = null;

    public TicketJpaController()
    {
        if(emf==null)
        {
            emf = Persistence.createEntityManagerFactory("WISPManagerPU");
        }
        if(utx==null)
        {
            try {
                Context ctx = new InitialContext();
                utx=(UserTransaction)ctx.lookup("java:comp/UserTransaction");
            } catch (NamingException ex) {
                Logger.getLogger(TicketJpaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ticket ticket) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(ticket);
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

    public void edit(Ticket ticket) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ticket = em.merge(ticket);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ticket.getId();
                if (findTicket(id) == null) {
                    throw new NonexistentEntityException("The ticket with id " + id + " no longer exists.");
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
            Ticket ticket;
            try {
                ticket = em.getReference(Ticket.class, id);
                ticket.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ticket with id " + id + " no longer exists.", enfe);
            }
            em.remove(ticket);
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

    public List<Ticket> findTicketEntities() {
        return findTicketEntities(true, -1, -1);
    }

    public List<Ticket> findTicketEntities(int maxResults, int firstResult) {
        return findTicketEntities(false, maxResults, firstResult);
    }

    private List<Ticket> findTicketEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Ticket as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Ticket findTicket(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ticket.class, id);
        } finally {
            em.close();
        }
    }

      public Ticket attiva_ticket(Ticket Tck) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Ticket as o WHERE o.utente='"+Tck.getUtente()+"'");
            List lstTck=q.getResultList();
            if(lstTck.size()!=1) return null;
            if(lstTck.size()==1)
            {
                Ticket TckSel=(Ticket)lstTck.get(0);
                if((Tck.getPassword().compareTo(TckSel.getPassword())==0) && (TckSel.getStato().compareTo("emesso")==0))
                {
                    TckSel.setNomecognome(Tck.getNomecognome());
                    TckSel.setNumerodocumento(Tck.getNumerodocumento());
                    TckSel.setIp(Tck.getIp());
                    TckSel.setMac(Tck.getMac());
                    TckSel.setStato("attivo");
                    
                    Date dataPrimUtilizzo=new Date();
                    Date dataScadenzaUtilizzo=new Date(dataPrimUtilizzo.getTime()+86400000*(TckSel.getDurataInGiorni()+1));
                    TckSel.setDataPrimoUtilizzo(dataPrimUtilizzo);
                    TckSel.setDataScadenzaUtilizzo(dataScadenzaUtilizzo);
                    
                    try {
                        edit(TckSel);
                        return TckSel;
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(TicketJpaController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RollbackFailureException ex) {
                        Logger.getLogger(TicketJpaController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(TicketJpaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
         } finally {
            em.close();
        }
        return null;
    }

    public int getTicketCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Ticket as o").getSingleResult()).intValue();
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
