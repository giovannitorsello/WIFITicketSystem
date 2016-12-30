/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;


/**
 *
 * @author torsello
 */
import converter.RadiusConverter;
import entities.Radius;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import jpa.RadiusJpaController;

public class RadiusController  {

    private Radius radius=new Radius();
    private RadiusJpaController radiusJpa=new RadiusJpaController();
    private RadiusConverter radiusCvr=new RadiusConverter();
    
    FacesContext facesContext;
    ELContext elContext;
    ELResolver elResolver;
    
    
    public RadiusController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();
        radius=new Radius();
        radiusJpa=(RadiusJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "radiusJpaController");
        List lisres=radiusJpa.findRadiusEntities();
        if(!lisres.isEmpty()) radius=(Radius) lisres.get(0);
    }
    

    public void save_radius()
    {
       try {
           List lisres=radiusJpa.findRadiusEntities();
            if(lisres.size()==1)
            {
                radiusJpa.edit(getRadius());
            }
            
            if(lisres.isEmpty())
            {
                radiusJpa.create(getRadius());
            }
                        
        } catch (Exception ex) {
            Logger.getLogger(RadiusController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * @return the radius
     */
    public Radius getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(Radius radius) {
        this.radius = radius;
    }
    
   }




