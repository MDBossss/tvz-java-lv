module com.hr.java.autosalon.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;

    opens com.hr.java.autosalon.main to javafx.fxml;
    exports com.hr.java.autosalon.main;

    opens com.hr.java.autosalon.controllers to javafx.fxml;
    exports com.hr.java.autosalon.controllers;

}