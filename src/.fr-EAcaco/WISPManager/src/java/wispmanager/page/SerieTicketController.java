/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.page;


/**
 *
 * @author torsello
 */
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility.pdf.PdfDoc;
import wispmanager.entities.SerieTicket;
import wispmanager.entities.jpa.SerieTicketJpaController;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;
import wispmanager.jsf.util.JSONParser;

public class SerieTicketController  {

    private String result_query="";
    SerieTicketJpaController serieTicketJpa;
    private SerieTicket serieTicket;
    private SerieTicketConverter converter=new SerieTicketConverter();
    FacesContext facesContext;
    ELContext elContext;
    ELResolver elResolver;
    List listSerieTicket;
    private SelectItem[] mapSerieTicket;

    public SerieTicketController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();
        serieTicket=new SerieTicket();
        serieTicketJpa=(SerieTicketJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "serieTicketJpaController");
        listSerieTicket=serieTicketJpa.findSerieTicketEntities();
    }

    

    public String crea_serieTicket()
    {
       try {
            serieTicket.setId(null);
            serieTicket.generaSeriale();
            serieTicket.generaTickets();
            serieTicketJpa.create(serieTicket);

            listSerieTicket.clear();
            listSerieTicket.add(serieTicket);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(SerieTicketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SerieTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "serieTicket";
    }

   
    public String seleziona_serieTicket(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        String cmd=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cmd");
        String value=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("value");

        HashMap MapField=(new JSONParser(value)).getMap();
        Long id= new Long((String) MapField.get("id"));
        serieTicket=serieTicketJpa.findSerieTicket(id);

        listSerieTicket.clear();
        listSerieTicket.add(getSerieTicket());

        return "serieTicket";
    }

    public String salva_serieTicket()
    {


       try {
            serieTicketJpa.edit(serieTicket);

            listSerieTicket.clear();
            listSerieTicket.add(serieTicket);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(SerieTicketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SerieTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "serieTicket";
    }

    public String elenca_tutti_serieTicket()
    {
        listSerieTicket.clear();
        listSerieTicket=serieTicketJpa.findSerieTicketEntities();
        return "serieTicket";
    }

    public String elimina_serieTicket()
    {
       try {
            serieTicketJpa.destroy(serieTicket.getId());
            listSerieTicket.clear();
            listSerieTicket=serieTicketJpa.findSerieTicketEntities();
       } catch (RollbackFailureException ex) {
            Logger.getLogger(SerieTicketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SerieTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "serieTicket";
    }
    /**
     * @return the result_query
     */
    public void query(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        update_query();
        result.append(result_query);        
    }
    
    public void update_query()
    {        
        result_query="{columns : [    { label : 'id', id : 'id'}, "+
                                     "{ label : 'Cliente', id : 'cliente'}," +
                                     "{ label : 'Seriale', id : 'seriale'}," +
                                     "{ label : 'Data Emissione', id : 'dataemissione'}," +
                                     "{ label : 'Data Scadenza', id : 'datascadenza'}," +
                                     "{ label : 'Numero Tickets', id : 'numeroticket'}," +
                                     "{ label : 'Durata in giorni', id : 'durataingiorni'}," +
                                     "{ label : 'Servizio di riferimento', id : 'servizio'}]," +
                                     "rows : [";

        for(int i=0; i< listSerieTicket.size();i++)
        {
            SerieTicket serTckSel=(SerieTicket)listSerieTicket.get(i);

            java.text.SimpleDateFormat df=new java.text.SimpleDateFormat();
            long id=serTckSel.getId();
            String cliente=serTckSel.getCliente().toString();
            String seriale=serTckSel.getSeriale();
            String dataemissione=df.format(serTckSel.getDataEmissione());
            String datascadenza=df.format(serTckSel.getDataScadenza());
            String numeroticket=serTckSel.getNumeroTickets().toString();
            String durataingiorni=serTckSel.getDurataInGiorni().toString();
            String servizio=serTckSel.getServizio().toString();
            result_query+="{"+
                    "id: '"+id+"', " +
                    "cliente: '"+cliente+"', " +
                    "seriale: '"+seriale+"', " +
                    "dataemissione: '"+dataemissione+"', " +
                    "datascadenza:'"+datascadenza+"', " +
                    "numeroticket: '"+numeroticket+"', " +
                    "durataingiorni: '"+durataingiorni+"', " +
                    "servizio: '"+servizio+"'}";
            if(i<listSerieTicket.size()-1) result_query+=",";
        }
        result_query+="]}";
    }


    /**
     * @param result_query the result_query to set
     */
    public void setResult_query(String result_query) {
        this.result_query = result_query;
    }

    /**
     * @return the converter
     */
    public SerieTicketConverter getConverter() {
        return converter;
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(SerieTicketConverter converter) {
        this.converter = converter;
    }

    /**
     * @return the serieTicket
     */
    public SerieTicket getSerieTicket() {
        return serieTicket;
    }

    /**
     * @param serieTicket the serieTicket to set
     */
    public void setSerieTicket(SerieTicket serieTicket) {
        this.serieTicket = serieTicket;
    }

    /**
     * @return the mapSerieTicket
     */
    public SelectItem[] getmapSerieTicket() {
        listSerieTicket=serieTicketJpa.findSerieTicketEntities();
        mapSerieTicket=new SelectItem[listSerieTicket.size()];
        int i = 0;
        while(i<listSerieTicket.size()){
            SerieTicket x = (SerieTicket) listSerieTicket.get(i);
            mapSerieTicket[i] = new SelectItem(x, x.getSeriale());
            i++;
        }
        return mapSerieTicket;
    }

    /**
     * @param mapSerieTicket the mapSerieTicket to set
     */
    public void setmapSerieTicket(SelectItem[] mapSerieTicket) {
        this.mapSerieTicket = mapSerieTicket;
    }


    public void StampaSerie() throws ServletException
    {
        FacesContext f=FacesContext.getCurrentInstance();

        if(!f.getResponseComplete()) {

        HttpServletRequest request=(HttpServletRequest) f.getExternalContext().getRequest();
        HttpServletResponse response=(HttpServletResponse) f.getExternalContext().getResponse();


        String FileXML=request.getSession().getServletContext().getRealPath("");
        String FileXSLT=request.getSession().getServletContext().getRealPath("");

        FileXSLT+="/pdf/SerieTicket.xslt";
        FileXML+="/pdf/SerieTicket.xml";
        try {
            serieTicket.StampaSerie(FileXML);
            PdfDoc pdfdoc=new PdfDoc();
            pdfdoc.OpenDocument(FileXML, FileXSLT);
            pdfdoc.doGetServlet(request, response);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerieTicketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(SerieTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }

        f.responseComplete();
        
        }



    }


   }




