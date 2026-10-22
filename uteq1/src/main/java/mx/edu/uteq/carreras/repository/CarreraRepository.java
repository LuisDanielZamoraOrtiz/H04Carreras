package mx.edu.uteq.carreras.repository;

import java.util.List;
import mx.edu.uteq.carreras.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    List<Carrera> findAllByOrderByNombreAsc();
    List<Carrera> findByDivisionIdOrderByNombreAsc(Long divisionId);
    List<Carrera> findByActivaTrueAndDivisionActivaTrueOrderByNombreAsc();
    long countByActivaTrueAndDivisionActivaTrue();
    long countByDivisionId(Long divisionId);
    long countByDivisionIdAndActivaTrue(Long divisionId);
    boolean existsByClaveIgnoreCase(String clave);
    boolean existsByClaveIgnoreCaseAndIdNot(String clave, Long id);
}
