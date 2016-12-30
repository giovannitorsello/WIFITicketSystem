/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

/**
 *
 * @author torsello
 */
import entities.Cliente;
import entities.Radius;
import entities.Ticket;
import entities.WebSurfer;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
 
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import jpa.ClienteJpaController;
import jpa.RadiusJpaController;
import jpa.TicketJpaController;
import jpa.WebSurferJpaController;
import util.EmailProcessorThread;
import util.IOdatabase;

 
/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo
 * Where "localhost" is the address of the host,
 * "EchoChamber" is the name of the package
 * and "echo" is the address to access this class from the server
 */
@ServerEndpoint(value = "/echo", configurator = TicketServerWebSocketConfigurator.class) 
public class TicketServer {
    
    private final ClienteJpaController cliJpaCtrl=new ClienteJpaController();
    private final WebSurferJpaController wsuJpaCtrl=new WebSurferJpaController();
    private final TicketJpaController tckJpaCtrl=new TicketJpaController();        
    private final RadiusJpaController radiusJpaCtrl=new RadiusJpaController();
    private final IOdatabase radius_db=new IOdatabase();
    
    HttpSession httpSession = null;
    ServletContext servletContext = null;            
    private Session session = null;
    
    Ticket tck=new Ticket();
    
    /**
     * @param session
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        System.out.println(session.getId() + " has opened a connection"); 
        try {
            String tck_msg="{\"command\":\"websocket_open\"}";
            this.session=session;
            httpSession = (HttpSession) config.getUserProperties().get("HttpSession");
            servletContext = (ServletContext) config.getUserProperties().get("ServletContext");
            session.getBasicRemote().sendText("websocket connected");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("Message from " + session.getId() + ": " + message);

        //getting phone first phase
        if(message.contains("<make_phone_entry>"))
        {
            StringTokenizer st=new StringTokenizer(message,"<>");
            String command=st.nextToken();            
            String phone=st.nextToken();
            String email=st.nextToken();
            String customer=st.nextToken();
            
            //Find customer            
            Cliente cli=(Cliente) cliJpaCtrl.findClienteEntities_from_den(customer).get(0);
            
            //Find previos phone created web surfer and update
            WebSurfer wsu=(WebSurfer) wsuJpaCtrl.findWebSurferEntities_from_den(phone).get(0);
            wsu.setEmail(email);
            wsu.setNote("ticket made by phone");
            try {
                wsuJpaCtrl.edit(wsu);
                
                //Saerch for old tickets active
                List listTicketWebSurfer=tckJpaCtrl.findTicketEntities_active(wsu);
                if(!listTicketWebSurfer.isEmpty()) 
                {
                    tck=(Ticket) listTicketWebSurfer.get(0);
                    tck.setLogin(phone); 
                    tckJpaCtrl.edit(tck);
                }
                else
                {
                    tck.prepare_to_store();
                    tck.setCliente(cli);
                    tck.setWebsurfer(wsu);
                    tck.prepare_to_store();
                    tck.setStato("emesso"); 
                    tck.setNote(phone+"|"+email+"customer");
                    tck.setLogin(phone);                    
                    tckJpaCtrl.create(tck);
                }
                
                String tck_msg="{\"command\":\"ticket_made\", "
                            + "\"username_ticket\": \""+tck.getLogin()+"\", "
                            + "\"password_ticket\": \""+tck.getPassword()+"\", "
                            + "\"webpage\":  \""+cli.getWeb()+"\"}";
                
                //send ticket credential to browser and show to client and waiting for confirmation call
                session.getBasicRemote().sendText(tck_msg);
                
                //Set attribute for PhoneActivatorServlet
                servletContext.setAttribute(tck.getLogin(), session);
                
                
            } catch (Exception ex) {
                Logger.getLogger(TicketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }
        //getting phone first phase
        if(message.contains("<activate_ticket>"))
        {
            //Find radiusServer            
            Radius radius=new Radius();
            List listServerRadius=radiusJpaCtrl.findRadiusEntities();
            if(!listServerRadius.isEmpty())
            {
                radius=(Radius) listServerRadius.get(0);                        
                radius_db.setUser(radius.getUtente());
                radius_db.setPassword(radius.getPassword());
                radius_db.setUrlDB(radius.getUrlDB());
            }
            
            Ticket tck=new Ticket();
            //insert login and pass in remote radius database
            radius_db.setUser(radius.getUtente());
            radius_db.setPassword(radius.getPassword());
            radius_db.setUrlDB(radius.getUrlDB());            
            radius_db.ExecuteCommand(radius.getInsertCommand(tck));
            radius_db.CloseConnection();
                    
            notify_ticket_by_email(tck);
        }
        
    }
    
    private void notify_ticket_by_email(Ticket ticket)
    {
            //Send email to client and websurfer
            String message="";
            message = ticket.getReportTicket();
            String subj="Wifinetcom ticket system - "+ticket.getSeriale();
            EmailProcessorThread ept=new EmailProcessorThread();
            Cliente cliente=ticket.getCliente();
            WebSurfer webSurfer=ticket.getWebsurfer();
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
 
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     * @param session
     */
    @OnClose
    public void onClose(Session session){
        System.out.println("Session " +session.getId()+" has ended");
    }
}