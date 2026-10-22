package mx.edu.uteq.carreras.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mx.edu.uteq.carreras.dto.DivisionResumen;
import mx.edu.uteq.carreras.dto.ResumenPublicaciones;
import mx.edu.uteq.carreras.entity.Division;
import mx.edu.uteq.carreras.repository.CarreraRepository;
import mx.edu.uteq.carreras.repository.DivisionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Reglas del panel de secretaría académica (HU-10): listar las divisiones con su
 * estado y el resumen de lo publicado.
 */
@Service
@RequiredArgsConstructor
public class SecretariaService {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final DivisionRepository divisionRepository;
    private final CarreraRepository carreraRepository;

    /** HU-10: todas las divisiones ordenadas por nombre, con su resumen de publicación. */
    @Transactional(readOnly = true)
    public List<DivisionResumen> listarDivisiones() {
        return divisionRepository.findAllByOrderByNombreAsc().stream()
                .map(this::resumir)
                .toList();
    }

    /** HU-10: totales de divisiones y de carreras publicadas en todo el sistema. */
    @Transactional(readOnly = true)
    public ResumenPublicaciones resumenPublicaciones() {
        long totalDivisiones = divisionRepository.count();
        long divisionesActivas = divisionRepository.countByActivaTrue();
        return new ResumenPublicaciones(
                totalDivisiones,
                divisionesActivas,
                totalDivisiones - divisionesActivas,
                carreraRepository.count(),
                carreraRepository.countByActivaTrueAndDivisionActivaTrue());
    }

    private DivisionResumen resumir(Division division) {
        long totalCarreras = carreraRepository.countByDivisionId(division.getId());
        long carrerasActivas = carreraRepository.countByDivisionIdAndActivaTrue(division.getId());
        // Lo publicado depende del estado de la división: si está deshabilitada, no se publica nada.
        long carrerasPublicadas = division.isActiva() ? carrerasActivas : 0;
        String fechaCreacion = division.getFechaCreacion() == null
                ? "—"
                : division.getFechaCreacion().format(FORMATO_FECHA);
        return new DivisionResumen(
                division.getId(),
                division.getClave(),
                division.getNombre(),
                division.getDescripcion(),
                division.isActiva(),
                fechaCreacion,
                totalCarreras,
                carrerasActivas,
                carrerasPublicadas);
    }
}
