package com.cine.controllers;

import com.cine.models.Sala;
import com.cine.services.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {
    private final SalaService service;
    public SalaController(SalaService service) { this.service = service; }
    @GetMapping public List<Sala> listar() { return service.listar(); }
    @GetMapping("/{id}") public Sala obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Sala crear(@Valid @RequestBody Sala sala) { return service.crear(sala); }
}
