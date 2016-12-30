/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.page;


/**
 *
 * @author torsello
 */
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import wispmanager.entities.Servizio;
import wispmanager.entities.jpa.ServizioJpaController;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;
import wispmanager.jsf.util.JSONParser;

public class ServizioController  {

    private String result_query="";
    ServizioJpaController servizioJpa;
    PersonaFisicaController perFisCtrl;
    private Servizio servizio;
    private ServizioConverter converter=new ServizioConverter();
    FacesContext facesContext;
    ELContext elContext;
    ELResolver elResolver;
    List listServizio;
    private SelectItem[] mapServizi;

    public ServizioController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();
        servizio=new Servizio();
        servizioJpa=(ServizioJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "servizioJpaController");
        listServizio=servizioJpa.findServizioEntities();
    }

    

    public String crea_servizio()
    {
       try {
            servizio.setId(null);
            servizioJpa.create(servizio);

            listServizio.clear();
            listServizio.add(servizio);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ServizioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServizioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "servizio";
    }

   
    public String seleziona_servizio(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        String cmd=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cmd");
        String value=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("value");

        HashMap MapField=(new JSONParser(value)).getMap();
        Long id= new Long((String) MapField.get("id"));
        setServizio(servizioJpa.findServizio(id));

        listServizio.clear();
        listServizio.add(servizio);

        return "servizio";
    }

    public String salva_servizio()
    {


       try {
            servizioJpa.edit(servizio);

            listServizio.clear();
            listServizio.add(servizio);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(ServizioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServizioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "servizio";
    }

    public String elenca_tutti_servizio()
    {
        listServizio.clear();
        listServizio=servizioJpa.findServizioEntities();
        return "servizio";
    }

    public String elimina_servizio()
    {


       try {
            servizioJpa.destroy(servizio.getId());

            listServizio.clear();
            listServizio=servizioJpa.findServizioEntities();

       } catch (RollbackFailureException ex) {
            Logger.getLogger(ServizioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServizioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "servizio";
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
                                     "{ label : 'Descrizione', id : 'descrizione'}," +
                                     "{ label : 'Base temporale', id : 'basetemporale'}," +
                                     "{ label : 'Importo', id : 'importo'}," +
                                     "{ label : 'IVA', id : 'iva'}," +
                                     "{ label : 'Descrizione unita', id : 'descrizioneunita'}," +
                                     "{ label : 'Unita di consumo', id : 'unitadiconsumo'}," +
                                     "{ label : 'Data Inizio', id : 'datainizio'}," +
                                     "{ label : 'Data Fine', id : 'datafine'}]," +
                                     "rows : [";

        for(int i=0; i< listServizio.size();i++)
        {
            Servizio serConSel=(Servizio)listServizio.get(i);

            java.text.SimpleDateFormat df=new java.text.SimpleDateFormat();
            long id=serConSel.getId();
            String descrizione=serConSel.getDescrizione();
            String basetemporale=serConSel.getBaseTemporaleImporto().toString();
            String importo=serConSel.getImporto().toString();
            String iva=serConSel.getIVA().toString();
            String descrizioneunita=serConSel.getDescrizioneUnitadiConsumo().toString();
            String unitadiconsumo=serConSel.getUnitaDiConsumo().toString();
            String datainizio=df.format(serConSel.getDataInizio());
            String datafine=df.format(serConSel.getDataFine());
            result_query+="{"+
                    "id: '"+id+"', " +
                    "descrizione: '"+descrizione+"', " +
                    "basetemporale: '"+basetemporale+"', " +
                    "importo: '"+importo+"', " +
                    "iva: '"+iva+"', " +
                    "descrizioneunita: '"+descrizioneunita+"', " +
                    "unitaconsumo: '"+unitadiconsumo+"', " +
                    "datainizio: '"+datainizio+"', " +
                    "datafine: '"+datafine+"'}";
            if(i<listServizio.size()-1) result_query+=",";
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
    public ServizioConverter getConverter() {
        return converter;
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(ServizioConverter converter) {
        this.converter = converter;
    }

    /**
     * @return the servizio
     */
    public Servizio getServizio() {
        return servizio;
    }

    /**
     * @param servizio the servizio to set
     */
    public void setServizio(Servizio servizio) {
        this.servizio = servizio;
    }

    /**
     * @return the mapServizi
     */
    public SelectItem[] getMapServizi() {
        listServizio=servizioJpa.findServizioEntities();
        mapServizi=new SelectItem[listServizio.size()];
        int i = 0;
        while(i<listServizio.size()){
            Servizio x = (Servizio) listServizio.get(i);
            mapServizi[i] = new SelectItem(x, x.getDescrizione());
            i++;
        }
        return mapServizi;
    }

    /**
     * @param mapServizi the mapServizi to set
     */
    public void setMapServizi(SelectItem[] mapServizi) {
        this.mapServizi = mapServizi;
    }


   }




