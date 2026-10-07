package services;

import data.Cuenta;
import data.Usuario;
import dataAccess.CuentaRepository;

import java.util.Optional;

import Util.SessionManager;

public class CuentaServices extends BaseServices<Cuenta> {
    private final CuentaRepository cuentaRepo;

    public CuentaServices(CuentaRepository cuentaRepo) {
        super(cuentaRepo);
        this.cuentaRepo = cuentaRepo;
    }

    public Optional<Cuenta> getByUsuarioId(int idUsuario) {
        return cuentaRepo.getByUsuarioId(idUsuario);
    }

    public Optional<Cuenta> getByNumeroCuenta(String numeroCuenta) {
        return cuentaRepo.getByNumeroCuenta(numeroCuenta);
    }

    public void actualizarSaldo(Cuenta cuenta, double nuevoSaldo) {
        cuenta.setSaldo(nuevoSaldo);
        cuentaRepo.update(cuenta);
    }
    public Optional<Usuario> getUsuarioByCuenta(Cuenta cuenta) {
        return SessionManager.getUsuarioRepository().getById(cuenta.getIdUsuario());
    }
    public Optional<Usuario> getUsuarioByCuentaId(int idCuenta) {
        return SessionManager.getUsuarioRepository()
                .getById(getById(idCuenta).orElseThrow().getIdUsuario());
    }

}
