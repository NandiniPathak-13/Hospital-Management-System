package com.hospitals.controllers;

import java.security.Principal;
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

import com.hospitals.entities.Hospital;
import com.hospitals.entities.User;
import com.hospitals.helpers.Helper;
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
  @PostMapping("/delete/{id}")
    public String deleteHospital(@PathVariable("id") Long id) {
        hospitalService.deleteHospital(id);  
        // service method
        doctorservice.deleteDoctor(id);
        return "redirect:/admin";  // after delete, go back to dashboard
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


    // @RequestMapping("/profile")
    // public String userproofile(Model model, Principal principal) {
    // String name = principal.getName();
    // logger.info("User logged in: {}", name);
    // System.out.println("User profile");
    // return "user/profile";
    // }

    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication,
            @AuthenticationPrincipal OAuth2User oauthUser) {
        // String name = oauthUser.getAttribute("name");
        // yeh hota hai actual user ka naam!
        //    model.addAttribute("showNavbar", true);
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
    // if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
    //     String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
    //     currentUser.setPassword(encodedPassword);
    // }

    // 4. Save updated user to DB
    userservice.saveUser(currentUser);

    // 5. Redirect back to profile page (or success page)
    return "redirect:/user/profile";
}

}  