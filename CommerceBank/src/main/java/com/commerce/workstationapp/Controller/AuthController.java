package com.commerce.workstationapp.Controller;

import com.commerce.workstationapp.AuthenticationHandle;
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
    public AuthenticationHandle authenticationHandle;

    public AuthController() {
        authenticationHandle = new AuthenticationHandle();
    }

    @Autowired
    private UserService userService;

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

                String token = authenticationHandle.createToken(claims);

                response.setHeader("Set-Cookie", "token=" + token + ";  SameSite=None; Max-Age=86400; Expires=Mon, 15-Nov-2022 22:21:31 GMT; Domain=xodius.io; Path=/; Secure");

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

            Jws<Claims> claims = authenticationHandle.parseJwt(tokenInfo.token);



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
