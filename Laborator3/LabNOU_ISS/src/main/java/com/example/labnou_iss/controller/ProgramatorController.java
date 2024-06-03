package com.example.labnou_iss.controller;

import com.example.labnou_iss.domain.Bug;
import com.example.labnou_iss.service.IObserver;
import com.example.labnou_iss.service.ServiceBugManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramatorController implements IObserver {

    private ServiceBugManagement serviceBugManagement;

    @FXML
    private TableView<Bug> bugTable;

    @FXML
    private TableColumn<Bug, String> denumireColumn;

    @FXML
    private TableColumn<Bug, String> descriereColumn;

    @FXML
    private TableColumn<Bug, String> statusColumn;

    private Stage stage;
    private String username_tester_logat;

    private ObservableList<Bug> bugList = FXCollections.observableArrayList();

    public void setService(ServiceBugManagement service, Stage stage, String username_tester_logat) {
        this.serviceBugManagement = service;
        this.stage = stage;
        this.username_tester_logat = username_tester_logat;
        init_model();
    }

    @FXML
    public void initialize() {
        denumireColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        bugTable.setItems(bugList);
    }

    public void init_model() {
        Iterable<Bug> bugs = serviceBugManagement.findAllBugs();
        List<Bug> bugArrayList = new ArrayList<>();
        bugs.forEach(bugArrayList::add);

        bugList.setAll(bugArrayList);

        initialize();
    }

    @FXML
    private void fixBugAction() {
        Bug selectedBug = bugTable.getSelectionModel().getSelectedItem();
        if (selectedBug != null) {
            serviceBugManagement.deleteBug(selectedBug);
            bugList.remove(selectedBug);
            showAlert("Success", "Bug has been deleted successfully.");
        } else {
            showAlert("Error", "No bug selected. Please select a bug to delete.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void fixUpdateBugAction() {
        Bug selectedBug = bugTable.getSelectionModel().getSelectedItem();
        if (selectedBug != null) {
            showUpdateBugDialog(selectedBug);
        } else {
            showAlert("Error", "No bug selected. Please select a bug to update.");
        }
    }

    private void showUpdateBugDialog(Bug bug) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/labnou_iss/updateBug.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update Bug");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            dialogStage.setScene(scene);

            UpdateBugController controller = loader.getController();
            controller.setService(serviceBugManagement);
            controller.setBugId(bug.getId().toString());

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
