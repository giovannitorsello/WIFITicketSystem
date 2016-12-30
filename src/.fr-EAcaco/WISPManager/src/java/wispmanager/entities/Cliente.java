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
public class Cliente implements Serializable {
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
    private PersonaGiuridica ClientePersonaGiuridica;
    @OneToOne
    private PersonaFisica ClientePersonaFisica;
 
    @OneToMany
    private List<Servizio> servizi=new ArrayList<Servizio>();

    @OneToMany
    private List<NumerazioneFonia> numerazioniFonia=new ArrayList<NumerazioneFonia>();

    @OneToMany
    private List<SerieTicket> serieTicket=new ArrayList<SerieTicket>();
    private String NoteCliente;

        

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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
       }
        return true;
    }

    @Override
    public String toString() {
        if(ClientePersonaGiuridica!=null) return ClientePersonaGiuridica.toString()+"_"+ClientePersonaGiuridica.getPartitaIva();
        if(ClientePersonaFisica!=null) return ClientePersonaFisica.toString()+"_"+ClientePersonaFisica.getCodiceFiscale();
        return "wispmanager.entities.Cliente[id=" + getId() + "]";
    }

    /**
     * @return the ClientePersonaGiuridica
     */
    public PersonaGiuridica getClientePersonaGiuridica() {
        return ClientePersonaGiuridica;
    }

    /**
     * @param ClientePersonaGiuridica the ClientePersonaGiuridica to set
     */
    public void setClientePersonaGiuridica(PersonaGiuridica ClientePersonaGiuridica) {
        this.ClientePersonaGiuridica = ClientePersonaGiuridica;
    }

    /**
     * @return the ClientePersonaFisica
     */
    public PersonaFisica getClientePersonaFisica() {
        return ClientePersonaFisica;
    }

    /**
     * @param ClientePersonaFisica the ClientePersonaFisica to set
     */
    public void setClientePersonaFisica(PersonaFisica ClientePersonaFisica) {
        this.ClientePersonaFisica = ClientePersonaFisica;
    }

    /**
     * @return the servizi
     */
    public List<Servizio> getServizi() {
        return servizi;
    }

    /**
     * @param servizi the servizi to set
     */
    public void setServizi(List<Servizio> servizi) {
        this.servizi = servizi;
    }

    /**
     * @return the NoteCliente
     */
    public String getNoteCliente() {
        return NoteCliente;
    }

    /**
     * @param NoteCliente the NoteCliente to set
     */
    public void setNoteCliente(String NoteCliente) {
        this.NoteCliente = NoteCliente;
    }

    /**
     * @return the serieTicket
     */
    public List<SerieTicket> getSerieTicket() {
        return serieTicket;
    }

    /**
     * @param serieTicket the serieTicket to set
     */
    public void setSerieTicket(List<SerieTicket> serieTicket) {
        this.serieTicket = serieTicket;
    }

    public void aggiungiServizio(Servizio servizio)
    {
        java.text.SimpleDateFormat df=new java.text.SimpleDateFormat();

        if(servizio.getTipologia().compareTo("ticket")==0)
        {
            int num_tickets=servizio.getUnitaDiConsumo();
            SerieTicket st=new SerieTicket();
            st.setCliente(this);
            st.setNumeroTickets(num_tickets);
            st.setDurataInGiorni(servizio.getBaseTemporaleImporto());
            st.generaTickets();
            serieTicket.add(st);
            NoteCliente+="Servizio "+st.toString()+" emesso in data "+df.format(new java.util.Date())+"\n";
        }
        else
        {
            servizi.add(servizio);
            NoteCliente+="Servizio "+servizio.toString()+" avviato in data "+df.format(new java.util.Date())+"\n";
        }
    }

    public void eliminaServizio(Servizio servizio)
    {
        java.text.SimpleDateFormat df=new java.text.SimpleDateFormat();

        if(servizio.getTipologia().compareTo("ticket")==0)
        {
             NoteCliente+="Servizio "+serieTicket.toString()+" ritirato in data "+df.format(new java.util.Date())+"\n";
        }
        else
        {
            servizi.remove(servizio);
            NoteCliente+="Servizio "+servizio.toString()+" concluso in data "+df.format(new java.util.Date())+"\n";
        }
    }

    public void eliminaSerieTicket(SerieTicket serieTck) {
        java.text.SimpleDateFormat df=new java.text.SimpleDateFormat();
        serieTicket.remove(serieTck);
        NoteCliente+="Servizio "+serieTicket.toString()+" ritirato in data "+df.format(new java.util.Date())+"\n";
    }

    /**
     * @return the numerazioniFonia
     */
    public List<NumerazioneFonia> getNumerazioniFonia() {
        return numerazioniFonia;
    }

    /**
     * @param numerazioniFonia the numerazioniFonia to set
     */
    public void setNumerazioniFonia(List<NumerazioneFonia> numerazioniFonia) {
        this.numerazioniFonia = numerazioniFonia;
    }

    public void aggiungiNumerazioneFonia(NumerazioneFonia numerazioneFonia) {
        java.text.SimpleDateFormat df=new java.text.SimpleDateFormat();
        numerazioniFonia.add(numerazioneFonia);
        NoteCliente+="Numerazione "+numerazioneFonia.toString()+" avviato in data "+df.format(new java.util.Date())+"\n";
    }

    public void eliminaNumerazioneFonia(NumerazioneFonia numerazioneFonia) {
        java.text.SimpleDateFormat df=new java.text.SimpleDateFormat();
        numerazioniFonia.remove(numerazioneFonia);
        NoteCliente+="Numerazione "+numerazioneFonia.toString()+" ritirato in data "+df.format(new java.util.Date())+"\n";
    }
}
