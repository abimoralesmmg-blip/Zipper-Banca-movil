package controllers.client;

import data.Cuenta;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Util.SessionManager;
import services.CuentaServices;

public class TransferenciaController {
    @FXML private TextField txtCuentaDestino;
    @FXML private TextField txtMonto;
    @FXML private Button btnTransferir;
    @FXML private Button btnCancelar;
    @FXML private Button btnVolver;

    private final CuentaServices cuentaServices = SessionManager.getCuentaServices();

    @FXML
    private void onTransferir() {
        try {
            String cuentaDestino = txtCuentaDestino.getText().trim();
            if (cuentaDestino.isEmpty()) {
                mostrarAlerta("Validación", "Debe ingresar un número de cuenta destino.");
                return;
            }

            if (txtMonto.getText().trim().isEmpty()) {
                mostrarAlerta("Validación", "Debe ingresar un monto.");
                return;
            }

            double monto;
            try {
                monto = Double.parseDouble(txtMonto.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta("Validación", "El monto debe ser un número válido.");
                return;
            }

            if (monto <= 0) {
                mostrarAlerta("Validación", "El monto debe ser mayor a cero.");
                return;
            }

            // Obtener cuenta origen
            Cuenta origen = cuentaServices.getByUsuarioId(SessionManager.getUsuarioLogeado().getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("No se encontró la cuenta de origen."));

            // Verificar saldo suficiente
            if (origen.getSaldo() < monto) {
                mostrarAlerta("Validación", "Saldo insuficiente para realizar la transferencia.");
                return;
            }

            // Obtener cuenta destino
            Cuenta destino = cuentaServices.getByNumeroCuenta(cuentaDestino)
                    .orElseThrow(() -> new RuntimeException("La cuenta destino no existe."));

            // Verificar que no sea la misma cuenta
            if (origen.getNumeroCuenta().equals(destino.getNumeroCuenta())) {
                mostrarAlerta("Validación", "No puede transferir a su propia cuenta.");
                return;
            }

            // Guardar transferencia pendiente y abrir confirmación
            SessionManager.setTransferenciaPendiente(origen, destino, monto);
            SessionManager.cambiarEscena("/views/Confirmacion.fxml", (Stage) btnTransferir.getScene().getWindow());

        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    private void onCancelar() {
        txtCuentaDestino.clear();
        txtMonto.clear();
    }

    @FXML
    private void onVolver() {
        SessionManager.cambiarEscena("/views/ClientMain.fxml", (Stage) btnVolver.getScene().getWindow());
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setHeaderText(titulo);
        alert.showAndWait();
    }
}
