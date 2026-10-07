package controllers.client;

import Util.Util;
import Util.SessionManager;
import data.Usuario;
import data.Cuenta;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistroController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCedula;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtContrasena;
    @FXML private PasswordField txtConfirmacionContrsena;

    @FXML
    private void onRegistrar() {
        try {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String cedula = txtCedula.getText().trim();
            String correo = txtEmail.getText().trim();
            String contra = txtContrasena.getText();
            String conf = txtConfirmacionContrsena.getText();

            // === Validaciones básicas ===
            if (!Util.soloLetras(nombre)) {
                Util.showError("Error", "Nombre solo puede tener letras.");
                return;
            }
            if (!Util.soloLetras(apellido)) {
                Util.showError("Error", "Apellido solo puede tener letras.");
                return;
            }
            if (!Util.cedulaValido(cedula)) {
                Util.showError("Error", "Cédula debe tener solo 10 números.");
                return;
            }
            if (!Util.correoValido(correo)) {
                Util.showError("Error", "Correo inválido.");
                return;
            }
            if (!contra.equals(conf)) {
                Util.showError("Error", "Las contraseñas no coinciden.");
                return;
            }

            // === Validar duplicados ===
            boolean existeCedula = SessionManager.getUsuarioServices()
                    .findByCedula(cedula).isPresent();
            if (existeCedula) {
                Util.showError("Error", "El número de cédula ya está registrado.");
                return;
            }

            boolean existeCorreo = SessionManager.getUsuarioServices()
                    .findByCorreo(correo).isPresent();
            if (existeCorreo) {
                Util.showError("Error", "El correo ya está registrado.");
                return;
            }

            // === Crear usuario ===

            String contraHash = Util.encriptarSHA256(contra);

            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setCedula(cedula);
            u.setCorreo(correo);
            u.setContrasena(contraHash);
            u.setEsAdmin(false);

            SessionManager.getUsuarioServices().create(u);

            // === Crear cuenta ===
            String numeroCuenta = String.valueOf((int)(Math.random() * 90000000 + 10000000));
            Cuenta cuenta = new Cuenta(u.getIdUsuario(), numeroCuenta, 0.0);
            SessionManager.getCuentaServices().create(cuenta);

            Util.showAlert("Registro exitoso", "Usuario y cuenta creados correctamente.");
            SessionManager.cambiarEscena("/views/Iniciar.fxml", (Stage) txtNombre.getScene().getWindow());

        } catch (Exception e) {
            Util.showError("Error en registro", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLogin() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        SessionManager.cambiarEscena("/views/Iniciar.fxml", stage);
    }
}
