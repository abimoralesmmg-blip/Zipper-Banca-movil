package services;

import data.Cuenta;
import data.Usuario;
import dataAccess.CuentaRepository;
import dataAccess.UsuarioRepository;

import java.util.Comparator;
import java.util.List;

public class StatsServices {
    private final UsuarioRepository usuarioRepo;
    private final CuentaRepository cuentaRepo;

    public StatsServices(UsuarioRepository usuarioRepo, CuentaRepository cuentaRepo) {
        this.usuarioRepo = usuarioRepo;
        this.cuentaRepo = cuentaRepo;
    }

    public long getTotalUsuarios() {
        return usuarioRepo.getAll().size();
    }

    public double getTotalDepositos() {
        return cuentaRepo.getAll().stream().mapToDouble(Cuenta::getSaldo).sum();
    }

    public String getUsuarioMayorSaldo() {
        return cuentaRepo.getAll().stream()
                .max(Comparator.comparingDouble(Cuenta::getSaldo))
                .flatMap(c -> usuarioRepo.getById(c.getIdUsuario()))
                .map(u -> u.getNombre() + " " + u.getApellido())
                .orElse("---");
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepo.getAll().stream().toList();
    }
}
