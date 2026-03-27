package com.sst.registro.controller;

import com.sst.registro.model.entity.Evento;
import com.sst.registro.service.EstadisticaService;
import com.sst.registro.service.EventoService;
import com.sst.registro.service.InvestigacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    
    private final EventoService eventoService;
    private final EstadisticaService estadisticaService;
    private final InvestigacionService investigacionService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        model.addAttribute("nombreUsuario", authentication.getName());
        
        Map<String, Long> resumen = estadisticaService.getResumenGeneral();
        model.addAttribute("resumen", resumen);
        
        List<Evento> recientes = eventoService.listarRecientes();
        model.addAttribute("eventosRecientes", recientes);
        
        var accionesPendientes = investigacionService.listarAccionesPendientes();
        model.addAttribute("accionesPendientes", accionesPendientes);
        
        return "dashboard/dashboard";
    }
}
