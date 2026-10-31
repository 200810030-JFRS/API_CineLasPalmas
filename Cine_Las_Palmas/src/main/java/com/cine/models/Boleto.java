package com.cine.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "boletos", uniqueConstraints = @UniqueConstraint(columnNames = "folio"))
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private String folio;

    @NotBlank
    @Column(nullable = false, length = 5)
    private String fila;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer numero;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "funcion_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Funcion funcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Boleto() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }
    public String getFila() { return fila; }
    public void setFila(String fila) { this.fila = fila; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Funcion getFuncion() { return funcion; }
    public void setFuncion(Funcion funcion) { this.funcion = funcion; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
