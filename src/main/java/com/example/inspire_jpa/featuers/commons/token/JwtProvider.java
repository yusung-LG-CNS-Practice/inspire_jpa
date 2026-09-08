package com.example.inspire_jpa.featuers.commons.token;

import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    // AT => access token
    public String createAT(String email){
        return "Bearer XXXXXX";
    }

    // RT => refresh token
    public String createRT(String email){
        return "XXXXXXXX";
    }
}
