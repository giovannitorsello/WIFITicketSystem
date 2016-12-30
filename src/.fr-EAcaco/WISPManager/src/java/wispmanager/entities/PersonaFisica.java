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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
/**
 *
 * @author torsello
 */
@Entity
@NamedQueries({@NamedQuery(name = "PersonaFisica.findById", query = "SELECT p FROM PersonaFisica p WHERE p.id = :id")})
public class PersonaFisica implements Serializable {

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
    private Long id=0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String nome="";
    private String cognome="";
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascita=new Date();
    private String comuneNascita="";
    private String codiceFiscale="";
    private String residenzaVia="";
    private String residenzaComune="";
    private String residenzaCAP="";
    private String residenzaProvincia="";
    private String domicilioVia="";
    private String domicilioComune="";
    private String domicilioCAP="";
    private String domicilioProvincia="";
    private String telefono="";
    private String cellulare="";
    private String email="";
    private byte[] fotoTesseraPersonale;
    private byte[] copiaDocumentoIdentita;
    private byte[] firmaLeggibileDepositata;
    public byte [] getFirmaLeggibileDepositata(){return firmaLeggibileDepositata;}
    private byte[] FirmaDigitale;
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaFisica)) {
            return false;
        }
        PersonaFisica other = (PersonaFisica) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        java.text.SimpleDateFormat df=new java.text.SimpleDateFormat();
        String DataNascita=df.format(getDataNascita());
        return nome+" "+cognome+" nato il "+DataNascita;
        //return "wispmanager.entities.PersonaFisica[id=" + id + "]";
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param cognome the cognome to set
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

  

    /**
     * @return the comuneNascita
     */
    public String getComuneNascita() {
        return comuneNascita;
    }

    /**
     * @param comuneNascita the comuneNascita to set
     */
    public void setComuneNascita(String comuneNascita) {
        this.comuneNascita = comuneNascita;
    }

    /**
     * @return the codiceFiscale
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * @param codiceFiscale the codiceFiscale to set
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * @return the residenzaVia
     */
    public String getResidenzaVia() {
        return residenzaVia;
    }

    /**
     * @param residenzaVia the residenzaVia to set
     */
    public void setResidenzaVia(String residenzaVia) {
        this.residenzaVia = residenzaVia;
    }

    /**
     * @return the residenzaComune
     */
    public String getResidenzaComune() {
        return residenzaComune;
    }

    /**
     * @param residenzaComune the residenzaComune to set
     */
    public void setResidenzaComune(String residenzaComune) {
        this.residenzaComune = residenzaComune;
    }

    /**
     * @return the residenzaCAP
     */
    public String getResidenzaCAP() {
        return residenzaCAP;
    }

    /**
     * @param residenzaCAP the residenzaCAP to set
     */
    public void setResidenzaCAP(String residenzaCAP) {
        this.residenzaCAP = residenzaCAP;
    }

    /**
     * @return the residenzaProvincia
     */
    public String getResidenzaProvincia() {
        return residenzaProvincia;
    }

    /**
     * @param residenzaProvincia the residenzaProvincia to set
     */
    public void setResidenzaProvincia(String residenzaProvincia) {
        this.residenzaProvincia = residenzaProvincia;
    }

    /**
     * @return the domicilioVia
     */
    public String getDomicilioVia() {
        return domicilioVia;
    }

    /**
     * @param domicilioVia the domicilioVia to set
     */
    public void setDomicilioVia(String domicilioVia) {
        this.domicilioVia = domicilioVia;
    }

    /**
     * @return the domicilioComune
     */
    public String getDomicilioComune() {
        return domicilioComune;
    }

    /**
     * @param domicilioComune the domicilioComune to set
     */
    public void setDomicilioComune(String domicilioComune) {
        this.domicilioComune = domicilioComune;
    }

    /**
     * @return the domicilioCAP
     */
    public String getDomicilioCAP() {
        return domicilioCAP;
    }

    /**
     * @param domicilioCAP the domicilioCAP to set
     */
    public void setDomicilioCAP(String domicilioCAP) {
        this.domicilioCAP = domicilioCAP;
    }

    /**
     * @return the domicilioProvincia
     */
    public String getDomicilioProvincia() {
        return domicilioProvincia;
    }

    /**
     * @param domicilioProvincia the domicilioProvincia to set
     */
    public void setDomicilioProvincia(String domicilioProvincia) {
        this.domicilioProvincia = domicilioProvincia;
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
     * @return the fotoTesseraPersonale
     */
    public byte[] getFotoTesseraPersonale() {
        return fotoTesseraPersonale;
    }

    /**
     * @param fotoTesseraPersonale the fotoTesseraPersonale to set
     */
    public void setFotoTesseraPersonale(byte[] fotoTesseraPersonale) {
        this.fotoTesseraPersonale = fotoTesseraPersonale;
    }

    /**
     * @return the copiaDocumentoIdentita
     */
    public byte[] getCopiaDocumentoIdentita() {
        return copiaDocumentoIdentita;
    }

    /**
     * @param copiaDocumentoIdentita the copiaDocumentoIdentita to set
     */
    public void setCopiaDocumentoIdentita(byte[] copiaDocumentoIdentita) {
        this.copiaDocumentoIdentita = copiaDocumentoIdentita;
    }

    /**
     * @param firmaLeggibileDepositata the firmaLeggibileDepositata to set
     */
    public void setFirmaLeggibileDepositata(byte[] firmaLeggibileDepositata) {
        this.firmaLeggibileDepositata = firmaLeggibileDepositata;
    }

    /**
     * @return the FirmaDigitale
     */
    public byte[] getFirmaDigitale() {
        return FirmaDigitale;
    }

    /**
     * @param FirmaDigitale the FirmaDigitale to set
     */
    public void setFirmaDigitale(byte[] FirmaDigitale) {
        this.FirmaDigitale = FirmaDigitale;
    }

    /**
     * @return the dataNascita
     */
    public Date getDataNascita() {
        return dataNascita;
    }

    /**
     * @param dataNascita the dataNascita to set
     */
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

   
}
