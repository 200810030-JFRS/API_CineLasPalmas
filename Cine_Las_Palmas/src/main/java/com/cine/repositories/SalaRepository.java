package com.cine.repositories;

import com.cine.models.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    boolean existsByNumero(Integer numero);
}
