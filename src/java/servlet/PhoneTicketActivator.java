/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import entities.Ticket;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import jpa.ClienteJpaController;
import jpa.RadiusJpaController;
import jpa.TicketJpaController;
import jpa.WebSurferJpaController;
import util.IOdatabase;
import websocket.TicketServer;

/**
 *
 * @author torsello
 */
public class PhoneTicketActivator extends HttpServlet {
    
    private final ClienteJpaController cliJpaCtrl=new ClienteJpaController();
    private final WebSurferJpaController wsuJpaCtrl=new WebSurferJpaController();
    private final TicketJpaController tckJpaCtrl=new TicketJpaController();        
    private final RadiusJpaController radiusJpaCtrl=new RadiusJpaController();
    private final IOdatabase radius_db=new IOdatabase();
    
    private HttpSession getSession(final String sessionId) {
    	final ServletContext context = getServletContext();
    	final HttpSession session = (HttpSession) context.getAttribute(sessionId);
    	return session;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String phone_number=request.getParameter("phone_number");
        String ticket_password=request.getParameter("ticket_password");
        String action=request.getParameter("action");
        
        if(action.equals("confirm_phone"))
        {
            Ticket tck=new Ticket();
            tck=tckJpaCtrl.findTicketEntities_by_websurfer(phone_number).get(0);
            
            ServletContext servletContext = this.getServletContext();
            Session session=(Session)servletContext.getAttribute(phone_number);
            String tck_msg="{\"command\":\"confirm_phone\", "
                            + "\"phone_number\": \""+phone_number+"\", "
                            + "\"username_ticket\": \""+tck.getLogin()+"\", "
                            + "\"password_ticket\": \""+tck.getPassword()+"\"}";
            session.getBasicRemote().sendText(tck_msg);
        }
        
        if(action.equals("activate_ticket"))
        {
            Ticket tck=new Ticket();
            tck=tckJpaCtrl.findTicketEntities_by_websurfer(phone_number).get(0);

            ServletContext servletContext = this.getServletContext();
            Session session=(Session)servletContext.getAttribute(phone_number);
            String tck_msg="{\"command\":\"activate_ticket\", "
                            + "\"phone_number\": \""+phone_number+"\", "
                            + "\"username_ticket\": \""+tck.getLogin()+"\", "
                            + "\"password_ticket\": \""+tck.getPassword()+"\"}";
            session.getBasicRemote().sendText(tck_msg);        
        }
        
        TicketServer conn=new TicketServer();
        
        //response.getWriter();
    
    }
    
}
