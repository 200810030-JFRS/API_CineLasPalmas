package com.cine.controllers;

import com.cine.models.Cliente;
import com.cine.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;
    public ClienteController(ClienteService service) { this.service = service; }
    @GetMapping public List<Cliente> listar() { return service.listar(); }
    @GetMapping("/{id}") public Cliente obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Cliente registrar(@Valid @RequestBody Cliente cliente) { return service.registrar(cliente); }
}
