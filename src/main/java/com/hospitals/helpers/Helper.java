package com.hospitals.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.userdetails.UserDetails;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        Object principal = authentication.getPrincipal();
//  var oauth2User =(OAuth2User) authentication.getPrincipal();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientId = oauthToken.getAuthorizedClientRegistrationId();

            if (clientId.equalsIgnoreCase("google")) {
                System.out.println("Getting email from Google client");

                OAuth2User oauthUser = (OAuth2User) principal;
                return oauthUser.getAttribute("email"); // ⚠️ Make sure "email" is present
            }
        }

        // If it's a standard login
        if (principal instanceof UserDetails) {
            System.out.println("Getting data from database");
            return ((UserDetails) principal).getUsername();
        }

        // Fallback
        return principal.toString();
    }
}
