/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

/**
 *
 * @author caleb
 */
public class Patient {
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private int DOB;
    private String Gender;
    
    
    
    Patient(String FirstName, String MiddleName, String LastName, int DOB, String phoneNum, String Gender)
    {
        this.FirstName = FirstName;
        this.MiddleName = MiddleName;
        this.LastName = LastName;
        this.DOB = DOB;
        this.Gender = Gender;
    }
    
    String getFirstName()
    {
        return this.FirstName;
    }
    
    String getMiddleName()
    {
        return this.MiddleName;
    }
    
    String getLastName()
    {
        return this.LastName;
    }
    
    int getDOB()
    {
        return this.DOB;
    }
    
    String getGender()
    {
        return this.Gender;
    }
    
    void setFirstName(String FirstName)
    {
        this.FirstName = FirstName;
    }
    
    void setMiddleName(String MiddleName)
    {
        this.MiddleName = MiddleName;
    }
    
    void setLastName(String LastName)
    {
        this.LastName = LastName;
    }
    
    void setDOB(int DOB)
    {
        this.DOB = DOB;
    }
    
    void setGender(String Gender)
    {
        this.Gender = Gender;
    }
     
    
}
