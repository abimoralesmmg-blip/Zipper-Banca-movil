package services;

import data.Usuario;
import dataAccess.UsuarioRepository;
import Util.Util;

import java.util.Optional;

public class UsuarioServices extends BaseServices<Usuario> {
    private final UsuarioRepository usuarioRepo;

    public UsuarioServices(UsuarioRepository usuarioRepo) {
        super(usuarioRepo);
        this.usuarioRepo = usuarioRepo;
    }

    // Alias para compatibilidad (RecoverController usa findByEmail)
    public Optional<Usuario> findByEmail(String correo) {
        return findByCorreo(correo);
    }

    // Buscar por correo directamente
    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepo.getByCorreo(correo);
    }

    // Buscar por cédula directamente
    public Optional<Usuario> findByCedula(String cedula) {
        return usuarioRepo.getByCedula(cedula);
    }

    // Registro de usuario con validaciones
    public void registrarUsuario(String nombre, String apellido, String cedula,
                                 String correo, String contrasena, String confirmar, boolean esAdmin) {
        if (!Util.soloLetras(nombre)) {
            throw new BussinesException("El nombre solo debe contener letras.");
        }
        if (!Util.soloLetras(apellido)) {
            throw new BussinesException("El apellido solo debe contener letras.");
        }
        if (!Util.cedulaValido(cedula)) {
            throw new BussinesException("La cédula debe tener 10 dígitos numéricos.");
        }
        if (!Util.correoValido(correo)) {
            throw new BussinesException("Correo inválido.");
        }
        if (!contrasena.equals(confirmar)) {
            throw new BussinesException("Las contraseñas no coinciden.");
        }

        // Verificar duplicados
        if (findByCedula(cedula).isPresent()) {
            throw new BussinesException("El número de cédula ya está registrado.");
        }
        if (findByCorreo(correo).isPresent()) {
            throw new BussinesException("El correo ya está registrado.");
        }

        String contrasenaEncriptada = Util.encriptarSHA256(contrasena);
        Usuario nuevo = new Usuario(0, nombre, apellido, cedula, correo, contrasenaEncriptada, esAdmin);
        usuarioRepo.insert(nuevo);
    }

    // Login de usuario (con comparación encriptada)
    public Optional<Usuario> login(String correo, String contrasena) {
        String contrasenaEncriptada = Util.encriptarSHA256(contrasena);
        return usuarioRepo.getByEmailAndPassword(correo, contrasenaEncriptada);
    }
}
