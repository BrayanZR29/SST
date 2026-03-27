package com.sst.registro.repository;

import com.sst.registro.model.entity.AccionCorrectiva;
import com.sst.registro.model.enums.EstadoAccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccionCorrectivaRepository extends JpaRepository<AccionCorrectiva, Long> {
    List<AccionCorrectiva> findByInvestigacionId(Long investigacionId);
    List<AccionCorrectiva> findByEstadoAccion(EstadoAccion estado);
    Long countByEstadoAccion(EstadoAccion estado);
}
