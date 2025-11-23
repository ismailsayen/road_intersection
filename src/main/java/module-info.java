module jraffic {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens jraffic to javafx.fxml;

    exports jraffic;
}
