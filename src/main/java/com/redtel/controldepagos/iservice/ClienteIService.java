package com.redtel.controldepagos.service;

import com.redtel.controldepagos.entidades.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteIService {
    // CRUD operations
    Cliente createCliente(Cliente cliente);
    List<Cliente> findAllClientes();
    Optional<Cliente> findClienteById(int id);
    Cliente updateCliente(int id, Cliente cliente);
    boolean deleteCliente(int id);
    
    // Métodos adicionales para compatibilidad
    Cliente saveCliente(Cliente cliente);
    
    // Custom queries
    Optional<Cliente> findClienteByNombreAndApellido(String nombre, String apellido);
    List<Cliente> findClientesByCelular(String celular);
    List<Cliente> findClientesConDeuda();
    List<Cliente> findClientesPagados();
    List<Cliente> findClientesByVelocidad(String velocidad);
    
    // Statistics
    long countClientesConDeuda();
    long countClientesPagados();
}