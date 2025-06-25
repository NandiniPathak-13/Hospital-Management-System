package com.hospitals.controllers;

import com.hospitals.entities.User;
import com.hospitals.services.Userservice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import java.security.Principal;

@ControllerAdvice
public class GlobalController 
{ 
    @Autowired
    private Userservice userservice;

    @ModelAttribute
    public void addCommonAttributes(Model model, Principal principal, HttpServletRequest request) {
        model.addAttribute("currentPath", request.getRequestURI());

        if (principal != null) {
            String email = principal.getName();
            User user = userservice.getUserByEmail(email);
            if (user != null) {
                model.addAttribute("LoggedInUser", user);  // 👈 this is what you're using in Thymeleaf
            }
        }
        System.out.println("✅ GlobalController fired: Principal = " + principal);

    }
}
