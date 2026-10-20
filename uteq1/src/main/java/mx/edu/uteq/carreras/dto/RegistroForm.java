package mx.edu.uteq.carreras.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistroForm {
    private String nombre;
    private String apellidos;
    private String correo;
    private String password;
    private String confirmarPassword;
    private Long carreraInteresId;
}
