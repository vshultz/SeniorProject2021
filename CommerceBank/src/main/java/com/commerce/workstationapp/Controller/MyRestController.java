package com.commerce.workstationapp.Controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

import com.commerce.workstationapp.domain.Cubicle;
import com.commerce.workstationapp.domain.Reservation;
import com.commerce.workstationapp.domain.User;
import com.commerce.workstationapp.repo.CubicleRepo;
import com.commerce.workstationapp.repo.ReservationRepo;
import com.commerce.workstationapp.repo.UserRepo;
import com.commerce.workstationapp.service.CubicleService;
import com.commerce.workstationapp.service.ReservationService;
import com.commerce.workstationapp.service.UserService;

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
	@GetMapping("/reservations/{id}")
    public ResponseEntity<Object> getReservations(@PathVariable String id)
    {
    	return new ResponseEntity<Object>( reservationService.findByID(id), HttpStatus.OK);
    }
	
	@PostMapping("/reservations")
	public ResponseEntity<Object> saveReseervation(@RequestBody Reservation reservation){
		reservationService.save(reservation);
		return new ResponseEntity("Created Successfully", HttpStatus.CREATED);
	}
	
	@PutMapping("/reservations/{id}")
	public ResponseEntity<Object> updateReservation(@PathVariable String id, @RequestBody Reservation reservation){
		reservationService.delete(id);
		reservationService.save(reservation);
		return new ResponseEntity("Created Successfully", HttpStatus.OK);
		
	}
	
	@DeleteMapping("reservations/{id}")
	public ResponseEntity<Object> deleteReservation(@PathVariable String id){
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
	
	@GetMapping("login")
	public ResponseEntity<Object> login(@RequestParam String userName, @RequestParam String password){
		if(userService.findByID(userName).get().getPassword().equals(password))
			return new ResponseEntity(userService.findByID(userName), HttpStatus.OK);
		else
			return new ResponseEntity("Incorrect Username or Password", HttpStatus.NOT_FOUND);
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
