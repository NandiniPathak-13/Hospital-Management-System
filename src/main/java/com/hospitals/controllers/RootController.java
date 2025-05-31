package com.hospitals.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.hospitals.entities.User;
import com.hospitals.helpers.Helper;
import com.hospitals.services.Userservice;

@ControllerAdvice
public class RootController {

private Logger logger= org.slf4j.LoggerFactory.getLogger(this.getClass()); 

@Autowired
private Userservice userservice;

    @ModelAttribute
public void addLoggedInUserInfo(Model model, Authentication authentication){

if (authentication == null) {
    return ;
}


System.out.println("Adding loggedin user info to every route");
    String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User loggedin :{}", username);

        User user = userservice.getUserByEmail(username);




        System.out.println(user.getName());
        System.out.println(user.getEmail());

        model.addAttribute("LoggedInUser", user);




}



}
