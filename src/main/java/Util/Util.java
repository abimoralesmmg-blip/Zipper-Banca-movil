package Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Util {

    public static void showError(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        agregarIconoAlert(alert);
        alert.showAndWait();
    }

    public static void showAlert(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        agregarIconoAlert(alert);
        alert.showAndWait();
    }
    public static String encriptarSHA256(String clave) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(clave.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }
    // --- Cambiado: ahora valida si el stream es null y prueba nombres alternativos ---
    public static void agregarIcono(Stage stage) {
        try {
            // Intentar icono preferido
            java.io.InputStream is = Util.class.getResourceAsStream("/img/icono.png");
            if (is == null) {
                // fallback 1: logo.png (tú tienes logo.png en resources)
                is = Util.class.getResourceAsStream("/img/logo.png");
            }
            if (is == null) {
                // fallback 2: intenta icon.png (por si acaso)
                is = Util.class.getResourceAsStream("/img/icon.png");
            }

            if (is != null) {
                Image logo = new Image(is);
                stage.getIcons().add(logo);
            } else {
                //DEBUG: imprime ruta de recursos disponibles (útil durante pruebas)
                System.out.println("No se encontró ningún icono en /img (buscado: icono.png, logo.png, icon.png).");
            }
        } catch (Exception e) {
            // logging más explícito para ayudar a depurar
            System.out.println("No se pudo cargar el icono: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void agregarIconoAlert(Alert alert) {
        try {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            agregarIcono(stage);
        } catch (Exception e) {
            System.out.println("No se pudo agregar icono al Alert: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean correoValido (String correo) {
        return correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$");
    }

    public static boolean cedulaValido (String cedula) {
        return cedula.matches("\\d{10}");
    }

    

    public static boolean soloLetras(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    
}
