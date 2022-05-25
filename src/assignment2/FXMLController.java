/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


/**
 * FXML Controller class
 *
 * @author 12110634
 */
public class FXMLController implements Initializable {

    /*
    Example of a button listener make sure to set the ID in the fxml to "button1" for example
    java will magically link the fxml to the variable name
    
    
    @FXML // This tells java this variable is refering to a fxml element
    Button button1 = new Button();

    //This should be run in the initializer method i think
    button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Testing");
            }
        });
    
     */
    //https://stackoverflow.com/questions/17014012/how-to-unmask-a-javafx-passwordfield-or-properly-mask-a-textfield
    @FXML
    TextField ip_text = new TextField();
    @FXML
    TextField pass_text = new TextField();
    @FXML
    Button connectBtn = new Button();

    @Override // This gets run when the form is being created.
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("initializing");

        connectBtn.setOnAction((ActionEvent e) -> {
            //triggers when connect button is pressed
            System.out.println("Connecting to database");
            DataBaseConnector dataBaseConnection = new DataBaseConnector();
            if (dataBaseConnection.connect(ip_text.getText(), pass_text.getText())) {
                JOptionPane.showMessageDialog(null, "Connection Success", "InfoBox: " + "Connection status", JOptionPane.INFORMATION_MESSAGE);

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
                                    "PatientView.fxml"
                            )
                    );

                    Stage stage = new Stage(StageStyle.DECORATED);
                    stage.setScene(
                            new Scene(loader.load())
                    );

                    PatientViewController controller = loader.getController();
                      controller.initData(dataBaseConnection);
                      stage.show();
                    
                    Stage rootStage = (Stage) ip_text.getScene().getWindow();
                    // do what you have to do
                    rootStage.close();

                } catch (IOException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Connection ERROR", "InfoBox: " + "Connection status", JOptionPane.INFORMATION_MESSAGE);

            }

        });

    }

}
