package mx.edu.uteq.carreras.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mx.edu.uteq.carreras.entity.Rol;
import mx.edu.uteq.carreras.entity.Usuario;

@Getter
@RequiredArgsConstructor
public class UsuarioSesion implements Serializable {
    public static final String ATRIBUTO = "usuarioSesion";

    private final Long id;
    private final String nombreCompleto;
    private final String correo;
    private final Rol rol;

    public static UsuarioSesion desde(Usuario usuario) {
        return new UsuarioSesion(usuario.getId(), usuario.getNombre() + " " + usuario.getApellidos(),
                usuario.getCorreo(), usuario.getRol());
    }
}
