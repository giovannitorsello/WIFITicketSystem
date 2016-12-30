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
import converter.UserConverter;
import converter.WebSurferConverter;
import entities.Cliente;
import entities.Ticket;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import jpa.ClienteJpaController;
import jpa.UserJpaController;
import jpa.WebSurferJpaController;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import util.EmailProcessorThread;
import util.PasswordGenerator;

public class ClienteController  {

    private Cliente cliente=new Cliente();
    private ClienteJpaController clienteJpa=new ClienteJpaController();
    private ClienteConverter clienteCvr=new ClienteConverter();
    
    private User user=new User();
    private UserJpaController userJpa=new UserJpaController();
    private UserConverter userCvr=new UserConverter();
        
    private WebSurferController webSurf=new WebSurferController();
    private WebSurferJpaController webSurfJpa=new WebSurferJpaController();
    private WebSurferConverter webSurfCvr=new WebSurferConverter();
    
    
    FacesContext facesContext;
    ELContext elContext;
    ELResolver elResolver;
    
    private List<Cliente> listCliente=new ArrayList();
    private List<User> listUtente=new ArrayList();
    
    public ClienteController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();
        cliente=new Cliente();
        user= new User();
        clienteJpa=(ClienteJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "clienteJpaController");
        listCliente=clienteJpa.findClienteEntities();
    }
    

    public void save_cliente()
    {
       try {
           //non esegue salvataggio se non viene impostata la partita iva
            if(cliente.getPartitaIva().isEmpty()) return;
            if(clienteJpa.checkExistClienteEntities(cliente))
            {
                clienteJpa.edit(cliente);
            }
            else
            {
                cliente.setId(null);
                clienteJpa.create(cliente);
            }
            
            listCliente.clear();
            listCliente.add(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public void save_utente()
    {
      try {
            if(user.getUtente().isEmpty()) return;
            if(user.getPassword().isEmpty()) return;
            
            user.setCliente(cliente);
            if(userJpa.checkExistUtenteEntities(user))
            {
                userJpa.edit(user);
            }
            else
            {                
                user.setId(null);
                userJpa.create(user);
            }
            notify_utente_by_email(user);
            listUtente.clear();
            listUtente.add(user);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } 
   }
    

    
    private void notify_utente_by_email(User user)
    {
            //Send email to client and websurfer
            String message=user.getReportUser();
            String subj="Accesso a sistema ticket di Wifinetcom - "+user.getCliente().getDenominazione();
            EmailProcessorThread ept=new EmailProcessorThread();
            Cliente cli=user.getCliente();
            if(!cli.getEmail().isEmpty()) 
            {
                ept.getSender().setTo(cliente.getEmail());
                ept.getSender().setBody(message);
                ept.getSender().setSubject(subj);
                ept.start();
            }    
    }
   
    
    
    public void get_all_cliente()
    {
        listCliente.clear();
        listCliente=clienteJpa.findClienteEntities();
    }

    public void selectCliente(SelectEvent event) {  
        cliente=(Cliente) event.getObject();  
        listUtente=userJpa.findUserEntities_by_cliente(cliente);
    } 
    
    public void updateDatatable(SelectEvent event) {  
        if(event!=null)
        {
            String query=(String) event.getObject();
            listCliente=clienteJpa.findClienteEntities_from_den(query);
        }
    } 
    
    
    public List<Cliente> autocompleteDenominazione(String query)
    {
        //List<Cliente> suggestions=new ArrayList();
        listCliente=clienteJpa.findClienteEntities_from_den(query);
        //for(Cliente p : listClienti) {  
        //    if(p.getDenominazione().startsWith(query))  
        //        suggestions.add(p);  
        //}  
          
        return listCliente;
    }

    public void delete_cliente()
    {        
       try {
            clienteJpa.destroy(cliente.getId());                        
            listCliente.remove(cliente);
       } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public void delete_utente()
    {        
       try {
            userJpa.destroy(user.getId());                        
            listUtente.remove(user);
       } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } 
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

    /**
     * @return the listCliente
     */
    public List<Cliente> getListCliente() {
        return listCliente;
    }

    /**
     * @param listCliente the listCliente to set
     */
    public void setListCliente(List<Cliente> listCliente) {
        this.listCliente = listCliente;
    }
    
    public void onRowSelectCliente(SelectEvent event) {  
        Cliente cli=(Cliente) event.getObject();
        //FacesMessage msg = new FacesMessage("Cliente selezionato", ((Cliente) event.getObject()).getModel());  
        //FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
  
    public void onRowUnselectCliente(UnselectEvent event) {  
        Cliente cli=(Cliente) event.getObject();
        //FacesMessage msg = new FacesMessage("Cliente deselezionato", ((Cliente) event.getObject()).getModel());    
        //FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  

    public void onRowSelectUtente(SelectEvent event) {  
        User usr=(User) event.getObject();
        //FacesMessage msg = new FacesMessage("Cliente selezionato", ((Cliente) event.getObject()).getModel());  
        //FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
  
    public void onRowUnselectUtente(UnselectEvent event) {  
        User usr=(User) event.getObject();
        //FacesMessage msg = new FacesMessage("Cliente deselezionato", ((Cliente) event.getObject()).getModel());    
        //FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        if(user!=null)
            this.user = user;
    }

    /**
     * @return the listUtente
     */
    public List<User> getListUtente() {
        return listUtente;
    }

    /**
     * @param listUtente the listUtente to set
     */
    public void setListUtente(List<User> listUtente) {
        this.listUtente = listUtente;
    }



   }




