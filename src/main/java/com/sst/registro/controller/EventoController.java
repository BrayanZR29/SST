package com.sst.registro.controller;

import com.sst.registro.model.entity.Evento;
import com.sst.registro.model.entity.Usuario;
import com.sst.registro.model.enums.EstadoEvento;
import com.sst.registro.model.enums.Gravedad;
import com.sst.registro.model.enums.TipoEvento;
import com.sst.registro.service.EventoService;
import com.sst.registro.service.ReporteService;
import com.sst.registro.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/evento")
@RequiredArgsConstructor
public class EventoController {
    
    private final EventoService eventoService;
    private final UsuarioService usuarioService;
    private final ReporteService reporteService;
    
    @GetMapping("/lista")
    public String lista(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) EstadoEvento estado,
            @RequestParam(required = false) TipoEvento tipo,
            @RequestParam(required = false) Gravedad gravedad,
            @RequestParam(required = false) String lugar,
            Model model) {
        
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("fechaCreacion").descending());
        Page<Evento> eventos = eventoService.buscarConFiltros(
                estado, tipo, gravedad, lugar, null, null, pageable);
        
        model.addAttribute("eventos", eventos);
        model.addAttribute("estado", estado);
        model.addAttribute("tipo", tipo);
        model.addAttribute("gravedad", gravedad);
        model.addAttribute("lugar", lugar);
        
        return "evento/lista";
    }
    
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("evento", new Evento());
        model.addAttribute("tipos", TipoEvento.values());
        model.addAttribute("gravedades", Gravedad.values());
        return "evento/formulario";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Evento evento = eventoService.buscarPorId(id);
        model.addAttribute("evento", evento);
        model.addAttribute("tipos", TipoEvento.values());
        model.addAttribute("gravedades", Gravedad.values());
        return "evento/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Evento evento, Authentication auth, Model model) {
        Usuario usuario = usuarioService.buscarPorCorreo(auth.getName());
        eventoService.guardar(evento, usuario);
        model.addAttribute("success", "Evento guardado correctamente");
        return "redirect:/evento/lista";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Evento evento = eventoService.buscarPorId(id);
        model.addAttribute("evento", evento);
        return "evento/detalle";
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        eventoService.eliminar(id);
        return "redirect:/evento/lista";
    }
    
    @PostMapping("/cerrar/{id}")
    public String cerrar(@PathVariable Long id) {
        eventoService.cerrarEvento(id);
        return "redirect:/evento/detalle/" + id;
    }
    
    @GetMapping("/exportar/pdf")
    public void exportarPdf(HttpServletResponse response,
            @RequestParam(required = false) EstadoEvento estado,
            @RequestParam(required = false) TipoEvento tipo,
            @RequestParam(required = false) Gravedad gravedad,
            @RequestParam(required = false) String lugar) throws IOException {
        
        List<Evento> eventos = eventoService.buscarConFiltros(estado, tipo, gravedad, lugar, null, null, 
            PageRequest.of(0, 1000, Sort.by("fechaCreacion").descending())).getContent();
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_eventos.pdf");
        
        reporteService.generarReporteEventos(response, eventos);
    }
    
    @GetMapping("/exportar/{id}/pdf")
    public void exportarEventoIndividualPdf(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Evento evento = eventoService.buscarPorId(id);
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=evento_" + evento.getCodigo() + ".pdf");
        
        reporteService.generarReporteEventoIndividual(response, evento);
    }
}
