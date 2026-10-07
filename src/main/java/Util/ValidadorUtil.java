package Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ValidadorUtil {
	//validacion correo
    public static boolean correoValido (String correo) {
    	return correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$");
    }
    
  //validacion cedula
    public static boolean cedulaValido (String cedula) {
    	return cedula.matches("\\d{10}");
    }
    

 // Validación para nombre y apellido (solo letras y espacios)
    public static boolean soloLetras(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }
    
  //encriptar
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
}
