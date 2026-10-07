package controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import data.Usuario;
import data.Transaccion;
import Util.SessionManager;
import Util.Util;

public class UserDetailController {

    @FXML private Label lblNombre;
    @FXML private Label lblApellido;
    @FXML private Label lblCedula;
    @FXML private Label lblCorreo;
    @FXML private Label lblCuenta;
    @FXML private TableView<Transaccion> tvTransactions;
    @FXML private Button btnClose;

    private Usuario usuario;

    @FXML private TableColumn<Transaccion, String> colTxnFecha;
    @FXML private TableColumn<Transaccion, String> colTxnTipo;
    @FXML private TableColumn<Transaccion, String> colTxnBeneficiario;
    @FXML private TableColumn<Transaccion, String> colTxnMonto;
    @FXML private TableColumn<Transaccion, String> colTxnEstado;

    public void initialize() {
        colTxnFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha().toString()));
        colTxnTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo().toString()));

        // ✅ Mostrar nombre completo del beneficiario en lugar de ID
        colTxnBeneficiario.setCellValueFactory(c -> {
            String nombre = "-";
            try {
                if (c.getValue().getIdCuentaDestino() != 0) {
                    nombre = SessionManager.getCuentaServices()
                            .getUsuarioByCuentaId(c.getValue().getIdCuentaDestino())
                            .map(u -> u.getNombre() + " " + u.getApellido())
                            .orElse("Desconocido");
                }
            } catch (Exception e) {
                nombre = "Desconocido";
            }
            return new SimpleStringProperty(nombre);
        });

        colTxnMonto.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getMonto())));
        colTxnEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado()));
    }

    public void setUsuario(Usuario u) {
        this.usuario = u;
        lblNombre.setText(u.getNombre());
        lblApellido.setText(u.getApellido());
        lblCorreo.setText(u.getCorreo());
        lblCedula.setText(u.getCedula());

        var cuentaOpt = SessionManager.getCuentaServices().getByUsuarioId(u.getIdUsuario());
        cuentaOpt.ifPresent(c -> lblCuenta.setText(c.getNumeroCuenta()));

        cargarHistorial();
    }

    private void cargarHistorial() {
        var cuentaOpt = SessionManager.getCuentaServices().getByUsuarioId(usuario.getIdUsuario());
        cuentaOpt.ifPresent(c -> {
            int cuentaId = c.getIdCuenta();
            ObservableList<Transaccion> data = FXCollections.observableArrayList(
                SessionManager.getTransaccionServices().obtenerHistorialPorCuenta(cuentaId)
            );
            tvTransactions.setItems(data);
        });
    }

    @FXML
    private void onClose() {
        Stage s = (Stage) btnClose.getScene().getWindow();
        s.close();
    }

    @FXML
    private void onExportUserPdf() {
        Util.showAlert("No implementado", "Exportar PDF no está implementado aún.");
    }

    @FXML
    private void onExportUserExcel() {
        Util.showAlert("No implementado", "Exportar Excel no está implementado aún.");
    }
}
