module com.example.labnou_iss {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires java.sql;
    requires org.apache.logging.log4j;

    opens com.example.labnou_iss.domain to javafx.fxml;
    exports com.example.labnou_iss.domain;

    opens com.example.labnou_iss.repository to javafx.fxml;
    exports com.example.labnou_iss.repository;

    opens com.example.labnou_iss.service to javafx.fxml;
    exports com.example.labnou_iss.service;

    opens com.example.labnou_iss.controller to javafx.fxml;
    exports com.example.labnou_iss.controller;

    opens com.example.labnou_iss to javafx.fxml;
    exports com.example.labnou_iss;
}