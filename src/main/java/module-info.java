module org.example.mylibrary {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.mylibrary to javafx.fxml;
    opens org.example.mylibrary.model to javafx.base, javafx.fxml;
    opens org.example.mylibrary.view to javafx.fxml;
    exports org.example.mylibrary;
}