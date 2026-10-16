package com.bolivar.gestion_poliza.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polizas")
public class Poliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoPoliza; // "INDIVIDUAL" o "COLECTIVA"

    @Column(nullable = false)
    private String estado; // "ACTIVA", "CANCELADA", "RENOVADA"

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private Double valorPrimaTotal;
    private Double valorCanonTotal;

    // La relación 1 a muchos. CascadeType.ALL garantiza que si cancelas la póliza, 
    // la acción afecte a todos sus riesgos asociados.
    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Riesgo> riesgos = new ArrayList<>();

    // Constructores, Getters y Setters
    public Poliza() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoPoliza() { return tipoPoliza; }
    public void setTipoPoliza(String tipoPoliza) { this.tipoPoliza = tipoPoliza; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Double getValorPrimaTotal() { return valorPrimaTotal; }
    public void setValorPrimaTotal(Double valorPrimaTotal) { this.valorPrimaTotal = valorPrimaTotal; }

    public Double getValorCanonTotal() { return valorCanonTotal; }
    public void setValorCanonTotal(Double valorCanonTotal) { this.valorCanonTotal = valorCanonTotal; }

    public List<Riesgo> getRiesgos() { return riesgos; }
    public void setRiesgos(List<Riesgo> riesgos) { this.riesgos = riesgos; }
    
    // Método auxiliar para agregar riesgos manteniendo la relación bidireccional
    public void addRiesgo(Riesgo riesgo) {
        riesgos.add(riesgo);
        riesgo.setPoliza(this);
    }
}