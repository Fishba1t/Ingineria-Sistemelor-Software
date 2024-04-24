package com.example.labnou_iss.controller;

import com.example.labnou_iss.domain.Angajat;
import com.example.labnou_iss.service.ServiceBugManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.UUID;

public class SignUpController {

    private ServiceBugManagement serviceBugManagement;
    private Stage stage;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField positionField;


    public void setService(ServiceBugManagement serviceBugManagement, Stage stage) {
        this.serviceBugManagement = serviceBugManagement;
        this.stage = stage;
    }

    @FXML
    public void signup() {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || positionField.getText().isEmpty()) {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("ERROR!");
            message.setContentText("YOU MUST INPUT USERNAME, PASSWORD, AND POSITION!");
            message.showAndWait();
        } else {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String position = positionField.getText();

            if (!Objects.equals(position, "Tester") && !Objects.equals(position, "Programator")) {
                Alert message = new Alert(Alert.AlertType.INFORMATION);
                message.setTitle("ERROR!");
                message.setContentText("INVALID POSITION!\n Position should be only 'Tester' or 'Programator' !\n Please spell it right!");
                message.showAndWait();
                return;
            }


            // Check if the username is already taken
            Angajat existingAngajat = serviceBugManagement.findAngajatByUsername(username);
            if (existingAngajat != null) {
                Alert message = new Alert(Alert.AlertType.INFORMATION);
                message.setTitle("ERROR!");
                message.setContentText("USERNAME ALREADY EXISTS!");
                message.showAndWait();
                return;
            }

            // Create the new Angajat and save it
            UUID id = UUID.randomUUID(); // Generate a new UUID for the new Angajat
            Angajat newAngajat = new Angajat(id,username, password, position);
            serviceBugManagement.addAngajat(newAngajat);

            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("SUCCESS!");
            message.setContentText("NEW ANGAJAT CREATED!");
            message.showAndWait();

            // Clear the text fields after signup
            usernameField.clear();
            passwordField.clear();
            positionField.clear();
        }
    }
}

