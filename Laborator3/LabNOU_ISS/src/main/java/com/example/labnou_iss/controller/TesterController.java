package com.example.labnou_iss.controller;

import com.example.labnou_iss.controller.AddBugController;
import com.example.labnou_iss.domain.Bug;
import com.example.labnou_iss.service.IObserver;
import com.example.labnou_iss.service.ServiceBugManagement;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TesterController implements IObserver {

    @FXML
    private TableView<Bug> bugTable = new TableView<>();

    @FXML
    private TableColumn<Bug, String> denumireColumn;

    @FXML
    private TableColumn<Bug, String> descriereColumn;

    @FXML
    private TableColumn<Bug, String> statusColumn;

    private ServiceBugManagement serviceBugManagement;
    private Stage stage;

    @FXML
    private ObservableList<Bug> bugList = FXCollections.observableArrayList();

    public void setService(ServiceBugManagement service) {
        this.serviceBugManagement = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        init_model();
    }

    public void initialize() {
        denumireColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        bugTable.setItems(bugList);
    }

    @Override
    public void init_model() {

        Iterable<Bug> bugs = serviceBugManagement.findAllBugs();
        List<Bug> bugArrayList = new ArrayList<>();
        bugs.forEach(bugArrayList::add);
        bugList.setAll(bugArrayList);
        initialize();
    }


    @FXML
    public void sesizareBug() {
        try {
            FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/com/example/labnou_iss/AddBug.fxml"));
            AnchorPane root = newLoader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Bug");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));


            AddBugController addBugController = newLoader.getController();
            addBugController.setService(serviceBugManagement,stage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
