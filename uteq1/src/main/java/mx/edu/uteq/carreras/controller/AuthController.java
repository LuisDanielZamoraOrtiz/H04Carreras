package mx.edu.uteq.carreras.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mx.edu.uteq.carreras.dto.RegistroForm;
import mx.edu.uteq.carreras.dto.UsuarioSesion;
import mx.edu.uteq.carreras.entity.Rol;
import mx.edu.uteq.carreras.entity.Usuario;
import mx.edu.uteq.carreras.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "registrado", required = false) String registrado,
                        HttpSession session, Model model) {
        UsuarioSesion usuario = (UsuarioSesion) session.getAttribute(UsuarioSesion.ATRIBUTO);
        if (usuario != null) {
            return destino(usuario.getRol());
        }
        if (registrado != null) {
            model.addAttribute("mensajeExito", "Tu cuenta se creó correctamente. Ya puedes iniciar sesión.");
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String iniciarSesion(@RequestParam("correo") String correo,
                               @RequestParam("password") String password,
                               HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = authService.autenticar(correo, password).orElse(null);
        if (usuario == null) {
            model.addAttribute("mensajeError", "Correo o contraseña incorrectos.");
            model.addAttribute("correo", correo);
            return "auth/login";
        }

        session.setAttribute(UsuarioSesion.ATRIBUTO, UsuarioSesion.desde(usuario));
        if (usuario.getRol() == Rol.ASPIRANTE) {
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Bienvenido(a), " + usuario.getNombre() + "!");
        }
        return destino(usuario.getRol());
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/registro")
    public String registro(@RequestParam(value = "carrera", required = false) Long carreraId, Model model) {
        RegistroForm form = new RegistroForm();
        form.setCarreraInteresId(carreraId);
        model.addAttribute("registroForm", form);
        model.addAttribute("carreras", authService.carrerasDisponibles());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registrar(@ModelAttribute("registroForm") RegistroForm form, Model model) {
        try {
            authService.registrarAspirante(form);
            return "redirect:/login?registrado";
        } catch (IllegalArgumentException ex) {
            form.setPassword(null);
            form.setConfirmarPassword(null);
            model.addAttribute("mensajeError", ex.getMessage());
            model.addAttribute("registroForm", form);
            model.addAttribute("carreras", authService.carrerasDisponibles());
            return "auth/registro";
        }
    }

    private String destino(Rol rol) {
        return switch (rol) {
            case ADMIN -> "redirect:/admin";
            case SECRETARIA -> "redirect:/secretaria";
            case ASPIRANTE -> "redirect:/";
        };
    }
}
