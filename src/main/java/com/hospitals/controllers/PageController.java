package com.hospitals.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hospitals.entities.Appointment;
import com.hospitals.entities.Doctor;
import com.hospitals.entities.Hospital;
import com.hospitals.entities.Provider;
import com.hospitals.entities.User;
import com.hospitals.forms.Doctorform;
import com.hospitals.forms.Hospitalform;
import com.hospitals.forms.Userform;
import com.hospitals.services.AppointmentService;
import com.hospitals.services.Doctorservice;
import com.hospitals.services.Hospitalservice;
import com.hospitals.services.Userservice;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PageController {

    @Autowired
    private Hospitalservice hospitalService;

    @Autowired
    private Doctorservice doctorservice;

    @Autowired
    private AppointmentService appointmentservice;

    @Autowired
    private Userservice userservice;

    @Autowired
    private Hospitalservice hospitalservice;

    // HOME page
    @RequestMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        model.addAttribute("page", "home");
        model.addAttribute("showNavbar", true);
        model.addAttribute("currentPath", request.getRequestURI());
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        model.addAttribute("hospitals", hospitals);
        return "home";
    }

    // HOME page

    // @GetMapping("/home")
    // public String homePage(HttpServletRequest request, Model model) {
    // model.addAttribute("currentPath", request.getRequestURI());
    // return "home";
    // }

    // ABOUT page
    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("page", "about");
        model.addAttribute("showNavbar", true);
        return "about";
    }

    // SERVICES page
    @RequestMapping("/services")
    public String services(Model model) {
        model.addAttribute("showNavbar", true);
        return "services";
    }

    // CONTACT page
    @RequestMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("showNavbar", true);
        return "contact";
    }

    // LOGIN page
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("showNavbar", true);
        return "login";
    }

    // REGISTER page - just show the form (GET)
    @RequestMapping("/register")

    public String register(Model model) {

        Userform userform = new Userform();
        model.addAttribute("userform", userform);
        // model.setName(name:"nandini");
        // model.addAttribute("showNavbar", true);
        return "register";
    }

    // PROCESS REGISTER - handle form submit (POST)
    @PostMapping("/do-register")
    public String registerUser(@ModelAttribute Userform userform, RedirectAttributes redirectAttributes) {

        // Pehle check karo user exist karta hai kya email se
        if (userservice.isUserExistByUsername(userform.getEmail())) {
            // Already registered hai
            redirectAttributes.addFlashAttribute("alreadyRegistered", "You are already registered. Please login.");
            return "redirect:/login";
        }

        // Naya user hai to register karo
        User user = new User();
        user.setName(userform.getName());
        user.setEmail(userform.getEmail());
        user.setPassword(userform.getPassword());
        user.setPhoneNumber(userform.getPhoneNumber());
        user.setProvider(Provider.SELF); // default value set manually here

        User savedUser = userservice.saveUser(user);
        System.out.println("Saved user: " + savedUser);

        // Success message
        redirectAttributes.addFlashAttribute("message", "Registration successful!");

        return "redirect:/login";
    }

    @RequestMapping("/")
    public String Dashboard(Model model) {
        model.addAttribute("showNavbar", true);
        return "login"; // This matches your Thymeleaf template: admin.html
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("hospitalform", new Hospitalform());
        model.addAttribute("hospitals", hospitalservice.getAllHospitals());
        model.addAttribute("doctorform", new Doctorform());
        model.addAttribute("doctor", doctorservice.getAllDoctors());
        // 🆕 Add these two lines 👇
        model.addAttribute("users", userservice.getAllUsers());

        model.addAttribute("appointments", appointmentservice.getAllAppointments());

        return "admin"; // This matches your Thymeleaf template: admin.html
    }

    @PostMapping("/admin/doctor/save")
    public String saveDoctor(@ModelAttribute Doctorform doctorform, RedirectAttributes redirectAttributes) {

        Hospital hospital = hospitalservice.getById(doctorform.getHospitalId());

        Doctor doctor = new Doctor();
        doctor.setName(doctorform.getName());
        doctor.setSpecialization(doctorform.getSpecialization());

        // Set both for DB link + display
        doctor.setHospital(hospital); // actual relationship
        doctor.setHospitalname(hospital.getName()); // display purpose

        doctorservice.saveDoctor(doctor);
        redirectAttributes.addFlashAttribute("message", "Doctor added successfully!");

        return "redirect:/admin";
    }

    @PostMapping("/admin/hospital/save")
    public String saveHospital(@ModelAttribute Hospitalform hospitalform, RedirectAttributes redirectAttributes) {
        Hospital hospital = new Hospital();
        hospital.setName(hospitalform.getName());
        hospital.setCity(hospitalform.getCity());
        hospital.setAddress(hospitalform.getAddress());
        hospital.setDescription(hospitalform.getDescription());
        hospital.setLocation(hospitalform.getLocation());
        hospital.setOpeningTime(hospitalform.getOpeningTime());
        hospital.setClosingTime(hospitalform.getClosingTime());

        hospitalservice.saveHospital(hospital);
        redirectAttributes.addFlashAttribute("message", "Hospital added successfully!");

        return "redirect:/admin";
    }

    @RestController
    public class EnvCheckController {
        @GetMapping("/envcheck")
        public String checkEnv() {
            return "Client ID from env: " + System.getenv("GOOGLE_CLIENT_ID");
        }
    }

}
