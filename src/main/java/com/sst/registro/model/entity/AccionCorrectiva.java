package com.sst.registro.model.entity;

import com.sst.registro.model.enums.EstadoAccion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "accion_correctiva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccionCorrectiva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String descripcion;
    
    @Column(length = 100)
    private String responsable;
    
    @Column(name = "fecha_limite")
    private LocalDate fechaLimite;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_accion", nullable = false)
    private EstadoAccion estadoAccion = EstadoAccion.PENDIENTE;
    
    @Column(name = "fecha_completado")
    private LocalDate fechaCompletado;
    
    @ManyToOne
    @JoinColumn(name = "investigacion_id")
    private Investigacion investigacion;
    
    public boolean estaVencada() {
        return estadoAccion == EstadoAccion.PENDIENTE 
               && fechaLimite != null 
               && fechaLimite.isBefore(LocalDate.now());
    }
    
    public void completar() {
        this.estadoAccion = EstadoAccion.COMPLETADA;
        this.fechaCompletado = LocalDate.now();
    }
}
