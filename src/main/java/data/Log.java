package data;

import java.sql.Timestamp;

public class Log {
    private long id;
    private Long usuarioId;
    private String accion;
    private String metadata;
    private Timestamp fecha;

    public Log() {}

    public Log(long id, Long usuarioId, String accion, String metadata, Timestamp fecha) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.accion = accion;
        this.metadata = metadata;
        this.fecha = fecha;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", accion='" + accion + '\'' +
                ", metadata='" + metadata + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
