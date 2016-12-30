/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

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
    private String seriale;
    private String utente;
    private String password;
    private String mac;
    private String ip;
    private String nomecognome;
    private String numerodocumento;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date DataEmissione=new Date();
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date DataScadenza=new Date();
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date DataPrimoUtilizzo=new Date();
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date DataScadenzaUtilizzo=new Date();

    private String Stato="emesso"; //emesso, attivo, scaduto
    private Integer DurataInGiorni;
    
    @OneToOne
    private Cliente cliente;


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
     * @return the utente
     */
    public String getUtente() {
        return utente;
    }

    /**
     * @param utente the utente to set
     */
    public void setUtente(String utente) {
        this.utente = utente;
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
        return DataEmissione;
    }

    /**
     * @param DataEmissione the DataEmissione to set
     */
    public void setDataEmissione(Date DataEmissione) {
        this.DataEmissione = DataEmissione;
    }

    /**
     * @return the DataScadenza
     */
    public Date getDataScadenza() {
        return DataScadenza;
    }

    /**
     * @param DataScadenza the DataScadenza to set
     */
    public void setDataScadenza(Date DataScadenza) {
        this.DataScadenza = DataScadenza;
    }

    /**
     * @return the DurataInGiorni
     */
    public Integer getDurataInGiorni() {
        return DurataInGiorni;
    }

    /**
     * @param DurataInGiorni the DurataInGiorni to set
     */
    public void setDurataInGiorni(Integer DurataInGiorni) {
        this.DurataInGiorni = DurataInGiorni;
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
        return DataPrimoUtilizzo;
    }

    /**
     * @param DataPrimoUtilizzo the DataPrimoUtilizzo to set
     */
    public void setDataPrimoUtilizzo(Date DataPrimoUtilizzo) {
        this.DataPrimoUtilizzo = DataPrimoUtilizzo;
    }

    /**
     * @return the DataScadenzaUtilizzo
     */
    public Date getDataScadenzaUtilizzo() {
        return DataScadenzaUtilizzo;
    }

    /**
     * @param DataScadenzaUtilizzo the DataScadenzaUtilizzo to set
     */
    public void setDataScadenzaUtilizzo(Date DataScadenzaUtilizzo) {
        this.DataScadenzaUtilizzo = DataScadenzaUtilizzo;
    }

    /**
     * @return the Stato
     */
    public String getStato() {
        return Stato;
    }

    /**
     * @param Stato the Stato to set
     */
    public void setStato(String Stato) {
        this.Stato = Stato;
    }

    /**
     * @return the nomecognome
     */
    public String getNomecognome() {
        return nomecognome;
    }

    /**
     * @param nomecognome the nomecognome to set
     */
    public void setNomecognome(String nomecognome) {
        this.nomecognome = nomecognome;
    }

    /**
     * @return the numerodocumento
     */
    public String getNumerodocumento() {
        return numerodocumento;
    }

    /**
     * @param numerodocumento the numerodocumento to set
     */
    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    /**
     * @return the mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac the mac to set
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

}
