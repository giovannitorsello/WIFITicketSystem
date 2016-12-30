package converter;

import entities.Cliente;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.ClienteJpaController;

/**
 *
 * @author torsello
 */
public class ClienteConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = new Long(string);
        ClienteJpaController controller = (ClienteJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "clienteJpaController");
        Object obj=controller.findCliente(id);
        return  obj;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Cliente) {
            Cliente o = (Cliente) object;
            Long id=o.getId();
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: entities.Cliente");
        }
    }

}
