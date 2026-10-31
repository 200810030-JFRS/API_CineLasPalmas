package com.cine.services;

import com.cine.models.Clasificacion;
import com.cine.models.Pelicula;
import com.cine.repositories.PeliculaRepository;
import com.cine.repositories.FuncionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PeliculaService {
    private final PeliculaRepository repository;
    private final FuncionRepository funcionRepository;
    public PeliculaService(PeliculaRepository repository, FuncionRepository funcionRepository) { this.repository = repository; this.funcionRepository = funcionRepository; }
    public List<Pelicula> listar() { return repository.findAll(); }
    public Pelicula obtener(Long id) { return repository.findById(id).orElseThrow(() -> new ReglaNegocioException("No existe la película con id " + id)); }
    public List<Pelicula> porClasificacion(Clasificacion clasificacion) { return repository.findByClasificacion(clasificacion); }
    public List<Pelicula> porGenero(String genero) { return repository.findByGeneroIgnoreCase(genero); }
    public Pelicula crear(Pelicula pelicula) {
        validar(pelicula);
        if (repository.existsByTituloIgnoreCase(pelicula.getTitulo())) throw new ReglaNegocioException("Ya existe una película con ese título");
        return repository.save(pelicula);
    }
    public Pelicula actualizar(Long id, Pelicula datos) {
        Pelicula actual = obtener(id);
        validar(datos);
        actual.setTitulo(datos.getTitulo());
        actual.setClasificacion(datos.getClasificacion());
        actual.setDuracion(datos.getDuracion());
        actual.setGenero(datos.getGenero());
        return repository.save(actual);
    }
    public void eliminar(Long id) {
        Pelicula pelicula = obtener(id);
        if (funcionRepository.existsByPeliculaId(id)) throw new ReglaNegocioException("No se puede eliminar una película que tiene funciones programadas");
        repository.delete(pelicula);
    }
    private void validar(Pelicula p) {
        if (p.getTitulo() == null || p.getTitulo().isBlank()) throw new ReglaNegocioException("El título es obligatorio");
        if (p.getDuracion() <= 0) throw new ReglaNegocioException("La duración debe ser mayor a cero");
        if (p.getClasificacion() == null) throw new ReglaNegocioException("La clasificación es obligatoria");
        if (p.getGenero() == null || p.getGenero().isBlank()) throw new ReglaNegocioException("El género es obligatorio");
    }
}
