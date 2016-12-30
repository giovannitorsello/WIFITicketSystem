/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;


/**
 *
 * @author torsello
 */
import converter.WebSurferConverter;
import entities.WebSurfer;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import jpa.WebSurferJpaController;

public class WebSurferController  {

    private WebSurferJpaController webSurferJpa;
    private WebSurfer webSurfer;
    private WebSurferConverter converter=new WebSurferConverter();
    FacesContext facesContext;
    ELContext elContext;
    ELResolver elResolver;
    private List listWebSurfer;
    
    public WebSurferController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();
        webSurfer=new WebSurfer();
        webSurferJpa=(WebSurferJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "webSurferJpaController");
        listWebSurfer=webSurferJpa.findWebSurferEntities();
    }

    public void create_webSurfer()
    {
       try {
            webSurfer.setId(null);
            webSurferJpa.create(webSurfer);

            getListWebSurfer().clear();
            getListWebSurfer().add(webSurfer);
        } catch (Exception ex) {
            Logger.getLogger(WebSurferController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

    public void save_webSurfer()
    {
       try {
            webSurferJpa.edit(webSurfer);
            getListWebSurfer().clear();
            getListWebSurfer().add(webSurfer);
        } catch (Exception ex) {
            Logger.getLogger(WebSurferController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public void get_all_webSurfer()
    {
        getListWebSurfer().clear();
        setListWebSurfer(webSurferJpa.findWebSurferEntities());
    }

    public void delete_webSurfer()
    {
       try {
            webSurferJpa.destroy(webSurfer.getId());
            getListWebSurfer().clear();
            setListWebSurfer(webSurferJpa.findWebSurferEntities());
       } catch (Exception ex) {
            Logger.getLogger(WebSurferController.class.getName()).log(Level.SEVERE, null, ex);
        } 
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
        this.webSurfer = webSurfer;
    }

    /**
     * @return the listWebSurfer
     */
    public List getListWebSurfer() {
        return listWebSurfer;
    }

    /**
     * @param listWebSurfer the listWebSurfer to set
     */
    public void setListWebSurfer(List listWebSurfer) {
        this.listWebSurfer = listWebSurfer;
    }

    
    public List<WebSurfer> autocompleteDenominazione(String query)
    {
        //List<Cliente> suggestions=new ArrayList();
        listWebSurfer=webSurferJpa.findWebSurferEntities_from_den(query);
        //for(Cliente p : listClienti) {  
        //    if(p.getDenominazione().startsWith(query))  
        //        suggestions.add(p);  
        //}  
          
        return listWebSurfer;
    }

   }




