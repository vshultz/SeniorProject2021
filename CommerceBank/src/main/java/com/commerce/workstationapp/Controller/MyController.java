package com.commerce.workstationapp.Controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	    	user.setAdmin(false);
	    	user.setEmail("test@test.com");
	    	user.setFirstName("Orihah");
	    	user.setLastName("Sage");
	    	user.setPassword("password");
	    	
	    	User user2 = new User();
	    	user2.setAdmin(true);
	    	user2.setEmail("asdfadsm");
	    	user2.setFirstName("Orihah");
	    	user2.setLastName("adfabag");
	    	user2.setPassword("dafdava");
	    
	    	
	    	Cubicle cube1 = new Cubicle();
	    	cube1.setBuilding("Building 1");
	    	cube1.setCubicleName("My Cubicle");
	    	cube1.setFloor("Floor One");
	    	
	    	ReservationID resID = new ReservationID();
	    	resID.cubicle = cube1;
	    	resID.startTime = new java.sql.Timestamp(0);
	    	
	    	Reservation res1 = new Reservation();
	    	res1.setId(resID);
	    	res1.setUser(user);
	    	
	    	userService.join(user);
	    	userService.join(user2);
	    	cubicleService.join(cube1);
	    	reservationService.join(res1);
		}

}
