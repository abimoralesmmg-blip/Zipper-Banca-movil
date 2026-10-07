package controllers.client;

import Util.SessionManager;
import Util.Util;
import data.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class RecoverController {

    @FXML
    private TextField txtEmail;

    @FXML
    private void onEnviar(ActionEvent event) {
        String correo = txtEmail.getText().trim();

        if (correo.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "Debe ingresar un correo válido.");
            return;
        }

        try {
            // Buscar usuario en la base de datos
            Optional<Usuario> usuarioOpt = SessionManager.getUsuarioServices().findByEmail(correo);

            if (usuarioOpt.isPresent()) {
                // Aquí iría la lógica real de envío de correo
                mostrarAlerta(Alert.AlertType.INFORMATION,
                        "Recuperación enviada",
                        "Se ha enviado un enlace de recuperación al correo: " + correo);
            } else {
                mostrarAlerta(Alert.AlertType.ERROR,
                        "Correo no encontrado",
                        "El correo ingresado no existe en nuestra banca.");
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error",
                    "Ha ocurrido un error al procesar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onVolverLogin(ActionEvent event) {
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        SessionManager.cambiarEscena("/views/Iniciar.fxml", stage);
    }

    // Método auxiliar para mostrar alertas
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Util.agregarIconoAlert(alert);
        alert.showAndWait();
    }
}
