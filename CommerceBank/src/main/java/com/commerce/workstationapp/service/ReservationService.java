package com.commerce.workstationapp.service;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.workstationapp.domain.Reservation;
import com.commerce.workstationapp.domain.ReservationID;
import com.commerce.workstationapp.domain.User;
import com.commerce.workstationapp.repo.ReservationRepo;
import com.commerce.workstationapp.repo.UserRepo;

@Service
@Transactional
public class ReservationService {
	@Autowired 
	private ReservationRepo reservationRepo;
	
	//add user after validating that username is not already taken
	public ReservationID join(Reservation res) {
		reservationRepo.save(res);
		return res.getId();
		}


}
