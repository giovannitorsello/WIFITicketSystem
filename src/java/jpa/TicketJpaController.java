/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import entities.Cliente;
import entities.Ticket;
import entities.WebSurfer;
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
public class TicketJpaController implements Serializable {
    @PersistenceUnit(unitName = "WIFITicketSystemPU")
    private EntityManagerFactory emf=null;

    public TicketJpaController()
    {
        if(emf==null) {
            emf = Persistence.createEntityManagerFactory("WIFITicketSystemPU");
        }        
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ticket ticket) throws Exception {
        EntityManager em = null;
        try {            
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ticket);
            em.getTransaction().commit();
        } catch (Exception ex) {
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ticket ticket) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ticket = em.merge(ticket);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ticket.getId();
                if (findTicket(id) == null) {
                    throw new Exception("The ticket with id " + id + " no longer exists.");
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
            Ticket ticket;
            try {
                ticket = em.getReference(Ticket.class, id);
                ticket.getId();
            } catch (EntityNotFoundException enfe) {
                throw new Exception("The ticket with id " + id + " no longer exists.", enfe);
            }
            em.getTransaction().begin();
            em.remove(ticket);
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
    
    public long checkExistTicketEntities(Ticket ticket) {
        EntityManager em = getEntityManager();
        try {            
            Query q = em.createQuery("select object(o) from WebSurfer as o where ("                    
                    + "o.login='"+ticket.getLogin()+"');");            
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
    

      public Ticket attiva_ticket(Ticket Tck) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Ticket as o WHERE o.login='"+Tck.getLogin()+"'");
            List lstTck=q.getResultList();
            if(lstTck.size()!=1) return null;
            if(lstTck.size()==1)
            {
                Ticket TckSel=(Ticket)lstTck.get(0);
                if((Tck.getPassword().compareTo(TckSel.getPassword())==0) && (TckSel.getStato().compareTo("emesso")==0))
                {
                    TckSel.setNote(Tck.getNote());                    
                    TckSel.setStato("attivo");
                    
                    Date dataPrimUtilizzo=new Date();
                    Date dataScadenzaUtilizzo=new Date(dataPrimUtilizzo.getTime()+86400000*(TckSel.getDurataInGiorni()+1));
                    TckSel.setDataPrimoUtilizzo(dataPrimUtilizzo);
                    TckSel.setDataScadenzaUtilizzo(dataScadenzaUtilizzo);
                    
                    try {
                        edit(TckSel);
                        return TckSel;
                    } catch (Exception ex) {
                        Logger.getLogger(TicketJpaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
         } 
        finally 
        {
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

    public List<Ticket> findTicketEntities_by_websurfer(String strSearch) {
        EntityManager em = getEntityManager();
        try {            
            Query q = em.createQuery("select object(o) from Ticket as o where ("                    
                    + "o.websurfer.denominazione LIKE '"+strSearch+"%'"
                    + "OR o.websurfer.email LIKE '"+strSearch+"%'"
                    + ");");            
            return q.getResultList();
        } finally {
            em.close();
        }        
    }

    public List<Ticket> findTicketEntities_active(Cliente cli) {
        EntityManager em = getEntityManager();
        try {            
            Query q = em.createQuery("select object(o) from Ticket as o where ("                    
                    + " (o.stato = 'emesso' OR o.stato='in utilizzo')"
                    + " AND o.cliente.id='"+cli.getId()+"') ORDER BY o.dataEmissione DESC;");            
            return q.getResultList();
        } finally {
            em.close();
        }    
    }

    public List<Ticket> findTicketEntities_active(WebSurfer wsu) {
        EntityManager em = getEntityManager();
        try {            
            Query q = em.createQuery("select object(o) from Ticket as o where ("                    
                    + " (o.stato = 'emesso' OR o.stato='in utilizzo')"
                    + " AND o.websurfer.id='"+wsu.getId()+"') ORDER BY o.dataEmissione DESC;");            
            return q.getResultList();
        } finally {
            em.close();
        }    
    }
    
    public List<Ticket> findTicketEntities_cliente_by_websurfer(Cliente cli, String strSearch) {
        EntityManager em = getEntityManager();
        try {            
            Query q = em.createQuery("select object(o) from Ticket as o where ("                    
                    + "(o.websurfer.denominazione LIKE '"+strSearch+"%'"
                    + " OR o.websurfer.email LIKE '"+strSearch+"%')"
                    + " AND o.cliente.id='"+cli.getId()+"');");
            return q.getResultList();
        } finally {
            em.close();
        }     
    }


}
