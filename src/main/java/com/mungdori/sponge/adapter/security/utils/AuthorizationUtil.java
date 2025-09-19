package com.mungdori.sponge.adapter.security.utils;

import com.mungdori.sponge.adapter.security.UserDetailsImpl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtil {

    public String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getClass() == AnonymousAuthenticationToken.class) {
            return null;
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userDetails.getEmail();
    }

//    public String getLoginType() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication.getClass() == AnonymousAuthenticationToken.class) {
//            return null;
//        }
//        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
//        return userDetails.getLoginType();
//    }

}
