module com.example.michat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.michat to javafx.fxml;
    exports com.example.michat;
}