/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;


/**
 *
 * @author torsello
 */
import converter.ClienteConverter;
import converter.RadiusConverter;
import converter.TicketConverter;
import converter.UserConverter;
import converter.WebSurferConverter;
import entities.Cliente;
import entities.Radius;
import entities.Ticket;
import entities.User;
import entities.WebSurfer;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import jpa.ClienteJpaController;
import jpa.RadiusJpaController;
import jpa.TicketJpaController;
import jpa.UserJpaController;
import jpa.WebSurferJpaController;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import util.EmailProcessorThread;
import util.IOdatabase;
import util.PasswordGenerator;

public class TicketController  {

    private Ticket ticket=new Ticket();
    private Ticket ticket_select=new Ticket();
    private TicketJpaController ticketJpa;    
    private TicketConverter converter=new TicketConverter();
    
    private WebSurfer webSurfer=new WebSurfer();
    private WebSurferJpaController webSurfJpa=new WebSurferJpaController();
    private WebSurferConverter webSurfCvr=new WebSurferConverter();
    
    private Cliente cliente=new Cliente();
    private ClienteJpaController clienteJpa=new ClienteJpaController();
    private ClienteConverter clienteCvr=new ClienteConverter();
    
    private User utente=new User();
    private UserJpaController utenteJpa=new UserJpaController();
    private UserConverter utenteCvr=new UserConverter();
    
    
    private Radius radius=new Radius();
    private RadiusJpaController radiusJpa=new RadiusJpaController();
    private RadiusConverter radiusCvr=new RadiusConverter();
    
    
    private IOdatabase radius_db=new IOdatabase();
    private String reportTicket="";
    private String reportError="Servizio attivo";
    
   
    FacesContext facesContext;
    ELContext elContext;
    ELResolver elResolver;
    private List<Ticket> listTicket=new ArrayList();
    private List<WebSurfer> listWebSurfer=new ArrayList();
    
    public TicketController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();
        
        ticketJpa=(TicketJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "ticketJpaController");
        webSurfJpa=(WebSurferJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "webSurferJpaController");
        clienteJpa=(ClienteJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "clienteJpaController");
        utenteJpa=(UserJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "userJpaController");        
        radiusJpa=(RadiusJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "radiusJpaController");
        
        ticket=new Ticket();
        webSurfer=new WebSurfer();

