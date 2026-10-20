package mx.edu.uteq.carreras.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mx.edu.uteq.carreras.dto.RegistroForm;
import mx.edu.uteq.carreras.entity.Carrera;
import mx.edu.uteq.carreras.entity.Rol;
import mx.edu.uteq.carreras.entity.Usuario;
import mx.edu.uteq.carreras.repository.CarreraRepository;
import mx.edu.uteq.carreras.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final CarreraRepository carreraRepository;

    public Optional<Usuario> autenticar(String correo, String password) {
        if (correo == null || correo.isBlank() || password == null || password.isBlank()) {
            return Optional.empty();
        }
        return usuarioRepository.findByCorreoIgnoreCase(correo.trim())
                .filter(usuario -> usuario.getPassword().equals(password));
    }

    public List<Carrera> carrerasDisponibles() {
        return carreraRepository.findByActivaTrueAndDivisionActivaTrueOrderByNombreAsc();
    }

    @Transactional
    public Usuario registrarAspirante(RegistroForm form) {
        if (vacio(form.getNombre()) || vacio(form.getApellidos()) || vacio(form.getCorreo())
                || vacio(form.getPassword()) || vacio(form.getConfirmarPassword())) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }
        if (form.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
        if (!form.getPassword().equals(form.getConfirmarPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        String nombre = form.getNombre().trim();
        String apellidos = form.getApellidos().trim();
        String correo = form.getCorreo().trim().toLowerCase(Locale.ROOT);
        if (nombre.length() > 100 || apellidos.length() > 100 || correo.length() > 150) {
            throw new IllegalArgumentException("Los datos exceden la longitud permitida.");
        }
        if (usuarioRepository.existsByCorreoIgnoreCase(correo)) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese correo.");
        }

        Carrera carreraInteres = null;
        if (form.getCarreraInteresId() != null) {
            carreraInteres = carrerasDisponibles().stream()
                    .filter(carrera -> carrera.getId().equals(form.getCarreraInteresId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("La carrera seleccionada no está disponible."));
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setCorreo(correo);
        usuario.setPassword(form.getPassword());
        usuario.setRol(Rol.ASPIRANTE);
        usuario.setCarreraInteres(carreraInteres);
        return usuarioRepository.save(usuario);
    }

    private boolean vacio(String valor) {
        return valor == null || valor.isBlank();
    }
}
