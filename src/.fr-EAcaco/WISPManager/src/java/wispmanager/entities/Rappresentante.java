/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author torsello
 */
@Entity
public class Rappresentante implements Serializable {
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
    @OneToOne
    private PersonaGiuridica rappresentantePersonaGiuridica;
    @OneToOne
    private PersonaFisica rappresentantePersonaFisica;
 
    @OneToMany
    private List<Cliente> clienti=new ArrayList<Cliente>();

    private String noteRappresentante="";


    private Float provvigionePercentuleClienti=0f;
    private Float premiomensile=0f;
    private Float premioannuale=0f;

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
        if (!(object instanceof Rappresentante)) {
            return false;
        }
        Rappresentante other = (Rappresentante) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
       }
        return true;
    }

    @Override
    public String toString() {
        if(getRappresentantePersonaGiuridica()!=null) return getRappresentantePersonaGiuridica().toString()+"_"+getRappresentantePersonaGiuridica().getPartitaIva();
        if(getRappresentantePersonaFisica()!=null) return getRappresentantePersonaFisica().toString()+"_"+getRappresentantePersonaFisica().getCodiceFiscale();
        return "wispmanager.entities.Cliente[id=" + getId() + "]";
    }

    /**
     * @return the RappresentantePersonaGiuridica
     */
    public PersonaGiuridica getRappresentantePersonaGiuridica() {
        return rappresentantePersonaGiuridica;
    }

    /**
     * @param RappresentantePersonaGiuridica the RappresentantePersonaGiuridica to set
     */
    public void setRappresentantePersonaGiuridica(PersonaGiuridica RappresentantePersonaGiuridica) {
        this.rappresentantePersonaGiuridica = RappresentantePersonaGiuridica;
    }

    /**
     * @return the RappresentantePersonaFisica
     */
    public PersonaFisica getRappresentantePersonaFisica() {
        return rappresentantePersonaFisica;
    }

    /**
     * @param RappresentantePersonaFisica the RappresentantePersonaFisica to set
     */
    public void setRappresentantePersonaFisica(PersonaFisica RappresentantePersonaFisica) {
        this.rappresentantePersonaFisica = RappresentantePersonaFisica;
    }

    /**
     * @return the clienti
     */
    public List<Cliente> getClienti() {
        return clienti;
    }

    /**
     * @param clienti the clienti to set
     */
    public void setClienti(List<Cliente> clienti) {
        this.setClienti(clienti);
    }

   
    public void aggiungiCliente(Cliente cliente)
    {
        java.text.SimpleDateFormat df=new java.text.SimpleDateFormat();
        getClienti().add(cliente);
        setNoteRappresentante(getNoteRappresentante() + "cliente " + cliente.toString() + " associato in data " + df.format(new java.util.Date()) + "\n");
    }

    public void eliminaCliente(Cliente cliente)
    {
        java.text.SimpleDateFormat df=new java.text.SimpleDateFormat();
        getClienti().remove(cliente);
        setNoteRappresentante(getNoteRappresentante() + "clienti " + cliente.toString() + " rimosso in data " + df.format(new java.util.Date()) + "\n");
    }

    /**
     * @return the noteRappresentante
     */
    public String getNoteRappresentante() {
        return noteRappresentante;
    }

    /**
     * @param noteRappresentante the noteRappresentante to set
     */
    public void setNoteRappresentante(String noteRappresentante) {
        this.noteRappresentante = noteRappresentante;
    }


   
    /**
     * @return the premiomensile
     */
    public Float getPremiomensile() {
        return premiomensile;
    }

    /**
     * @param premiomensile the premiomensile to set
     */
    public void setPremiomensile(Float premiomensile) {
        this.premiomensile = premiomensile;
    }

    /**
     * @return the premioannuale
     */
    public Float getPremioannuale() {
        return premioannuale;
    }

    /**
     * @param premioannuale the premioannuale to set
     */
    public void setPremioannuale(Float premioannuale) {
        this.premioannuale = premioannuale;
    }
   

    /**
     * @return the provvigionePercentuleClienti
     */
    public Float getProvvigionePercentuleClienti() {
        return provvigionePercentuleClienti;
    }

    /**
     * @param provvigionePercentuleClienti the provvigionePercentuleClienti to set
     */
    public void setProvvigionePercentuleClienti(Float provvigionePercentuleClienti) {
        this.provvigionePercentuleClienti = provvigionePercentuleClienti;
    }

   
    
}
