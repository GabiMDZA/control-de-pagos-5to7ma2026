package com.redtel.controldepagos.controller;

import com.redtel.controldepagos.entidades.Velocidad;
import com.redtel.controldepagos.iservice.VelocidadIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/velocidades")
@CrossOrigin(origins = "*")
public class VelocidadController {

    @Autowired
    private VelocidadIService velocidadService;

    @GetMapping
    public List<Velocidad> getAll() {
        return velocidadService.obtenerTodasLasVelocidades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Velocidad> getById(@PathVariable int id) {
        return velocidadService.obtenerVelocidadPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Velocidad velocidad) {
        try {
            Velocidad nueva = velocidadService.crearVelocidad(velocidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Velocidad velocidad) {
        try {
            return ResponseEntity.ok(velocidadService.actualizarVelocidad(id, velocidad));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return velocidadService.eliminarVelocidad(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}