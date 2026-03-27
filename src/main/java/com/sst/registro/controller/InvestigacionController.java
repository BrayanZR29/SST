package com.sst.registro.controller;

import com.sst.registro.model.entity.AccionCorrectiva;
import com.sst.registro.model.entity.Evento;
import com.sst.registro.model.entity.Investigacion;
import com.sst.registro.model.entity.Usuario;
import com.sst.registro.service.EventoService;
import com.sst.registro.service.InvestigacionService;
import com.sst.registro.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/investigacion")
@RequiredArgsConstructor
public class InvestigacionController {
    
    private final InvestigacionService investigacionService;
    private final EventoService eventoService;
    private final UsuarioService usuarioService;
    
    @GetMapping("/nueva/{eventoId}")
    public String nueva(@PathVariable Long eventoId, Model model) {
        Evento evento = eventoService.buscarPorId(eventoId);
        Investigacion investigacion = investigacionService.buscarPorEventoId(eventoId);
        
        if (investigacion == null) {
            investigacion = new Investigacion();
        }
        
        model.addAttribute("evento", evento);
        model.addAttribute("investigacion", investigacion);
        model.addAttribute("existe", investigacionService.existeInvestigacion(eventoId));
        
        return "investigacion/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute Investigacion investigacion,
            @RequestParam Long eventoId,
            Authentication auth) {
        
        Usuario investigador = usuarioService.buscarPorCorreo(auth.getName());
        investigacionService.guardar(investigacion, eventoId, investigador);
        
        return "redirect:/evento/detalle/" + eventoId;
    }
    
    @GetMapping("/detalle/{eventoId}")
    public String detalle(@PathVariable Long eventoId, Model model) {
        Investigacion investigacion = investigacionService.buscarPorEventoId(eventoId);
        
        if (investigacion != null) {
            model.addAttribute("investigacion", investigacion);
            model.addAttribute("acciones", investigacionService.listarAccionesPorInvestigacion(investigacion.getId()));
        }
        
        model.addAttribute("eventoId", eventoId);
        return "investigacion/detalle";
    }
    
    @PostMapping("/accion/agregar")
    public String agregarAccion(
            @ModelAttribute AccionCorrectiva accion,
            @RequestParam Long investigacionId,
            @RequestParam Long eventoId) {
        
        investigacionService.agregarAccionCorrectiva(accion, investigacionId);
        return "redirect:/evento/detalle/" + eventoId;
    }
    
    @PostMapping("/accion/completar/{id}")
    public String completarAccion(@PathVariable Long id, @RequestParam Long eventoId) {
        investigacionService.completarAccion(id);
        return "redirect:/evento/detalle/" + eventoId;
    }
    
    @PostMapping("/accion/eliminar/{id}")
    public String eliminarAccion(@PathVariable Long id, @RequestParam Long eventoId) {
        investigacionService.eliminarAccion(id);
        return "redirect:/evento/detalle/" + eventoId;
    }
}
