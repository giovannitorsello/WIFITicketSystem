/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import util.PasswordGenerator;

/**
 *
 * @author torsello
 */
@Entity
public class Ticket implements Serializable {
    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    
    @ManyToOne
    private WebSurfer websurfer;
    private String seriale;
    private String login;    
    private String password;    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEmissione=new Date();          //data creazione credenziali
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataScadenza=new Date(new Date().getTime()+259200000);  //data scadenza credenziali (emissione+3 giorni)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataPrimoUtilizzo=new Date();      //data primo utilizzo
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataScadenzaUtilizzo=new Date();   //data fine utilizzo

    private String stato="emesso"; //emesso, attivo, scaduto
    private Integer durataInGiorni;
    
    @OneToOne
    private Cliente cliente;
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getReportTicket()
    {
        SimpleDateFormat sdf=new SimpleDateFormat();
        sdf.applyPattern("dd/MM/yyyy");
        String strDataEmissione=sdf.format(dataEmissione);
        String strDataScadenza=sdf.format(dataScadenza);
        
        String report="Report ticket: "+seriale+" \n"
                    + "username: "+login+" \n"
                    + "password: "+ password+" \n"
                    + "note: "+note+" \n"
                    + "emissione:"+strDataEmissione+" \n"
                    + "scadenza:"+strDataScadenza+" \n"
                    + "durata:"+getDurataInGiorni();
       /* try {
            report=new String(report.getBytes(),"UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return report;
    }
    
    @Override
    public String toString() {
        //return "wispmanager.entities.Ticket[id=" + getId() + "]";
        return seriale+"-"+getStato();
    }

    /**
     * @return the seriale
     */
    public String getSeriale() {
        return seriale;
    }

    /**
     * @param seriale the seriale to set
     */
    public void setSeriale(String seriale) {
        this.seriale = seriale;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the DataEmissione
     */
    public Date getDataEmissione() {
        return dataEmissione;
    }

    /**
     * @param DataEmissione the DataEmissione to set
     */
    public void setDataEmissione(Date dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    /**
     * @return the DataScadenza
     */
    public Date getDataScadenza() {
        return dataScadenza;
    }

    /**
     * @param DataScadenza the DataScadenza to set
     */
    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    /**
     * @return the durataInGiorni
     */
    public Integer getDurataInGiorni() {
        return durataInGiorni;
    }

    /**
     * @param DurataInGiorni the DurataInGiorni to set
     */
    public void setDurataInGiorni(Integer durataInGiorni) {
        this.durataInGiorni = durataInGiorni;
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

    /**
     * @return the DataPrimoUtilizzo
     */
    public Date getDataPrimoUtilizzo() {
        return dataPrimoUtilizzo;
    }

    /**
     * @param DataPrimoUtilizzo the DataPrimoUtilizzo to set
     */
    public void setDataPrimoUtilizzo(Date dataPrimoUtilizzo) {
        this.dataPrimoUtilizzo = dataPrimoUtilizzo;
    }

    /**
     * @return the DataScadenzaUtilizzo
     */
    public Date getDataScadenzaUtilizzo() {
        return dataScadenzaUtilizzo;
    }

    /**
     * @param DataScadenzaUtilizzo the DataScadenzaUtilizzo to set
     */
    public void setDataScadenzaUtilizzo(Date dataScadenzaUtilizzo) {
        this.dataScadenzaUtilizzo = dataScadenzaUtilizzo;
    }

    /**
     * @return the Stato
     */
    public String getStato() {
        return stato;
    }

    /**
     * @param Stato the Stato to set
     */
    public void setStato(String stato) {
        this.stato = stato;
    }

    /**
     * @return the nomecognome
     */
    public String getNote() {
        return note;
    }

    /**
     * @param nomecognome the nomecognome to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the websurfer
     */
    public WebSurfer getWebsurfer() {
        return websurfer;
    }

    /**
     * @param websurfer the websurfer to set
     */
    public void setWebsurfer(WebSurfer websurfer) {
        this.websurfer = websurfer;
    }

    public void prepare_to_store()
    {
        durataInGiorni=(int)( (dataScadenza.getTime() - dataEmissione.getTime()) / (1000 * 60 * 60 * 24));
        
        if(durataInGiorni>30)
        {
            durataInGiorni=30;
            long millis=dataEmissione.getTime()+((long)durataInGiorni*1000*60*60*24);
            dataScadenza.setTime(millis);            
        }
        
        String str_millis=(new Long(new Date().getTime())).toString();
        seriale=str_millis+"-"+cliente.getId().toString();
        login=str_millis.substring(7); //Taglia le prime sette cifre       
        password=PasswordGenerator.GeneraPassCasuale(5);
        
        long this_time=(new Date()).getTime();
        if(this_time>dataScadenza.getTime())   stato="scaduto";
        
        if(this_time>dataEmissione.getTime() &&
           this_time<dataScadenza.getTime())   stato="in utilizzo";
        
        if(this_time<dataEmissione.getTime())  stato="emesso";
    }
    
    public String getDenominazione()
    {
        return websurfer.getDenominazione();
    }
    
    public String getEmail()
    {
        return websurfer.getEmail();
    }
}
