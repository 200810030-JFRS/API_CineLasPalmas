package com.cine.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "peliculas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String titulo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private Clasificacion clasificacion;

    @Min(1)
    @Column(nullable = false)
    private int duracion;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String genero;

    public Pelicula() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Clasificacion getClasificacion() { return clasificacion; }
    public void setClasificacion(Clasificacion clasificacion) { this.clasificacion = clasificacion; }
    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}
