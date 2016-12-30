/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author torsello
 */
public class MenuController {
    private String urlDestination="gestione_clienti";

    private void update()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response=(HttpServletResponse) context.getExternalContext().getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);   
        try {
            response.flushBuffer();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void clienti()
    {        
        urlDestination="gestione_clienti";        
    }
    
    public void utenti()
    {        
        urlDestination="gestione_utenti";        
    }
    
    public void ticket()
    {        
        urlDestination="gestione_ticket";        
    }
    
    public void radius()
    {        
            urlDestination="gestione_radius";            
    }
    
    public void stato_sistema()
    {        
            urlDestination="stato_sistema";
    }
    /**
     * @return the urlDestination
     */
    public String getUrlDestination() {
        return urlDestination;
    }

    /**
     * @param urlDestination the urlDestination to set
     */
    public void setUrlDestination(String urlDestination) {
        this.urlDestination = urlDestination;
    }
    
}
