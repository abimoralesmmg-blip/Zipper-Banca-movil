package data;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String cedula;
    private String correo;
    private String contrasena; // siempre encriptada
    private boolean esAdmin;

    public Usuario(int idUsuario, String nombre, String apellido,
                   String cedula, String correo, String contrasena, boolean esAdmin) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.correo = correo;
        this.contrasena = contrasena;
        this.esAdmin = esAdmin;
    }

    public Usuario(String nombre, String apellido, String cedula,
                   String correo, String contrasena, boolean esAdmin) {
        this(0, nombre, apellido, cedula, correo, contrasena, esAdmin);
    }

    
    public Usuario() {
		super();
	}

	public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public boolean isEsAdmin() { return esAdmin; }
    public void setEsAdmin(boolean esAdmin) { this.esAdmin = esAdmin; }
}
