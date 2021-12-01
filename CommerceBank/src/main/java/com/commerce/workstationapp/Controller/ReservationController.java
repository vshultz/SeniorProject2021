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
import java.net.http.HttpResponse;
import java.security.Key;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;

    public AuthenticationHandle authenticationHandle;

    public ReservationController() {
        authenticationHandle = new AuthenticationHandle();
    }

    //Should be get?
    @PostMapping(value = "/",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity reservation(HttpServletResponse response, @RequestBody ReservationInformation reservationInformation) {
        HashMap<String, Object> responseBody = new HashMap<>();

        try {

            Jws<Claims> claims = authenticationHandle.parseJwt(reservationInformation.token);

            String userid = (String) claims.getBody().get("userId");
            String role = claims.getBody().get("role").toString();

            responseBody.put("reservations", reservationService.findByUser(userid));

        } catch (JwtException ex) {
            responseBody.put("error", "Invalid token");
        }

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping(value = "/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity reservations(HttpServletResponse response, @RequestBody ReservationInformation reservationInformation) {
        HashMap<String, Object> responseBody = new HashMap<>();

        try {

            Jws<Claims> claims = authenticationHandle.parseJwt(reservationInformation.token);

            String userid = (String) claims.getBody().get("userId");
            String role = claims.getBody().get("role").toString();
            if (role.equals("1")|| userid.equals(reservationInformation.filter.userID.get())) {
                List<Reservation> unfilteredReservations = reservationService.findAll();
                List<Reservation> filteredReservations = reservationService.findAll();
                for (Reservation res : unfilteredReservations) {
                    if (!reservationInformation.filter.userID.isEmpty())
                        if (!res.getUserID().equals(reservationInformation.filter.userID.get())) {
                            filteredReservations.remove(res);
                            continue;
                        }
                    if (!reservationInformation.filter.cubicleID.isEmpty())
                        if (!res.getId().cubicleID.equals(reservationInformation.filter.cubicleID.get())) {
                            filteredReservations.remove(res);
                            continue;
                        }
                    if (!reservationInformation.filter.startTime.isEmpty() && !reservationInformation.filter.endTime.isEmpty())
                        if (!(res.getId().startTime.after(reservationInformation.filter.startTime.get()) && res.getEndTime().before(reservationInformation.filter.endTime.get()))) {
                            filteredReservations.remove(res);
                            continue;
                        }
                }

                responseBody.put("reservations", filteredReservations);
            } else
                responseBody.put("invalid user request", null);

        } catch (JwtException ex) {
            responseBody.put("error", "Invalid token");
        }

        return ResponseEntity.ok().body(responseBody);
    }


    @DeleteMapping(value = "/delete",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity delete(HttpServletResponse response, @RequestBody ReservationInformation reservationInformation) {
        HashMap<String, Object> responseBody = new HashMap<>();

        try {
            Jws<Claims> claims = authenticationHandle.parseJwt(reservationInformation.token);

            String userid = (String) claims.getBody().get("userId");
            String role = claims.getBody().get("role").toString();
            if (reservationInformation.filter.reservationID.isEmpty())
                responseBody.put("no reservation id provided", null);
            else if (role.equals("1") || reservationInformation.filter.userID.get().equals(userid)) {
                reservationService.delete(reservationInformation.filter.reservationID.get());
                responseBody.put("deleted", null);
            } else
                responseBody.put("invalid user request", null);
        } catch (JwtException ex) {
            responseBody.put("error", "Invalid token");
        }

        return ResponseEntity.ok().body(responseBody);
    }


    @PostMapping(value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity create(HttpServletResponse response, @RequestBody ReservationInformation reservationInformation) {
        HashMap<String, Object> responseBody = new HashMap<>();

        try {

            Jws<Claims> claims = authenticationHandle.parseJwt(reservationInformation.token);

            String userid = (String) claims.getBody().get("userId");
            String role = claims.getBody().get("role").toString();
            ReservationID id = new ReservationID();
            id.startTime = reservationInformation.filter.startTime.get();
            id.cubicleID = reservationInformation.filter.cubicleID.get();
            Reservation res = new Reservation(id, reservationInformation.filter.endTime.get(), userid);
            if (res != null)
                responseBody.put("reservations", reservationService.save(res));
            else
                responseBody.put("incorrect reservation information", null);

        } catch (JwtException ex) {
            responseBody.put("error", "Invalid token");
        }

        return ResponseEntity.ok().body(responseBody);
    }


    @PostMapping(value = "/filter",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity filter(HttpServletResponse response, @RequestBody ReservationInformation reservationInformation) {
        HashMap<String, Object> responseBody = new HashMap<>();

        try {

            Jws<Claims> claims = authenticationHandle.parseJwt(reservationInformation.token);

            String userid = (String) claims.getBody().get("userId");
            String role = claims.getBody().get("role").toString();
            if (role.equals("1")|| userid.equals(reservationInformation.filter.userID.get())) {
                List<Reservation> unfilteredReservations = reservationService.findAll();
                List<Reservation> filteredReservations = reservationService.findAll();
                for (Reservation res : unfilteredReservations) {
                    if (reservationInformation.filter.userID != null)
                        if (!res.getUserID().equals(reservationInformation.filter.userID.get())) {
                            filteredReservations.remove(res);
                            continue;
                        }
                    if (reservationInformation.filter.cubicleID != null)
                        if (!res.getId().cubicleID.equals(reservationInformation.filter.cubicleID.get())) {
                            filteredReservations.remove(res);
                            continue;
                        }
                    if (reservationInformation.filter.startTime != null && reservationInformation.filter.endTime != null)
                        if (!(res.getId().startTime.after(reservationInformation.filter.startTime.get()) && res.getEndTime().before(reservationInformation.filter.endTime.get()))) {
                            filteredReservations.remove(res);
                            continue;
                        }
                }

                responseBody.put("reservations", filteredReservations);
            } else
                responseBody.put("invalid user request", null);

        } catch (JwtException ex) {
            responseBody.put("error", "Invalid token");
        }

        return ResponseEntity.ok().body(responseBody);
    }


    @PostMapping(value = "/available",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity available(HttpServletResponse response, @RequestBody ReservationInformation reservationInformation) {
        HashMap<String, Object> responseBody = new HashMap<>();

        try {

            Jws<Claims> claims = authenticationHandle.parseJwt(reservationInformation.token);

            String userid = (String) claims.getBody().get("userId");
            String role = claims.getBody().get("role").toString();
            List<Cubicle> availableCubicles;
            if (((reservationInformation.filter.startTime.isPresent()) && (reservationInformation.filter.endTime.isPresent()))) {
                if (!reservationInformation.filter.startTime.get().before(new Timestamp(LocalDateTime.now().plusMonths(3).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
                    responseBody.put("start time cannot be greater then 3 months in the future", null);
                else if (!reservationInformation.filter.endTime.get().before(new Timestamp(LocalDateTime.now().plusMonths(6).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
                    responseBody.put("end time cannot be greater then 6 months in the future", null);
                else {
                    availableCubicles = reservationService.findAvailable(reservationInformation.filter.startTime.get(), reservationInformation.filter.endTime.get());
                    responseBody.put("cubicles", availableCubicles);
                }
            } else
                responseBody.put("invalid times", null);


        } catch (JwtException ex) {
            responseBody.put("error", "Invalid token");
        }

        return ResponseEntity.ok().body(responseBody);
    }


    @PostMapping(value = "/filter2",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity filter2(HttpServletResponse response, @RequestBody ReservationInformation reservationInformation) {
        HashMap<String, Object> responseBody = new HashMap<>();
        List<Cubicle> cubicles = new ArrayList<>();
        try {

            Jws<Claims> claims = authenticationHandle.parseJwt(reservationInformation.token);

            String userid = (String) claims.getBody().get("userId");
            String role = claims.getBody().get("role").toString();
            if (role.equals("1") || userid.equals(reservationInformation.filter.userID.get())) {
                List<Reservation> unfilteredReservations = reservationService.findAll();
                List<Reservation> filteredReservations = reservationService.findAll();
                for (Reservation res : unfilteredReservations) {
                    if (reservationInformation.filter.userID != null)
                        if (!res.getUserID().equals(reservationInformation.filter.userID.get())) {
                            filteredReservations.remove(res);
                            continue;
                        }
                    if (reservationInformation.filter.cubicleID != null)
                        for (Cubicle cubicle : cubicles
                        ) {
                            if (!res.getId().cubicleID.equals(cubicle.getCubicleID())) {
                                filteredReservations.remove(res);
                                continue;
                            }
                        }
                    if (reservationInformation.filter.startTime != null && reservationInformation.filter.endTime != null)
                        if (!(res.getId().startTime.after(reservationInformation.filter.startTime.get()) && res.getEndTime().before(reservationInformation.filter.endTime.get()))) {
                            filteredReservations.remove(res);
                            continue;
                        }
                }

                responseBody.put("reservations", filteredReservations);
            } else
                responseBody.put("invalid user request", null);

        } catch (JwtException ex) {
            responseBody.put("error", "Invalid token");
        }

        return ResponseEntity.ok().body(responseBody);
    }
}