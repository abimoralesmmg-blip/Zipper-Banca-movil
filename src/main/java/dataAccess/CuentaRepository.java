package dataAccess;

import data.Cuenta;

import java.sql.*;
import java.util.*;

public class CuentaRepository extends BaseRepository<Cuenta> {

    @Override
    public void insert(Cuenta cuenta) {
        String sql = "INSERT INTO cuentas(usuario_id, numero_cuenta, saldo) VALUES (?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, cuenta.getIdUsuario());
            pst.setString(2, cuenta.getNumeroCuenta());
            pst.setDouble(3, cuenta.getSaldo());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) cuenta.setIdCuenta(rs.getInt(1));

        } catch (SQLException e) {
            throw new DataAccessException("Error al insertar cuenta", e);
        }
    }

    @Override
    public void update(Cuenta cuenta) {
        String sql = "UPDATE cuentas SET usuario_id=?, numero_cuenta=?, saldo=? WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, cuenta.getIdUsuario());
            pst.setString(2, cuenta.getNumeroCuenta());
            pst.setDouble(3, cuenta.getSaldo());
            pst.setInt(4, cuenta.getIdCuenta());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar cuenta", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM cuentas WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar cuenta", e);
        }
    }

    @Override
    public Optional<Cuenta> getById(int id) {
        String sql = "SELECT * FROM cuentas WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return Optional.of(new Cuenta(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("numero_cuenta"),
                        rs.getDouble("saldo")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener cuenta", e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Cuenta> getAll() {
        List<Cuenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuentas";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Cuenta(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("numero_cuenta"),
                        rs.getDouble("saldo")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al listar cuentas", e);
        }
        return lista;
    }

    public Optional<Cuenta> getByUsuarioId(int usuarioId) {
        String sql = "SELECT * FROM cuentas WHERE usuario_id=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, usuarioId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return Optional.of(new Cuenta(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("numero_cuenta"),
                        rs.getDouble("saldo")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar cuenta por usuario", e);
        }
        return Optional.empty();
    }

    public Optional<Cuenta> getByNumeroCuenta(String numeroCuenta) {
        String sql = "SELECT * FROM cuentas WHERE numero_cuenta=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, numeroCuenta);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return Optional.of(new Cuenta(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("numero_cuenta"),
                        rs.getDouble("saldo")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar cuenta por número", e);
        }
        return Optional.empty();
    }
}
