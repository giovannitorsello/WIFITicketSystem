/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author torsello
 */

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOdatabase {

   
    //Variabile per memorizzazione connessione al database
    private Connection conn;
    private ResultSet resSet;
    private String urlDB="jdbc:mysql://5.83.124.9:3306/radius";
    private String user;
    private String password;
    public boolean OpenConnection()
    {
        try {
                Class.forName("com.mysql.jdbc.Driver");                
                conn = DriverManager.getConnection(urlDB, user, password); 
                if(conn !=null) return true;
                else return false;
        } catch (Exception ex) {
            Logger.getLogger(IOdatabase.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return false;
    }
    
    public void CloseConnection() {
        try {
            if(conn!=null)
                conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(IOdatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet ExecuteQuery(String query)
    {
        try {
            Statement stmt = conn.createStatement();
            resSet = stmt.executeQuery(query);
            return resSet;
        } catch (Exception ex) {
            Logger.getLogger(IOdatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        
    public void ExecuteCommand(String command)
    {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(command);            
        } catch (Exception ex) {
            Logger.getLogger(IOdatabase.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    /**
     * @return the urlDB
     */
    public String getUrlDB() {
        return urlDB;
    }

    /**
     * @param urlDB the urlDB to set
     */
    public void setUrlDB(String urlDB) {
        this.urlDB = urlDB;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
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

    
}

