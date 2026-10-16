package com.bolivar.gestion_poliza.repository;

import com.bolivar.gestion_poliza.model.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Long> {
    // Método automático para buscar pólizas por tipo ("INDIVIDUAL" o "COLECTIVA")
    List<Poliza> findByTipoPoliza(String tipoPoliza);
}