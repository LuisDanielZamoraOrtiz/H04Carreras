package mx.edu.uteq.carreras.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "divisiones")
@Getter
@Setter
@NoArgsConstructor
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String nombre;

    @Column(nullable = false, unique = true, length = 10)
    private String clave;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private boolean activa = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "division")
    @OrderBy("nombre ASC")
    private List<Carrera> carreras = new ArrayList<>();

    @PrePersist
    void asignarFechaCreacion() {
        fechaCreacion = LocalDateTime.now();
    }
}
