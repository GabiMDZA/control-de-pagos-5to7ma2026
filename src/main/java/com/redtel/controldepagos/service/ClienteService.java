package com.redtel.controldepagos.service;

import com.redtel.controldepagos.entidades.Cliente;
import com.redtel.controldepagos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements ClienteIService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Override
    public Cliente createCliente(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }
        if (cliente.getApellido() == null || cliente.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El apellido del cliente no puede estar vacío");
        }
        return clienteRepository.save(cliente);
    }
    
    @Override
    public List<Cliente> findAllClientes() {
        return clienteRepository.findAll();
    }
    
    @Override
    public Optional<Cliente> findClienteById(int id) {
        return clienteRepository.findById(id);
    }
    
    @Override
    public Cliente updateCliente(int id, Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        
        if (clienteExistente.isPresent()) {
            Cliente clienteActualizado = clienteExistente.get();
            
            if (cliente.getNombre() != null && !cliente.getNombre().isEmpty()) {
                clienteActualizado.setNombre(cliente.getNombre());
            }
            if (cliente.getApellido() != null && !cliente.getApellido().isEmpty()) {
                clienteActualizado.setApellido(cliente.getApellido());
            }
            if (cliente.getCelular() != null && !cliente.getCelular().isEmpty()) {
                clienteActualizado.setCelular(cliente.getCelular());
            }
            if (cliente.getDireccion() != null && !cliente.getDireccion().isEmpty()) {
                clienteActualizado.setDireccion(cliente.getDireccion());
            }
            clienteActualizado.setPagó(cliente.isPagó());
            
            if (cliente.getVelocidad() != null && !cliente.getVelocidad().isEmpty()) {
                clienteActualizado.setVelocidad(cliente.getVelocidad());
            }
            
            if (cliente.getMontoPaga() > 0) {
                clienteActualizado.setMontoPaga(cliente.getMontoPaga());
            }
            
            return clienteRepository.save(clienteActualizado);
        }
        
        throw new RuntimeException("Cliente con id " + id + " no encontrado");
    }
    
    @Override
    public boolean deleteCliente(int id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    public Optional<Cliente> findClienteByNombreAndApellido(String nombre, String apellido) {
        return clienteRepository.findByNombreAndApellido(nombre, apellido);
    }
    
    @Override
    public List<Cliente> findClientesByCelular(String celular) {
        return clienteRepository.findByCelular(celular);
    }
    
    @Override
    public List<Cliente> findClientesConDeuda() {
        return clienteRepository.findByPagó(false);
    }
    
    @Override
    public List<Cliente> findClientesPagados() {
        return clienteRepository.findByPagó(true);
    }
    
    @Override
    public List<Cliente> findClientesByVelocidad(String velocidad) {
        return clienteRepository.findClientesByVelocidad(velocidad);
    }
    
    @Override
    public long countClientesConDeuda() {
        return findClientesConDeuda().size();
    }
    
    @Override
    public long countClientesPagados() {
        return findClientesPagados().size();
    }
    
    @Override
    public Cliente saveCliente(Cliente cliente) {
        return createCliente(cliente);
    }
}