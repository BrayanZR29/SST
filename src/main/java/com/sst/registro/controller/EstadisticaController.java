package com.sst.registro.controller;

import com.sst.registro.model.entity.Evento;
import com.sst.registro.service.EstadisticaService;
import com.sst.registro.service.EventoService;
import com.sst.registro.service.ReporteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/estadisticas")
@RequiredArgsConstructor
public class EstadisticaController {
    
    private final EstadisticaService estadisticaService;
    private final EventoService eventoService;
    private final ReporteService reporteService;
    
    @GetMapping
    public String estadisticas(Model model) {
        model.addAttribute("resumen", estadisticaService.getResumenGeneral());
        model.addAttribute("porMes", estadisticaService.getPorMes());
        model.addAttribute("porArea", estadisticaService.getPorArea());
        model.addAttribute("porTipo", estadisticaService.getPorTipo());
        model.addAttribute("porGravedad", estadisticaService.getPorGravedad());
        
        return "estadistica/estadisticas";
    }
    
    @GetMapping("/exportar/pdf")
    public void exportarPdf(HttpServletResponse response) throws Exception {
        Map<String, Long> resumen = estadisticaService.getResumenGeneral();
        List<Evento> eventos = eventoService.listarRecientes();
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_estadisticas.pdf");
        
        reporteService.generarReporteEstadisticas(response, resumen, eventos);
    }
}
