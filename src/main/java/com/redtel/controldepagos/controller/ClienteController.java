package com.redtel.controldepagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redtel.controldepagos.entidades.Cliente;
import com.redtel.controldepagos.iservice.IClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private IClienteService cService;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return cService.findAllClientes();
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return cService.saveCliente(cliente);
    }
}
