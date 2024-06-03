package com.example.labnou_iss;

import com.example.labnou_iss.controller.LoginSignUpController;
import com.example.labnou_iss.repository.AngajatRepository;
import com.example.labnou_iss.repository.BugRepository;
import com.example.labnou_iss.service.ServiceBugManagement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {

    private ServiceBugManagement serviceBugManagement;
    @Override
    public void start(Stage stage) throws IOException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        AngajatRepository arbitruRepo = new AngajatRepository(props);

        BugRepository participantRepo = new BugRepository(props);
        this.serviceBugManagement= new ServiceBugManagement(arbitruRepo, participantRepo);
        initView();
    }

    private void initView() throws IOException {
        // Load and show the staff view
        FXMLLoader staffLoader = new FXMLLoader();
        staffLoader.setLocation(getClass().getResource("LoginSignUp.fxml"));
        AnchorPane staffLayout = staffLoader.load();
        LoginSignUpController startController = staffLoader.getController();
        startController.setService(this.serviceBugManagement);

        Stage staffStage = new Stage();
        staffStage.setTitle("Log In/Sign Up");
        staffStage.setScene(new Scene(staffLayout));
        staffStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}