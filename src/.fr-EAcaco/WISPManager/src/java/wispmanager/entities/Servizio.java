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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author torsello
 */
@Entity
public class Servizio implements Serializable {
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
    private String Descrizione;
    private String DescrizioneEstesa;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date DataInizio=new Date();
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date DataFine=new Date();
    private String DescrizioneUnitadiConsumo=""; //descrizione della tipologia di unita di consumo scatti, ticket ecc.
    private Integer UnitaDiConsumo=0; //es. secondi di conversazione, scatti ecc.
    private Float Attivazione=new Float(0.0); //Costo di attivazione del servizio
    private Float Importo=new Float(0.0);
    private Float IVA=new Float(20.0);
    private Integer BaseTemporaleImporto=0; //Giornaliera=1,settimanale=7,Mensile=30,Bimestrale=60, Trimestrale=90 ecc.
    private Integer IntervalloFatturazione=0; //Mensile=30,Bimestrale=60, Trimestrale=90 ecc.
    private String Tipologia=""; //canone, consumo, telefonia, manutenzione, ticket ecc.
    @OneToOne
    private Fornitore fornitore=new Fornitore();

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
        if (!(object instanceof Servizio)) {
            return false;
        }
        Servizio other = (Servizio) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "wispmanager.entities.ServizioCosumo[id=" + getId() + "]";
        return Descrizione;
    }

    /**
     * @return the DataInizio
     */
    public Date getDataInizio() {
        return DataInizio;
    }

    /**
     * @param DataInizio the DataInizio to set
     */
    public void setDataInizio(Date DataInizio) {
        this.DataInizio = DataInizio;
    }

    /**
     * @return the DataFine
     */
    public Date getDataFine() {
        return DataFine;
    }

    /**
     * @param DataFine the DataFine to set
     */
    public void setDataFine(Date DataFine) {
        this.DataFine = DataFine;
    }

    /**
     * @return the UnitaDiConsumo
     */
    public Integer getUnitaDiConsumo() {
        return UnitaDiConsumo;
    }

    /**
     * @param UnitaDiConsumo the UnitaDiConsumo to set
     */
    public void setUnitaDiConsumo(Integer UnitaDiConsumo) {
        this.UnitaDiConsumo = UnitaDiConsumo;
    }

    /**
     * @return the Importo
     */
    public Float getImporto() {
        return Importo;
    }

    /**
     * @param Importo the Importo to set
     */
    public void setImporto(Float Importo) {
        this.Importo = Importo;
    }

    /**
     * @return the IVA
     */
    public Float getIVA() {
        return IVA;
    }

    /**
     * @param IVA the IVA to set
     */
    public void setIVA(Float IVA) {
        this.IVA = IVA;
    }

    /**
     * @return the BaseTemporaleImporto
     */
    public Integer getBaseTemporaleImporto() {
        return BaseTemporaleImporto;
    }

    /**
     * @param BaseTemporaleImporto the BaseTemporaleImporto to set
     */
    public void setBaseTemporaleImporto(Integer BaseTemporaleImporto) {
        this.BaseTemporaleImporto = BaseTemporaleImporto;
    }

   

    /**
     * @return the Descrizione
     */
    public String getDescrizione() {
        return Descrizione;
    }

    /**
     * @param Descrizione the Descrizione to set
     */
    public void setDescrizione(String Descrizione) {
        this.Descrizione = Descrizione;
    }

    

    /**
     * @return the IntervalloFatturazione
     */
    public Integer getIntervalloFatturazione() {
        return IntervalloFatturazione;
    }

    /**
     * @param IntervalloFatturazione the IntervalloFatturazione to set
     */
    public void setIntervalloFatturazione(Integer IntervalloFatturazione) {
        this.IntervalloFatturazione = IntervalloFatturazione;
    }

    /**
     * @return the DescrizioneUnitadiConsumo
     */
    public String getDescrizioneUnitadiConsumo() {
        return DescrizioneUnitadiConsumo;
    }

    /**
     * @param DescrizioneUnitadiConsumo the DescrizioneUnitadiConsumo to set
     */
    public void setDescrizioneUnitadiConsumo(String DescrizioneUnitadiConsumo) {
        this.DescrizioneUnitadiConsumo = DescrizioneUnitadiConsumo;
    }

    /**
     * @return the Tipologia
     */
    public String getTipologia() {
        return Tipologia;
    }

    /**
     * @param Tipologia the Tipologia to set
     */
    public void setTipologia(String Tipologia) {
        this.Tipologia = Tipologia;
    }

    /**
     * @return the DescrizioneEstesa
     */
    public String getDescrizioneEstesa() {
        return DescrizioneEstesa;
    }

    /**
     * @param DescrizioneEstesa the DescrizioneEstesa to set
     */
    public void setDescrizioneEstesa(String DescrizioneEstesa) {
        this.DescrizioneEstesa = DescrizioneEstesa;
    }

    /**
     * @return the Attivazione
     */
    public Float getAttivazione() {
        return Attivazione;
    }

    /**
     * @param Attivazione the Attivazione to set
     */
    public void setAttivazione(Float Attivazione) {
        this.Attivazione = Attivazione;
    }

    /**
     * @return the fornitore
     */
    public Fornitore getFornitore() {
        return fornitore;
    }

    /**
     * @param fornitore the fornitore to set
     */
    public void setFornitore(Fornitore fornitore) {
        this.fornitore = fornitore;
    }

    
   
}
