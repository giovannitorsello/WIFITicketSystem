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
import wispmanager.entities.Cliente;
import wispmanager.entities.Fornitore;
import wispmanager.entities.PersonaFisica;
import wispmanager.entities.PersonaGiuridica;
import wispmanager.entities.jpa.FornitoreJpaController;
import wispmanager.entities.jpa.exceptions.NonexistentEntityException;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;
import wispmanager.jsf.util.JSONParser;

public class FornitoreController  {

    private String result_query="";
    private FornitoreJpaController forJpa;
    private Fornitore fornitore=new Fornitore();
    private FornitoreConverter converter=new FornitoreConverter();
    private FacesContext facesContext;
    private ELContext elContext;
    private ELResolver elResolver;    
    private List<Fornitore> fornitori;
    private SelectItem[] mapFornitori;
    private String NoteFornitore;

    public FornitoreController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();        
        forJpa=(FornitoreJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "fornitoreJpaController");
        fornitori=forJpa.findFornitoreEntities();
    }

    public String crea_Fornitore_PersonaFisica()
    {
       try {
            fornitore.setId(null);
            fornitore.setNoteFornitore("");
            fornitore.setFornitorePersonaGiuridica(null);

            forJpa().create(fornitore);

            fornitori.clear();
            fornitori.add(fornitore);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(FornitoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FornitoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "fornitore";
    }

    

    public String crea_Fornitore_PersonaGiuridica()
    {
       try {
            fornitore.setId(null);
            fornitore.setNoteFornitore("");
            PersonaFisica LegRappr=fornitore.getFornitorePersonaGiuridica().getLegaleRappresentante();
            if(LegRappr!=null) fornitore.setFornitorePersonaFisica(LegRappr);
            //prevedere messaggio di errore
            else return "fornitore";

            forJpa().create(fornitore);

           fornitori.clear();
            fornitori.add(fornitore);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(FornitoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FornitoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "fornitore";
    }

    public String seleziona_Fornitore(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        String cmd=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cmd");
        String value=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("value");


        HashMap MapField=(new JSONParser(value)).getMap();
        Long id= new Long((String) MapField.get("id"));
        setFornitore(forJpa().findFornitore(id));

        fornitori.clear();
        fornitori.add(fornitore);

        return "fornitore";
    }

    public String elenca_tutti()
    {
        fornitori.clear();
        fornitori=forJpa.findFornitoreEntities();
        return "fornitore";
    }

    public String salva_Fornitore()
    {


       try {
            forJpa().edit(fornitore);

            fornitori.clear();
            fornitori.add(fornitore);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(FornitoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FornitoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "fornitore";
    }

    public String elimina_Fornitore()
    {


       try {
            forJpa().destroy(fornitore.getId());

            fornitori.clear();
            fornitori=forJpa.findFornitoreEntities();

        } catch (RollbackFailureException ex) {
            Logger.getLogger(FornitoreController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FornitoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "fornitore";
    }
    
    

    /**
     * @return the result_query
     */
    public void queryFornitore(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
       if(fornitori==null)
        fornitori=forJpa.findFornitoreEntities();

        setResult_query("{columns : [    { label : 'id', id : 'id'}, " + "{ label : 'Persona Fisica', id : 'personafisica'}," + "{ label : 'Persona Giuridica', id : 'personagiuridica'}," + "{ label : 'Servizi', id : 'servizi'}]," + "rows : [");

        for(int i=0; i< fornitori.size();i++)
        {
            Fornitore forSel=(Fornitore) fornitori.get(i);
            PersonaGiuridica perGiu=forSel.getFornitorePersonaGiuridica();
            PersonaFisica perFis=forSel.getFornitorePersonaFisica();


            long id=forSel.getId();
            String personafisica="";
            String personagiuridica="";
           

            if(perFis!=null) personafisica=perFis.toString();
            if(perGiu!=null) personagiuridica=perGiu.toString();
            else personagiuridica="Fornitore privato";

            setResult_query(getResult_query() + "{" + "id: '" + id + "', " + "personafisica: '" + personafisica + "', " + "personagiuridica: '" + personagiuridica + "', " + "servizi: ''}");
            if(i<fornitori.size()-1) setResult_query(getResult_query() + ",");
        }
        setResult_query(getResult_query() + "]}");
        result.append(getResult_query());
        
    }

  
    /**
     * @param result_query the result_query to set
     */
    public void setResult_query(String result_query) {
        this.result_query = result_query;
    }

    /**
     * @return the Fornitore
     */
    public Fornitore getFornitore() {
        return fornitore;
    }

    /**
     * @param Fornitore the Fornitore to set
     */
    public void setFornitore(Fornitore fornitore) {
        this.fornitore = fornitore;
    }

    /**
     * @return the converter
     */
    public FornitoreConverter getConverter() {
        return converter;
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(FornitoreConverter converter) {
        this.converter = converter;
    }


    /**
     * @return the result_query
     */
    public String getResult_query() {
        return result_query;
    }

    /**
     * @return the forJpa
     */
    public FornitoreJpaController forJpa() {
        return getRapJpa();
    }

    /**
     * @param forJpa the forJpa to set
     */
    public void setforJpa(FornitoreJpaController forJpa) {
        this.setRapJpa(forJpa);
    }

    /**
     * @return the facesContext
     */
    public FacesContext getFacesContext() {
        return facesContext;
    }

    /**
     * @param facesContext the facesContext to set
     */
    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    /**
     * @return the elContext
     */
    public ELContext getElContext() {
        return elContext;
    }

    /**
     * @param elContext the elContext to set
     */
    public void setElContext(ELContext elContext) {
        this.elContext = elContext;
    }

    /**
     * @return the elResolver
     */
    public ELResolver getElResolver() {
        return elResolver;
    }

    /**
     * @param elResolver the elResolver to set
     */
    public void setElResolver(ELResolver elResolver) {
        this.elResolver = elResolver;
    }
   
  
  
    /**
     * @return the mapClienti
     */
    public SelectItem[] getMapFornitori() {
        setFornitori(getRapJpa().findFornitoreEntities());
        mapFornitori=new SelectItem[getFornitori().size()];
        int i = 0;
        while(i<fornitori.size()){
            Fornitore x = (Fornitore) fornitori.get(i);
            mapFornitori[i] = new SelectItem(x, x.toString());
            i++;
        }
        return mapFornitori;
    }

    /**
     * @param mapClienti the mapClienti to set
     */
    public void setMapFornitori(SelectItem[] mapFornitori) {
        this.mapFornitori=mapFornitori;
    }

    /**
     * @return the forJpa
     */
    public FornitoreJpaController getRapJpa() {
        return forJpa;
    }

    /**
     * @param forJpa the forJpa to set
     */
    public void setRapJpa(FornitoreJpaController forJpa) {
        this.forJpa = forJpa;
    }



    /**
     * @return the fornitori
     */
    public List<Fornitore> getFornitori() {
        return fornitori;
    }

    /**
     * @param fornitori the fornitori to set
     */
    public void setFornitori(List<Fornitore> fornitori) {
        this.fornitori = fornitori;
    }

    /**
     * @return the NoteFornitore
     */
    public String getNoteFornitore() {
        return NoteFornitore;
    }

    /**
     * @param NoteFornitore the NoteFornitore to set
     */
    public void setNoteFornitore(String NoteFornitore) {
        this.NoteFornitore = NoteFornitore;
    }


  
   }




