package com.cine.controllers;

import com.cine.models.Boleto;
import com.cine.services.BoletoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/boletos")
public class BoletoController {
    private final BoletoService service;
    public BoletoController(BoletoService service) { this.service = service; }
    @GetMapping("/funcion/{funcionId}") public List<Boleto> porFuncion(@PathVariable Long funcionId) { return service.porFuncion(funcionId); }
    @GetMapping("/{id}") public Boleto obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Boleto comprar(@RequestParam Long funcionId, @RequestParam(required = false) Long clienteId, @RequestParam String fila, @RequestParam Integer numero) { return service.comprar(funcionId, clienteId, fila, numero); }
}
