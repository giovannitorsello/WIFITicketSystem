package converter;

import entities.User;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.UserJpaController;

/**
 *
 * @author torsello
 */
public class UserConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = new Long(string);
        UserJpaController controller = (UserJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "userJpaController");
        Object obj=controller.findUser(id);
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
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: entities.User");
        }
    }

}
