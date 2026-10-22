package mx.edu.uteq.carreras.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.uteq.carreras.service.SecretariaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Panel de la secretaría académica. El acceso está restringido al rol
 * SECRETARIA por {@code AuthInterceptor} para las rutas {@code /secretaria/**}.
 */
@Controller
@RequestMapping("/secretaria")
@RequiredArgsConstructor
public class SecretariaController {
    private final SecretariaService secretariaService;

    /** HU-10: listado de divisiones con estado y resumen de lo publicado. */
    @GetMapping
    public String panel(Model model) {
        model.addAttribute("resumen", secretariaService.resumenPublicaciones());
        model.addAttribute("divisiones", secretariaService.listarDivisiones());
        return "secretaria/panel";
    }
}
