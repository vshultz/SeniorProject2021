package com.commerce.workstationapp.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.workstationapp.domain.Reservation;
import com.commerce.workstationapp.domain.User;
import com.commerce.workstationapp.repo.CubicleRepo;
import com.commerce.workstationapp.repo.ReservationRepo;
import com.commerce.workstationapp.repo.UserRepo;
import com.commerce.workstationapp.service.CubicleService;
import com.commerce.workstationapp.service.ReservationService;
import com.commerce.workstationapp.service.UserService;

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
	
	@GetMapping("/hello")
    public List<User> sayHello()
    {
    	List<User> users = userRepo.findByFirstName("Orihah");
        return users;
    }

	@GetMapping("/hel")
    public List<Object> getReservations()
    {
    	List<Object> reservations = reservationRepo.findAll();
        return reservations;
    }
	
}
