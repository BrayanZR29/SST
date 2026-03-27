package com.sst.registro.service;

import com.sst.registro.model.entity.Evento;
import com.sst.registro.model.enums.EstadoEvento;
import com.sst.registro.model.enums.Gravedad;
import com.sst.registro.model.enums.TipoEvento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EstadisticaService {
    
    private final EventoService eventoService;
    
    public Map<String, Long> getResumenGeneral() {
        Map<String, Long> resumen = new HashMap<>();
        resumen.put("totalAccidentes", eventoService.countByTipo(TipoEvento.ACCIDENTE));
        resumen.put("totalIncidentes", eventoService.countByTipo(TipoEvento.INCIDENTE));
        resumen.put("totalEnfermedades", eventoService.countByTipo(TipoEvento.ENFERMEDAD_PROFESIONAL));
        resumen.put("totalLeve", eventoService.countByGravedad(Gravedad.LEVE));
        resumen.put("totalGrave", eventoService.countByGravedad(Gravedad.GRAVE));
        resumen.put("totalMortal", eventoService.countByGravedad(Gravedad.MORTAL));
        resumen.put("totalAbiertos", eventoService.countByEstado(EstadoEvento.ABIERTO));
        resumen.put("totalEnProceso", eventoService.countByEstado(EstadoEvento.EN_PROCESO));
        resumen.put("totalCerrados", eventoService.countByEstado(EstadoEvento.CERRADO));
        return resumen;
    }
    
    public Map<String, Integer> getPorMes() {
        List<Object[]> datos = eventoService.getEstadisticasPorMes();
        Map<String, Integer> porMes = new HashMap<>();
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                         "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        
        for (int i = 0; i < 12; i++) {
            porMes.put(meses[i], 0);
        }
        
        for (Object[] row : datos) {
            int mes = ((Number) row[0]).intValue();
            long total = ((Number) row[1]).longValue();
            if (mes >= 1 && mes <= 12) {
                porMes.put(meses[mes - 1], (int) total);
            }
        }
        return porMes;
    }
    
    public Map<String, Integer> getPorArea() {
        List<Object[]> datos = eventoService.getEstadisticasPorArea();
        Map<String, Integer> porArea = new HashMap<>();
        for (Object[] row : datos) {
            String area = (String) row[0];
            long total = ((Number) row[1]).longValue();
            porArea.put(area != null ? area : "Sin definir", (int) total);
        }
        return porArea;
    }
    
    public Map<String, Long> getPorTipo() {
        Map<String, Long> porTipo = new HashMap<>();
        porTipo.put("Accidente", eventoService.countByTipo(TipoEvento.ACCIDENTE));
        porTipo.put("Incidente", eventoService.countByTipo(TipoEvento.INCIDENTE));
        porTipo.put("Enfermedad Profesional", eventoService.countByTipo(TipoEvento.ENFERMEDAD_PROFESIONAL));
        return porTipo;
    }
    
    public Map<String, Long> getPorGravedad() {
        Map<String, Long> porGravedad = new HashMap<>();
        porGravedad.put("Leve", eventoService.countByGravedad(Gravedad.LEVE));
        porGravedad.put("Grave", eventoService.countByGravedad(Gravedad.GRAVE));
        porGravedad.put("Mortal", eventoService.countByGravedad(Gravedad.MORTAL));
        return porGravedad;
    }
}
