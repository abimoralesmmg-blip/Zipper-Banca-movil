package controllers.client;

import Util.Util;
import Util.SessionManager;
import data.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Button btnVolver;

    @FXML
    private void onLogin() {
        try {
            String email = txtEmail.getText().trim();
            String pass = txtPassword.getText().trim();

            Usuario usuario = SessionManager.getUsuarioServices()
                    .login(email, pass)
                    .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

            SessionManager.setUsuarioLogeado(usuario);

            Stage stage = (Stage) btnLogin.getScene().getWindow();

            if (usuario.isEsAdmin()) {
                SessionManager.cambiarEscena("/views/AdminMain.fxml", stage);
            } else {
                SessionManager.cambiarEscena("/views/ClientMain.fxml", stage);
            }
        } catch (Exception e) {
            mostrarError("Error al iniciar sesión", e.getMessage());
        }
    }

    @FXML
    private void onVolver(ActionEvent event) {
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        SessionManager.cambiarEscena("/views/Start.fxml", stage);
    }

    @FXML
    private void onRecuperar(ActionEvent event) {
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        SessionManager.cambiarEscena("/views/Recuperar.fxml", stage);
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        Util.agregarIconoAlert(alert);
        alert.showAndWait();
    }
}