        //Get cliente from session
        cliente=(Cliente)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "cliente");
        utente=(User)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "user");
        
        List listServerRadius=radiusJpa.findRadiusEntities();
        if(!listServerRadius.isEmpty())
        {
            radius=(Radius) listServerRadius.get(0);                        
            radius_db.setUser(radius.getUtente());
            radius_db.setPassword(radius.getPassword());
            radius_db.setUrlDB(radius.getUrlDB());
        }
        else
            radius=new Radius();
        
        
        //Verifica connessione a database radius
        boolean b_db_radius=radius_db.OpenConnection();         
        if(b_db_radius==false) 
        {
             reportError="Servizio non disponibile (no radius)";
             return;
        }
        else
            radius_db.CloseConnection();
    }

    public void update_password_utente()
    {
        try {            
            utenteJpa.edit(utente);
            notify_password_by_email(utente);
        } catch (Exception ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void select_ticket(SelectEvent event)
    {
        Ticket tck=(Ticket) event.getObject();
        ticket_select=tck;    
        reportTicket=getReportTicket();
    }
    
    public String update_report_ticket()
    {
        reportTicket=ticket_select.getReportTicket();
        return reportTicket;
    }
    
    public void select_web_surfer()
    {

    }
    
    public void create_webSurfer() throws Exception
    {
        long wsrf=webSurfJpa.checkExistWebSurferEntities(webSurfer);
        if(wsrf!=0)
        {
            webSurfer.setId(wsrf);
            webSurfJpa.edit(webSurfer);
        }
        else
        {
            webSurfer.setId(null);
            webSurfJpa.create(webSurfer);
            listWebSurfer.add(webSurfer);
        }    
    }
    
    public void create_ticket()
    {
       try {
            //Inserisce il consumatore finale
            if(webSurfer.getDenominazione().isEmpty()) {
               return;
           }
            //if(webSurfer.getEmail().isEmpty()) return;
            //Verifica connessione a database radius
            boolean b_db_radius=radius_db.OpenConnection(); 
            b_db_radius=true;
            if(b_db_radius==false) 
            {
                reportError="Servizio non disponibile (no radius)";
                return;
            }
            create_webSurfer();
                       
            ticket.setId(null);
            ticket.setWebsurfer(webSurfer);
            ticket.setCliente(cliente);
            ticket.prepare_to_store();
            
            String strTicketNote=webSurfer.getDenominazione()+"-"+new String(webSurfer.getNote().getBytes("ISO-8859-15"),"UTF-8");                        
            ticket.setNote(strTicketNote);
            ticketJpa.create(ticket);
            
            //insert login and pass in remote radius database
            radius_db.setUser(radius.getUtente());
            radius_db.setPassword(radius.getPassword());
            radius_db.setUrlDB(radius.getUrlDB());            
            radius_db.ExecuteCommand(radius.getInsertCommand(ticket));
            radius_db.CloseConnection();
            
            notify_ticket_by_email(ticket);
             
            ticket_select=ticket;            
            listTicket.clear();
            listTicket.add(ticket_select);
            
            //svuota per l'inserimento successivo
            ticket=new Ticket();
            webSurfer=new WebSurfer();
            
        } catch (Exception ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

    
    private void notify_ticket_by_email(Ticket ticket)
    {
            //Send email to client and websurfer
            String message="";
            message = ticket.getReportTicket();
            String subj="Wifinetcom ticket system - "+ticket.getSeriale();
            EmailProcessorThread ept=new EmailProcessorThread();
            if(!cliente.getEmail().isEmpty()) 
            {
                ept.getSender().setTo(cliente.getEmail());
                if(!webSurfer.getEmail().isEmpty()){             
                    ept.getSender().setCc(webSurfer.getEmail());
                }
                ept.getSender().setBcc("ticket@wifinetcom.net");
                ept.getSender().setBody(message);
                ept.getSender().setSubject(subj);
                ept.start();
            }    
    }
    
    private void notify_password_by_email(User utente)
    {
            //Send email to cliente        
            String message=utente.getReportUser();
            String subj="Password Aggiornata - Wifinetcom ticket system";
            EmailProcessorThread ept=new EmailProcessorThread();
            Cliente cli=utente.getCliente();
            if(!cli.getEmail().isEmpty()) 
            {
                ept.getSender().setTo(cli.getEmail());                
                ept.getSender().setBody(message);
                ept.getSender().setSubject(subj);
                ept.start();
            }    
    }
    
    public void save_ticket()
    {
       try {
            ticketJpa.edit(ticket);
            getListTicket().clear();
            getListTicket().add(ticket);
        } catch (Exception ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public void get_all_ticket()
    {
        getListTicket().clear();
        setListTicket(ticketJpa.findTicketEntities());
    }

    public void delete_ticket()
    {
       try {
            boolean b_db_radius=radius_db.OpenConnection(); 
            if(b_db_radius==false) 
            {
                reportError="Servizio non disponibile (no radius)";                
            }
            else
            {
                //delete login and pass in remote radius database
                radius_db.setUser(radius.getUtente());
                radius_db.setPassword(radius.getPassword());
                radius_db.setUrlDB(radius.getUrlDB());
                radius_db.ExecuteCommand(radius.getRemoveCommand(ticket_select));
                radius_db.CloseConnection();
            }
           
            ticketJpa.destroy(ticket_select.getId());
            listTicket.remove(ticket_select);
       } catch (Exception ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public List<Ticket> autocompleteTicket(String strSearch)
    {
        listTicket=ticketJpa.findTicketEntities_by_websurfer(strSearch);        
        return listTicket;
    }
    
    public List<Ticket> autocompleteTicket_cliente(String strSearch)
    {
        listTicket=ticketJpa.findTicketEntities_cliente_by_websurfer(cliente, strSearch);        
        return listTicket;
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
        if(ticket!=null)
            this.ticket = ticket;
    }

    /**
     * @return the listTicket
     */
    public List getListTicket() {
        return listTicket;
    }

    /**
     * @param listTicket the listTicket to set
     */
    public void setListTicket(List listTicket) {
        if(listTicket!=null)
            this.listTicket = listTicket;
    }

    /**
     * @return the webSurfer
     */
    public WebSurfer getWebSurfer() {
        return webSurfer;
    }

    /**
     * @param webSurfer the webSurfer to set
     */
    public void setWebSurfer(WebSurfer webSurfer) {
        if(webSurfer!=null)
            this.webSurfer = webSurfer;
    }

    /**
     * @return the listWebSurfer
     */
    public List<WebSurfer> getListWebSurfer() {
        return listWebSurfer;
    }

    /**
     * @param listWebSurfer the listWebSurfer to set
     */
    public void setListWebSurfer(List<WebSurfer> listWebSurfer) {
        if(listWebSurfer!=null)
            this.listWebSurfer = listWebSurfer;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        if(cliente!=null)
            this.cliente = cliente;
    }


    public void onRowSelectTicket(SelectEvent event) {  
        Ticket tck=(Ticket) event.getObject();
        ticket_select=tck;
        webSurfer=tck.getWebsurfer();

        String note="";
        try {            
            note=new String(webSurfer.getNote().getBytes("ISO-8859-15"),"UTF-8");
            webSurfer.setNote(note);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }                    
    }  
  
    public void onRowUnselectTicket(UnselectEvent event) {  
        Ticket tck=(Ticket) event.getObject();        
    }  

    public void get_all_active_ticket()
    {
        listTicket=ticketJpa.findTicketEntities_active(cliente);                
    }

    /**
     * @param reportTicket the reportTicket to set
     */
    public void setReportTicket(String reportTicket) {
        if(reportTicket!=null && (!reportTicket.isEmpty()))
            this.reportTicket = reportTicket;
    }
    
    public String getReportTicket()
    {
        if(ticket_select!=null)
        {
            reportTicket=ticket_select.getReportTicket();
        }        
       return reportTicket;
    }

    /**
     * @return the reportError
     */
    public String getReportError() {
        return reportError;
    }

    /**
     * @param reportError the reportError to set
     */
    public void setReportError(String reportError) {
        this.reportError = reportError;
    }

    /**
     * @return the utente
     */
    public User getUtente() {
        return utente;
    }

    /**
     * @param utente the utente to set
     */
    public void setUtente(User utente) {
        this.utente = utente;
    }

    /**
     * @return the ticket_select
     */
    public Ticket getTicket_select() {
        if(ticket_select==null) ticket_select=ticket;
        return ticket_select;
    }

    /**
     * @param ticket_select the ticket_select to set
     */
    public void setTicket_select(Ticket ticket_select) {
        this.ticket_select = ticket_select;
    }

    
   }




