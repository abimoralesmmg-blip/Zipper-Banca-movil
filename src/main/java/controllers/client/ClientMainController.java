package controllers.client;

import data.Cuenta;
import data.HistorialDTO;
import data.Transaccion;
import data.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.CuentaServices;
import services.TransaccionServices;
import Util.SessionManager;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ClientMainController {
    @FXML private Label lblNombre;
    @FXML private Label lblCuenta;
    @FXML private Label lblSaldota;
    @FXML private TableView<HistorialDTO> tblHistorial;
    @FXML private TableColumn<HistorialDTO, String> colBeneficiario;
    @FXML private TableColumn<HistorialDTO, String> colFecha;
    @FXML private TableColumn<HistorialDTO, String> colMonto;
    @FXML private Button btnTransferencia;
    @FXML private Button btnSalida;
    @FXML private VBox menuLateral;
    @FXML private Button btnMenu;

    private final CuentaServices cuentaServices = SessionManager.getCuentaServices();
    private final TransaccionServices transaccionServices = SessionManager.getTransaccionServices();

    private Usuario usuario;
    private Cuenta cuenta;

    public void initialize() {
        usuario = SessionManager.getUsuarioLogeado();
        cuenta = cuentaServices.getByUsuarioId(usuario.getIdUsuario()).orElse(null);

        if (cuenta == null) {
            lblNombre.setText("Hola, " + usuario.getNombre());
            lblCuenta.setText("Cuenta no encontrada");
            lblSaldota.setText("Saldo: N/A");
            return;
        }

        lblNombre.setText("Hola, " + usuario.getNombre());
        lblCuenta.setText("Cuenta: " + cuenta.getNumeroCuenta());
        lblSaldota.setText("Saldo: $" + cuenta.getSaldo());

        // Configuración de columnas
        colBeneficiario.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getBeneficiario()));
        colFecha.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        colMonto.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty("$" + data.getValue().getMonto()));

        cargarHistorial();
    }

    private void cargarHistorial() {
        if (cuenta == null) return;

        List<Transaccion> transacciones = transaccionServices.obtenerHistorialPorCuenta(cuenta.getIdCuenta());

        List<HistorialDTO> historial = transacciones.stream().map(t -> {
            // Buscar nombre beneficiario de forma segura
            String nombreBeneficiario = "Desconocido";
            try {
                nombreBeneficiario = cuentaServices.getUsuarioByCuentaId(t.getIdCuentaDestino())
                        .map(u -> u.getNombre() + " " + u.getApellido())
                        .orElse("Desconocido");
            } catch (Exception e) {
                // Si hay error, se deja como "Desconocido"
            }

            return new HistorialDTO(nombreBeneficiario, t.getFecha(), t.getMonto());
        }).collect(Collectors.toList());

        tblHistorial.setItems(FXCollections.observableArrayList(historial));
    }

    @FXML
    private void onTransferencia() {
        SessionManager.cambiarEscena("/views/Transferencia.fxml", (Stage) lblNombre.getScene().getWindow());
    }

    @FXML
    private void onSalir() {
        SessionManager.cerrarSesion((Stage) lblNombre.getScene().getWindow());
    }

    @FXML
    private void onMenuToggle() {
        menuLateral.setVisible(!menuLateral.isVisible());
    }
}
