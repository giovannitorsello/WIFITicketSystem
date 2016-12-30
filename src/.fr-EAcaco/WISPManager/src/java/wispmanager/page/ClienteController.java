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
import wispmanager.entities.NumerazioneFonia;
import wispmanager.entities.PersonaFisica;
import wispmanager.entities.PersonaGiuridica;
import wispmanager.entities.SerieTicket;
import wispmanager.entities.Servizio;
import wispmanager.entities.jpa.ClienteJpaController;
import wispmanager.entities.jpa.NumerazioneFoniaJpaController;
import wispmanager.entities.jpa.exceptions.NonexistentEntityException;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;
import wispmanager.jsf.util.JSONParser;

public class ClienteController  {

    private String result_query="";
    private ClienteJpaController cliJpa;
    private NumerazioneFoniaJpaController numFonJpa;
    private Cliente cliente=new Cliente();
    private ClienteConverter converter=new ClienteConverter();
    private FacesContext facesContext;
    private ELContext elContext;
    private ELResolver elResolver;
    private List listCli;
    private Servizio servizio;
    private NumerazioneFonia numerazioneFonia=new NumerazioneFonia();
    private NumerazioneFonia numerazioneFoniaSelection=new NumerazioneFonia();
    private SerieTicket serieTicket=new SerieTicket();
    private SelectItem[] mapClienti;
    private SelectItem[] mapTicket;
    private SelectItem[] mapNumerazioni;
    

    public ClienteController() {
        facesContext = FacesContext.getCurrentInstance();
        elContext=facesContext.getELContext();
        elResolver=facesContext.getApplication().getELResolver();        
        cliJpa=(ClienteJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "clienteJpaController");
        numFonJpa=(NumerazioneFoniaJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "numerazioneFoniaJpaController");
        listCli=cliJpa.findClienteEntities(true, 50, 0);
    }

