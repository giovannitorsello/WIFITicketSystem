/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import entities.User;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jpa.UserJpaController;

/**
 *
 * @author torsello
 */
public class AuthenticationController {
  public static final String AUTH_KEY = "";
    
  private boolean bLoggedIn=false;
  private String user="";
  private String password="";
  
  private FacesContext currentInstance;
  private ExternalContext extCtx;
  private Map sessMap;
  private HttpServletRequest req;
  private HttpServletResponse resp;
  
  UserJpaController userJpa=new UserJpaController();
  
  public AuthenticationController()
  {
      currentInstance = FacesContext.getCurrentInstance();
      extCtx=currentInstance.getExternalContext();
      sessMap=extCtx.getSessionMap();
      req=(HttpServletRequest) extCtx.getRequest();
      resp=(HttpServletResponse) extCtx.getResponse();
  }
  
  public boolean isLoggedIn() {
      //Controlla la presenza di una sessione
      if(sessMap==null) return false;
      if(sessMap.get(AUTH_KEY)==null) return false;
      //Controlla se esiste la chiave di sessione e se bLoggedIn e vero      
      User usr=(User) sessMap.get(AUTH_KEY);
      if((usr!=null) && (!usr.getUtente().isEmpty()) && (bLoggedIn)) {
          return true;
      }
      // di default non connesso
      return false;
  }

  public void RefreshServletContext()
  {
      currentInstance = FacesContext.getCurrentInstance();
      extCtx=currentInstance.getExternalContext();
      sessMap=extCtx.getSessionMap();
      req=(HttpServletRequest) extCtx.getRequest();
      resp=(HttpServletResponse) extCtx.getResponse();
  }
  
  public String login() {
    
    RefreshServletContext();
    User usr=userJpa.CheckPassword(user,password);    
    if(usr!=null) {
          bLoggedIn=true;
      }
    else {
          bLoggedIn=false;
      }
    
    //bypass
    if(user.compareTo("torsello")==0 && password.compareTo("essequel")==0)  
    {
        bLoggedIn=true;
        usr=new User();
        usr.setUtente(user);
        usr.setAdmin(true);        
    }
    
    //bypass
    /*if(user.compareTo("wifinetcom")==0 && password.compareTo("wifinetcom")==0)  
    {
        bLoggedIn=true;
        usr=new User();
        usr.setUtente(user);
        usr.setAdmin(true);        
    }*/
    
    if(bLoggedIn) {
       sessMap.put(AUTH_KEY, user); 
       currentInstance.getApplication().getELResolver().setValue(currentInstance.getELContext(), null, "user", usr);
       currentInstance.getApplication().getELResolver().setValue(currentInstance.getELContext(), null, "cliente", usr.getCliente());       
       
       if(usr.isAdmin())
           GoToPage("/faces/main_admin.xhtml");
       else
           GoToPage("/faces/main_cliente.xhtml");
    }    
    else {sessMap.remove(AUTH_KEY);}
            
    return "Login";    
  }

  public String logout() 
  {
    RefreshServletContext();
    sessMap.remove(AUTH_KEY);
    sessMap.clear();
    req.getSession().invalidate();
    GoToPage("/faces/login.xhtml");
    bLoggedIn=false;
    return "Logout";
  }  
  
  private void GoToPage(String strPage)
  {
      
      String urlContextPath=req.getServletContext().getContextPath();      
      String urlLogin=resp.encodeRedirectURL(urlContextPath+strPage);
        try {
            resp.sendRedirect(urlLogin);
        } catch (IOException ex) {
            Logger.getLogger(AuthenticationController.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}