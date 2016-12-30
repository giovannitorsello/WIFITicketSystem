package converter;

import entities.WebSurfer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.WebSurferJpaController;

/**
 *
 * @author torsello
 */
public class WebSurferConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = new Long(string);
        WebSurferJpaController controller = (WebSurferJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "webSurferJpaController");
        Object obj=controller.findWebSurfer(id);
        return  obj;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof WebSurfer) {
            WebSurfer o = (WebSurfer) object;
            Long id=o.getId();
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: entities.WebSurfer");
        }
    }

}
