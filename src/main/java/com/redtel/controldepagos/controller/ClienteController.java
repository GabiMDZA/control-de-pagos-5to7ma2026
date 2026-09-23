package com.redtel.controldepagos.controller;

import com.redtel.controldepagos.entidades.Cliente;
import com.redtel.controldepagos.service.ClienteIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    
    @Autowired
    private ClienteIService clienteService;
    
    /**
     * Obtener todos los clientes
     * GET /api/clientes
     */
    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAllClientes();
    }
    
    /**
     * Obtener un cliente por ID
     * GET /api/clientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable int id) {
        Optional<Cliente> cliente = clienteService.findClienteById(id);
        
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Crear un nuevo cliente
     * POST /api/clientes
     */
    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.saveCliente(cliente);
    }
    
    /**
     * Actualizar un cliente
     * PUT /api/clientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable int id, @RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = clienteService.updateCliente(id, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Eliminar un cliente
     * DELETE /api/clientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable int id) {
        if (clienteService.deleteCliente(id)) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Obtener clientes por nombre y apellido
     * GET /api/clientes/buscar?nombre=Juan&apellido=Perez
     */
    @GetMapping("/buscar")
    public ResponseEntity<Cliente> searchByNombreYApellido(
            @RequestParam String nombre,
            @RequestParam String apellido) {
        Optional<Cliente> cliente = clienteService.findClienteByNombreAndApellido(nombre, apellido);
        
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Obtener clientes por celular
     * GET /api/clientes/celular/{celular}
     */
    @GetMapping("/celular/{celular}")
    public ResponseEntity<List<Cliente>> getClientesByCelular(@PathVariable String celular) {
        List<Cliente> clientes = clienteService.findClientesByCelular(celular);
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Obtener clientes con deuda (no pagaron)
     * GET /api/clientes/deuda/todos
     */
    @GetMapping("/deuda/todos")
    public ResponseEntity<List<Cliente>> getClientesConDeuda() {
        List<Cliente> clientes = clienteService.findClientesConDeuda();
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Obtener clientes pagados
     * GET /api/clientes/pagados/todos
     */
    @GetMapping("/pagados/todos")
    public ResponseEntity<List<Cliente>> getClientesPagados() {
        List<Cliente> clientes = clienteService.findClientesPagados();
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Obtener clientes por velocidad
     * GET /api/clientes/velocidad/{velocidad}
     */
    @GetMapping("/velocidad/{velocidad}")
    public ResponseEntity<List<Cliente>> getClientesByVelocidad(@PathVariable String velocidad) {
        List<Cliente> clientes = clienteService.findClientesByVelocidad(velocidad);
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Obtener estadísticas de pagos
     * GET /api/clientes/estadisticas/pagos
     */
    @GetMapping("/estadisticas/pagos")
    public ResponseEntity<Map<String, Object>> getEstadisticas() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalClientes = clienteService.findAllClientes().size();
        long clientesConDeuda = clienteService.countClientesConDeuda();
        long clientesPagados = clienteService.countClientesPagados();
        
        estadisticas.put("totalClientes", totalClientes);
        estadisticas.put("clientesConDeuda", clientesConDeuda);
        estadisticas.put("clientesPagados", clientesPagados);
        estadisticas.put("porcentajePagado", totalClientes > 0 ? (clientesPagados * 100 / totalClientes) : 0);
        
        return ResponseEntity.ok(estadisticas);
    }
}