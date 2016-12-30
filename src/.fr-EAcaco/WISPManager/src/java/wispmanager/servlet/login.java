/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.el.ELContext;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
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
public class login implements  javax.servlet.Filter {

    private FilterConfig filterConfig;
    private String loginForm;
    private String homeForm;
 

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        loginForm = this.filterConfig.getInitParameter("login_form");
        homeForm = this.filterConfig.getInitParameter("home_form");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


     /*   FacesContextFactory factory = (FacesContextFactory)
                FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);

        if(factory!=null)
        {
            factory.
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
       

        if( session != null) {
            String currentUser = (String)session.getAttribute("user");
                if (currentUser == null)
                {                    
                    System.out.println("currentUser null");
                        ((HttpServletResponse) response).sendRedirect(loginForm);                                            
                }
        }
*/
        chain.doFilter(request,response);
    }

    public void destroy() {

    }

    private boolean CheckUser(ServletRequest request, ServletResponse response) {
        String user=request.getParameter("user");
        if(user==null || user.isEmpty()) return false;

        String password=request.getParameter("password");
        if(password==null || password.isEmpty()) return false;

        if(user.compareTo("operatore")==0 &&
           password.compareTo("vincenzo")==0)
            return true;
        else
            return false;
    }

}
