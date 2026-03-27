package com.sst.registro.controller;

import com.sst.registro.service.EstadisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/estadisticas")
@RequiredArgsConstructor
public class EstadisticaController {
    
    private final EstadisticaService estadisticaService;
    
    @GetMapping
    public String estadisticas(Model model) {
        model.addAttribute("resumen", estadisticaService.getResumenGeneral());
        model.addAttribute("porMes", estadisticaService.getPorMes());
        model.addAttribute("porArea", estadisticaService.getPorArea());
        model.addAttribute("porTipo", estadisticaService.getPorTipo());
        model.addAttribute("porGravedad", estadisticaService.getPorGravedad());
        
        return "estadistica/estadisticas";
    }
}
