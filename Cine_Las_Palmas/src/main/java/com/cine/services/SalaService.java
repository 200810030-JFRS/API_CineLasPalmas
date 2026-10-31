package com.cine.services;

import com.cine.models.Sala;
import com.cine.repositories.SalaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SalaService {
    private final SalaRepository repository;
    public SalaService(SalaRepository repository) { this.repository = repository; }
    public List<Sala> listar() { return repository.findAll(); }
    public Sala obtener(Long id) { return repository.findById(id).orElseThrow(() -> new ReglaNegocioException("No existe la sala con id " + id)); }
    public Sala crear(Sala sala) {
        if (sala.getNumero() == null || sala.getNumero() < 1) throw new ReglaNegocioException("El número de sala debe ser positivo");
        if (sala.getCapacidad() == null || sala.getCapacidad() < 1) throw new ReglaNegocioException("La capacidad debe ser mayor a cero");
        if (repository.existsByNumero(sala.getNumero())) throw new ReglaNegocioException("El número de sala ya está registrado");
        return repository.save(sala);
    }
}
