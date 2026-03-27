package com.sst.registro.model.entity;

import com.sst.registro.model.enums.EstadoEvento;
import com.sst.registro.model.enums.Gravedad;
import com.sst.registro.model.enums.TipoEvento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, length = 20)
    private String codigo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEvento tipo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gravedad gravedad;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEvento estado = EstadoEvento.ABIERTO;
    
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;
    
    @Column(length = 100)
    private String lugar;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(columnDefinition = "TEXT")
    private String consecuencias;
    
    @Column(name = "personas_involucradas", length = 255)
    private String personasInvolucradas;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioRegistra;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @OneToOne(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Investigacion investigacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
