/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author caleb
 */
public class DataBaseConnector {

    private Connection connection;

    DataBaseConnector() {

    }

    boolean connect(String ipAddr, String password) {
        try {
            //"jdbc:mysql://localhost/StudentDB", "root", "Password22"
            connection = DriverManager.getConnection("jdbc:mysql://" + ipAddr + "/covidtestdb", "root", password);
            
            return true;

        } catch (SQLException ex) {
            System.out.println("Error unable to connect \n **STACK TRACE** \n \n \n");
            System.out.println(ex);
            return false;
        }

    }

    private boolean checkConnection(int timeout)
    {
         try {
             return connection.isValid(timeout);
        } catch (SQLException ex) {
            System.out.println("Error connection ERROR \n **STACK TRACE** \n \n \n");
            System.out.println(ex);
            return false;
        }
    }
    
    boolean connectionState() {
       return checkConnection(2000); //default timeout
    }
    boolean connectionState(int timeOut) {
       return checkConnection(timeOut); //custom timeout
    }
    
    
    Connection getConnectionObj()
    {
        return this.connection;
    }

}
