package mx.edu.uteq.carreras.repository;

import java.util.List;
import mx.edu.uteq.carreras.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DivisionRepository extends JpaRepository<Division, Long> {
    List<Division> findAllByOrderByNombreAsc();
    List<Division> findByActivaTrueOrderByNombreAsc();
    long countByActivaTrue();
    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByClaveIgnoreCase(String clave);
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
    boolean existsByClaveIgnoreCaseAndIdNot(String clave, Long id);
}
