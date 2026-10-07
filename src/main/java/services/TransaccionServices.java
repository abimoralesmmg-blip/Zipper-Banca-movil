package services;

import data.Cuenta;
import data.TipoTransaccion;
import data.Transaccion;
import dataAccess.CuentaRepository;
import dataAccess.TransaccionRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TransaccionServices extends BaseServices<Transaccion> {
    private final TransaccionRepository transaccionRepo;
    private final CuentaRepository cuentaRepo;

    public TransaccionServices(TransaccionRepository transaccionRepo, CuentaRepository cuentaRepo) {
        super(transaccionRepo);
        this.transaccionRepo = transaccionRepo;
        this.cuentaRepo = cuentaRepo;
    }

    // Transferencia entre cuentas
    public void transferir(Cuenta origen, Cuenta destino, double monto) {
        if (monto <= 0) throw new BussinesException("El monto debe ser mayor a cero.");
        if (origen.getSaldo() < monto) throw new BussinesException("Saldo insuficiente.");
        if (origen.getNumeroCuenta().equals(destino.getNumeroCuenta()))
            throw new BussinesException("No puede transferir a su propia cuenta.");

        // Actualizar saldos
        origen.setSaldo(origen.getSaldo() - monto);
        destino.setSaldo(destino.getSaldo() + monto);
        cuentaRepo.update(origen);
        cuentaRepo.update(destino);

        // Registrar transacción
        Transaccion t = new Transaccion(
                0,
                origen.getIdCuenta(),
                destino.getIdCuenta(),
                monto,
                LocalDateTime.now(),
                TipoTransaccion.TRANSFERENCIA,
                "COMPLETADA"
        );
        transaccionRepo.insert(t);
    }

    public List<Transaccion> obtenerHistorialPorCuenta(int idCuenta) {
        return transaccionRepo.getByCuenta(idCuenta);
    }
}
