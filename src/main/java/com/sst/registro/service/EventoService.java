package com.sst.registro.service;

import com.sst.registro.model.entity.Evento;
import com.sst.registro.model.entity.Usuario;
import com.sst.registro.model.enums.EstadoEvento;
import com.sst.registro.model.enums.Gravedad;
import com.sst.registro.model.enums.TipoEvento;
import com.sst.registro.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {
    
    private final EventoRepository eventoRepository;
    
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }
    
    public Page<Evento> listarTodos(Pageable pageable) {
        return eventoRepository.findAll(pageable);
    }
    
    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
    }
    
    public Evento buscarPorCodigo(String codigo) {
        return eventoRepository.findByCodigo(codigo).orElse(null);
    }
    
    @Transactional
    public Evento guardar(Evento evento, Usuario usuario) {
        if (evento.getId() == null) {
            evento.setCodigo(generarCodigo(evento.getTipo()));
            evento.setUsuarioRegistra(usuario);
        }
        return eventoRepository.save(evento);
    }
    
    @Transactional
    public void eliminar(Long id) {
        eventoRepository.deleteById(id);
    }
    
    @Transactional
    public Evento cerrarEvento(Long id) {
        Evento evento = buscarPorId(id);
        evento.setEstado(EstadoEvento.CERRADO);
        return eventoRepository.save(evento);
    }
    
    public Page<Evento> buscarConFiltros(
            EstadoEvento estado, TipoEvento tipo, Gravedad gravedad,
            String lugar, LocalDateTime fechaInicio, LocalDateTime fechaFin,
            Pageable pageable) {
        return eventoRepository.buscarConFiltros(estado, tipo, gravedad, lugar, fechaInicio, fechaFin, pageable);
    }
    
    public List<Evento> listarRecientes() {
        return eventoRepository.findTop10ByOrderByFechaCreacionDesc();
    }
    
    public List<Evento> buscarPorCodigoOLugar(String buscar) {
        return eventoRepository.findByCodigoContainingOrLugarContaining(buscar, buscar);
    }
    
    public Long countByTipo(TipoEvento tipo) {
        return eventoRepository.countByTipo(tipo);
    }
    
    public Long countByGravedad(Gravedad gravedad) {
        return eventoRepository.countByGravedad(gravedad);
    }
    
    public Long countByEstado(EstadoEvento estado) {
        return eventoRepository.countByEstado(estado);
    }
    
    public List<Object[]> getEstadisticasPorMes() {
        return eventoRepository.countByMes();
    }
    
    public List<Object[]> getEstadisticasPorArea() {
        return eventoRepository.countByArea();
    }
    
    private String generarCodigo(TipoEvento tipo) {
        String prefix = switch (tipo) {
            case ACCIDENTE -> "ACC";
            case INCIDENTE -> "INC";
            case ENFERMEDAD_PROFESIONAL -> "ENF";
        };
        
        Integer maxNum = eventoRepository.findMaxCodigoByPrefix(prefix);
        int nextNum = (maxNum != null ? maxNum : 0) + 1;
        
        return String.format("%s-%03d", prefix, nextNum);
    }
}
