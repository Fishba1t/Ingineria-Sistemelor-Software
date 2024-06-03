package com.example.labnou_iss.controller;

import com.example.labnou_iss.HelloApplication;
import com.example.labnou_iss.domain.Angajat;
import com.example.labnou_iss.service.IObserver;
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

public class LoginSignUpController implements IObserver {

    private ServiceBugManagement serviceBugManagement;
    private Stage stage;
    private Angajat angajat;

    private TesterController testerController;
    private ProgramatorController programatorController;

    @FXML
    private TextField usernameField = new TextField();

    @FXML
    private TextField passwordField = new TextField();

    public LoginSignUpController() {
    }

    public void setService(ServiceBugManagement serviceBugManagement) {
        this.serviceBugManagement = serviceBugManagement;
    }

    public void setTester(Angajat angajat) {
        this.angajat = angajat;
        this.serviceBugManagement.loginTester(angajat, this.testerController);
    }

    public void setProgramator(Angajat angajat) {
        this.angajat = angajat;
        this.serviceBugManagement.loginProgramator(angajat, this.programatorController);
    }

    @FXML
    public void loginAction() {
        if (this.passwordField.getText().isEmpty() || this.usernameField.getText().isEmpty()) {
            Alert message1 = new Alert(Alert.AlertType.INFORMATION);
            message1.setTitle("ERROR!");
            message1.setContentText("YOU MUST INPUT A USERNAME AND A PASSWORD!");
            message1.showAndWait();
        } else {
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

            Angajat a = this.serviceBugManagement.handleLogin(username, parola);
            String position = a.getPosition();
            System.out.println(a);
            System.out.println(position);

            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("EMPLOYEE CONNECTED");
            message.setContentText("WELCOME EMPLOYEE!");
            message.showAndWait();

            try {
                if (Objects.equals(position, "Tester")) {
                    FXMLLoader new_loader = new FXMLLoader(HelloApplication.class.getResource("Tester.fxml"));
                    Scene root = new Scene(new_loader.load());

                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("TESTER Logged In: " + a.getUsername());
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.setScene(root);

                    this.testerController = new_loader.getController();
                    this.testerController.setService(this.serviceBugManagement);
                    this.testerController.setStage(dialogStage);
                    setTester(a);

                    dialogStage.show();
                } else if (Objects.equals(position, "Programator")) {
                    FXMLLoader new_loader = new FXMLLoader(HelloApplication.class.getResource("Programator.fxml"));
                    Scene root = new Scene(new_loader.load());

                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("PROGRAMATOR Logged In: " + a.getUsername());
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.setScene(root);

                    this.programatorController = new_loader.getController();
                    this.programatorController.setService(this.serviceBugManagement, dialogStage, username);
                    setProgramator(a);

                    dialogStage.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void signupAction() {
        try {
            FXMLLoader new_loader = new FXMLLoader(getClass().getResource("/com/example/labnou_iss/SignUp.fxml"));
            AnchorPane root = new_loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sign Up");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            SignUpController signupController = new_loader.getController();
            signupController.setService(this.serviceBugManagement, dialogStage);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init_model() {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Tester.fxml"));
        try {
            AnchorPane root = loader.load();
            TesterController testerController = loader.getController();

            testerController.setService(this.serviceBugManagement);
            testerController.setStage(this.stage);
            testerController.init_model();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
