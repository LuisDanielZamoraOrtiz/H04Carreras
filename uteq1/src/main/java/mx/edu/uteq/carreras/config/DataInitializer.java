package mx.edu.uteq.carreras.config;

import lombok.RequiredArgsConstructor;
import mx.edu.uteq.carreras.entity.Carrera;
import mx.edu.uteq.carreras.entity.Division;
import mx.edu.uteq.carreras.entity.NivelCarrera;
import mx.edu.uteq.carreras.entity.Rol;
import mx.edu.uteq.carreras.entity.Usuario;
import mx.edu.uteq.carreras.repository.CarreraRepository;
import mx.edu.uteq.carreras.repository.DivisionRepository;
import mx.edu.uteq.carreras.repository.UsuarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final DivisionRepository divisionRepository;
    private final CarreraRepository carreraRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (usuarioRepository.count() != 0 || divisionRepository.count() != 0) {
            return;
        }

        Division dti = division("División de Tecnologías de la Información", "DTI", true);
        Division din = division("División Industrial", "DIN", true);
        Division dea = division("División Económico Administrativa", "DEA", false);

        carrera("Ingeniería en Desarrollo y Gestión de Software", "IDGS", NivelCarrera.INGENIERIA, dti, true);
        carrera("Desarrollo de Software Multiplataforma", "DSM", NivelCarrera.TSU, dti, true);
        carrera("Ingeniería en Redes Inteligentes y Ciberseguridad", "IRIC", NivelCarrera.INGENIERIA, dti, false);
        carrera("Ingeniería Mecatrónica", "IMEC", NivelCarrera.INGENIERIA, din, true);
        carrera("Mantenimiento Área Industrial", "MAI", NivelCarrera.TSU, din, true);
        carrera("Licenciatura en Gestión de Negocios", "LGN", NivelCarrera.LICENCIATURA, dea, true);

        usuario("Administrador", "UTEQ", "admin@uteq.edu.mx", "admin123", Rol.ADMIN);
        usuario("Secretaría", "UTEQ", "secretaria@uteq.edu.mx", "secre123", Rol.SECRETARIA);
    }

    private Division division(String nombre, String clave, boolean activa) {
        Division division = new Division();
        division.setNombre(nombre);
        division.setClave(clave);
        division.setActiva(activa);
        return divisionRepository.save(division);
    }

    private void carrera(String nombre, String clave, NivelCarrera nivel, Division division, boolean activa) {
        Carrera carrera = new Carrera();
        carrera.setNombre(nombre);
        carrera.setClave(clave);
        carrera.setNivel(nivel);
        carrera.setDivision(division);
        carrera.setActiva(activa);
        carreraRepository.save(carrera);
    }

    private void usuario(String nombre, String apellidos, String correo, String password, Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setCorreo(correo);
        usuario.setPassword(password);
        usuario.setRol(rol);
        usuarioRepository.save(usuario);
    }
}
