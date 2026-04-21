package com.sst.registro.repository;

import com.sst.registro.model.entity.Evento;
import com.sst.registro.model.enums.EstadoEvento;
import com.sst.registro.model.enums.Gravedad;
import com.sst.registro.model.enums.TipoEvento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
    Optional<Evento> findByCodigo(String codigo);
    
    Page<Evento> findByEstado(EstadoEvento estado, Pageable pageable);
    
    Page<Evento> findByTipo(TipoEvento tipo, Pageable pageable);
    
    Page<Evento> findByGravedad(Gravedad gravedad, Pageable pageable);
    
    Page<Evento> findByLugar(String lugar, Pageable pageable);
    
    Page<Evento> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin, Pageable pageable);
    
    @Query("SELECT e FROM Evento e WHERE " +
           "(:estado IS NULL OR e.estado = :estado) AND " +
           "(:tipo IS NULL OR e.tipo = :tipo) AND " +
           "(:gravedad IS NULL OR e.gravedad = :gravedad) AND " +
           "(:lugar IS NULL OR LOWER(e.lugar) LIKE LOWER(CONCAT('%', :lugar, '%'))) AND " +
           "(:codigo IS NULL OR LOWER(e.codigo) LIKE LOWER(CONCAT('%', :codigo, '%'))) AND " +
           "(:fechaInicio IS NULL OR e.fechaHora >= :fechaInicio) AND " +
           "(:fechaFin IS NULL OR e.fechaHora <= :fechaFin)")
    Page<Evento> buscarConFiltros(
            EstadoEvento estado, TipoEvento tipo, Gravedad gravedad,
            String lugar, String codigo, LocalDateTime fechaInicio, LocalDateTime fechaFin,
            Pageable pageable);
    
    List<Evento> findTop10ByOrderByFechaCreacionDesc();
    
    List<Evento> findByCodigoContainingOrLugarContaining(String codigo, String lugar);
    
    @Query("SELECT COUNT(e) FROM Evento e WHERE e.tipo = :tipo")
    Long countByTipo(TipoEvento tipo);
    
    @Query("SELECT COUNT(e) FROM Evento e WHERE e.gravedad = :gravedad")
    Long countByGravedad(Gravedad gravedad);
    
    @Query("SELECT COUNT(e) FROM Evento e WHERE e.estado = :estado")
    Long countByEstado(EstadoEvento estado);
    
    @Query(value = "SELECT MONTH(e.fecha_hora) as mes, COUNT(*) as total " +
                   "FROM evento e WHERE YEAR(e.fecha_hora) = YEAR(CURRENT_DATE) " +
                   "GROUP BY MONTH(e.fecha_hora)", nativeQuery = true)
    List<Object[]> countByMes();
    
    @Query(value = "SELECT e.lugar, COUNT(*) as total FROM evento e GROUP BY e.lugar", nativeQuery = true)
    List<Object[]> countByArea();
    
    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING(e.codigo, 5) AS int)), 0) FROM Evento e WHERE e.codigo LIKE :prefix%")
    Integer findMaxCodigoByPrefix(String prefix);
}
