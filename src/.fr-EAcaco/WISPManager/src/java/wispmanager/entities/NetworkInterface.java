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
public class NetworkInterface implements Serializable {
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
    private String NetworkMAC;
    private String Type; //Ethernet, Wireless ecc.
    @ManyToOne
    private Device DeviceInterface;
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
        if (!(object instanceof NetworkInterface)) {
            return false;
        }
        NetworkInterface other = (NetworkInterface) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wispmanager.entities.NetworkInterface[id=" + getId() + "]";
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
     * @return the NetworkMAC
     */
    public String getNetworkMAC() {
        return NetworkMAC;
    }

    /**
     * @param NetworkMAC the NetworkMAC to set
     */
    public void setNetworkMAC(String NetworkMAC) {
        this.NetworkMAC = NetworkMAC;
    }

    /**
     * @return the Type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param Type the Type to set
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * @return the DeviceInterface
     */
    public Device getDeviceInterface() {
        return DeviceInterface;
    }

    /**
     * @param DeviceInterface the DeviceInterface to set
     */
    public void setDeviceInterface(Device DeviceInterface) {
        this.DeviceInterface = DeviceInterface;
    }

}
