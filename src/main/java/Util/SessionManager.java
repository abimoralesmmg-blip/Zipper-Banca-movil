package Util;

import data.Usuario;

import java.io.IOException;

import data.Cuenta;
import dataAccess.UsuarioRepository;
import dataAccess.CuentaRepository;
import dataAccess.TransaccionRepository;
import services.UsuarioServices;
import services.CuentaServices;
import services.TransaccionServices;
import services.StatsServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class SessionManager {

    // ==============================
    // REPOSITORIOS
    // ==============================
    private static final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private static final CuentaRepository cuentaRepo = new CuentaRepository();
    private static final TransaccionRepository transaccionRepo = new TransaccionRepository();

    // ==============================
    // SERVICIOS
    // ==============================
    private static final UsuarioServices usuarioServices = new UsuarioServices(usuarioRepo);
    private static final CuentaServices cuentaServices = new CuentaServices(cuentaRepo);
    private static final TransaccionServices transaccionServices = new TransaccionServices(transaccionRepo, cuentaRepo);
    private static final StatsServices statsServices = new StatsServices(usuarioRepo, cuentaRepo);

    // ==============================
    // SESIÓN
    // ==============================
    private static Usuario usuarioLogeado;
    private static Usuario usuarioDetalle;
    private static TransferenciaPendiente transferenciaPendiente;

    // ==============================
    // GETTERS
    // ==============================
    public static UsuarioServices getUsuarioServices() { return usuarioServices; }
    public static CuentaServices getCuentaServices() { return cuentaServices; }
    public static TransaccionServices getTransaccionServices() { return transaccionServices; }
    public static StatsServices getStatsServices() { return statsServices; }

    public static UsuarioRepository getUsuarioRepository() { return usuarioRepo; }
    public static CuentaRepository getCuentaRepository() { return cuentaRepo; }

    public static Usuario getUsuarioLogeado() { return usuarioLogeado; }
    public static void setUsuarioLogeado(Usuario u) { usuarioLogeado = u; }

    public static Usuario getUsuarioDetalle() { return usuarioDetalle; }
    public static void setUsuarioDetalle(Usuario u) { usuarioDetalle = u; }

    // ==============================
    // TRANSFERENCIA PENDIENTE
    // ==============================
    public static void setTransferenciaPendiente(Cuenta origen, Cuenta destino, double monto) {
        transferenciaPendiente = new TransferenciaPendiente(origen, destino, monto);
    }

    public static TransferenciaPendiente getTransferenciaPendiente() {
        return transferenciaPendiente;
    }

    public static void limpiarTransferenciaPendiente() {
        transferenciaPendiente = null;
    }

 // ==============================
 // CAMBIO DE ESCENAS
 // ==============================
    public static void cambiarEscena(String fxml, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(SessionManager.class.getResource(fxml));
            Parent root = loader.load();

            // Intentar obtener los tamaños preferidos del root
            double prefW = root.prefWidth(-1);
            double prefH = root.prefHeight(-1);

            // Si no se obtiene nada válido, intentar obtenerlo si es Region
            if ((Double.isNaN(prefW) || prefW <= 0) && root instanceof Region) {
                prefW = ((Region) root).getPrefWidth();
            }
            if ((Double.isNaN(prefH) || prefH <= 0) && root instanceof Region) {
                prefH = ((Region) root).getPrefHeight();
            }

            // Fallback razonable si sigue sin valor
            if (Double.isNaN(prefW) || prefW <= 0) prefW = 800;
            if (Double.isNaN(prefH) || prefH <= 0) prefH = 600;

            // Crear la escena con el tamaño preferido del FXML
            Scene scene = new Scene(root, prefW, prefH);
            stage.setScene(scene);

            // Ajustar ventana sólo si no está maximizada o en pantalla completa
            if (!stage.isMaximized() && !stage.isFullScreen()) {
                stage.sizeToScene();   // ajusta el stage al tamaño de la scene (incluye chrome)
                // Evitar que el usuario haga la ventana más pequeña que el contenido
                stage.setMinWidth(prefW);
                stage.setMinHeight(prefH);
            }

            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    // ==============================
    // CLASE INTERNA PARA TRANSFERENCIA
    // ==============================
    public static class TransferenciaPendiente {
        private final Cuenta origen;
        private final Cuenta destino;
        private final double monto;

        public TransferenciaPendiente(Cuenta origen, Cuenta destino, double monto) {
            this.origen = origen;
            this.destino = destino;
            this.monto = monto;
        }

        public Cuenta getOrigen() { return origen; }
        public Cuenta getDestino() { return destino; }
        public double getMonto() { return monto; }
    }
    // ==============================
    // CERRAR SESIÓN Y VOLVER AL Starr
    // ==============================
    
        public static void cerrarSesion(Stage stage) {
            usuarioLogeado = null; // limpiar sesión
            cambiarEscena("/views/Start.fxml", stage); // <-- ruta corregida
        }
    
}
