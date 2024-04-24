package com.example.labnou_iss.controller;

import com.example.labnou_iss.service.ServiceBugManagement;
import javafx.stage.Stage;

public class ProgramatorController {

    private ServiceBugManagement serviceBugManagement;

    private Stage stage;
    private String username_programator_logat;

    public void setService(ServiceBugManagement service,Stage stage,String username_programator_logat){
        this.serviceBugManagement=service;
        this.stage=stage;
        this.username_programator_logat=username_programator_logat;
        //init_model();
    }
}
