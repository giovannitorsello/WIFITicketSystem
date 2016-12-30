/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.entities;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
/**
 *
 * @author torsello
 */
@Entity
@NamedQueries({@NamedQuery(name = "PersonaGiuridica.findById", query = "SELECT p FROM PersonaGiuridica p WHERE p.id = :id")})
public class PersonaGiuridica implements Serializable {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String denominazione;
    private String partitaIva;
    private String sedeLegaleComune;
    private String sedeLegaleVia;
    private String sedeLegaleCap;
    private String sedeLegaleProvincia;
    private String telefono;
    private String cellulare;
    private String email;
    private String sito;
    @OneToOne
    private PersonaFisica legaleRappresentante=new PersonaFisica();
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaGiuridica)) {
            return false;
        }
        PersonaGiuridica other = (PersonaGiuridica) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDenominazione()+"_"+getPartitaIva()+"_"+getLegaleRappresentante().toString();
        //return "wispmanager.entities.PersonaGiuridica[id=" + id + "]";
    }

    /**
     * @return the denominazione
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * @param denominazione the denominazione to set
     */
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    /**
     * @return the partitaIva
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * @param partitaIva the partitaIva to set
     */
    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    /**
     * @return the sedeLegaleComune
     */
    public String getSedeLegaleComune() {
        return sedeLegaleComune;
    }

    /**
     * @param sedeLegaleComune the sedeLegaleComune to set
     */
    public void setSedeLegaleComune(String sedeLegaleComune) {
        this.sedeLegaleComune = sedeLegaleComune;
    }

    /**
     * @return the sedeLegaleVia
     */
    public String getSedeLegaleVia() {
        return sedeLegaleVia;
    }

    /**
     * @param sedeLegaleVia the sedeLegaleVia to set
     */
    public void setSedeLegaleVia(String sedeLegaleVia) {
        this.sedeLegaleVia = sedeLegaleVia;
    }

    /**
     * @return the sedeLegaleCap
     */
    public String getSedeLegaleCap() {
        return sedeLegaleCap;
    }

    /**
     * @param sedeLegaleCap the sedeLegaleCap to set
     */
    public void setSedeLegaleCap(String sedeLegaleCap) {
        this.sedeLegaleCap = sedeLegaleCap;
    }

    /**
     * @return the sedeLegaleProvincia
     */
    public String getSedeLegaleProvincia() {
        return sedeLegaleProvincia;
    }

    /**
     * @param sedeLegaleProvincia the sedeLegaleProvincia to set
     */
    public void setSedeLegaleProvincia(String sedeLegaleProvincia) {
        this.sedeLegaleProvincia = sedeLegaleProvincia;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the cellulare
     */
    public String getCellulare() {
        return cellulare;
    }

    /**
     * @param cellulare the cellulare to set
     */
    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the sito
     */
    public String getSito() {
        return sito;
    }

    /**
     * @param sito the sito to set
     */
    public void setSito(String sito) {
        this.sito = sito;
    }

    /**
     * @return the legaleRappresentante
     */
    public PersonaFisica getLegaleRappresentante() {
        return legaleRappresentante;
    }

    /**
     * @param legaleRappresentante the legaleRappresentante to set
     */
    public void setLegaleRappresentante(PersonaFisica legaleRappresentante) {
        this.legaleRappresentante = legaleRappresentante;
    }


    


}
