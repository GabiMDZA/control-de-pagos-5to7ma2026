package com.redtel.controldepagos.service;
 
import com.redtel.controldepagos.entidades.Admin;
import java.util.List;
import java.util.Optional;
 
public interface AdminIService {
    // CRUD operations
    Admin createAdmin(Admin admin);
    List<Admin> findAllAdmins();
    Optional<Admin> findAdminById(int id);
    Admin updateAdmin(int id, Admin admin);
    boolean deleteAdmin(int id);
    
    // Custom queries
    Optional<Admin> findAdminByUsuario(String usuario);
    boolean validateCredentials(String usuario, String contraseña);
}
 