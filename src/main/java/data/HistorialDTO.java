package data;

import java.time.LocalDateTime;

public class HistorialDTO {
    private String beneficiario;
    private LocalDateTime fecha;
    private double monto;

    public HistorialDTO(String beneficiario, LocalDateTime fecha, double monto) {
        this.beneficiario = beneficiario;
        this.fecha = fecha;
        this.monto = monto;
    }

    public String getBeneficiario() { return beneficiario; }
    public LocalDateTime getFecha() { return fecha; }
    public double getMonto() { return monto; }
}
