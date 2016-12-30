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
import javax.persistence.ManyToOne;

/**
 *
 * @author torsello
 */
@Entity
public class NumerazioneFonia implements Serializable {
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

    private String areaInternazionale="";
    private String areaLocale="";
    private String numeroTelefono="";
    private String tipoNumerazione="normale"; //normale, emergenza, numero verde
    @ManyToOne
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
        if (!(object instanceof NumerazioneFonia)) {
            return false;
        }
        NumerazioneFonia other = (NumerazioneFonia) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return areaInternazionale+" "+areaLocale+" "+numeroTelefono;
        
        //return "wispmanager.entities.NumerazioneFonia[id=" + getId() + "]";
    }

    /**
     * @return the areaInternazionale
     */
    public String getAreaInternazionale() {
        return areaInternazionale;
    }

    /**
     * @param areaInternazionale the areaInternazionale to set
     */
    public void setAreaInternazionale(String areaInternazionale) {
        this.areaInternazionale = areaInternazionale;
    }

    /**
     * @return the areaLocale
     */
    public String getAreaLocale() {
        return areaLocale;
    }

    /**
     * @param areaLocale the areaLocale to set
     */
    public void setAreaLocale(String areaLocale) {
        this.areaLocale = areaLocale;
    }

    /**
     * @return the numeroTelefono
     */
    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    /**
     * @param numeroTelefono the numeroTelefono to set
     */
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
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
     * @return the tipoNumerazione
     */
    public String getTipoNumerazione() {
        return tipoNumerazione;
    }

    /**
     * @param tipoNumerazione the tipoNumerazione to set
     */
    public void setTipoNumerazione(String tipoNumerazione) {
        this.tipoNumerazione = tipoNumerazione;
    }

}
