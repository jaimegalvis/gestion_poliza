package com.bolivar.gestion_poliza.service;

import com.bolivar.gestion_poliza.model.Poliza;
import com.bolivar.gestion_poliza.model.Riesgo;
import com.bolivar.gestion_poliza.repository.PolizaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PolizaService {

    private final PolizaRepository polizaRepository;

    public PolizaService(PolizaRepository polizaRepository) {
        this.polizaRepository = polizaRepository;
    }

    public List<Poliza> obtenerTodas() {
        return polizaRepository.findAll();
    }

    @Transactional
    public Poliza crearPoliza(Poliza poliza) {
        // REGLA DE NEGOCIO: Póliza individual solo permite 1 riesgo
        if ("INDIVIDUAL".equalsIgnoreCase(poliza.getTipoPoliza()) && poliza.getRiesgos().size() > 1) {
            throw new IllegalArgumentException("Las pólizas individuales solo pueden tener máximo un (1) riesgo.");
        }

        poliza.setEstado("ACTIVA");
        
        // Asegurar la relación bidireccional antes de guardar
        if (poliza.getRiesgos() != null) {
            for (Riesgo riesgo : poliza.getRiesgos()) {
                riesgo.setPoliza(poliza);
                if (riesgo.getEstado() == null) {
                    riesgo.setEstado("ACTIVO");
                }
            }
        }
        
        return polizaRepository.save(poliza);
    }

    @Transactional
    public Poliza renovarPoliza(Long id, Double porcentajeIpc) {
        Poliza poliza = polizaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Póliza no encontrada con ID: " + id));

        // REGLA DE NEGOCIO: No se puede renovar si está cancelada
        if ("CANCELADA".equalsIgnoreCase(poliza.getEstado())) {
            throw new IllegalStateException("No es posible renovar una póliza que ya se encuentra cancelada.");
        }

        // Aplicar incremento por IPC
        double factorIncremento = 1 + (porcentajeIpc / 100);
        poliza.setValorPrimaTotal(poliza.getValorPrimaTotal() * factorIncremento);
        poliza.setValorCanonTotal(poliza.getValorCanonTotal() * factorIncremento);
        
        poliza.setEstado("RENOVADA");
        
        return polizaRepository.save(poliza);
    }

    @Transactional
    public Poliza cancelarPoliza(Long id) {
        Poliza poliza = polizaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Póliza no encontrada con ID: " + id));

        poliza.setEstado("CANCELADA");

        // REGLA DE NEGOCIO: Cancelación en cascada para los riesgos
        if (poliza.getRiesgos() != null) {
            for (Riesgo riesgo : poliza.getRiesgos()) {
                riesgo.setEstado("CANCELADO");
            }
        }

        return polizaRepository.save(poliza);
    }
}