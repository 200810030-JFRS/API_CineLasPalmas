package com.cine.services;

import com.cine.models.Cliente;
import com.cine.repositories.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    public ClienteService(ClienteRepository repository) { this.repository = repository; }
    public List<Cliente> listar() { return repository.findAll(); }
    public Cliente obtener(Long id) { return repository.findById(id).orElseThrow(() -> new ReglaNegocioException("No existe el cliente con id " + id)); }
    public Cliente registrar(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) throw new ReglaNegocioException("El nombre es obligatorio");
        if (cliente.getEmail() == null || !cliente.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) throw new ReglaNegocioException("El correo electrónico no es válido");
        if (repository.findByEmailIgnoreCase(cliente.getEmail()).isPresent()) throw new ReglaNegocioException("El correo ya está registrado");
        cliente.setRegistrado(true);
        cliente.setPuntos(0);
        return repository.save(cliente);
    }
}
