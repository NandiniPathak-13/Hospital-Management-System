package com.example.hospitalmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hospitals.entities.Hospital;
import com.hospitals.services.Hospitalservice;

@Controller
@RequestMapping("/user")
public class AdminController {

    @Autowired
    private Hospitalservice hospitalService;

    @GetMapping("/dashboard")
    public String showHospitalsDashboard(Model model) {
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        model.addAttribute("hospitals", hospitals);
        return "/user/dashboard";  // Thymeleaf HTML page
    }
}
