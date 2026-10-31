package com.cine.services;

import com.cine.models.Boleto;
import com.cine.models.Cliente;
import com.cine.models.Funcion;
import com.cine.repositories.BoletoRepository;
import com.cine.repositories.ClienteRepository;
import com.cine.repositories.FuncionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class BoletoService {
    private final BoletoRepository boletoRepository;
    private final FuncionRepository funcionRepository;
    private final ClienteRepository clienteRepository;
    public BoletoService(BoletoRepository b, FuncionRepository f, ClienteRepository c) { this.boletoRepository = b; this.funcionRepository = f; this.clienteRepository = c; }
    public List<Boleto> porFuncion(Long funcionId) { return boletoRepository.findByFuncionId(funcionId); }
    public Boleto obtener(Long id) { return boletoRepository.findById(id).orElseThrow(() -> new ReglaNegocioException("No existe el boleto con id " + id)); }
    @Transactional
    public Boleto comprar(Long funcionId, Long clienteId, String fila, Integer numero) {
        Funcion funcion = funcionRepository.findById(funcionId).orElseThrow(() -> new ReglaNegocioException("No existe la función indicada"));
        if (fila == null || !fila.trim().matches("[A-Za-z]{1,2}")) throw new ReglaNegocioException("La fila debe contener una o dos letras");
        if (numero == null || numero < 1 || numero > funcion.getSala().getCapacidad()) throw new ReglaNegocioException("El número de asiento debe estar entre 1 y la capacidad de la sala");
        String filaNormalizada = fila.trim().toUpperCase();
        if (boletoRepository.existsByFuncionIdAndFilaIgnoreCaseAndNumero(funcionId, filaNormalizada, numero)) throw new ReglaNegocioException("El asiento ya está ocupado para esta función");
        Boleto boleto = new Boleto();
        boleto.setFolio(UUID.randomUUID().toString().toUpperCase());
        boleto.setFuncion(funcion);
        boleto.setFila(filaNormalizada);
        boleto.setNumero(numero);
        if (clienteId != null) {
            Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ReglaNegocioException("No existe el cliente indicado"));
            boleto.setCliente(cliente);
            if (cliente.isRegistrado()) {
                int puntosGanados = (int) Math.floor(funcion.getPrecio() / 10.0);
                cliente.setPuntos((cliente.getPuntos() == null ? 0 : cliente.getPuntos()) + puntosGanados);
                clienteRepository.save(cliente);
            }
        }
        return boletoRepository.save(boleto);
    }
}
