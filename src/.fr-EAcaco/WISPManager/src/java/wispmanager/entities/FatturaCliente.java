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
public class FatturaCliente implements Serializable {
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
    private Cliente ClienteFat;
    private Float[] Importi;
    private String[] Servizi;
    private String[] IvaServizi;
    private Float Imponibile;
    private Float IvaTotale;
    private Float TotaleFattura;

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
        if (!(object instanceof FatturaCliente)) {
            return false;
        }
        FatturaCliente other = (FatturaCliente) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wispmanager.entities.FatturaCliente[id=" + getId() + "]";
    }

    /**
     * @return the ClienteFat
     */
    public Cliente getClienteFat() {
        return ClienteFat;
    }

    /**
     * @param ClienteFat the ClienteFat to set
     */
    public void setClienteFat(Cliente ClienteFat) {
        this.ClienteFat = ClienteFat;
    }

    /**
     * @return the Importi
     */
    public Float[] getImporti() {
        return Importi;
    }

    /**
     * @param Importi the Importi to set
     */
    public void setImporti(Float[] Importi) {
        this.setImporti(Importi);
    }

    /**
     * @return the Servizi
     */
    public String[] getServizi() {
        return Servizi;
    }

    /**
     * @param Servizi the Servizi to set
     */
    public void setServizi(String[] Servizi) {
        this.setServizi(Servizi);
    }

    /**
     * @return the IvaServizi
     */
    public String[] getIvaServizi() {
        return IvaServizi;
    }

    /**
     * @param IvaServizi the IvaServizi to set
     */
    public void setIvaServizi(String[] IvaServizi) {
        this.setIvaServizi(IvaServizi);
    }

    /**
     * @return the Imponibile
     */
    public Float getImponibile() {
        return Imponibile;
    }

    /**
     * @param Imponibile the Imponibile to set
     */
    public void setImponibile(Float Imponibile) {
        this.Imponibile = Imponibile;
    }

    /**
     * @return the IvaTotale
     */
    public Float getIvaTotale() {
        return IvaTotale;
    }

    /**
     * @param IvaTotale the IvaTotale to set
     */
    public void setIvaTotale(Float IvaTotale) {
        this.IvaTotale = IvaTotale;
    }

    /**
     * @return the TotaleFattura
     */
    public Float getTotaleFattura() {
        return TotaleFattura;
    }

    /**
     * @param TotaleFattura the TotaleFattura to set
     */
    public void setTotaleFattura(Float TotaleFattura) {
        this.TotaleFattura = TotaleFattura;
    }

   
}
