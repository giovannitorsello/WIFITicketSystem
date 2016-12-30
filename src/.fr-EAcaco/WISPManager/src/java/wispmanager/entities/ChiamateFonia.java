/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.entities;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class ChiamateFonia implements Serializable {
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
    private Timestamp OraChiamata;
    private Integer DurataInSecondi;
    private String OperatoreDestinatario;
    private String OperatoreChiamante;
    private String NumeroChiamante;
    private String NumeroDestinazione;
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
        if (!(object instanceof ChiamateFonia)) {
            return false;
        }
        ChiamateFonia other = (ChiamateFonia) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wispmanager.entities.ChiamateFonia[id=" + getId() + "]";
    }

    /**
     * @return the OraChiamata
     */
    public Timestamp getOraChiamata() {
        return OraChiamata;
    }

    /**
     * @param OraChiamata the OraChiamata to set
     */
    public void setOraChiamata(Timestamp OraChiamata) {
        this.OraChiamata = OraChiamata;
    }

    /**
     * @return the DurataInSecondi
     */
    public Integer getDurataInSecondi() {
        return DurataInSecondi;
    }

    /**
     * @param DurataInSecondi the DurataInSecondi to set
     */
    public void setDurataInSecondi(Integer DurataInSecondi) {
        this.DurataInSecondi = DurataInSecondi;
    }

    /**
     * @return the OperatoreDestinatario
     */
    public String getOperatoreDestinatario() {
        return OperatoreDestinatario;
    }

    /**
     * @param OperatoreDestinatario the OperatoreDestinatario to set
     */
    public void setOperatoreDestinatario(String OperatoreDestinatario) {
        this.OperatoreDestinatario = OperatoreDestinatario;
    }

    /**
     * @return the OperatoreChiamante
     */
    public String getOperatoreChiamante() {
        return OperatoreChiamante;
    }

    /**
     * @param OperatoreChiamante the OperatoreChiamante to set
     */
    public void setOperatoreChiamante(String OperatoreChiamante) {
        this.OperatoreChiamante = OperatoreChiamante;
    }

    /**
     * @return the NumeroChiamante
     */
    public String getNumeroChiamante() {
        return NumeroChiamante;
    }

    /**
     * @param NumeroChiamante the NumeroChiamante to set
     */
    public void setNumeroChiamante(String NumeroChiamante) {
        this.NumeroChiamante = NumeroChiamante;
    }

    /**
     * @return the NumeroDestinazione
     */
    public String getNumeroDestinazione() {
        return NumeroDestinazione;
    }

    /**
     * @param NumeroDestinazione the NumeroDestinazione to set
     */
    public void setNumeroDestinazione(String NumeroDestinazione) {
        this.NumeroDestinazione = NumeroDestinazione;
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
