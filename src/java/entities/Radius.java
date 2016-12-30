/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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
public class Radius  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String utente;
    private String password;
    private String serverRadius;
    private String databaseRadius;
    private String tableRadius;
    private String queryInsert;
    private String queryRemove;
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    public Radius()
    {
        serverRadius="10.21.0.101";
        databaseRadius="radius";
        utente="radius";
        password="radius";
        tableRadius="radcheck";
        queryInsert="INSERT INTO "+tableRadius+" (UserName, Attribute, Value) VALUES (%%user_name%%, 'Password', %%user_password%%);";
        queryRemove="DELETE FROM "+tableRadius+" WHERE (UserName='%%user_name%%' AND Attribute='Password' AND Value='%%user_password%%');";
    }

    /**
     * @return the user
     */
    public String getUtente() {
        return utente;
    }

    /**
     * @param user the user to set
     */
    public void setUtente(String utente) {
        this.utente = utente;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    

    /**
     * @return the server
     */
    public String getServerRadius() {
        return serverRadius;
    }

    /**
     * @param server the server to set
     */
    public void setServerRadius(String serverRadius) {
        this.serverRadius = serverRadius;
    }

    /**
     * @return the database
     */
    public String getDatabaseRadius() {
        return databaseRadius;
    }

    /**
     * @param database the database to set
     */
    public void setDatabaseRadius(String databaseRadius) {
        this.databaseRadius = databaseRadius;
    }

    /**
     * @return the table
     */
    public String getTableRadius() {
        return tableRadius;
    }

    /**
     * @param table the table to set
     */
    public void setTableRadius(String tableRadius) {
        this.tableRadius = tableRadius;
    }

    /**
     * @return the queryInsert
     */
    public String getQueryInsert() {
        return queryInsert;
    }

    /**
     * @param queryInsert the queryInsert to set
     */
    public void setQueryInsert(String queryInsert) {
        this.queryInsert = queryInsert;
    }

    /**
     * @return the queryRemove
     */
    public String getQueryRemove() {
        return queryRemove;
    }

    /**
     * @param queryRemove the queryRemove to set
     */
    public void setQueryRemove(String queryRemove) {
        this.queryRemove = queryRemove;
    }

    public String getUrlDB() {
        return "jdbc:mysql://"+serverRadius+":3306/"+databaseRadius;
    }
    
    public String getInsertCommand(Ticket tck) {
        String command=queryInsert.replaceAll("%%user_name%%", tck.getLogin());
        command=command.replaceAll("%%user_password%%", tck.getPassword());
        return command;
    }
    
    public String getRemoveCommand(Ticket tck) {
        String command=queryRemove.replaceAll("%%user_name%%", tck.getLogin());
        command=command.replaceAll("%%user_password%%", tck.getPassword());
        return command;
    }
    
}
