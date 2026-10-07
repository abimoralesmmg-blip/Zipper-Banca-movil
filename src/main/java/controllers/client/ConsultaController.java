package controllers.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import data.Usuario;
import data.Cuenta;
import Util.SessionManager;

public class ConsultaController {
    @FXML private Label lblSaldo;
    @FXML private Label lblNumCuenta;
    @FXML private javafx.scene.control.Button btnRegresar;

    private Usuario usuarioActual;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        if (usuario != null) {
            Cuenta cuenta = SessionManager.getCuentaServices()
                    .getByUsuarioId(usuario.getIdUsuario())
                    .orElse(null);

            if (cuenta != null) {
                lblNumCuenta.setText("Cuenta: " + cuenta.getNumeroCuenta());
                lblSaldo.setText(String.format("$ %.2f", cuenta.getSaldo()));
            } else {
                lblNumCuenta.setText("Cuenta no encontrada");
                lblSaldo.setText("Error");
            }
        }
    }

    @FXML
    private void handleRegresar() {
        Stage st = (Stage) lblSaldo.getScene().getWindow();
        st.close();
    }
}
