/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package assignment2;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author caleb
 */
public class TestsViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TableView<Test> ui_test_table;
    @FXML
    TableColumn<Test, Integer> ui_test_table_id;
    @FXML
    TableColumn<Test, String> ui_test_table_method;
    @FXML
    TableColumn<Test, String> ui_test_table_date;
    @FXML
    TableColumn<Test, String> ui_test_table_result;
    @FXML
    ChoiceBox ui_result_field;
    @FXML
    Button ui_add_btn;
    @FXML
    DatePicker ui_date_field;
    @FXML
    TextField ui_method_field;

    ObservableList<Test> list = FXCollections.observableArrayList();

    DataBaseConnector connection;
    Patient patient;

    boolean stringToBool(String str) {
        boolean returnValue = false;
        if ("1".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str)
                || "true".equalsIgnoreCase(str) || "on".equalsIgnoreCase(str)) {
            returnValue = true;
        }
        return returnValue;
    }

    void masterSearch() {
        try {
            String sql = "SELECT * FROM covidtestdb.virustest WHERE PatientID = ?;";

            PreparedStatement searchStatement = connection.getConnectionObj().prepareStatement(sql);

            searchStatement.setInt(1, patient.getPatientID());
            ResultSet rs = searchStatement.executeQuery();
            int index = 1;
            list.clear();
            while (rs.next()) {
                /*
                if (rs.getString(1).compareToIgnoreCase("Test") == 0) {
                   
                }
                 */
                list.add(new Test(Integer.valueOf(rs.getInt(1)), Integer.valueOf(rs.getInt(2)), rs.getString(5), DateTools.SqlDateToString(rs.getDate(4), "yyyy-mm-dd"), rs.getString(3)));

                index++;

            }
            for (int i = 0; i < list.size(); i++) {
                list.get(i).printAll();
            }
            ui_test_table.setItems(list);

        } catch (SQLException ex) {
            Logger.getLogger(TestsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initData(DataBaseConnector connection, Patient patient) {
        this.connection = connection;
        this.patient = patient;
        ui_test_table_id.setCellValueFactory(cellData -> cellData.getValue().testID());
        ui_test_table_method.setCellValueFactory(cellData -> cellData.getValue().method());
        ui_test_table_date.setCellValueFactory(cellData -> cellData.getValue().date());
        ui_test_table_result.setCellValueFactory(cellData -> cellData.getValue().result());
        ui_result_field.getItems().add("true");
        ui_result_field.getItems().add("false");

        masterSearch();

        ui_add_btn.setOnAction((ActionEvent e) -> {
            try {
                String tmp;
                String sql = "INSERT INTO covidtestdb.virustest (PatientID, Result, TestDate, Method) VALUES (?, ?, ?, ?);";
                if (ui_result_field.getItems().toString().equals("true")) {
                    tmp = "possitive";
                } else {
                    tmp = "negative";
                }
                PreparedStatement queryInsertTest = connection.getConnectionObj().prepareStatement(sql);

                queryInsertTest.setInt(1, patient.getPatientID());
                queryInsertTest.setString(2, tmp);
                queryInsertTest.setDate(3, java.sql.Date.valueOf(ui_date_field.getValue().toString()));
                queryInsertTest.setString(4, ui_method_field.getText());
                queryInsertTest.executeUpdate();
                masterSearch();

            } catch (SQLException ex) {
                Logger.getLogger(TestsViewController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

}
