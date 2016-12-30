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
import wispmanager.entities.Rappresentante;
import wispmanager.entities.PersonaFisica;
import wispmanager.entities.PersonaGiuridica;
import wispmanager.entities.jpa.RappresentanteJpaController;
import wispmanager.entities.jpa.exceptions.NonexistentEntityException;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;
import wispmanager.jsf.util.JSONParser;

public class RappresentanteController  {

    private String result_query="";
    private RappresentanteJpaController rapJpa;
    private Rappresentante rappresentante=new Rappresentante();
    private Cliente cliente=new Cliente();
    private RappresentanteConverter converter=new RappresentanteConverter();
    private FacesContext facesContext;
    private ELContext elContext;
    private ELResolver elResolver;
    private List<Cliente> clienti;
    private List<Rappresentante> rappresentanti;    
    private SelectItem[] mapRappresentanti;


    public RappresentanteController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();        
        rapJpa=(RappresentanteJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "rappresentanteJpaController");
        rappresentanti=rapJpa.findRappresentanteEntities();
    }

    public String crea_Rappresentante_PersonaFisica()
    {
       try {
            getRappresentante().setId(null);
            getRappresentante().getClienti().clear();
            getRappresentante().setNoteRappresentante("");
            getRappresentante().setRappresentantePersonaGiuridica(null);

            rapJpa().create(getRappresentante());

            rappresentanti.clear();
            rappresentanti.add(rappresentante);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "rappresentante";
    }

    

    public String crea_Rappresentante_PersonaGiuridica()
    {
       try {
            getRappresentante().setId(null);
            getRappresentante().getClienti().clear();
            getRappresentante().setNoteRappresentante("");
            PersonaFisica LegRappr=getRappresentante().getRappresentantePersonaGiuridica().getLegaleRappresentante();
            if(LegRappr!=null) getRappresentante().setRappresentantePersonaFisica(LegRappr);
            //prevedere messaggio di errore
            else return "rappresentante";

            rapJpa().create(getRappresentante());

           rappresentanti.clear();
            rappresentanti.add(rappresentante);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "rappresentante";
    }

    public String seleziona_Rappresentante(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        String cmd=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cmd");
        String value=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("value");


        HashMap MapField=(new JSONParser(value)).getMap();
        Long id= new Long((String) MapField.get("id"));
        rappresentante=rapJpa().findRappresentante(id);

        rappresentanti.clear();
        if(rappresentante!=null)
            rappresentanti.add(rappresentante);

        return "rappresentante";
    }


    public String salva_Rappresentante()
    {


       try {
            rapJpa().edit(rappresentante);

            rappresentanti.clear();
            if(rappresentante!=null)
                rappresentanti.add(rappresentante);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "rappresentante";
    }

    public String elenca_tutti()
    {
        rappresentanti.clear();
        rappresentanti=rapJpa.findRappresentanteEntities();
        return "rappresentante";
    }
    
    public String elimina_Rappresentante()
    {


       try {
           if(rappresentante!=null)
           {
            rapJpa().destroy(rappresentante.getId());

            rappresentanti.clear();
            rappresentanti=rapJpa.findRappresentanteEntities();
           }
        } catch (RollbackFailureException ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "rappresentante";
    }
    
    
    public String aggiungi_Cliente()
    {

        getRappresentante().aggiungiCliente(getCliente());
        try {
            getRapJpa().edit(getRappresentante());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "rappresentante";
    }

    public String elimina_Cliente()
    {
        getRappresentante().eliminaCliente(getCliente());
        
        try {
            getRapJpa().edit(getRappresentante());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RappresentanteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "rappresentante";
    }

    /**
     * @return the result_query
     */
    public void queryRappresentante(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
       if(rappresentanti==null || rappresentanti.size()==0)
        rappresentanti=rapJpa.findRappresentanteEntities();

        setResult_query("{columns : [    { label : 'id', id : 'id'}, " + "{ label : 'Persona Fisica', id : 'personafisica'}," + "{ label : 'Persona Giuridica', id : 'personagiuridica'}," + "{ label : 'Clienti', id : 'clienti'}]," + "rows : [");

        for(int i=0; i<rappresentanti.size();i++)
        {
            Rappresentante rapSel=(Rappresentante) rappresentanti.get(i);
            PersonaGiuridica perGiu=rapSel.getRappresentantePersonaGiuridica();
            PersonaFisica perFis=rapSel.getRappresentantePersonaFisica();


            long id=rapSel.getId();
            String personafisica="";
            String personagiuridica="";
            String strClienti="";
            int k=0;            
            while(k<rapSel.getClienti().size())
            {
                strClienti+="<p>"+rapSel.getClienti().get(k).toString()+"</p>";
                k++;
            }

            if(perFis!=null) personafisica=perFis.toString();
            if(perGiu!=null) personagiuridica=perGiu.toString();
            else personagiuridica="Rappresentante privato";

            setResult_query(getResult_query() + "{" + "id: '" + id + "', " + "personafisica: '" + personafisica + "', " + "personagiuridica: '" + personagiuridica + "', " + "clienti: '" + strClienti + "'}");
            if(i<rappresentanti.size()-1) setResult_query(getResult_query() + ",");
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
     * @return the Rappresentante
     */
    public Rappresentante getRappresentante() {
        return rappresentante;
    }

    /**
     * @param Rappresentante the Rappresentante to set
     */
    public void setRappresentante(Rappresentante Rappresentante) {
        this.rappresentante = Rappresentante;
    }

    /**
     * @return the converter
     */
    public RappresentanteConverter getConverter() {
        return converter;
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(RappresentanteConverter converter) {
        this.converter = converter;
    }


    /**
     * @return the result_query
     */
    public String getResult_query() {
        return result_query;
    }

    /**
     * @return the rapJpa
     */
    public RappresentanteJpaController rapJpa() {
        return getRapJpa();
    }

    /**
     * @param rapJpa the rapJpa to set
     */
    public void setrapJpa(RappresentanteJpaController rapJpa) {
        this.setRapJpa(rapJpa);
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
    public SelectItem[] getMapRappresentanti() {
        setRappresentanti(getRapJpa().findRappresentanteEntities());
        setMapRappresentanti(new SelectItem[getClienti().size()]);
        int i = 0;
        while(i<rappresentanti.size()){
            Rappresentante x = (Rappresentante) rappresentanti.get(i);
            mapRappresentanti[i] = new SelectItem(x, x.toString());
            i++;
        }
        return mapRappresentanti;
    }

    /**
     * @param mapClienti the mapClienti to set
     */
    public void setMapRappresentanti(SelectItem[] mapRappresentanti) {
        this.setMapRappresentanti(mapRappresentanti);
    }

    /**
     * @return the rapJpa
     */
    public RappresentanteJpaController getRapJpa() {
        return rapJpa;
    }

    /**
     * @param rapJpa the rapJpa to set
     */
    public void setRapJpa(RappresentanteJpaController rapJpa) {
        this.rapJpa = rapJpa;
    }

    /**
     * @param clienti the clienti to set
     */
    public void setClienti(List<Cliente> clienti) {
        this.setClienti(clienti);
    }


     /**
     * @param clienti the clienti to set
     */
    public List<Cliente> getClienti() {
        return clienti;
    }


    /**
     * @return the rappresentanti
     */
    public List<Rappresentante> getRappresentanti() {
        return rappresentanti;
    }

    /**
     * @param rappresentanti the rappresentanti to set
     */
    public void setRappresentanti(List<Rappresentante> rappresentanti) {
        this.setRappresentanti(rappresentanti);
    }

       /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

  

  
   }




