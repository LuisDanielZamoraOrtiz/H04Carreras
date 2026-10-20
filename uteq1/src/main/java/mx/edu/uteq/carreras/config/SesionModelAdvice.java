package mx.edu.uteq.carreras.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mx.edu.uteq.carreras.dto.UsuarioSesion;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SesionModelAdvice {
    @ModelAttribute("usuarioSesion")
    public UsuarioSesion usuarioSesion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (UsuarioSesion) session.getAttribute(UsuarioSesion.ATRIBUTO);
    }
}
