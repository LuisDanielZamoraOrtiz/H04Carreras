package mx.edu.uteq.carreras.dto;

/**
 * Totales que se muestran en la parte superior del panel de secretaría (HU-10).
 */
public record ResumenPublicaciones(
        long totalDivisiones,
        long divisionesActivas,
        long divisionesDeshabilitadas,
        long totalCarreras,
        long carrerasPublicadas) {
}
