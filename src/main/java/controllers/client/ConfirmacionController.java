package controllers.client;

import data.Cuenta;
import data.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Util.SessionManager;
import services.CuentaServices;
import services.TransaccionServices;

public class ConfirmacionController {
    @FXML private Label lblBeneficiario;
    @FXML private Label lblCuentaBeneficiario;
    @FXML private Label lblMontoConfirmacion;
    @FXML private Button btnAceptar;
    @FXML private Button btnCancelarConfirmacion;

    private final TransaccionServices transaccionServices = SessionManager.getTransaccionServices();
    private final CuentaServices cuentaServices = SessionManager.getCuentaServices();
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    @FXML
    public void initialize() {
    	
    	var pendiente =SessionManager.getTransferenciaPendiente();
    	if(pendiente!= null) {
    		//buscar usuario destino
    		Cuenta cuentaDestino = pendiente.getDestino();
    		Usuario usuarioDestino = cuentaServices.getUsuarioByCuenta(cuentaDestino).orElse(null);
    		
    		if( usuarioDestino != null) {
    			lblBeneficiario.setText("Beneficiario: \n"+
    					usuarioDestino.getNombre()+
    					" "+ usuarioDestino.getApellido());
    		}else {
    			lblBeneficiario.setText("Beneficiario: No encntrado");
    		}
    		lblCuentaBeneficiario.setText("Cuenta: "+ cuentaDestino.getNumeroCuenta());
    		lblMontoConfirmacion.setText("Monto: $"+ pendiente.getMonto());
    	}
       
    }

    @FXML
    private void onAceptar() {
        var pendiente = SessionManager.getTransferenciaPendiente();
        if( pendiente!=null) {
        	try {
        		transaccionServices.transferir(pendiente.getOrigen(), pendiente.getDestino(),pendiente.getMonto());
        		mostrarAlerta(Alert.AlertType.INFORMATION,"Éxito","Transferencia realizada con éxito");
        		SessionManager.cambiarEscena("/views/Transferencia.fxml",(Stage)btnAceptar.getScene().getWindow());
        	}catch(Exception e) {
        		mostrarAlerta(Alert.AlertType.ERROR,"Error","No se pudo completar la transferencia: "+ e.getMessage());
        	}finally {
        		SessionManager.limpiarTransferenciaPendiente();
        	}
        }
        
    }

    @FXML
    private void onCancelar() {
    	SessionManager.limpiarTransferenciaPendiente();
    	SessionManager.cambiarEscena("/views/ClientMain.fxml",(Stage)btnCancelarConfirmacion.getScene().getWindow());
        
    }

  
}
