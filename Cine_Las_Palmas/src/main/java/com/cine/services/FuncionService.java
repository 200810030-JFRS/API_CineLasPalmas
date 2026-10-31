package com.cine.services;

import com.cine.models.Funcion;
import com.cine.models.Pelicula;
import com.cine.models.Sala;
import com.cine.repositories.FuncionRepository;
import com.cine.repositories.PeliculaRepository;
import com.cine.repositories.SalaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FuncionService {
    private final FuncionRepository funcionRepository;
    private final PeliculaRepository peliculaRepository;
    private final SalaRepository salaRepository;
    public FuncionService(FuncionRepository f, PeliculaRepository p, SalaRepository s) { this.funcionRepository = f; this.peliculaRepository = p; this.salaRepository = s; }
    public List<Funcion> listar() { return funcionRepository.findAll(); }
    public Funcion obtener(Long id) { return funcionRepository.findById(id).orElseThrow(() -> new ReglaNegocioException("No existe la función con id " + id)); }
    public Funcion programar(Long peliculaId, Long salaId, Funcion funcion) {
        Pelicula pelicula = peliculaRepository.findById(peliculaId).orElseThrow(() -> new ReglaNegocioException("No existe la película indicada"));
        Sala sala = salaRepository.findById(salaId).orElseThrow(() -> new ReglaNegocioException("No existe la sala indicada"));
        if (funcion.getFechaHora() == null) throw new ReglaNegocioException("La fecha y hora son obligatorias");
        if (funcion.getPrecio() <= 0) throw new ReglaNegocioException("El precio debe ser mayor a cero");
        LocalDateTime inicio = funcion.getFechaHora();
        LocalDateTime fin = inicio.plusMinutes(pelicula.getDuracion());
        List<Funcion> existentes = funcionRepository.findBySalaId(salaId);
        for (Funcion existente : existentes) {
            LocalDateTime finExistente = existente.getFechaHora().plusMinutes(existente.getPelicula().getDuracion());
            if (inicio.isBefore(finExistente) && fin.isAfter(existente.getFechaHora())) throw new ReglaNegocioException("La sala ya tiene una función programada en ese horario");
        }
        funcion.setPelicula(pelicula);
        funcion.setSala(sala);
        return funcionRepository.save(funcion);
    }
}
