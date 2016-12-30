/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.entities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import wispmanager.util.MD5;

/**
 *
 * @author torsello
 */
@Entity
public class SerieTicket implements Serializable {
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
    private Servizio servizio;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Ticket> tickets=new ArrayList<Ticket>();

    private Integer durataInGiorni=1;
    private Integer numeroTickets=50;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date DataEmissione=new Date();
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date DataScadenza=new Date();
    private String Seriale="";

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
        if (!(object instanceof SerieTicket)) {
            return false;
        }
        SerieTicket other = (SerieTicket) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Serie Ticket per cliente: "+cliente.toString();
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
     * @return the tickets
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * @param tickets the tickets to set
     */
    public void setTickets(List<Ticket> tickets) {
        this.setTickets(tickets);
    }

    public void generaTickets() {
        int i=0;
        MD5 md = new MD5();  
        String ser=getSeriale();
        tickets.clear();
        for(i=0;i<getNumeroTickets();i++)
        {
            Ticket tck=new Ticket();
            tck.setCliente(getCliente());
            tck.setDurataInGiorni(getDurataInGiorni());

            DataEmissione=new Date(); DataScadenza=new Date();
            DataScadenza.setTime(DataEmissione.getTime()+31536000000L);
            tck.setDataEmissione(DataEmissione);
            tck.setDataScadenza(DataScadenza);            
            tck.setSeriale(ser+"-"+Integer.toString(i));
            tck.setUtente(GeneraPassCasuale(8));
            tck.setPassword(GeneraPassCasuale(8));
            
            tickets.add(tck);
        }
    }

    private String GeneraPassCasuale(int lenght)
    {
        String pass="";
        String charArray[]={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","y","w","z","1","2","3","4","5","6","7","8","9","0"};
        int i=0;
        while(i<lenght)
        {
            int index=(int) (((float)charArray.length) * (float)java.lang.Math.random());
            pass+=charArray[index];
            i++;
        }
        return pass;
    }
    /**
     * @return the durataInGiorni
     */
    public Integer getDurataInGiorni() {
        return durataInGiorni;
    }

    /**
     * @param durataInGiorni the durataInGiorni to set
     */
    public void setDurataInGiorni(Integer durataInGiorni) {
        this.durataInGiorni = durataInGiorni;
    }

    /**
     * @return the numeroTickets
     */
    public Integer getNumeroTickets() {
        return numeroTickets;
    }

    /**
     * @param numeroTickets the numeroTickets to set
     */
    public void setNumeroTickets(Integer numeroTickets) {
        this.numeroTickets = numeroTickets;
    }

   

    /**
     * @return the DataEmissione
     */
    public Date getDataEmissione() {
        return DataEmissione;
    }

    /**
     * @param DataEmissione the DataEmissione to set
     */
    public void setDataEmissione(Date DataEmissione) {
        this.DataEmissione = DataEmissione;
    }

    /**
     * @return the DataScadenza
     */
    public Date getDataScadenza() {
        return DataScadenza;
    }

    /**
     * @param DataScadenza the DataScadenza to set
     */
    public void setDataScadenza(Date DataScadenza) {
        this.DataScadenza = DataScadenza;
    }

    /**
     * @return the servizio
     */
    public Servizio getServizio() {
        return servizio;
    }

    /**
     * @param servizio the servizio to set
     */
    public void setServizio(Servizio servizio) {
        this.servizio = servizio;
    }

    /**
     * @return the Seriale
     */
    public String getSeriale() {        
        return Seriale;
    }

    /**
     * @param Seriale the Seriale to set
     */
    public void setSeriale(String Seriale) {
        this.Seriale = Seriale;
    }

    

   public void generaSeriale()
   {
            Long l=(new Date()).getTime();
            Seriale=l.toString();
   }


   public void StampaSerie(String FileXML) throws FileNotFoundException
    {

        List<Ticket> l=getTickets();
        String XMLHeader="<SerieTicket>\n";
        XMLHeader+="<SerieTicket>\n"+getSeriale()+"</SerieTicket>\n";
        XMLHeader+="<DenominazioneAzienda>\n"+cliente.getClientePersonaGiuridica().getDenominazione()+"</DenominazioneAzienda>\n";
        XMLHeader+="<Page>";
        String XMLFoot="</Page>\n</SerieTicket>";
        try {
        FileOutputStream fos=new FileOutputStream(FileXML);
        fos.write(XMLHeader.getBytes());
        SimpleDateFormat df=new SimpleDateFormat();
        int i=0;
                while (i<l.size())
                {
                    Ticket tck=l.get(i);
                    String seriale=tck.getSeriale();
                    String user=tck.getUtente();
                    String password=tck.getPassword();
                    String dataemissione=df.format(tck.getDataEmissione());
                    String duratagiorni=tck.getDurataInGiorni().toString();
                    String datascadenza=df.format(tck.getDataScadenza());
                    String distributore=tck.getCliente().toString();


                    String Ticket="\t<Ticket>\n" +
                            "\t\t<seriale>"+seriale+"</seriale>\n"+
                            "\t\t<user>"+user+"</user>\n"+
                            "\t\t<password>"+password+"</password>\n"+
                            "\t\t<duratagiorni>"+duratagiorni+"</duratagiorni>\n"+
                            "\t\t<dataemissione>"+dataemissione+"</dataemissione>\n"+
                            "\t\t<datascadenza>"+datascadenza+"</datascadenza>\n"+
                            "\t\t<distributore>"+distributore+"</distributore>\n"+
                            "\t</Ticket>\n";
                    fos.write(Ticket.getBytes());
                    i++;
               }
            fos.write(XMLFoot.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
