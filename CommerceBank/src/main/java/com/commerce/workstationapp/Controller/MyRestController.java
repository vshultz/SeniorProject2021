package com.commerce.workstationapp.Controller;

import java.security.Key;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.commerce.workstationapp.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.workstationapp.repo.CubicleRepo;
import com.commerce.workstationapp.repo.ReservationRepo;
import com.commerce.workstationapp.repo.UserRepo;
import com.commerce.workstationapp.service.CubicleService;
import com.commerce.workstationapp.service.ReservationService;
import com.commerce.workstationapp.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MyRestController {
	 @Autowired
	 private UserRepo userRepo;
	 @Autowired
	 private UserService userService;
	 @Autowired
	 private ReservationRepo reservationRepo;
	 @Autowired
	 private ReservationService reservationService;
	 @Autowired
	 private CubicleRepo cubicleRepo;
	 @Autowired
	 private CubicleService cubicleService;
	
	@CrossOrigin
	@GetMapping("/users")
    public ResponseEntity<Object> getUsers()
    {
    	return new ResponseEntity(userService.findAll(), HttpStatus.OK);
    }
	
	@CrossOrigin
	@GetMapping("/users/{id}")
    public ResponseEntity<Object> getUsers(@PathVariable String id)
    {
		return new ResponseEntity(userService.findByID(id), HttpStatus.OK);
    }
	
	@PostMapping("/users")
	public ResponseEntity<Object> saveUser(@RequestBody User user){
		userService.save(user);
		return new ResponseEntity("Created Successfully", HttpStatus.CREATED);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable String id, @RequestBody User user){
		userService.delete(id);
		userService.save(user);
		return new ResponseEntity("Created Successfully", HttpStatus.OK);
		
	}
	
	@DeleteMapping("users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable String id){
		userService.delete(id);
		return new ResponseEntity("Successfully Deleted", HttpStatus.OK);
	}
	
	//
	
	
	@CrossOrigin
	@GetMapping("/reservations")
    public ResponseEntity<Object> getReservations()
    {
    	return new ResponseEntity( reservationService.findAll(), HttpStatus.OK);
    }
	
	@CrossOrigin
	@GetMapping("/reservation")
    public ResponseEntity<Object> getReservations(@RequestBody ReservationID id)
    {
    	return new ResponseEntity<Object>( reservationService.findByID(id), HttpStatus.OK);
    }
	
	@PostMapping("/reservation")
	public ResponseEntity<Object> saveReseervation(@RequestBody Reservation reservation){
		reservationService.save(reservation);
		return new ResponseEntity("Created Successfully", HttpStatus.CREATED);
	}
	
	@PutMapping("/reservation")
	public ResponseEntity<Object> updateReservation(@RequestBody ReservationID id, @RequestBody Reservation reservation){
		reservationService.delete(id);
		reservationService.save(reservation);
		return new ResponseEntity("Created Successfully", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/reservation")
	public ResponseEntity<Object> deleteReservation(@RequestBody ReservationID id){
		reservationService.delete(id);
		return new ResponseEntity("Successfully Deleted", HttpStatus.OK);
	}
	
	//
	@CrossOrigin
	@GetMapping("/cubicles")
    public List<Cubicle> getCubicles()
    {
    	return cubicleService.findAll();
    }
	
	@CrossOrigin
	@GetMapping("/cubicles/{id}")
    public Optional<Cubicle> getCubicles(@PathVariable String id)
    {
    	return cubicleService.findByID(id);
    }
	
	@PostMapping("/cubicles")
	public ResponseEntity<Object> saveCubicle(@RequestBody Cubicle cubicle){
		cubicleService.save(cubicle);
		return new ResponseEntity("Created Successfully", HttpStatus.CREATED);
	}
	
	@PutMapping("/cubicles/{id}")
	public ResponseEntity<Object> updateCubicle(@PathVariable String id, @RequestBody Cubicle cubicle){
		cubicleService.delete(id);
		cubicleService.save(cubicle);
		return new ResponseEntity("Created Successfully", HttpStatus.OK);
		
	}
	
	@DeleteMapping("cubicles/{id}")
	public ResponseEntity<Object> deleteCubicle(@PathVariable String id){
		cubicleService.delete(id);
		return new ResponseEntity("Successfully Deleted", HttpStatus.OK);
	}
	
	
	//
	
	
	@CrossOrigin(origins = "https://www.xodius.io", maxAge = 3600, allowCredentials = "true", allowedHeaders = "*") //
	@PostMapping(value = "/login",
			consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity login(HttpServletResponse response ,@RequestBody LoginInformaiton login){
		Optional<User> user = userService.findByID(login.username);
		
			if(user.isPresent()) {
				if(user.get().getPassword().equals(login.password)) {
			Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
			
			String jws = Jwts.builder().claim("userid",login.username).claim("role", user.get().getAdmin() ).signWith(key).compact();
			Cookie cookie = new Cookie("token", jws);
			cookie.setMaxAge(60*60*24);
			cookie.setDomain("xodius.io");
			
			response.addCookie(cookie);
			HashMap<String, String> r = new HashMap<>();
			r.put("username", login.username);
			r.put("role", "" + user.get().getAdmin());
			return ResponseEntity.ok().body(r);
			
				}
				else {
					HashMap<String, String> r = new HashMap<>();
					r.put("error", "Incorrect Password");
					return ResponseEntity.ok().body(r);
				}
		}
		else {
			HashMap<String, String> r = new HashMap<>();
			r.put("error", "Unknown User");
			return ResponseEntity.ok().body(r);
		}
	}
	
	@CrossOrigin(origins = "https://www.xodius.io", maxAge = 3600, allowCredentials = "true", allowedHeaders = "*") //
	@PostMapping(value = "/checkToken",
			consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity checkToken(HttpServletResponse response ,@RequestBody TokenInformation tokenInfo){
		Jws<Claims> jws;
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
		try {
		    jws = Jwts.parserBuilder()  // (1)
		    .setSigningKey(key)         // (2)
		    .build()                    // (3)
		    .parseClaimsJws(tokenInfo.token); // (4)
		    System.out.println(jws.getBody().get("userid"));
		    jws.getBody().forEach((k,v)->{
		    	System.out.println(k + " " + v);
		    });
		    // we can safely trust the JWT
		    String userid = (String) jws.getBody().get("userid");
		    HashMap<String, String> r = new HashMap<>();
			r.put("username", userid);
			return ResponseEntity.ok().body(r);
		    
		}    
		catch (JwtException ex) {       // (5)
			HashMap<String, String> r = new HashMap<>();
			r.put("error", "Invalid token");
			return ResponseEntity.ok().body(r);
		    // we *cannot* use the JWT as intended by its creator
		}
		
		
		
			
	}
	
	
	
	@GetMapping("/cubicles/available")
    public List<Cubicle> getAvailable(@RequestParam   String start, @RequestParam  String end)
    {
		java.sql.Timestamp s = Timestamp.valueOf(start);
		java.sql.Timestamp e = Timestamp.valueOf(end);
    	return reservationService.findAvailable(s, e);
    }
	
	@GetMapping("/reservations/user/{userName}")
    public List<Reservation> findReservationByUser(@PathVariable String userName)
    {
    	return reservationService.findByUser(userName);
    }
	

	@GetMapping("/reservations/cubicle/{cubicleID}")
    public List<Reservation> findReservationByCubicle(@PathVariable String cubicleID)
    {
    	return reservationService.findByCubicle(cubicleID);
    }
	
	
	
}
