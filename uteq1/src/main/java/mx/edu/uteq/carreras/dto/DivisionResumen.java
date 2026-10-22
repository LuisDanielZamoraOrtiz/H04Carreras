package mx.edu.uteq.carreras.dto;

/**
 * Fila del panel de secretaría (HU-10): datos de una división más el resumen
 * de lo que está publicado dentro de ella.
 *
 * <p>Una carrera se considera publicada cuando la carrera está activa y su
 * división está habilitada, que es la misma regla que usa el registro de
 * aspirantes para ofrecer carreras.</p>
 */
public record DivisionResumen(
        Long id,
        String clave,
        String nombre,
        String descripcion,
        boolean activa,
        String fechaCreacion,
        long totalCarreras,
        long carrerasActivas,
        long carrerasPublicadas) {
}
