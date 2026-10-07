package controllers.admin;

import data.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.UsuarioServices;
import Util.SessionManager;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;

public class AdminMainController {
    @FXML private TextField txtSearch;
    @FXML private TableView<Usuario> tvUsers;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colApellido;
    @FXML private TableColumn<Usuario, String> colCorreo;
    @FXML private TableColumn<Usuario, String> colCedula;

    private final UsuarioServices usuarioServices = SessionManager.getUsuarioServices();

    public void initialize() {
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colApellido.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getApellido()));
        colCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCorreo()));
        colCedula.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCedula()));

        cargarUsuarios();
    }

    private void cargarUsuarios() {
        tvUsers.getItems().setAll(usuarioServices.getAll());
    }

    @FXML
    private void onSearchKeyReleased() {
        String filtro = txtSearch.getText().toLowerCase();
        tvUsers.getItems().setAll(
            usuarioServices.getAll().stream()
                .filter(u -> u.getNombre().toLowerCase().contains(filtro)
                        || u.getApellido().toLowerCase().contains(filtro)
                        || u.getCedula().contains(filtro)
                        || u.getCorreo().toLowerCase().contains(filtro))
                .toList()
        );
    }

    @FXML
    private void onCreateUser() {
        try {
            FXMLLoader loader = new FXMLLoader(SessionManager.class.getResource("/views/CreateUser.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Crear Usuario");
            stage.setScene(new Scene(root));
            stage.initOwner(tvUsers.getScene().getWindow());
            stage.showAndWait();

            cargarUsuarios(); // refrescar al cerrar
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "No se pudo abrir la ventana de crear usuario.").showAndWait();
        }
    }

    @FXML
    private void onDeleteUser() {
        Usuario u = tvUsers.getSelectionModel().getSelectedItem();
        if (u == null) {
            new Alert(Alert.AlertType.WARNING, "Seleccione un usuario para eliminar.").showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Está seguro de eliminar al usuario " + u.getNombre() + "?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(res -> {
            if (res == ButtonType.YES) {
                usuarioServices.delete(u.getIdUsuario());
                cargarUsuarios();
            }
        });
    }

    @FXML
    private void onViewUser() {
        Usuario u = tvUsers.getSelectionModel().getSelectedItem();
        if (u == null) {
            new Alert(Alert.AlertType.WARNING, "Seleccione un usuario para ver sus detalles.").showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(SessionManager.class.getResource("/views/InfUsuario.fxml"));
            Parent root = loader.load();

            UserDetailController controller = loader.getController();
            controller.setUsuario(u);

            Stage stage = new Stage();
            stage.setTitle("Detalles de " + u.getNombre());
            stage.setScene(new Scene(root));
            stage.initOwner(tvUsers.getScene().getWindow());
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "No se pudo abrir la ventana de detalles.").showAndWait();
        }
    }

    @FXML
    private void onRefresh() {
        cargarUsuarios();
    }

    @FXML
    private void onOpenStats() {
        try {
            FXMLLoader loader = new FXMLLoader(SessionManager.class.getResource("/views/Stats.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Estadísticas");
            stage.setScene(new Scene(root));
            stage.initOwner(tvUsers.getScene().getWindow());
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "No se pudo abrir la ventana de estadísticas.").showAndWait();
        }
    }

    @FXML
    private void onLogout() {
        SessionManager.cerrarSesion((Stage) tvUsers.getScene().getWindow());
    }
}
