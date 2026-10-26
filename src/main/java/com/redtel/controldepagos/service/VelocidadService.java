package com.redtel.controldepagos.service;

import com.redtel.controldepagos.entidades.Velocidad;
import com.redtel.controldepagos.iservice.VelocidadIService;
import com.redtel.controldepagos.repository.VelocidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VelocidadService implements VelocidadIService {

    @Autowired
    private VelocidadRepository velocidadRepository;

    @Override
    public Velocidad crearVelocidad(Velocidad velocidad) {
        validar(velocidad);
        if (velocidadRepository.existsByCantidad(velocidad.getCantidad())) {
            throw new IllegalArgumentException("Ya existe un plan de " + velocidad.getCantidad() + " Mbps");
        }
        return velocidadRepository.save(velocidad);
    }

    @Override
    public List<Velocidad> obtenerTodasLasVelocidades() {
        return velocidadRepository.findAllByOrderByCantidadAsc();
    }

    @Override
    public Optional<Velocidad> obtenerVelocidadPorId(int id) {
        return velocidadRepository.findById(id);
    }

    @Override
    public Velocidad actualizarVelocidad(int id, Velocidad velocidad) {
        validar(velocidad);
        Velocidad existente = velocidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Velocidad con id " + id + " no encontrada"));
        existente.setCantidad(velocidad.getCantidad());
        existente.setPrecio(velocidad.getPrecio());
        return velocidadRepository.save(existente);
    }

    @Override
    public boolean eliminarVelocidad(int id) {
        if (velocidadRepository.existsById(id)) {
            velocidadRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validar(Velocidad v) {
        if (v.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad de Mbps debe ser mayor a 0");
        }
        if (v.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }
}