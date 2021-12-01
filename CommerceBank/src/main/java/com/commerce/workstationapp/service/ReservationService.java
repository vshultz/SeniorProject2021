package com.commerce.workstationapp.service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
			if(res.getUserID().equals(userName) && res.getEndTime().after(new Timestamp(System.currentTimeMillis())) )
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
		List<Reservation> reserved = reservationRepo.findAll();

		for(Reservation res : reserved) {
			if ((start.before(res.getId().startTime) && end.after(res.getId().startTime)) ||
					(start.before(res.getEndTime()) && end.after(res.getEndTime())) ||
					(start.before(res.getId().startTime) && end.after(res.getEndTime())) ||
					(start.after(res.getId().startTime) && end.before(res.getEndTime())) ||
					(start.equals(res.getId().startTime))||
					(end.equals(res.getEndTime()))) {
				Cubicle cubicle = cubicleRepo.getById(res.getId().cubicleID);
				if (available.contains(cubicle))
					available.remove(cubicle);
			}

		}
		return available;
	}


}
