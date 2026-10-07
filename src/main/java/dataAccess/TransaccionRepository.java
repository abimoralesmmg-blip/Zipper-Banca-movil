package dataAccess;

import data.Transaccion;
import data.TipoTransaccion;

import java.sql.*;
import java.util.*;

public class TransaccionRepository extends BaseRepository<Transaccion> {

    @Override
    public void insert(Transaccion t) {
        String sql = "INSERT INTO transacciones(cuenta_origen, cuenta_destino, monto, fecha, descripcion, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, t.getIdCuentaOrigen());
            pst.setInt(2, t.getIdCuentaDestino());
            pst.setDouble(3, t.getMonto());
            pst.setTimestamp(4, Timestamp.valueOf(t.getFecha()));
            pst.setString(5, t.getTipo().name());
            pst.setString(6, t.getEstado());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) t.setIdTransaccion(rs.getInt(1));

        } catch (SQLException e) {
            throw new DataAccessException("Error al insertar transacción", e);
        }
    }

    @Override
    public void update(Transaccion t) {
        String sql = "UPDATE transacciones SET cuenta_origen=?, cuenta_destino=?, monto=?, fecha=?, descripcion=?, estado=? WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, t.getIdCuentaOrigen());
            pst.setInt(2, t.getIdCuentaDestino());
            pst.setDouble(3, t.getMonto());
            pst.setTimestamp(4, Timestamp.valueOf(t.getFecha()));
            pst.setString(5, t.getTipo().name());
            pst.setString(6, t.getEstado());
            pst.setInt(7, t.getIdTransaccion());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar transacción", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM transacciones WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar transacción", e);
        }
    }

    @Override
    public Optional<Transaccion> getById(int id) {
        String sql = "SELECT * FROM transacciones WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return Optional.of(new Transaccion(
                        rs.getInt("id"),
                        rs.getInt("cuenta_origen"),
                        rs.getInt("cuenta_destino"),
                        rs.getDouble("monto"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        TipoTransaccion.valueOf(rs.getString("descripcion")),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener transacción", e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Transaccion> getAll() {
        List<Transaccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM transacciones";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Transaccion(
                        rs.getInt("id"),
                        rs.getInt("cuenta_origen"),
                        rs.getInt("cuenta_destino"),
                        rs.getDouble("monto"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        TipoTransaccion.valueOf(rs.getString("descripcion")),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al listar transacciones", e);
        }
        return lista;
    }

    public List<Transaccion> getByCuenta(int idCuenta) {
        List<Transaccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM transacciones WHERE cuenta_origen=? OR cuenta_destino=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idCuenta);
            pst.setInt(2, idCuenta);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lista.add(new Transaccion(
                        rs.getInt("id"),
                        rs.getInt("cuenta_origen"),
                        rs.getInt("cuenta_destino"),
                        rs.getDouble("monto"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        TipoTransaccion.valueOf(rs.getString("descripcion")),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener transacciones de cuenta", e);
        }
        return lista;
    }
}
