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
public class Device implements Serializable {
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
    private String Description;
    private String NetworkAddress;
    private String NetworkNetmask;
    private String NetworkGateway;
    private String NetworkDNSPrimary; //Example: 192.168.0.1
    private String NetworkDNSSecondary; //Example: 192.168.0.1
    private String Producer;
    private String Model;
    private String Firmware;
    @OneToOne
    private Location InstallLocation;
    private byte[] Configuration;
    
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
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wispmanager.entities.Device[id=" + getId() + "]";
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param Description the Description to set
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * @return the NetworkAddress
     */
    public String getNetworkAddress() {
        return NetworkAddress;
    }

    /**
     * @param NetworkAddress the NetworkAddress to set
     */
    public void setNetworkAddress(String NetworkAddress) {
        this.NetworkAddress = NetworkAddress;
    }

    /**
     * @return the NetworkNetmask
     */
    public String getNetworkNetmask() {
        return NetworkNetmask;
    }

    /**
     * @param NetworkNetmask the NetworkNetmask to set
     */
    public void setNetworkNetmask(String NetworkNetmask) {
        this.NetworkNetmask = NetworkNetmask;
    }

    /**
     * @return the NetworkGateway
     */
    public String getNetworkGateway() {
        return NetworkGateway;
    }

    /**
     * @param NetworkGateway the NetworkGateway to set
     */
    public void setNetworkGateway(String NetworkGateway) {
        this.NetworkGateway = NetworkGateway;
    }

    /**
     * @return the NetworkDNSPrimary
     */
    public String getNetworkDNSPrimary() {
        return NetworkDNSPrimary;
    }

    /**
     * @param NetworkDNSPrimary the NetworkDNSPrimary to set
     */
    public void setNetworkDNSPrimary(String NetworkDNSPrimary) {
        this.NetworkDNSPrimary = NetworkDNSPrimary;
    }

    /**
     * @return the NetworkDNSSecondary
     */
    public String getNetworkDNSSecondary() {
        return NetworkDNSSecondary;
    }

    /**
     * @param NetworkDNSSecondary the NetworkDNSSecondary to set
     */
    public void setNetworkDNSSecondary(String NetworkDNSSecondary) {
        this.NetworkDNSSecondary = NetworkDNSSecondary;
    }

    /**
     * @return the Producer
     */
    public String getProducer() {
        return Producer;
    }

    /**
     * @param Producer the Producer to set
     */
    public void setProducer(String Producer) {
        this.Producer = Producer;
    }

    /**
     * @return the Model
     */
    public String getModel() {
        return Model;
    }

    /**
     * @param Model the Model to set
     */
    public void setModel(String Model) {
        this.Model = Model;
    }

    /**
     * @return the Firmware
     */
    public String getFirmware() {
        return Firmware;
    }

    /**
     * @param Firmware the Firmware to set
     */
    public void setFirmware(String Firmware) {
        this.Firmware = Firmware;
    }

    /**
     * @return the InstallLocation
     */
    public Location getInstallLocation() {
        return InstallLocation;
    }

    /**
     * @param InstallLocation the InstallLocation to set
     */
    public void setInstallLocation(Location InstallLocation) {
        this.InstallLocation = InstallLocation;
    }

    /**
     * @return the Configuration
     */
    public byte[] getConfiguration() {
        return Configuration;
    }

    /**
     * @param Configuration the Configuration to set
     */
    public void setConfiguration(byte[] Configuration) {
        this.setConfiguration(Configuration);
    }

   
   
}
