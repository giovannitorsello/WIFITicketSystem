/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.page;


/**
 *
 * @author torsello
 */
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;
import wispmanager.entities.PersonaGiuridica;
import wispmanager.entities.jpa.PersonaGiuridicaJpaController;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;
import wispmanager.jsf.util.JSONParser;

public class PersonaGiuridicaController  {

    private String result_query="";
    PersonaGiuridicaJpaController perGiuJpa;
    PersonaFisicaController perFisCtrl;
    private PersonaGiuridica personaGiuridica;
    private PersonaGiuridica converter=new PersonaGiuridica();
    FacesContext facesContext;
    ELContext elContext;
    ELResolver elResolver;
    List listPerGiu;
    private HashMap mapPersoneGiuridiche=new HashMap();

    public PersonaGiuridicaController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();
        personaGiuridica=new PersonaGiuridica();
        perGiuJpa=(PersonaGiuridicaJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "personaGiuridicaJpaController");
        perFisCtrl=(PersonaFisicaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "personaFisicaController");
        listPerGiu=perGiuJpa.findPersonaGiuridicaEntities(true, 50, 0);
    }

    

    public String crea_personaGiuridica()
    {
       try {
            personaGiuridica.setId(null);
            perGiuJpa.create(personaGiuridica);

            listPerGiu.clear();
            listPerGiu.add(personaGiuridica);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(PersonaGiuridicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PersonaGiuridicaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "cliente";
    }

    public String seleziona_personaGiuridica(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        String cmd=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cmd");
        String value=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("value");


        HashMap MapField=(new JSONParser(value)).getMap();
        Long id= new Long((String) MapField.get("id"));
        personaGiuridica=perGiuJpa.findPersonaGiuridica(id);

        listPerGiu.clear();
        listPerGiu.add(personaGiuridica);

        return "personagiuridica";
    }

    public String salva_personaGiuridica()
    {


       try {
            perGiuJpa.edit(personaGiuridica);

            listPerGiu.clear();
            listPerGiu.add(personaGiuridica);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(PersonaGiuridicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PersonaGiuridicaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "personagiuridica";
    }

    public String elenca_tutti_personaGiuridica()
    {
        listPerGiu.clear();
        listPerGiu=perGiuJpa.findPersonaGiuridicaEntities();
        return "personagiuridica";
    }

    public String elimina_personaGiuridica()
    {


       try {
            perGiuJpa.destroy(personaGiuridica.getId());
            listPerGiu=perGiuJpa.findPersonaGiuridicaEntities(true, 50, 0);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PersonaGiuridicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PersonaGiuridicaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "personagiuridica";
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
                                     "{ label : 'Denominazione', id : 'denominazione'}," +
                                     "{ label : 'Legale Rappresentante', id : 'legalerappresentante'}," +
                                     "{ label : 'Partita IVA', id : 'pariva'}," +
                                     "{ label :'Sede legale', id : 'sedelegale'}," +
                                     "{ label : 'Contatti', id : 'contatti'}]," +
                                     "rows : [";

        for(int i=0; i< listPerGiu.size();i++)
        {
            PersonaGiuridica perGiuSel=(PersonaGiuridica)listPerGiu.get(i);


            long id=perGiuSel.getId();
            String denominazione=perGiuSel.getDenominazione();
            String pariva=perGiuSel.getPartitaIva();
            String sedelegale=perGiuSel.getSedeLegaleVia()+" "+perGiuSel.getSedeLegaleCap()+" "+perGiuSel.getSedeLegaleComune()+"["+perGiuSel.getSedeLegaleProvincia()+"]";
            String contatti="Telefono "+perGiuSel.getTelefono()+"</br> Cellulare "+perGiuSel.getCellulare()+"</br> Email "+perGiuSel.getEmail();
            String legalerappresentante="";
            if(perGiuSel.getLegaleRappresentante()!=null)
                legalerappresentante=perGiuSel.getLegaleRappresentante().toString();
            result_query+="{"+
                    "id: '"+id+"', " +
                    "denominazione: '"+denominazione+"', " +
                    "legalerappresentante: '"+legalerappresentante+"', " +
                    "pariva: '"+pariva+"', " +
                    "sedelegale: '"+sedelegale+"', " +
                    "contatti: '"+contatti+"'}";
            if(i<listPerGiu.size()-1) result_query+=",";
        }
        result_query+="]}";
    }

    public void findByDenominazione(ActionEvent e)
    {
        listPerGiu=perGiuJpa.findByField("denominazione",personaGiuridica.getDenominazione());
    }

    /**
     * @param result_query the result_query to set
     */
    public void setResult_query(String result_query) {
        this.result_query = result_query;
    }

    /**
     * @return the PersonaGiuridica
     */
    public PersonaGiuridica getPersonaGiuridica() {
        return personaGiuridica;
    }

    /**
     * @param PersonaGiuridica the PersonaGiuridica to set
     */
    public void setPersonaGiuridica(PersonaGiuridica personaGiuridica) {
        this.personaGiuridica = personaGiuridica;
    }

     public HashMap getMapPersoneGiuridiche() {
        listPerGiu=perGiuJpa.findPersonaGiuridicaEntities(true, 50, 0);
        int i=0;
        while(i<listPerGiu.size())
        {
            PersonaGiuridica p=(PersonaGiuridica)listPerGiu.get(i);
            mapPersoneGiuridiche.put(p,p);
            i++;
        }
        return mapPersoneGiuridiche;
    }

    /**
     * @param mapPersoneGiuridiche the mapPersoneGiuridiche to set
     */
    public void setMapPersoneGiuridiche(HashMap mapPersoneGiuridiche) {
        this.mapPersoneGiuridiche = mapPersoneGiuridiche;
    }

    /**
     * @return the converter
     */
    public PersonaGiuridica getConverter() {
        return converter;
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(PersonaGiuridica converter) {
        this.converter = converter;
    }


   }




