package com.redtel.controldepagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redtel.controldepagos.IService.IAdminService;
import com.redtel.controldepagos.entidades.Admin;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private IAdminService aService;

    @GetMapping
    public List<Admin> getAllAdmins() {
        return aService.findAllAdmins();
    }

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return aService.saveAdmin(admin);
    }
}