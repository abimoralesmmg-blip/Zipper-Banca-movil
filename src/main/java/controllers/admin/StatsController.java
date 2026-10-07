package controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import Util.SessionManager;
import Util.Util;
import services.StatsServices;

public class StatsController {

    @FXML private Label lblTotalUsers;
    @FXML private Label lblTotalDeposits;
    @FXML private Label lblTopUser;
    @FXML private Button btnCloseStats;

    private final StatsServices statsService = SessionManager.getStatsServices();

    @FXML
    private void initialize() {
        try {
            lblTotalUsers.setText(String.valueOf(statsService.getTotalUsuarios()));
            lblTotalDeposits.setText(String.valueOf(statsService.getTotalDepositos()));
            lblTopUser.setText(statsService.getUsuarioMayorSaldo());
        } catch (Exception e) {
            Util.showError("Error", e.getMessage());
        }
    }

    @FXML
    private void onExportStatsPdf() {
        Util.showAlert("No implementado", "Exportar PDF no implementado aún.");
    }

    @FXML
    private void onExportStatsExcel() {
        Util.showAlert("No implementado", "Exportar Excel no implementado aún.");
    }

    @FXML
    private void onCloseStats() {
        btnCloseStats.getScene().getWindow().hide();
    }
}
