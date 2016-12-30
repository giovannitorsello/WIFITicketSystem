/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import entities.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author torsello
 */
public class AuthenticationFilter implements Filter {
    private FilterConfig config;
    boolean bLoggedIn=false;
    
    @Override
  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain chain) throws IOException, ServletException {
    
    String urlContextPath=req.getServletContext().getContextPath();
    String urlLogin=((HttpServletResponse) resp).encodeRedirectURL(urlContextPath+"/faces/login.xhtml");

    //calcola se autorizzato
    boolean bAuth=false;
    HttpSession session = ((HttpServletRequest)req).getSession(false);
    if (session != null) 
    {
        User usr = (User) session.getAttribute("user");        
        if(usr != null) bAuth=true;
    }
    
    String requestedPage = getRequestedPage((HttpServletRequest)req); 

    //Se vuoto redirige sul login
    if (requestedPage.equals("")){((HttpServletResponse)resp).sendRedirect(urlLogin);return;} 
    
    //Autorizza la pagina di login se non autorizzato
    if (requestedPage.equals("faces/login.xhtml") && !bAuth){chain.doFilter(req, resp);return;} 
    //se autorizzato continua
    else if(bAuth) {chain.doFilter(req, resp);return;}
                    
    //Default redirect sulla pagina di login
    ((HttpServletResponse)resp).sendRedirect(urlLogin);         

  }

    @Override
  public void init(FilterConfig config) throws ServletException {
    this.config = config;
  }

    @Override
  public void destroy() {
    config = null;
  }
    
  private String getRequestedPage(HttpServletRequest aHttpRequest) 
  { 
        String url = aHttpRequest.getRequestURI(); 
        int firstSlash = url.indexOf("/",1); 
        String requestedPage = null; 
        if (firstSlash != -1) requestedPage = 
            url.substring(firstSlash + 1, url.length()); 
        return requestedPage; 
    } 
    
}
