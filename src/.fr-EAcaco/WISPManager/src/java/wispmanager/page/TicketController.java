/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.page;


/**
 *
 * @author torsello
 */
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import wispmanager.entities.Ticket;
import wispmanager.entities.jpa.TicketJpaController;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;
import wispmanager.jsf.util.JSONParser;

public class TicketController  {

    private String result_query="";
    private TicketJpaController ticketJpa;
    private Ticket ticket;
    private TicketConverter converter=new TicketConverter();
    FacesContext facesContext;
    ELContext elContext;
    ELResolver elResolver;
    List listTicket;
    private SelectItem[] mapTicket;

    public TicketController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();
        ticket=new Ticket();
        ticketJpa=(TicketJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "ticketJpaController");
        listTicket=ticketJpa.findTicketEntities();
    }

    

    public String crea_ticket()
    {
       try {
            ticket.setId(null);
            getTicketJpa().create(ticket);

            listTicket.clear();
            listTicket.add(ticket);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ticket";
    }

   
    public String seleziona_ticket(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        String cmd=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cmd");
        String value=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("value");

        HashMap MapField=(new JSONParser(value)).getMap();
        Long id= new Long((String) MapField.get("id"));
        ticket=getTicketJpa().findTicket(id);

        listTicket.clear();
        listTicket.add(getTicket());

        return "ticket";
    }

    public String salva_ticket()
    {


       try {
            getTicketJpa().edit(ticket);

            listTicket.clear();
            listTicket.add(ticket);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "ticket";
    }

    public String elenca_tutti_ticket()
    {
        listTicket.clear();
        listTicket=getTicketJpa().findTicketEntities();
        return "ticket";
    }

    public String elimina_ticket()
    {
       try {
            getTicketJpa().destroy(ticket.getId());
            listTicket.clear();
            listTicket=getTicketJpa().findTicketEntities();
       } catch (RollbackFailureException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "ticket";
    }



    /**
     * @return the result_query
     */
    public void query(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        update_query();
        result.append(result_query);        
    }
    
    public void update_query()
    {        
        result_query="{columns : [    { label : 'id', id : 'id'}, "+
                                     "{ label : 'Cliente', id : 'cliente'}," +
                                     "{ label : 'Data Emissione', id : 'dataemissione'}," +
                                     "{ label : 'Data Scadenza', id : 'datascadenza'}," +
                                     "{ label : 'Data Primo Utilizzo', id : 'dataprimoutilizzo'}," +
                                     "{ label : 'Data Scadenza Utilizzo', id : 'datascadenzautilizzo'}," +
                                     "{ label : 'Utente', id : 'utente'}," +
                                     "{ label : 'Password', id : 'password'}," +
                                     "{ label : 'Stato', id : 'stato'}]," +
                                     "rows : [";

        for(int i=0; i< listTicket.size();i++)
        {
            Ticket tckSel=(Ticket)listTicket.get(i);

            java.text.SimpleDateFormat df=new java.text.SimpleDateFormat();
            long id=tckSel.getId();
            String cliente=tckSel.getCliente().toString();
            String dataemissione=df.format(tckSel.getDataEmissione());
            String datascadenza=df.format(tckSel.getDataScadenza());
            String dataprimoutilizzo=df.format(tckSel.getDataPrimoUtilizzo());
            String datascadenzautilizzo=df.format(tckSel.getDataScadenzaUtilizzo());
            String utente=tckSel.getUtente();
            String password=tckSel.getPassword();
            String durataingiorni=tckSel.getDurataInGiorni().toString();
            String stato=tckSel.getStato();

            result_query+="{"+
                    "id: '"+id+"', " +
                    "cliente: '"+cliente+"', " +
                    "dataemissione: '"+dataemissione+"', " +
                    "datascadenza '"+datascadenza+"', " +
                    "dataprimoutilizzo: '"+dataprimoutilizzo+"', " +
                    "datascadenzautilizzo: '"+datascadenzautilizzo+"', " +
                    "utente: '"+utente+"', " +
                    "password: '"+password+"', " +
                    "durataingiorni: '"+durataingiorni+"', " +
                    "stato: '"+stato+"'}";
            if(i<listTicket.size()-1) result_query+=",";
        }
        result_query+="]}";
    }


    /**
     * @param result_query the result_query to set
     */
    public void setResult_query(String result_query) {
        this.result_query = result_query;
    }

    /**
     * @return the converter
     */
    public TicketConverter getConverter() {
        return converter;
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(TicketConverter converter) {
        this.converter = converter;
    }

    /**
     * @return the ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * @return the mapTicket
     */
    public SelectItem[] getmapTicket() {
        listTicket=getTicketJpa().findTicketEntities();
        mapTicket=new SelectItem[listTicket.size()];
        int i = 0;
        while(i<listTicket.size()){
            Ticket x = (Ticket) listTicket.get(i);
            mapTicket[i] = new SelectItem(x, x.getSeriale());
            i++;
        }
        return mapTicket;
    }

    /**
     * @param mapTicket the mapTicket to set
     */
    public void setmapTicket(SelectItem[] mapTicket) {
        this.mapTicket = mapTicket;
    }

    /**
     * @return the ticketJpa
     */
    public TicketJpaController getTicketJpa() {
        return ticketJpa;
    }

    /**
     * @param ticketJpa the ticketJpa to set
     */
    public void setTicketJpa(TicketJpaController ticketJpa) {
        this.ticketJpa = ticketJpa;
    }


   }




