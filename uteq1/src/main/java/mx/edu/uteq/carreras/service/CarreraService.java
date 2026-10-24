package mx.edu.uteq.carreras.service;

import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import mx.edu.uteq.carreras.entity.Carrera;
import mx.edu.uteq.carreras.entity.Division;
import mx.edu.uteq.carreras.repository.CarreraRepository;
import mx.edu.uteq.carreras.repository.DivisionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarreraService {
    private final DivisionRepository divisionRepository;
    private final CarreraRepository carreraRepository;

    public Division buscarDivision(Long divisionId) {
        return divisionRepository.findById(divisionId)
                .orElseThrow(() -> new IllegalArgumentException("La división no existe."));
    }

    public List<Carrera> listarPorDivision(Long divisionId) {
        return carreraRepository.findByDivisionIdOrderByNombreAsc(divisionId);
    }

    @Transactional
    public Carrera crear(Long divisionId, Carrera datos) {
        Division division = buscarDivision(divisionId);
        // Se crea otra entidad: el formulario nunca decide el id, la división ni el estado.
        Carrera carrera = validarYNormalizar(datos);
        carrera.setDivision(division);
        return carreraRepository.save(carrera);
    }

    private Carrera validarYNormalizar(Carrera datos) {
        String nombre = limpiar(datos.getNombre());
        String clave = limpiar(datos.getClave());
        String descripcion = limpiar(datos.getDescripcion());
        if (nombre == null || clave == null || datos.getNivel() == null) {
            throw new IllegalArgumentException("Nombre, clave y nivel son obligatorios.");
        }
        clave = clave.toUpperCase(Locale.ROOT);
        validarLongitud(nombre, 150, "El nombre");
        validarLongitud(clave, 10, "La clave");
        validarLongitud(descripcion, 500, "La descripción");
        if (carreraRepository.existsByClaveIgnoreCase(clave)) {
            throw new IllegalArgumentException("Ya existe una carrera con esa clave.");
        }

        Carrera carrera = new Carrera();
        carrera.setNombre(nombre);
        carrera.setClave(clave);
        carrera.setNivel(datos.getNivel());
        carrera.setDescripcion(descripcion);
        return carrera;
    }

    private String limpiar(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        return valor.trim();
    }

    private void validarLongitud(String valor, int maximo, String campo) {
        if (valor != null && valor.length() > maximo) {
            throw new IllegalArgumentException(campo + " debe tener máximo " + maximo + " caracteres.");
        }
    }
}
