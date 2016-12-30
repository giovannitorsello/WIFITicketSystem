/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import entities.Ticket;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jpa.TicketJpaController;

/**
 *
 * @author torsello
 */
public class TicketServlet extends HttpServlet {

    PrintWriter out =null;
    private boolean bValid=false;
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        CheckTicket(request,response);

        out = response.getWriter();
        try {
            /* TODO output your page here*/
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Procedura di registrazione</title>");
            out.println("</head>");
            out.println("<body>");

            if(bValid==true)
            {
                out.println("<p align='center'><h1>Registazione avvenuta con successo buona navigazione</h1></p>");
                out.println("<p align='center'><h1>Registration succefull</h1></p>");
                out.println("<p align='center'><a href='http://www.google.it'>Google</a></p>");
            }

            else

            {
                out.println("<p align='center'><h1>Registazione fallita controlla le credenziali ritornando alla pagina precedente</h1></p>");
                out.println("<p align='center'><h1>Registration failed please go back</h1></p>");
            }
            out.println("</body>");
            out.println("</html>");           
        } finally {
            out.close();
        }

        
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Sistema di autenticazione per ticket";
    }// </editor-fold>

    protected boolean CheckTicket(HttpServletRequest request, HttpServletResponse response)
    {
        bValid=false;
        TicketJpaController TckJpa=new TicketJpaController();
        Ticket Tck=new Ticket();

        String mac=request.getParameter("mac");
        if(mac==null || mac.isEmpty()) return false;

        String ip=request.getParameter("ip");
        if(ip==null || ip.isEmpty()) return false;

        String note=request.getParameter("note");
        if(note==null || note.isEmpty()) return false;
        
        String ticketuser=request.getParameter("ticketuser");
        if(ticketuser==null || ticketuser.isEmpty()) return false;

        String ticketpassword=request.getParameter("ticketpassword");
        if(ticketpassword==null || ticketpassword.isEmpty()) return false;


        Tck.setNote(note);        
        Tck.setPassword(ticketpassword);
        Ticket TckSel=TckJpa.attiva_ticket(Tck);

        if(TckSel!=null)
        {
            note=note.replace(" ", "_");
            note=note.replace("'", "_");
            java.text.SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            Date datainizio=TckSel.getDataPrimoUtilizzo();
            Date datafine=TckSel.getDataScadenzaUtilizzo();
            String strDataInizio=df.format(datainizio);
            String strDataFine=df.format(datafine);
            String cmd=
            "/home/captiveportal/script/attiva_ssh.sh "+
            note+" "+" "+mac+" "+strDataInizio+" "+strDataFine+" "+"reg";
            try {
                Process p = Runtime.getRuntime().exec(cmd);
                if(p.waitFor()==0) { 
                    bValid=true;
                }
                else {
                    bValid=false;
                }                
            } catch (IOException ex) {
                Logger.getLogger(TicketServlet.class.getName()).log(Level.SEVERE, null, ex);
                bValid=false;
            } catch (InterruptedException ex) {
                Logger.getLogger(TicketServlet.class.getName()).log(Level.SEVERE, null, ex);
                bValid=false;
            }
        }

        return bValid;
    }

}
