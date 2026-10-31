package com.cine.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "salas", uniqueConstraints = @UniqueConstraint(columnNames = "numero"))
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1)
    @Column(nullable = false, unique = true)
    private Integer numero;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer capacidad;

    public Sala() {}
    public Sala(Integer numero, Integer capacidad) { this.numero = numero; this.capacidad = capacidad; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
}
