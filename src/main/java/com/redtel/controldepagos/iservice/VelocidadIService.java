package com.redtel.controldepagos.iservice;

import com.redtel.controldepagos.entidades.Velocidad;
import java.util.List;
import java.util.Optional;

public interface VelocidadIService {
    Velocidad crearVelocidad(Velocidad velocidad);
    List<Velocidad> obtenerTodasLasVelocidades();
    Optional<Velocidad> obtenerVelocidadPorId(int id);
    Velocidad actualizarVelocidad(int id, Velocidad velocidad);
    boolean eliminarVelocidad(int id);
}