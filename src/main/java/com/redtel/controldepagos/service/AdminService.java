package com.redtel.controldepagos.service;

import com.redtel.controldepagos.entidades.Admin;
import com.redtel.controldepagos.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements AdminIService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Override
    public Admin createAdmin(Admin admin) {
        if (admin.getUsuario() == null || admin.getUsuario().isEmpty()) {
            throw new IllegalArgumentException("El usuario no puede estar vacío");
        }
        if (admin.getContraseña() == null || admin.getContraseña().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        return adminRepository.save(admin);
    }
    
    @Override
    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }
    
    @Override
    public Optional<Admin> findAdminById(int id) {
        return adminRepository.findById(id);
    }
    
    @Override
    public Admin updateAdmin(int id, Admin admin) {
        Optional<Admin> adminExistente = adminRepository.findById(id);
        
        if (adminExistente.isPresent()) {
            Admin adminActualizado = adminExistente.get();
            
            if (admin.getUsuario() != null && !admin.getUsuario().isEmpty()) {
                adminActualizado.setUsuario(admin.getUsuario());
            }
            if (admin.getContraseña() != null && !admin.getContraseña().isEmpty()) {
                adminActualizado.setContraseña(admin.getContraseña());
            }
            
            return adminRepository.save(adminActualizado);
        }
        
        throw new RuntimeException("Admin con id " + id + " no encontrado");
    }
    
    @Override
    public boolean deleteAdmin(int id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    public Optional<Admin> findAdminByUsuario(String usuario) {
        return adminRepository.findByUsuario(usuario);
    }
    
    @Override
    public boolean validateCredentials(String usuario, String contraseña) {
        Optional<Admin> admin = adminRepository.findByUsuario(usuario);
        
        if (admin.isPresent()) {
            return admin.get().getContraseña().equals(contraseña);
        }
        
        return false;
    }
}