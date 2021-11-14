package com.commerce.workstationapp.Controller;

import com.commerce.workstationapp.domain.LoginInformaiton;
import com.commerce.workstationapp.domain.TokenInformation;
import com.commerce.workstationapp.domain.User;
import com.commerce.workstationapp.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    @Autowired
    private UserService userService;

    public static Jws<Claims> parseJwt(String jwtString) {
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(jwtString);

        return jwt;
    }

    @PostMapping(value = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity login(HttpServletResponse response, @RequestBody LoginInformaiton login) {
        HashMap<String, String> responseBody = new HashMap<>();
        Optional<User> user = userService.findByID(login.username);

        if (user.isPresent()) {
            if (user.get().getPassword().equals(login.password)) {
                HashMap<String, Object> claims = new HashMap<>();
                claims.put("userId", login.username);
                claims.put("role", user.get().getAdmin());

                Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

                String token = Jwts.builder().setClaims(claims).signWith(hmacKey).compact();

                Cookie cookie = new Cookie("token", token);
                cookie.setMaxAge(60 * 60 * 24);
                cookie.setPath("/");
                cookie.setDomain("xodius.io");
                response.addCookie(cookie);
                cookie.setDomain("localhost");
                response.addCookie(cookie);
                response.addCookie(cookie);


                responseBody.put("username", login.username);
                responseBody.put("role", "" + user.get().getAdmin());
            } else {
                responseBody.put("error", "Incorrect Password");
            }
        } else {
            responseBody.put("error", "Unknown User");
        }

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping(value = "/checkToken",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity checkToken(HttpServletResponse response, @RequestBody TokenInformation tokenInfo) {
        HashMap<String, String> responseBody = new HashMap<>();

        try {
            Jws<Claims> claims = parseJwt(tokenInfo.token);

            claims.getBody().forEach((k, v) -> {
                System.out.println(k + " " + v);
            });

            String userid = (String) claims.getBody().get("userId");
            String role = claims.getBody().get("role").toString();

            responseBody.put("username", userid);
            responseBody.put("role", role);
        } catch (JwtException ex) {
            responseBody.put("error", "Invalid token");
        }
        return ResponseEntity.ok().body(responseBody);
    }
}
