package com.sst.registro.repository;

import com.sst.registro.model.entity.Investigacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestigacionRepository extends JpaRepository<Investigacion, Long> {
    Optional<Investigacion> findByEventoId(Long eventoId);
    boolean existsByEventoId(Long eventoId);
}
