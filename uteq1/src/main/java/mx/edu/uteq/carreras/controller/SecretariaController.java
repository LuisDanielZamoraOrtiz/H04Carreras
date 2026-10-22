package mx.edu.uteq.carreras.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.uteq.carreras.dto.DivisionResumen;
import mx.edu.uteq.carreras.service.SecretariaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /** HU-11: habilitar o deshabilitar una división. */
    @PostMapping("/divisiones/{id}/estado")
    public String alternarEstado(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            DivisionResumen division = secretariaService.alternarEstado(id);
            String clave = division.clave();
            redirectAttributes.addFlashAttribute("mensajeExito", division.activa()
                    ? "División " + clave + " habilitada. Sus carreras activas ya se publican."
                    : "División " + clave + " deshabilitada. Sus carreras dejan de publicarse.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("mensajeError", ex.getMessage());
        }
        return "redirect:/secretaria";
    }
}
