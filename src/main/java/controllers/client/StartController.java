package controllers.client;

import Util.SessionManager;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StartController {

    @FXML
    public Button btnLogin;

    @FXML
    public Button btnRegiste;

    // Botón "Iniciar sesión"
    @FXML
    public void onLogin(ActionEvent event) {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        SessionManager.cambiarEscena("/views/Iniciar.fxml", stage);
    }

    // Botón "Registrar"
    @FXML
    public void onRegister(ActionEvent event) {
        Stage stage = (Stage) btnRegiste.getScene().getWindow();
        SessionManager.cambiarEscena("/views/Registro.fxml", stage);
    }

    // Hover enter
    @FXML
    public void handleMouseEnterButton(MouseEvent event) {
        if (event.getSource() instanceof Button btn) {
            btn.setScaleX(1.03);
            btn.setScaleY(1.03);
        }
    }

    // Hover exit
    @FXML
    public void handleMouseExitButton(MouseEvent event) {
        if (event.getSource() instanceof Button btn) {
            btn.setScaleX(1.0);
            btn.setScaleY(1.0);
        }
    }
}
