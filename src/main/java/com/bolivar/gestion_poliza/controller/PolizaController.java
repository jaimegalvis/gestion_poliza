package com.bolivar.gestion_poliza.controller;

import com.bolivar.gestion_poliza.model.Poliza;
import com.bolivar.gestion_poliza.service.PolizaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PolizaController {

    private static final Logger logger = LoggerFactory.getLogger(PolizaController.class);
    private final PolizaService polizaService;

    public PolizaController(PolizaService polizaService) {
        this.polizaService = polizaService;
    }

    @GetMapping("/polizas")
    public ResponseEntity<List<Poliza>> obtenerPolizas() {
        return ResponseEntity.ok(polizaService.obtenerTodas());
    }

    @PostMapping("/polizas")
    public ResponseEntity<Poliza> crearPoliza(@RequestBody Poliza poliza) {
        Poliza nuevaPoliza = polizaService.crearPoliza(poliza);
        registrarEventoCore("CREACION", nuevaPoliza.getId());
        return ResponseEntity.ok(nuevaPoliza);
    }

    @PutMapping("/polizas/{id}/renovar")
    public ResponseEntity<Poliza> renovarPoliza(@PathVariable Long id, @RequestParam Double ipc) {
        Poliza polizaRenovada = polizaService.renovarPoliza(id, ipc);
        registrarEventoCore("RENOVACION", polizaRenovada.getId());
        return ResponseEntity.ok(polizaRenovada);
    }

    @DeleteMapping("/polizas/{id}")
    public ResponseEntity<Poliza> cancelarPoliza(@PathVariable Long id) {
        Poliza polizaCancelada = polizaService.cancelarPoliza(id);
        registrarEventoCore("CANCELACION", polizaCancelada.getId());
        return ResponseEntity.ok(polizaCancelada);
    }

    // --- MOCK DEL CORE SOLICITADO EN LA PRUEBA ---
    @PostMapping("/core-mock/evento")
    public ResponseEntity<Void> mockCoreEvento(@RequestBody Map<String, Object> payload) {
        // Su único propósito: registrar en el log, tal como lo pide la prueba
        logger.info("CORE MOCK EVENTO REGISTRADO EN LOG: {}", payload);
        return ResponseEntity.ok().build();
    }

    // Método auxiliar para registrar la acción (Simula la llamada al mock)
    private void registrarEventoCore(String tipoEvento, Long polizaId) {
        logger.info("Simulando envío al CORE -> Evento: {}, PolizaID: {}", tipoEvento, polizaId);
    }
}