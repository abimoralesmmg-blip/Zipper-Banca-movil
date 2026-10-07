package data;

import java.time.LocalDateTime;

public class Transaccion {
    private int idTransaccion;
    private int idCuentaOrigen;
    private int idCuentaDestino;
    private double monto;
    private LocalDateTime fecha;
    private TipoTransaccion tipo;
    private String estado;

    public Transaccion(int idTransaccion, int idCuentaOrigen, int idCuentaDestino,
                       double monto, LocalDateTime fecha, TipoTransaccion tipo, String estado) {
        this.idTransaccion = idTransaccion;
        this.idCuentaOrigen = idCuentaOrigen;
        this.idCuentaDestino = idCuentaDestino;
        this.monto = monto;
        this.fecha = fecha;
        this.tipo = tipo;
        this.estado = estado;
    }

    public Transaccion(int idCuentaOrigen, int idCuentaDestino,
                       double monto, TipoTransaccion tipo) {
        this(0, idCuentaOrigen, idCuentaDestino, monto, LocalDateTime.now(), tipo, "COMPLETADA");
    }

    public int getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(int idTransaccion) { this.idTransaccion = idTransaccion; }

    public int getIdCuentaOrigen() { return idCuentaOrigen; }
    public void setIdCuentaOrigen(int idCuentaOrigen) { this.idCuentaOrigen = idCuentaOrigen; }

    public int getIdCuentaDestino() { return idCuentaDestino; }
    public void setIdCuentaDestino(int idCuentaDestino) { this.idCuentaDestino = idCuentaDestino; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public TipoTransaccion getTipo() { return tipo; }
    public void setTipo(TipoTransaccion tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
