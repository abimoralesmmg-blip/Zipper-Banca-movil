package data;

public class Cuenta {
    private int idCuenta;
    private int idUsuario;
    private String numeroCuenta;
    private double saldo;

    public Cuenta(int idCuenta, int idUsuario, String numeroCuenta, double saldo) {
        this.idCuenta = idCuenta;
        this.idUsuario = idUsuario;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    public Cuenta(int idUsuario, String numeroCuenta, double saldo) {
        this(0, idUsuario, numeroCuenta, saldo);
    }

    public int getIdCuenta() { return idCuenta; }
    public void setIdCuenta(int idCuenta) { this.idCuenta = idCuenta; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
}
