package mx.edu.uteq.carreras.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mx.edu.uteq.carreras.entity.Usuario;
import mx.edu.uteq.carreras.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;

    public Optional<Usuario> autenticar(String correo, String password) {
        if (correo == null || correo.isBlank() || password == null || password.isBlank()) {
            return Optional.empty();
        }
        return usuarioRepository.findByCorreoIgnoreCase(correo.trim())
                .filter(usuario -> usuario.getPassword().equals(password));
    }
}
