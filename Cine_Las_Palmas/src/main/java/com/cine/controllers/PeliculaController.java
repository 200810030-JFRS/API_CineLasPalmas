package com.cine.controllers;

import com.cine.models.Clasificacion;
import com.cine.models.Pelicula;
import com.cine.services.PeliculaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
    private final PeliculaService service;
    public PeliculaController(PeliculaService service) { this.service = service; }
    @GetMapping public List<Pelicula> listar(@RequestParam(required = false) Clasificacion clasificacion, @RequestParam(required = false) String genero) {
        if (clasificacion != null) return service.porClasificacion(clasificacion);
        if (genero != null && !genero.isBlank()) return service.porGenero(genero);
        return service.listar();
    }
    @GetMapping("/{id}") public Pelicula obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Pelicula crear(@Valid @RequestBody Pelicula pelicula) { return service.crear(pelicula); }
    @PutMapping("/{id}") public Pelicula actualizar(@PathVariable Long id, @Valid @RequestBody Pelicula pelicula) { return service.actualizar(id, pelicula); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
