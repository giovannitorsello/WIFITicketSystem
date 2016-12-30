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

/**
 *
 * @author torsello
 */
@Entity
public class Network implements Serializable {
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

    private String Netmask; //Example: 255.255.0.0
    private String NetworkClass;   //Example: 192.168.0.0
    private String NetworkGateway; //Example: 192.168.0.1
    private String NetworkDNSPrimary; //Example: 192.168.0.1
    private String NetworkDNSSecondary; //Example: 192.168.0.1


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
        if (!(object instanceof Network)) {
            return false;
        }
        Network other = (Network) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wispmanager.entities.Network[id=" + getId() + "]";
    }

    /**
     * @return the Netmask
     */
    public String getNetmask() {
        return Netmask;
    }

    /**
     * @param Netmask the Netmask to set
     */
    public void setNetmask(String Netmask) {
        this.Netmask = Netmask;
    }

    /**
     * @return the NetworkClass
     */
    public String getNetworkClass() {
        return NetworkClass;
    }

    /**
     * @param NetworkClass the NetworkClass to set
     */
    public void setNetworkClass(String NetworkClass) {
        this.NetworkClass = NetworkClass;
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

}
