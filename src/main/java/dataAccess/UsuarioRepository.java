package dataAccess;

import data.Usuario;
import java.sql.*;
import java.util.*;

public class UsuarioRepository extends BaseRepository<Usuario> {

    @Override
    public void insert(Usuario usuario) {
        String sql = "INSERT INTO usuarios(nombre, apellido, cedula, correo, contrasena, es_admin) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getApellido());
            pst.setString(3, usuario.getCedula());
            pst.setString(4, usuario.getCorreo());
            pst.setString(5, usuario.getContrasena());
            pst.setBoolean(6, usuario.isEsAdmin());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                usuario.setIdUsuario(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al insertar usuario", e);
        }
    }

    @Override
    public void update(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, cedula=?, correo=?, contrasena=?, es_admin=? WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getApellido());
            pst.setString(3, usuario.getCedula());
            pst.setString(4, usuario.getCorreo());
            pst.setString(5, usuario.getContrasena());
            pst.setBoolean(6, usuario.isEsAdmin());
            pst.setInt(7, usuario.getIdUsuario());

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar usuario", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM usuarios WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar usuario", e);
        }
    }

    @Override
    public Optional<Usuario> getById(int id) {
        String sql = "SELECT * FROM usuarios WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return Optional.of(mapUsuario(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener usuario", e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Usuario> getAll() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapUsuario(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al listar usuarios", e);
        }
        return lista;
    }

    public Optional<Usuario> getByEmailAndPassword(String correo, String password) {
        String sql = "SELECT * FROM usuarios WHERE correo=? AND contrasena=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, correo);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return Optional.of(mapUsuario(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error en login", e);
        }
        return Optional.empty();
    }

    // ✅ Nuevo: buscar por correo
    public Optional<Usuario> getByCorreo(String correo) {
        String sql = "SELECT * FROM usuarios WHERE correo=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, correo);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return Optional.of(mapUsuario(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar por correo", e);
        }
        return Optional.empty();
    }

    // ✅ Nuevo: buscar por cédula
    public Optional<Usuario> getByCedula(String cedula) {
        String sql = "SELECT * FROM usuarios WHERE cedula=?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, cedula);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return Optional.of(mapUsuario(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar por cédula", e);
        }
        return Optional.empty();
    }

    private Usuario mapUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("cedula"),
                rs.getString("correo"),
                rs.getString("contrasena"),
                rs.getBoolean("es_admin")
        );
    }
}
