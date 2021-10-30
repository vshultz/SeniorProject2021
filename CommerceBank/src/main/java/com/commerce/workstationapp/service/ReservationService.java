package com.commerce.workstationapp.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.workstationapp.domain.Cubicle;
import com.commerce.workstationapp.domain.Reservation;
import com.commerce.workstationapp.domain.ReservationID;
import com.commerce.workstationapp.domain.User;
import com.commerce.workstationapp.repo.CubicleRepo;
import com.commerce.workstationapp.repo.ReservationRepo;
import com.commerce.workstationapp.repo.UserRepo;

@Service
@Transactional
public class ReservationService {
	@Autowired 
	private ReservationRepo reservationRepo;
	@Autowired 
	private CubicleRepo cubicleRepo;
	
	public ReservationID save(Reservation reservation) {
		reservationRepo.save(reservation);
		return reservation.getId();
		}
	
	public void delete(ReservationID id) {
		reservationRepo.delete(reservationRepo.getById(id));
	}
	
	public Optional<Reservation> findByID(ReservationID id) {
		return reservationRepo.findById(id);
	}
	
	public List<Reservation> findAll(){
		return reservationRepo.findAll();
	}
	
	public List<Reservation> findByUser(String userName){
		List<Reservation> reserved = findAll();
		List<Reservation> usersRes = new ArrayList<Reservation>();
		for(Reservation res : reserved)
			if(res.getUserID().equals(userName) )
				usersRes.add(res);
		return usersRes;
		
	}
	
	public List<Reservation> findByCubicle(String cubicleID){
		List<Reservation> reserved = findAll();
		List<Reservation> usersRes = new ArrayList<Reservation>();
		for(Reservation res : reserved)
			if(res.getId().cubicleID.equals(cubicleID) )
				usersRes.add(res);
		return usersRes;
		
	}
	
	public List<Cubicle> findAvailable(java.sql.Timestamp start, java.sql.Timestamp end){
		List<Cubicle> available = cubicleRepo.findAll();
		List<Reservation> reserved = findAll();
		for(Reservation res : reserved)
			if(!(res.getId().startTime.after(end) && res.getEndTime().before(start)))
				available.remove(cubicleRepo.getById(res.getId().cubicleID));
		
		return available;
	}
	

}
