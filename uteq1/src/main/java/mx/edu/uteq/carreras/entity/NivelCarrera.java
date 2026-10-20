package mx.edu.uteq.carreras.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NivelCarrera {
    TSU("Técnico Superior Universitario"),
    INGENIERIA("Ingeniería"),
    LICENCIATURA("Licenciatura");

    private final String etiqueta;
}
