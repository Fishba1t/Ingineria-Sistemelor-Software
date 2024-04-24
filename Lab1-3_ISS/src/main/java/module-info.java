module com.example.lab13_iss {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.lab13_iss to javafx.fxml;
    exports com.example.lab13_iss;
}