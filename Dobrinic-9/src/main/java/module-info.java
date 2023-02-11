module hr.java.vjezbe {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;


    opens hr.java.vjezbe to javafx.fxml;
    exports hr.java.vjezbe;
}