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
public class Fornitore implements Serializable {
    @OneToOne(mappedBy = "fornitore")
    private Servizio servizio;
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
    private PersonaGiuridica fornitorePersonaGiuridica;
    @OneToOne
    private PersonaFisica fornitorePersonaFisica;

    private String noteFornitore="";

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
        if (!(object instanceof Fornitore)) {
            return false;
        }
        Fornitore other = (Fornitore) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(fornitorePersonaGiuridica!=null) return fornitorePersonaGiuridica.toString()+"_"+fornitorePersonaGiuridica.getPartitaIva();
        if(fornitorePersonaFisica!=null) return fornitorePersonaFisica.toString()+"_"+fornitorePersonaFisica.getCodiceFiscale();
        return "wispmanager.entities.Fornitore[id=" + getId() + "]";
    }

    /**
     * @return the fornitorePersonaGiuridica
     */
    public PersonaGiuridica getFornitorePersonaGiuridica() {
        return fornitorePersonaGiuridica;
    }

    /**
     * @param fornitorePersonaGiuridica the fornitorePersonaGiuridica to set
     */
    public void setFornitorePersonaGiuridica(PersonaGiuridica fornitorePersonaGiuridica) {
        this.fornitorePersonaGiuridica = fornitorePersonaGiuridica;
    }

    /**
     * @return the fornitorePersonaFisica
     */
    public PersonaFisica getFornitorePersonaFisica() {
        return fornitorePersonaFisica;
    }

    /**
     * @param fornitorePersonaFisica the fornitorePersonaFisica to set
     */
    public void setFornitorePersonaFisica(PersonaFisica fornitorePersonaFisica) {
        this.fornitorePersonaFisica = fornitorePersonaFisica;
    }

    /**
     * @return the NoteFornitore
     */
    public String getNoteFornitore() {
        return noteFornitore;
    }

    /**
     * @param NoteFornitore the NoteFornitore to set
     */
    public void setNoteFornitore(String NoteFornitore) {
        this.noteFornitore = NoteFornitore;
    }

}
