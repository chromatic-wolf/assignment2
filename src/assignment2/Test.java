/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author caleb
 */
public class Test {
    private SimpleIntegerProperty testID;
    private SimpleIntegerProperty patientID;
    private SimpleStringProperty testType;
    private SimpleStringProperty testDate;
    private SimpleStringProperty testResult;
    
    Test(Integer TestID, Integer PatientID, String testType, String testDate, String testResult)
    {
        this.testID = new SimpleIntegerProperty(TestID);
        this.patientID = new SimpleIntegerProperty(PatientID);
        this.testType = new SimpleStringProperty(testType);
        this.testDate = new SimpleStringProperty(testDate);
        this.testResult = new SimpleStringProperty(testResult);
    }
  
  
    public final ObjectProperty<Integer> testID() {
        return new SimpleObjectProperty<>(this.testID.get());
    }
    
    public final ObjectProperty<String> method() {
        return new SimpleObjectProperty<>(this.testType.get());
    }
        public final ObjectProperty<String> date() {
        return new SimpleObjectProperty<>(this.testDate.get());
    }
           public final ObjectProperty<String> result() {
        return new SimpleObjectProperty<>(this.testResult.get());
    }
    
    Integer getTestID()
    {
        return Integer.valueOf(testID.get());
    }
    
    Integer getPatientID()
    {
        return Integer.valueOf(patientID.get());
    }
    
    String getTestType()
    {
        return testType.get();
    }
    
    String getTestDate()
    {
        return testDate.get();
    }
    
    String getTestResult()
    {
        return testResult.get();
    }
    
    void printAll()
    {
        System.out.println("ID: " + testID.get());
        System.out.println("PAT ID: " + patientID.get());
        System.out.println("Type: " + testType.get());
        System.out.println("Date: " + testDate.get());
        System.out.println("res: " + testResult.get());
    }
    
}
