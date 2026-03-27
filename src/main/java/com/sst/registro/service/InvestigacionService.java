package com.sst.registro.service;

import com.sst.registro.model.entity.AccionCorrectiva;
import com.sst.registro.model.entity.Evento;
import com.sst.registro.model.entity.Investigacion;
import com.sst.registro.model.entity.Usuario;
import com.sst.registro.model.enums.EstadoEvento;
import com.sst.registro.repository.AccionCorrectivaRepository;
import com.sst.registro.repository.EventoRepository;
import com.sst.registro.repository.InvestigacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestigacionService {
    
    private final InvestigacionRepository investigacionRepository;
    private final AccionCorrectivaRepository accionRepository;
    private final EventoRepository eventoRepository;
    
    public Investigacion buscarPorId(Long id) {
        return investigacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investigacion no encontrada"));
    }
    
    public Investigacion buscarPorEventoId(Long eventoId) {
        return investigacionRepository.findByEventoId(eventoId).orElse(null);
    }
    
    public boolean existeInvestigacion(Long eventoId) {
        return investigacionRepository.existsByEventoId(eventoId);
    }
    
    @Transactional
    public Investigacion guardar(Investigacion investigacion, Long eventoId, Usuario investigador) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        
        investigacion.setEvento(evento);
        investigacion.setInvestigador(investigador);
        
        if (!existeInvestigacion(eventoId)) {
            evento.setEstado(EstadoEvento.EN_PROCESO);
            eventoRepository.save(evento);
        }
        
        return investigacionRepository.save(investigacion);
    }
    
    @Transactional
    public void eliminar(Long id) {
        Investigacion inv = buscarPorId(id);
        investigacionRepository.delete(inv);
    }
    
    @Transactional
    public AccionCorrectiva agregarAccionCorrectiva(AccionCorrectiva accion, Long investigacionId) {
        Investigacion investigacion = buscarPorId(investigacionId);
        accion.setInvestigacion(investigacion);
        return accionRepository.save(accion);
    }
    
    @Transactional
    public AccionCorrectiva completarAccion(Long accionId) {
        AccionCorrectiva accion = accionRepository.findById(accionId)
                .orElseThrow(() -> new RuntimeException("Accion no encontrada"));
        accion.completar();
        return accionRepository.save(accion);
    }
    
    @Transactional
    public void eliminarAccion(Long accionId) {
        accionRepository.deleteById(accionId);
    }
    
    public List<AccionCorrectiva> listarAccionesPorInvestigacion(Long investigacionId) {
        return accionRepository.findByInvestigacionId(investigacionId);
    }
    
    public List<AccionCorrectiva> listarAccionesPendientes() {
        return accionRepository.findByEstadoAccion(
                com.sst.registro.model.enums.EstadoAccion.PENDIENTE);
    }
}
