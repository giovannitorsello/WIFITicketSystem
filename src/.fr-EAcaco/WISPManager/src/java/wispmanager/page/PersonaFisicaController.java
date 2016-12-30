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
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import wispmanager.entities.PersonaFisica;
import wispmanager.entities.jpa.PersonaFisicaJpaController;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;
import wispmanager.jsf.util.JSONParser;

public class PersonaFisicaController  {

    private String result_query="";
    PersonaFisicaJpaController perFisJpa;
    private PersonaFisica personaFisica;
    private PersonaFisica converter=new PersonaFisica();
    FacesContext facesContext;
    ELContext elContext;
    ELResolver elResolver;
    List listPerFis;
    private SelectItem[] mapPersoneFisiche;
    

    

    public PersonaFisicaController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();
        personaFisica=new PersonaFisica();     
        perFisJpa=(PersonaFisicaJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "personaFisicaJpaController");
        listPerFis=perFisJpa.findPersonaFisicaEntities(true, 50, 0);
    }

    

    public String crea_personaFisica()
    {
       try {
            personaFisica.setId(null);
            perFisJpa.create(personaFisica);
            listPerFis.clear();
            listPerFis.add(perFisJpa.findPersonaFisica(personaFisica.getId()));
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PersonaFisicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PersonaFisicaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "personafisica";
    }

    public String elenca_tutti_personaFisica()
    {
        listPerFis.clear();
        listPerFis=perFisJpa.findPersonaFisicaEntities();
        return "personafisica";
    }
    
    public String seleziona_personaFisica(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        String cmd=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cmd");
        String value=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("value");


        HashMap MapField=(new JSONParser(value)).getMap();
        Long id= new Long((String) MapField.get("id"));
        personaFisica=perFisJpa.findPersonaFisica(id);

        listPerFis.clear();
        listPerFis.add(perFisJpa.findPersonaFisica(personaFisica.getId()));

        return "personafisica";
    }

    public String salva_personaFisica()
    {


       try {
            perFisJpa.edit(personaFisica);
            listPerFis.clear();
            listPerFis.add(perFisJpa.findPersonaFisica(personaFisica.getId()));
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PersonaFisicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PersonaFisicaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "personafisica";
    }

    public String elimina_personaFisica()
    {
       try {
            perFisJpa.destroy(personaFisica.getId());
            listPerFis=perFisJpa.findPersonaFisicaEntities(true, 50, 0);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PersonaFisicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PersonaFisicaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "personafisica";
    }

    public void query(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        update_query();
        result.append(result_query);
    }

    /**
     * @return the result_query
     */
    public void update_query()
    {
        result_query="{columns : [    { label : 'id', id : 'id'}, "+
                                     "{ label : 'Nome', id : 'nome'}," +
                                     "{ label :'Cognome', id : 'cognome'}," +
                                     "{ label : 'Residenza', id : 'residenza'}," +
                                     "{ label : 'Contatti', id : 'contatti'}]," +
                                     "rows : [";

        for(int i=0; i< listPerFis.size();i++)
        {
            PersonaFisica perFisSel=(PersonaFisica)listPerFis.get(i);
            

            long id=perFisSel.getId();
            String nome=perFisSel.getNome();
            String cognome=perFisSel.getCognome();
            String residenza=perFisSel.getResidenzaVia()+" "+perFisSel.getResidenzaCAP()+" "+perFisSel.getResidenzaComune()+"["+perFisSel.getResidenzaProvincia()+"]";
            String contatti="Telefono "+perFisSel.getTelefono()+"</br> Cellulare "+perFisSel.getCellulare()+"</br> Email "+perFisSel.getEmail();

            result_query+="{"+
                    "id: '"+id+"', " +
                    "nome: '"+nome+"', " +
                    "cognome: '"+cognome+"', " +
                    "residenza: '"+residenza+"', " +
                    "contatti: '"+contatti+"'}";
            if(i<listPerFis.size()-1) result_query+=",";
        }
        result_query+="]}";                
    }

    public void findByCognome(ActionEvent e)
    {
        listPerFis=perFisJpa.findByField("cognome",personaFisica.getCognome());
    }

    /**
     * @param result_query the result_query to set
     */
    public void setResult_query(String result_query) {
        this.result_query = result_query;
    }

    /**
     * @return the personaFisica
     */
    public PersonaFisica getPersonaFisica() {
        return personaFisica;
    }

    /**
     * @param personaFisica the personaFisica to set
     */
    public void setPersonaFisica(PersonaFisica personaFisica) {
        this.personaFisica = personaFisica;
    }

   

    /**
     * @return the converter
     */
    public PersonaFisica getConverter() {
        return converter;
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(PersonaFisica converter) {
        this.converter = converter;
    }

    /**
     * @return the mapPersoneFisiche
     */
    public SelectItem[] getMapPersoneFisiche() {
        listPerFis=perFisJpa.findPersonaFisicaEntities(true, 50, 0);
        mapPersoneFisiche = new SelectItem[listPerFis.size()];
        int i = 0;
        while(i<listPerFis.size()){
            PersonaFisica x = (PersonaFisica) listPerFis.get(i);
            mapPersoneFisiche[i] = new SelectItem(x, x.getNome()+" "+x.getCognome()+" "+x.getDataNascita().toString());
            i++;
        }
        return mapPersoneFisiche;
    }

    /**
     * @param mapPersoneFisiche the mapPersoneFisiche to set
     */
    public void setMapPersoneFisiche(SelectItem[] mapPersoneFisiche) {
        this.mapPersoneFisiche = mapPersoneFisiche;
    }


   }




