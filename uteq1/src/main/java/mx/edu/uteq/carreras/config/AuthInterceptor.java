package mx.edu.uteq.carreras.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.uteq.carreras.dto.UsuarioSesion;
import mx.edu.uteq.carreras.entity.Rol;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        UsuarioSesion usuario = request.getSession(false) == null ? null
                : (UsuarioSesion) request.getSession(false).getAttribute(UsuarioSesion.ATRIBUTO);
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        Rol requerido = request.getRequestURI().substring(request.getContextPath().length())
                .startsWith("/admin") ? Rol.ADMIN : Rol.SECRETARIA;
        if (usuario.getRol() != requerido) {
            response.sendRedirect(request.getContextPath() + "/?sinPermiso");
            return false;
        }
        return true;
    }
}
