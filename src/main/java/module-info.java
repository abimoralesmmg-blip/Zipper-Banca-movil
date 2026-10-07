module Cliente {
    // interfaz grafica
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.base;

    // base de datos
    requires java.sql;
    requires org.apache.poi.ooxml;

    opens Banca to javafx.graphics, javafx.fxml;
    opens controllers.client to javafx.graphics, javafx.fxml;
    opens controllers.admin to javafx.graphics, javafx.fxml;
    opens data to javafx.base; // ¡Esto es crítico para PropertyValueFactory!
}
