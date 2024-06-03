package com.example.labnou_iss.controller;

import com.example.labnou_iss.domain.Bug;
import com.example.labnou_iss.service.ServiceBugManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.UUID;

public class UpdateBugController {

    @FXML
    private TextField bugIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    private ServiceBugManagement serviceBugManagement;

    public void setService(ServiceBugManagement service) {
        this.serviceBugManagement = service;
    }

    public void setBugId(String bugId) {
        this.bugIdField.setText(bugId);
    }

    @FXML
    public void updateBug() {
        String bugId = bugIdField.getText();
        String newName = nameField.getText();
        String newDescription = descriptionField.getText();

        if (bugId.isEmpty() || newName.isEmpty() || newDescription.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Bug ID, new name, and new description must be provided!");
            alert.showAndWait();
            return;
        }

        try {
            UUID bugUUID = UUID.fromString(bugId);
            Bug bug = serviceBugManagement.findBugById(bugUUID);
            if (bug != null) {
                bug.setName(newName);
                bug.setDescription(newDescription);
                serviceBugManagement.updateBug(bug);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Bug updated successfully!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Bug not found!");
                alert.showAndWait();
            }
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Bug ID format!");
            alert.showAndWait();
        }
    }
}
