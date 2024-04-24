package com.example.labnou_iss.controller;

import com.example.labnou_iss.service.ServiceBugManagement;
import javafx.stage.Stage;

public class TesterController {

    private ServiceBugManagement serviceBugManagement;

    private Stage stage;
    private String username_tester_logat;

    public void setService(ServiceBugManagement service,Stage stage,String username_tester_logat){
        this.serviceBugManagement=service;
        this.stage=stage;
        this.username_tester_logat=username_tester_logat;
        //init_model();
    }
}
