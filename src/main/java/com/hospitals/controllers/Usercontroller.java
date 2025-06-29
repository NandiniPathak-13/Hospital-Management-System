package com.hospitals.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hospitals.entities.Appointment;
import com.hospitals.entities.Doctor;
import com.hospitals.entities.Hospital;
import com.hospitals.entities.User;
import com.hospitals.forms.AppointmentForm;
import com.hospitals.forms.Doctorform;
import com.hospitals.helpers.Helper;
import com.hospitals.repositories.AppointmentRepo;
import com.hospitals.repositories.Doctorrepo;
import com.hospitals.repositories.HospitalRepo;
import com.hospitals.repositories.Userrepo;
import com.hospitals.services.Doctorservice;
import com.hospitals.services.Hospitalservice;
import com.hospitals.services.Userservice;

@Controller
@RequestMapping("/user")

public class Usercontroller {

    @Autowired
    private Doctorrepo doctor;

    @Autowired
    private Doctorservice doctorservice;

    @Autowired
    private Userrepo userRepository;

    private Logger logger = LoggerFactory.getLogger(Usercontroller.class);

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private Userservice userservice;

    @Autowired
    private Hospitalservice hospitalService;

    @Autowired
    private HospitalRepo hospitalRepository;

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("showNavbar", true);
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        model.addAttribute("hospitals", hospitals);

        return "user/dashboard";
    }

    @GetMapping("/hospital/{id}/book")
    public String showAppointmentForm(@PathVariable Long id, Model model,
            Principal principal) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        String username = principal.getName();
        User user = userservice.getUserByEmail(username);

        List<Doctor> doctors = doctor.findByHospitalId(id);
        AppointmentForm form = new AppointmentForm();
        form.setHospitalId(id);
        model.addAttribute("showNavbar", true);
        model.addAttribute("hospital", hospital);
        model.addAttribute("doctors", doctors);
        model.addAttribute("user", user);
        model.addAttribute("appointmentform", new AppointmentForm());
        // To control modal visibility

        return "user/appointment"; // Thymeleaf view
    }

    @PostMapping("/submit-appointment")
    public String submitAppointment(@ModelAttribute("appointmentform") AppointmentForm appointmentform,
            Principal principal,
            RedirectAttributes redirectAttributes,
            Model model) {
        try {
            System.out.println("🧾 Received: " + appointmentform);

            User user = userservice.getUserByEmail(principal.getName());
            Hospital hospital = hospitalRepository.findById(appointmentform.getHospitalId())
                    .orElseThrow(() -> new RuntimeException("Hospital not found"));
            Doctor selectedDoctor = doctor.findById(appointmentform.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            Appointment appointment = new Appointment();
            appointment.setUser(user);
            appointment.setHospital(hospital);
            appointment.setDoctor(selectedDoctor);
            appointment.setPatientName(appointmentform.getPatientName());
            appointment.setPhoneNumber(appointmentform.getPhoneNumber());
            appointment.setDetails(appointmentform.getDetails());
            appointment.setDate(appointmentform.getDate());

            System.out.println("🏥 Hospital ID: " + appointmentform.getHospitalId());
            System.out.println("🩺 Doctor ID: " + appointmentform.getDoctorId());
            appointmentRepo.save(appointment);

            model.addAttribute("appointment", appointment);
            model.addAttribute("hospital", hospital);
            model.addAttribute("doctor", selectedDoctor);
            model.addAttribute("confirmed", true);
            model.addAttribute("showNavbar", true);
            model.addAttribute("doctors", doctor.findByHospitalId(hospital.getId()));
            model.addAttribute("appointmentform", new AppointmentForm());

            return "user/appointment";
        } catch (Exception e) {
            System.out.println("🔥 Error while submitting appointment: " + e.getMessage());
            e.printStackTrace(); // Show full stacktrace in logs

            return "error"; // fallback error page
        }
    }

    // @GetMapping("/hospital/{id}/book")
    // public String showAppointmentForm(@PathVariable Long id, Model model,
    // Principal principal) {
    // Hospital hospital = hospitalRepository.findById(id)
    // .orElseThrow(() -> new RuntimeException("Hospital not found"));

    // String username = principal.getName();
    // User user = userservice.getUserByEmail(username);

    // List<Doctor> doctors = doctor.findByHospitalId(id);

    // // ✅ HARD-CODED TEST: Save appointment and message
    // String saveMessage = "";
    // if (!doctors.isEmpty()) {
    // Appointment a = new Appointment();
    // a.setUser(user);
    // a.setHospital(hospital);
    // a.setDoctor(doctors.get(0)); // First doctor
    // a.setPatientName("Nandini Test");
    // a.setPhoneNumber("9999999999");
    // a.setDetails("Hardcoded test save");
    // a.setDate(LocalDate.now().plusDays(1));

    // appointmentRepo.save(a);

    // saveMessage = "✅ Hardcoded appointment saved successfully!";
    // System.out.println("💾 Saved: " + a);
    // } else {
    // saveMessage = "⚠️ No doctor found to assign appointment!";
    // }

    // model.addAttribute("message", saveMessage); // send to view
    // model.addAttribute("showNavbar", true);
    // model.addAttribute("hospital", hospital);
    // model.addAttribute("doctors", doctors);
    // model.addAttribute("user", user);
    // model.addAttribute("appointmentform", new AppointmentForm());
    // model.addAttribute("confirmed", true);

    // return "user/appointment";
    // }

    @PostMapping("/delete/{id}")
    public String deleteHospital(@PathVariable("id") Long id) {
        hospitalService.deleteHospital(id);
        // service method
        doctorservice.deleteDoctor(id);
        return "redirect:/admin"; // after delete, go back to dashboard
    }

    @GetMapping("/hospitals/{id}") // ✅ Fixed URL pattern
    public String showHospitalDetails(@PathVariable Long id, Model model) {
        model.addAttribute("showNavbar", true);

        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));
        model.addAttribute("hospital", hospital);
        model.addAttribute("showNavbar", true);
        model.addAttribute("doctor", doctor);

        return "user/hospitaldetails"; // Make sure this file exists in templates!
    }

    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication,
            @AuthenticationPrincipal OAuth2User oauthUser) {
        // String name = oauthUser.getAttribute("name");
        // yeh hota hai actual user ka naam!
        // model.addAttribute("showNavbar", true);
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User loggedin :{}", username);

        User user = userservice.getUserByEmail(username);
        System.out.println(user.getName());
        System.out.println(user.getEmail());

        model.addAttribute("LoggedInUser", user);

        return "user/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute User updatedUser, Principal principal) {
        // 1. Get logged in user's current info by username/email
        String username = principal.getName();
        User currentUser = userservice.getUserByEmail(username);

        // 2. Update current user details with submitted form data
        currentUser.setName(updatedUser.getName());
        currentUser.setEmail(updatedUser.getEmail());

        // 3. Password update - only if new password provided
        // if (updatedUser.getPassword() != null &&
        // !updatedUser.getPassword().isEmpty()) {
        // String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
        // currentUser.setPassword(encodedPassword);
        // }

        // 4. Save updated user to DB
        userservice.saveUser(currentUser);

        // 5. Redirect back to profile page (or success page)
        return "redirect:/user/profile";
    }

}