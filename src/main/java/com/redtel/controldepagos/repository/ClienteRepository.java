package com.redtel.controldepagos.repository;

import com.redtel.controldepagos.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByNombreAndApellido(String nombre, String apellido);
    List<Cliente> findByCelular(String celular);
    List<Cliente> findByPagó(boolean pagó);
    
    @Query("SELECT c FROM Cliente c WHERE c.velocidad = :velocidad")
    List<Cliente> findClientesByVelocidad(@Param("velocidad") String velocidad);
}