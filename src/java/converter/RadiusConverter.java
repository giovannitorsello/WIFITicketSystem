package converter;

import entities.User;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.RadiusJpaController;

/**
 *
 * @author torsello
 */
public class RadiusConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = new Long(string);
        RadiusJpaController controller = (RadiusJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "radiusJpaController");
        Object obj=controller.findRadius(id);
        return  obj;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof User) {
            User o = (User) object;
            Long id=o.getId();
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: entities.Radius");
        }
    }

}
