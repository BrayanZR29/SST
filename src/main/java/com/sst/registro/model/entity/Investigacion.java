package com.sst.registro.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "investigacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investigacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "causa_inmediata", columnDefinition = "TEXT")
    private String causaInmediata;
    
    @Column(name = "causa_basica", columnDefinition = "TEXT")
    private String causaBasica;
    
    @Column(name = "factores_contribuitorios", columnDefinition = "TEXT")
    private String factoresContribuitorios;
    
    @Column(name = "fecha_investigacion")
    private LocalDateTime fechaInvestigacion;
    
    @OneToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
    
    @ManyToOne
    @JoinColumn(name = "investigador_id")
    private Usuario investigador;
    
    @OneToMany(mappedBy = "investigacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccionCorrectiva> accionesCorrectivas = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        fechaInvestigacion = LocalDateTime.now();
    }
    
    public void agregarAccionCorrectiva(AccionCorrectiva accion) {
        accionesCorrectivas.add(accion);
        accion.setInvestigacion(this);
    }
}
