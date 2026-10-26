package com.redtel.controldepagos.repository;

import com.redtel.controldepagos.entidades.Velocidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VelocidadRepository extends JpaRepository<Velocidad, Integer> {
    List<Velocidad> findAllByOrderByCantidadAsc();
    boolean existsByCantidad(int cantidad);
}