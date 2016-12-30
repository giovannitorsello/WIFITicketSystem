package converter;

import entities.Ticket;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.TicketJpaController;

/**
 *
 * @author torsello
 */
public class TicketConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = new Long(string);
        TicketJpaController controller = (TicketJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "ticketJpaController");
        Object obj=controller.findTicket(id);
        return  obj;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Ticket) {
            Ticket o = (Ticket) object;
            Long id=o.getId();
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: entities.Ticket");
        }
    }

}
