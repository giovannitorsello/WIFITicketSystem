/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.page;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author torsello
 */

public class LoginController {

    private String user;
    private String password;

    /** Creates a new instance of LoginController */
    public LoginController() {
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

    public String login()
    {
        FacesContext f=FacesContext.getCurrentInstance();
        HttpSession sess=(HttpSession) f.getExternalContext().getSession(false);
        sess.setAttribute("FacesContext", f);
        if(user.compareTo("operatore")==0 &&
           password.compareTo("vincenzo")==0)
        {
            sess.setAttribute("user", user);
            return "success";
        }
        else
            return "fail";

    }
}
