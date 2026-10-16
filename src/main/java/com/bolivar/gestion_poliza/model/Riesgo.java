package com.bolivar.gestion_poliza.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "riesgos")
public class Riesgo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String estado; // "ACTIVO", "CANCELADO"

    private Double valorAsegurado;

    // Relación de vuelta hacia la Póliza
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poliza_id", nullable = false)
    @JsonIgnore // Evita bucles infinitos al convertir a JSON en las respuestas REST
    private Poliza poliza;

    // Constructores, Getters y Setters
    public Riesgo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getValorAsegurado() { return valorAsegurado; }
    public void setValorAsegurado(Double valorAsegurado) { this.valorAsegurado = valorAsegurado; }

    public Poliza getPoliza() { return poliza; }
    public void setPoliza(Poliza poliza) { this.poliza = poliza; }
}