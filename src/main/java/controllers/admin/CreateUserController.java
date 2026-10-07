package controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import data.Usuario;
import Util.Util;
import Util.SessionManager;

public class CreateUserController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCedula;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private Button btnCreate;
    @FXML private Button btnCancel;

    @FXML
    private void onCreate() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String cedula = txtCedula.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contra = txtContrasena.getText();

        // --- Validaciones ---
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contra.isEmpty() || cedula.isEmpty()) {
            Util.showError("Campos incompletos", "Debe llenar todos los campos.");
            return;
        }
        if (!Util.soloLetras(nombre) || !Util.soloLetras(apellido)) {
            Util.showError("Nombre inválido", "El nombre y apellido solo deben contener letras.");
            return;
        }
        if (!Util.cedulaValido(cedula)) {
            Util.showError("Cédula inválida", "La cédula debe tener exactamente 10 números.");
            return;
        }
        if (!Util.correoValido(correo)) {
            Util.showError("Correo inválido", "Formato de correo electrónico no válido.");
            return;
        }

        // --- Verificar duplicados ---
        boolean cedulaExiste = SessionManager.getUsuarioServices().getAll()
                .stream().anyMatch(u -> u.getCedula().equals(cedula));
        if (cedulaExiste) {
            Util.showError("Duplicado", "Ya existe un usuario con esa cédula.");
            return;
        }

        boolean correoExiste = SessionManager.getUsuarioServices().getAll()
                .stream().anyMatch(u -> u.getCorreo().equalsIgnoreCase(correo));
        if (correoExiste) {
            Util.showError("Duplicado", "Ya existe un usuario con ese correo.");
            return;
        }

        try {
            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setCedula(cedula);
            u.setCorreo(correo);
            u.setContrasena(Util.encriptarSHA256(contra));
            u.setEsAdmin(false); // por defecto cliente

            SessionManager.getUsuarioServices().create(u);

            Util.showAlert("Éxito", "Usuario creado correctamente.");
            Stage stage = (Stage) btnCreate.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Util.showError("Error", e.getMessage());
        }
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
