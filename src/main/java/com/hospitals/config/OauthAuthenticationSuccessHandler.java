package com.hospitals.config;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.hospitals.entities.Provider;
import com.hospitals.entities.User;
import com.hospitals.helpers.AppConstant;
import com.hospitals.repositories.Userrepo;
import com.hospitals.services.Userservice;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OauthAuthenticationSuccessHandler.class);

@Autowired
private Userrepo userrepo;



private User orElse;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("OauthAuthenticationSuccessHandler");
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        

        String email = user.getAttribute("email").toString();
        String name = user.getAttribute("name").toString();
        
        //create user
         User user1 =new User();
         user1.setEmail(email);
         user1.setName(name);
         user1.setProvider(Provider.GOOGLE);
         user1.setPassword("password");
         user1.setProviderid(user.getName());
         user1.setRoleList(List.of(AppConstant.ROLE_USER));

   
User user2 = userrepo.findByEmail(email).orElse(null);

if (user2 == null) {
    userrepo.save(user1);
    logger.info("User saved" + email);
}

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }

}
