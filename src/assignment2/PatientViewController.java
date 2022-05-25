/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package assignment2;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.NULL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.text.DateFormatter;

/**
 * FXML Controller class
 *
 * @author caleb
 */
public class PatientViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField ui_first_name;
    @FXML
    TextField ui_middle_name;
    @FXML
    TextField ui_last_name;
    @FXML
    TextField ui_phone_number;
    @FXML
    TextField ui_dob;
    @FXML
    TextField ui_gender;
    @FXML
    TextField ui_id_field;
    @FXML
    Button ui_search_id;
    @FXML
    Button ui_add_patient;
    @FXML
    TableView<Patient> ui_table;
    @FXML
    TableColumn<Patient, Integer> ui_table_id;
    @FXML
    TableColumn<Patient, String> ui_table_first_name;
    @FXML
    TableColumn<Patient, String> ui_table_middle_name;
    @FXML
    TableColumn<Patient, String> ui_table_last_name;
    @FXML
    TableColumn<Patient, String> ui_table_phone_number;
    @FXML
    TableColumn<Patient, String> ui_table_dob;
    @FXML
    TableColumn<Patient, String> ui_table_gender;
    @FXML
    Button ui_delete_btn;
    @FXML
    Button ui_view_patient_test_btn;

    ObservableList<Patient> list = FXCollections.observableArrayList();

    DataBaseConnector database;

    void openTestView(Patient patient) {
        //Parent root;

        /*
                    root = FXMLLoader.load(getClass().getResource("PatientView.fxml"));
                    Scene scene = new Scene(root);
                    Stage window = new Stage();
                    window.setScene(scene);
                    window.show();
                    PatientViewController controller = root.getController();
         */
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "TestsView.fxml"
                    )
            );

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(
                    new Scene(loader.load())
            );

            TestsViewController controller = loader.getController();
            controller.initData(database, patient);
            stage.show();

            Stage rootStage = (Stage) ui_first_name.getScene().getWindow();
            // do what you have to do
            //rootStage.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addPatient() {
        System.out.println("Adding patient");
        String sql = "INSERT INTO covidtestdb.patient(FirstName, MiddleName, LastName, DOB, Phone, Gender) VALUES(?,?,?,?,?,?); ";
        try {
            PreparedStatement queryInsertPatient = database.getConnectionObj().prepareStatement(sql);

            queryInsertPatient.setString(1, ui_first_name.getText());
            queryInsertPatient.setString(2, ui_middle_name.getText());
            queryInsertPatient.setString(3, ui_last_name.getText());
            queryInsertPatient.setDate(4, DateTools.StringToDate(ui_dob.getText(), "dd/mm/yyyy"));
            queryInsertPatient.setString(5, ui_phone_number.getText());
            queryInsertPatient.setString(6, ui_gender.getText());

            queryInsertPatient.executeUpdate();
            masterSearch();
        } catch (SQLException ex) {
            System.out.println("Error in executing query");
            System.out.println(ex);
        }
    }

    //Will search using data from all fields
    void masterSearch() {
        System.out.println("Starting master search");

        //String sql = "SELECT * FROM medical.patients WHERE PatientID = ?";
        String sql = "SELECT * FROM covidtestdb.patient WHERE PatientID LIKE NULL OR FirstName LIKE ? AND MiddleName LIKE ? AND LastName LIKE ? AND DOB LIKE ? AND Phone LIKE ? AND Gender LIKE ?;";
        try {
            PreparedStatement searchStatement = database.getConnectionObj().prepareStatement(sql);

            searchStatement.setString(1, ui_first_name.getText() + '%');
            searchStatement.setString(2, ui_middle_name.getText() + '%');
            searchStatement.setString(3, ui_last_name.getText() + '%');

            if (DateTools.StringToDate(ui_dob.getText(), "yyyy-MM-dd") == null) {
                //invalid date
                searchStatement.setString(4, "%");
            } else {
                searchStatement.setDate(4, DateTools.StringToDate(ui_dob.getText(), "yyyy-MM-dd"));
            }

            searchStatement.setString(5, ui_phone_number.getText() + '%');
            searchStatement.setString(6, ui_gender.getText() + '%');

            ResultSet rs = searchStatement.executeQuery();
            int index = 1;
            list.clear();
            while (rs.next()) {
                /*
                if (rs.getString(1).compareToIgnoreCase("Test") == 0) {
                   
                }
                 */
                list.add(new Patient(Integer.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4), DateTools.SqlDateToString(rs.getDate(5), "yyyy-mm-dd"), rs.getString(6), rs.getString(7)));

                index++;

            }
            for (int i = 0; i < list.size(); i++) {
                list.get(i).printAll();
            }
            ui_table.setItems(list);

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    public void initData(DataBaseConnector connector) {
        database = connector;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ui_table_id.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        ui_table_first_name.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        ui_table_middle_name.setCellValueFactory(new PropertyValueFactory<>("MiddleName"));
        ui_table_last_name.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        ui_table_dob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        ui_table_phone_number.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        ui_table_gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));

        ui_first_name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String keyPressed, String text) {
                System.out.println("Key pressed: " + keyPressed + "\n" + "Line: " + text);
                masterSearch();
            }
        });

        ui_middle_name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String keyPressed, String text) {
                System.out.println("Key pressed: " + keyPressed + "\n" + "Line: " + text);
                masterSearch();

            }
        });

        ui_last_name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String keyPressed, String text) {
                System.out.println("Key pressed: " + keyPressed + "\n" + "Line: " + text);
                masterSearch();

            }
        });

        ui_phone_number.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String keyPressed, String text) {
                System.out.println("Key pressed: " + keyPressed + "\n" + "Line: " + text);
                masterSearch();

            }
        });

        ui_dob.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String keyPressed, String text) {
                System.out.println("Key pressed: " + keyPressed + "\n" + "Line: " + text);
                masterSearch();

            }
        });

        ui_gender.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String keyPressed, String text) {
                System.out.println("Key pressed: " + keyPressed + "\n" + "Line: " + text);
                masterSearch();

            }
        });

        ui_id_field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String keyPressed, String text) {
                System.out.println("Key pressed: " + keyPressed + "\n" + "Line: " + text);
                masterSearch();

            }
        });

        ui_add_patient.setOnAction((ActionEvent e) -> {
            addPatient();
        });

        ui_delete_btn.setOnAction((ActionEvent e) -> {
            ui_table.getSelectionModel().getSelectedItem().deletePatient(database);
            masterSearch();
        });

        ui_view_patient_test_btn.setOnAction((ActionEvent e) -> {
            openTestView(ui_table.getSelectionModel().getSelectedItem());
        });

    }

}
