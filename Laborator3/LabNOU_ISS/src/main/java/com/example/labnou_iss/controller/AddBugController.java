package com.example.labnou_iss.controller;

import com.example.labnou_iss.HelloApplication;
import com.example.labnou_iss.domain.Bug;
import com.example.labnou_iss.service.IObserver;
import com.example.labnou_iss.service.ServiceBugManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

public class AddBugController{

    private ServiceBugManagement service;

    @FXML
    private TextField denumireField;

    @FXML
    private TextArea descriereField;

    @FXML
    private TextField statusField;

    private Stage stage;

    public void setService(ServiceBugManagement service,Stage stage) {
        this.service = service;
        this.stage = stage;
    }


    @FXML
    public void addBug() {
        String denumire = denumireField.getText().trim();
        String descriere = descriereField.getText().trim();
        String status = statusField.getText().trim();

        if (denumire.isEmpty() || descriere.isEmpty() || status.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Incomplete Fields", "Please fill in all fields.");
            return;
        }

        Bug newBug = new Bug(UUID.randomUUID(), denumire, descriere, status);

        service.addBug(newBug);

        clearInputFields();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Bug Added", "Bug successfully added.");
    }

    private void clearInputFields() {
        denumireField.clear();
        descriereField.clear();
        statusField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


}
