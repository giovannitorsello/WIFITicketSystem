/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author torsello
 */
@Entity
public class Location implements Serializable {
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
    private String Longitude;
    private String Latitude;
    private String Altitude;
    private String Descritpion;
    @OneToOne
    private PersonaFisica RiferimentoPersonaFisica;
    @OneToOne
    private PersonaGiuridica RiferimentoPersonaGiuridica;


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
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wispmanager.entities.Location[id=" + getId() + "]";
    }

    /**
     * @return the Longitude
     */
    public String getLongitude() {
        return Longitude;
    }

    /**
     * @param Longitude the Longitude to set
     */
    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    /**
     * @return the Latitude
     */
    public String getLatitude() {
        return Latitude;
    }

    /**
     * @param Latitude the Latitude to set
     */
    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    /**
     * @return the Altitude
     */
    public String getAltitude() {
        return Altitude;
    }

    /**
     * @param Altitude the Altitude to set
     */
    public void setAltitude(String Altitude) {
        this.Altitude = Altitude;
    }

    /**
     * @return the Descritpion
     */
    public String getDescritpion() {
        return Descritpion;
    }

    /**
     * @param Descritpion the Descritpion to set
     */
    public void setDescritpion(String Descritpion) {
        this.Descritpion = Descritpion;
    }

    /**
     * @return the RiferimentoPersonaFisica
     */
    public PersonaFisica getRiferimentoPersonaFisica() {
        return RiferimentoPersonaFisica;
    }

    /**
     * @param RiferimentoPersonaFisica the RiferimentoPersonaFisica to set
     */
    public void setRiferimentoPersonaFisica(PersonaFisica RiferimentoPersonaFisica) {
        this.RiferimentoPersonaFisica = RiferimentoPersonaFisica;
    }

    /**
     * @return the RiferimentoPersonaGiuridica
     */
    public PersonaGiuridica getRiferimentoPersonaGiuridica() {
        return RiferimentoPersonaGiuridica;
    }

    /**
     * @param RiferimentoPersonaGiuridica the RiferimentoPersonaGiuridica to set
     */
    public void setRiferimentoPersonaGiuridica(PersonaGiuridica RiferimentoPersonaGiuridica) {
        this.RiferimentoPersonaGiuridica = RiferimentoPersonaGiuridica;
    }

}
