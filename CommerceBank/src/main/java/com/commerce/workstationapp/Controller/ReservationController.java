package com.commerce.workstationapp.Controller;

import com.commerce.workstationapp.AuthenticationHandle;
import com.commerce.workstationapp.domain.*;
import com.commerce.workstationapp.service.ReservationService;
import com.commerce.workstationapp.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;

    public AuthenticationHandle authenticationHandle;
    public ReservationController(){
        authenticationHandle = new AuthenticationHandle();
    }


    @PostMapping(value = "/",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity reservation(HttpServletResponse response, @RequestBody ReservationInformation reservationInformation) {
        HashMap<String, Object> responseBody = new HashMap<>();
        System.out.println(reservationInformation.token);
        try {

            Jws<Claims> claims = authenticationHandle.parseJwt(reservationInformation.token);
            claims.getBody().forEach((k,v)->{System.out.println(k + " " + v);});
            String userid = (String) claims.getBody().get("userId");
            String role = claims.getBody().get("role").toString();

            responseBody.put("reservations", reservationService.findByUser(userid));

        } catch (JwtException ex) {
            responseBody.put("error", "Invalid token");
        }

        return ResponseEntity.ok().body(responseBody);
    }
}
