package com.commerce.workstationapp.Controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.commerce.workstationapp.domain.Cubicle;
import com.commerce.workstationapp.domain.Reservation;
import com.commerce.workstationapp.domain.ReservationID;
import com.commerce.workstationapp.domain.User;
import com.commerce.workstationapp.repo.CubicleRepo;
import com.commerce.workstationapp.repo.ReservationRepo;
import com.commerce.workstationapp.repo.UserRepo;
import com.commerce.workstationapp.service.UserService;
import com.commerce.workstationapp.service.ReservationService;
import com.commerce.workstationapp.service.CubicleService;



@Controller

public class MyController {
	
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

	 

		
	 
	 

		
	 
		@GetMapping("/verification")
		public void verification() {	
			User user = new User();
			user.setUserID("123");
	    	user.setAdmin(0);
	    	user.setEmail("test@test.com");
	    	user.setFirstName("Orihah");
	    	user.setLastName("Sage");
	    	user.setPassword("password");
	    	
	    	User user2 = new User();
	    	user2.setUserID("321");
	    	user2.setAdmin(1);
	    	user2.setEmail("asdfadsm");
	    	user2.setFirstName("Orihah");
	    	user2.setLastName("adfabag");
	    	user2.setPassword("dafdava");
	    
   	
	    	Cubicle cube1 = new Cubicle();
	    	cube1.setBuilding("Building 1");
	    	cube1.setCubicleID("My Cubicle");
	    	cube1.setFloor("Floor One");
	    	
	    	Cubicle cube2 = new Cubicle();
	    	cube2.setBuilding("Building 1");
	    	cube2.setCubicleID("Your Cubicle");
	    	cube2.setFloor("Floor One");
	    	
	    	Cubicle cube3 = new Cubicle();
	    	cube3.setBuilding("Building 1");
	    	cube3.setCubicleID("Their Cubicle");
	    	cube3.setFloor("Floor One");
	    	
	    	ReservationID resID = new ReservationID();
	    	resID.cubicleID = cube1.getCubicleID();
	    	resID.startTime = new Timestamp(System.currentTimeMillis());
	    	
	    	Reservation res1 = new Reservation();
	    	res1.setId(resID);
	    	res1.setEndTime( new Timestamp(System.currentTimeMillis()));
	    	res1.setUserID(user.getUserID());
	    	
	    	userService.save(user);
	    	userService.save(user2);
	    	cubicleService.save(cube1);
	    	cubicleService.save(cube2);
	    	cubicleService.save(cube3);
	    	reservationService.save(res1);
		}

	
}
