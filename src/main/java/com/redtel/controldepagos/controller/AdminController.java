package com.redtel.controldepagos.controller;

import com.redtel.controldepagos.entidades.Admin;
import com.redtel.controldepagos.service.AdminIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private AdminIService adminService;
    
    /**
     * Obtener todos los administradores
     * GET /api/admins
     */
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.findAllAdmins();
    }
    
    /**
     * Obtener un administrador por ID
     * GET /api/admins/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable int id) {
        Optional<Admin> admin = adminService.findAdminById(id);
        
        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());
        }
        
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Crear un nuevo administrador
     * POST /api/admins
     */
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        try {
            Admin nuevoAdmin = adminService.createAdmin(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAdmin);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Actualizar un administrador
     * PUT /api/admins/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody Admin admin) {
        try {
            Admin adminActualizado = adminService.updateAdmin(id, admin);
            return ResponseEntity.ok(adminActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Eliminar un administrador
     * DELETE /api/admins/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable int id) {
        if (adminService.deleteAdmin(id)) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Obtener admin por usuario
     * GET /api/admins/usuario/{usuario}
     */
    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<Admin> getByUsuario(@PathVariable String usuario) {
        Optional<Admin> admin = adminService.findAdminByUsuario(usuario);
        
        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());
        }
        
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Validar credenciales de login
     * POST /api/admins/validar
     */
    @PostMapping("/validar")
    public ResponseEntity<Boolean> validateCredentials(@RequestBody Admin admin) {
        boolean esValido = adminService.validateCredentials(admin.getUsuario(), admin.getContraseña());
        return ResponseEntity.ok(esValido);
    }
}