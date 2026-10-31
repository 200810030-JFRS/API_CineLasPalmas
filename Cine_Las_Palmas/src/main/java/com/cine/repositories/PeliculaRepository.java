package com.cine.repositories;

import com.cine.models.Clasificacion;
import com.cine.models.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    List<Pelicula> findByGeneroIgnoreCase(String genero);
    List<Pelicula> findByClasificacion(Clasificacion clasificacion);
    boolean existsByTituloIgnoreCase(String titulo);
}
