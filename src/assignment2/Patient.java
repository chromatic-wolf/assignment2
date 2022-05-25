/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author caleb
 */
public class Patient {

    private SimpleIntegerProperty patientID;
    private SimpleStringProperty FirstName;
    private SimpleStringProperty MiddleName;
    private SimpleStringProperty LastName;
    private SimpleStringProperty DOB;
    private SimpleStringProperty phoneNum;
    private SimpleStringProperty Gender;

    boolean deletePatient(DataBaseConnector dbConnection) {
        try {
            String sql = " DELETE FROM covidtestdb.patient WHERE PatientID=?; ";

            PreparedStatement queryInsertPatient = dbConnection.getConnectionObj().prepareStatement(sql);
            queryInsertPatient.setInt(1, Integer.valueOf(this.patientID.get()));
            queryInsertPatient.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error cannot delete");
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
    

    public Patient(Integer id, String FirstName, String MiddleName, String LastName, String DOB, String phoneNum, String Gender) {
        this.patientID = new SimpleIntegerProperty(id);
        this.FirstName = new SimpleStringProperty(FirstName);
        this.MiddleName = new SimpleStringProperty(MiddleName);
        this.LastName = new SimpleStringProperty(LastName);
        this.DOB = new SimpleStringProperty(DOB);
        this.phoneNum = new SimpleStringProperty(phoneNum);
        this.Gender = new SimpleStringProperty(Gender);
    }

    public Integer getPatientID() {
        return Integer.valueOf(this.patientID.get());
    }

    public String getFirstName() {
        return this.FirstName.get();
    }

    public String getMiddleName() {
        return this.MiddleName.get();
    }

    public String getLastName() {
        return this.LastName.get();
    }

    public String getDOB() {
        return this.DOB.get();
    }

    public String getPhoneNum() {
        return this.phoneNum.get();
    }

    public String getGender() {
        return this.Gender.get();
    }

    public void setID(SimpleIntegerProperty id) {
        this.patientID = id;
    }

    public void setFirstName(SimpleStringProperty FirstName) {
        this.FirstName = FirstName;
    }

    public void setMiddleName(SimpleStringProperty MiddleName) {
        this.MiddleName = MiddleName;
    }

    public void setLastName(SimpleStringProperty LastName) {
        this.LastName = LastName;
    }

    public void setDOB(SimpleStringProperty DOB) {
        this.DOB = DOB;
    }

    public void setPhoneNum(SimpleStringProperty phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setGender(SimpleStringProperty Gender) {
        this.Gender = Gender;
    }

    public void printAll() {
        System.out.println("ID:" + this.patientID);

        System.out.println("First:" + this.FirstName);
        System.out.println("Middle:" + this.MiddleName);
        System.out.println("Last:" + this.LastName);
        System.out.println("DOB:" + this.DOB);
        System.out.println("Phone:" + this.phoneNum);
        System.out.println("Gender:" + this.Gender);

    }

}
