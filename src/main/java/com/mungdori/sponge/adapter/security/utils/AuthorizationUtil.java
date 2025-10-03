package com.mungdori.sponge.adapter.security.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtil {

    public Long getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getClass() == AnonymousAuthenticationToken.class) {
            return null;
        }
        return (Long) authentication.getPrincipal();

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
