package com.example.labnou_iss.controller;

import com.example.labnou_iss.HelloApplication;
import com.example.labnou_iss.domain.Angajat;
import com.example.labnou_iss.service.ServiceBugManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginSignUpController {

    private ServiceBugManagement serviceBugManagement;

    private Stage stage;

    @FXML
    private TextField usernameField=new TextField();

    @FXML
    private TextField passwordField=new TextField();

    public LoginSignUpController(){

    }

    public void setService(ServiceBugManagement serviceBugManagement){
        this.serviceBugManagement=serviceBugManagement;
    }

    @FXML
    public void loginAction(){
        if(this.passwordField.getText().isEmpty() || this.usernameField.getText().isEmpty()) {
            Alert message1 = new Alert(Alert.AlertType.INFORMATION);
            message1.setTitle("ERROR!");
            message1.setContentText("   YOU MUST INPUT AN USERNAME AND A PASSWORD!");
            message1.showAndWait();
        }
        else{
            String username = this.usernameField.getText();
            String parola = this.passwordField.getText();
            Iterable<Angajat> angajati = this.serviceBugManagement.findAllAngajati();
            boolean exists = false;
            for (Angajat angajat : angajati) {
                if (angajat.getUsername().equals(username) && angajat.getPassword().equals(parola)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                Alert message = new Alert(Alert.AlertType.ERROR);
                message.setTitle("ERROR!");
                message.setContentText("WRONG USERNAME OR PASSWORD! THE EMPLOYEE DOES NOT EXIST! \n PLEASE SIGN UP FIRST!");
                message.showAndWait();
                return;
            }


            Angajat a = this.serviceBugManagement.findAngajatByUsernamePassword(username,parola);
            String position=a.getPosition();
            System.out.println(a);
            System.out.println(position);
            //this.serviceBugManagement.setLoggedInArbitru(a);
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("EMPLOYEE CONNECTED");
            message.setContentText("WELCOME EMPLOYEE!");
            message.showAndWait();

            try {
                if(Objects.equals(position, "Tester")){
                FXMLLoader new_loader = new FXMLLoader(HelloApplication.class.getResource("Tester.fxml"));
                Scene root =new Scene( new_loader.load());

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("TESTER Logged In:" + " " + a.getUsername());
                dialogStage.initModality(Modality.WINDOW_MODAL);
                //dialogStage.initOwner(primaryStage);
                dialogStage.setScene(root);

                TesterController testerController = new_loader.getController();
                testerController.setService(this.serviceBugManagement, dialogStage, username);

                dialogStage.show();
                }
                else if(Objects.equals(position, "Programator")){
                    FXMLLoader new_loader = new FXMLLoader(HelloApplication.class.getResource("Programator.fxml"));
                    Scene root =new Scene( new_loader.load());

                    // Create the dialog Stage.
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("PROGRAMATOR Logged In:" + " " + a.getUsername());
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    //dialogStage.initOwner(primaryStage);
                    dialogStage.setScene(root);

                    ProgramatorController programatorController = new_loader.getController();
                    programatorController.setService(this.serviceBugManagement, dialogStage, username);

                    dialogStage.show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
    }
    public void signupAction() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader new_loader = new FXMLLoader();
            new_loader.setLocation(getClass().getResource("/com/example/labnou_iss/SignUp.fxml"));


            AnchorPane root = new_loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sign Up");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            SignUpController signupController = new_loader.getController();
            signupController.setService(this.serviceBugManagement, dialogStage);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