    public String crea_Cliente_PersonaFisica()
    {
       try {
            cliente.setId(null);
            cliente.getServizi().clear();
            cliente.setNoteCliente("");
            cliente.setClientePersonaGiuridica(null);

            cliJpa().create(cliente);

            listCli().clear();
            listCli().add(cliente);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "cliente";
    }

    

    public String crea_Cliente_PersonaGiuridica()
    {
       try {
            cliente.setId(null);
            cliente.getServizi().clear();
            cliente.setNoteCliente("");
            PersonaFisica LegRappr=cliente.getClientePersonaGiuridica().getLegaleRappresentante();
            if(LegRappr!=null) cliente.setClientePersonaFisica(LegRappr);
            //prevedere messaggio di errore
            else return "cliente";

            cliJpa().create(cliente);

            listCli().clear();
            listCli().add(cliente);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "cliente";
    }

    public String seleziona_Cliente(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        String cmd=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cmd");
        String value=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("value");


        HashMap MapField=(new JSONParser(value)).getMap();
        Long id= new Long((String) MapField.get("id"));
        cliente=cliJpa().findCliente(id);

        listCli().clear();
        listCli().add(cliente);
        
        return "cliente";
    }

    public String elenca_tutti()
    {
        listCli().clear();
        setListCli(cliJpa().findClienteEntities());
        return "personagiuridica";
    }

    public String salva_Cliente()
    {


       try {
            cliJpa().edit(cliente);

            listCli().clear();
            listCli().add(cliente);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "perFiscliente";
    }

    public String elimina_Cliente()
    {


       try {
            cliJpa().destroy(cliente.getId());

            listCli().clear();
            setListCli(cliJpa().findClienteEntities(true, 50, 0));

        } catch (RollbackFailureException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "cliente";
    }
    
    public String elimina_SerieTicket()
    {
       try {
            cliente.eliminaSerieTicket(getSerieTicket());
            cliJpa.edit(cliente);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "cliente";
    }

    public String aggiungi_Servizio()
    {

        cliente.aggiungiServizio(servizio);
        try {
            cliJpa.edit(cliente);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "cliente";
    }

    public String elimina_Servizio()
    {
        cliente.eliminaServizio(servizio);
        
        try {
            cliJpa.edit(cliente);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "cliente";
    }


    public String aggiungi_NumerazioneFonia()
    {
                
        try {
            numerazioneFonia.setId(null);
            numerazioneFonia.setCliente(cliente);
            numFonJpa.create(numerazioneFonia);

            cliente.aggiungiNumerazioneFonia(numerazioneFonia);
     //       cliJpa.edit(cliente);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "cliente";
    }

    public String elimina_NumerazioneFonia()
    {        

        try {
            cliente.eliminaNumerazioneFonia(numerazioneFoniaSelection);
            numFonJpa.destroy(numerazioneFoniaSelection.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "cliente";
    }


    /**
     * @return the result_query
     */
    public void queryCliente(javax.faces.context.FacesContext facesContext, jmaki.runtime.jsf.AjaxResult result)
    {
        if(listCli()==null)
        listCli=cliJpa.findClienteEntities();

        setResult_query("{columns : [    { label : 'id', id : 'id'}, " + "{ label : 'Persona Fisica', id : 'personafisica'}," + "{ label : 'Persona Giuridica', id : 'personagiuridica'}," + "{ label : 'numerazioni fonia', id : 'numerazionifonia'}, { label : 'Servizi', id : 'servizi'}]," + "rows : [");

        for(int i=0; i< listCli().size();i++)
        {
            Cliente cliSel=(Cliente)listCli().get(i);
            PersonaGiuridica perGiu=cliSel.getClientePersonaGiuridica();
            PersonaFisica perFis=cliSel.getClientePersonaFisica();


            long id=cliSel.getId();
            String personafisica="";
            String personagiuridica="";
            String strServizi="";
            String strNumerazioniFonia="";
            int k=0; 
            while(k<cliSel.getServizi().size())
            {
                strServizi+="<p>"+cliSel.getServizi().get(k).toString()+"</p>";
                k++;
            }


            k=0;
            while(k<cliSel.getNumerazioniFonia().size())
            {
                strNumerazioniFonia+="<p>"+cliSel.getNumerazioniFonia().get(k).toString()+"</p>";
                k++;
            }


            if(perFis!=null) personafisica=perFis.toString();
            if(perGiu!=null) personagiuridica=perGiu.toString();
            else personagiuridica="cliente privato";

            setResult_query(getResult_query() + "{" + "id: '" + id + "', " + "personafisica: '" + personafisica + "', " + "personagiuridica: '" + personagiuridica + "', "+ "numerazionifonia: '" + strNumerazioniFonia + "', " + "servizi: '" + strServizi + "'}");
            if(i<listCli().size()-1) setResult_query(getResult_query() + ",");
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
     * @return the Cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param Cliente the Cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the converter
     */
    public ClienteConverter getConverter() {
        return converter;
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(ClienteConverter converter) {
        this.converter = converter;
    }


    /**
     * @return the result_query
     */
    public String getResult_query() {
        return result_query;
    }

    /**
     * @return the cliJpa
     */
    public ClienteJpaController cliJpa() {
        return cliJpa;
    }

    /**
     * @param cliJpa the cliJpa to set
     */
    public void setCliJpa(ClienteJpaController cliJpa) {
        this.cliJpa = cliJpa;
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
     * @return the listCli
     */
    public List listCli() {
        return getListCli();
    }

    /**
     * @param listCli the listCli to set
     */
    public void setListCli(List listCli) {
        this.listCli = listCli;
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
     * @return the mapTicket
     */
    public SelectItem[] getMapTicket() {
        List<SerieTicket> listSerTck=cliente.getSerieTicket();
        mapTicket=new SelectItem[listSerTck.size()];
        int i = 0;
        while(i<listSerTck.size()){
            SerieTicket x = (SerieTicket) listSerTck.get(i);
            mapTicket[i] = new SelectItem(x, x.getDataEmissione().toString());
            i++;
        }
        return mapTicket;
    }

    /**
     * @param mapTicket the mapTicket to set
     */
    public void setMapTicket(SelectItem[] mapTicket) {
        this.mapTicket=mapTicket;
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
     * @return the mapClienti
     */
    public SelectItem[] getMapClienti() {
        listCli=cliJpa.findClienteEntities();
        mapClienti=new SelectItem[getListCli().size()];
        int i = 0;
        while(i<getListCli().size()){
            Cliente x = (Cliente) getListCli().get(i);
            mapClienti[i] = new SelectItem(x, x.toString());
            i++;
        }
        return mapClienti;
    }

    /**
     * @param mapClienti the mapClienti to set
     */
    public void setMapClienti(SelectItem[] mapClienti) {
        this.mapClienti=mapClienti;
    }

    /**
     * @return the listCli
     */
    public List getListCli() {
        return listCli;
    }

    /**
     * @return the numerazioneFonia
     */
    public NumerazioneFonia getNumerazioneFonia() {
        return numerazioneFonia;
    }

    /**
     * @param numerazioneFonia the numerazioneFonia to set
     */
    public void setNumerazioneFonia(NumerazioneFonia numerazioneFonia) {
        this.numerazioneFonia = numerazioneFonia;
    }
   

    /**
     * @return the mapNumerazioni
     */
    public SelectItem[] getMapNumerazioni() {
        List<NumerazioneFonia> listNumFon=cliente.getNumerazioniFonia();
        mapNumerazioni=new SelectItem[listNumFon.size()];
        int i = 0;
        while(i<listNumFon.size()){
            NumerazioneFonia x = (NumerazioneFonia) listNumFon.get(i);
            mapNumerazioni[i] = new SelectItem(x, x.toString());
            i++;
        }
        return mapNumerazioni;
    }

    /**
     * @param mapNumerazioni the mapNumerazioni to set
     */
    public void setMapNumerazioni(SelectItem[] mapNumerazioni) {
        this.mapNumerazioni = mapNumerazioni;
    }

    /**
     * @return the numerazioneFoniaSelection
     */
    public NumerazioneFonia getNumerazioneFoniaSelection() {
        return numerazioneFoniaSelection;
    }

    /**
     * @param numerazioneFoniaSelection the numerazioneFoniaSelection to set
     */
    public void setNumerazioneFoniaSelection(NumerazioneFonia numerazioneFoniaSelection) {
        this.numerazioneFoniaSelection = numerazioneFoniaSelection;
    }

   }




