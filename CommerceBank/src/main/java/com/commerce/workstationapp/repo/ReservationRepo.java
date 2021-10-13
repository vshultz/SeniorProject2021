package com.commerce.workstationapp.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.commerce.workstationapp.domain.Reservation;




@Repository
public class ReservationRepo{

	@PersistenceContext
	private EntityManager em;
	//saves a new reservation to the database
	public void save(Reservation reservation) {
		em.persist(reservation);
	}
	//returns a reservation by cubicleID
	public List<Reservation> findByCubicleID(String cubicleID) {
		return em.createQuery("select n from Reservation n where n.cubicleID = :cubicleID").setParameter("cubicleID", cubicleID).getResultList();
		
	}
	
	//returns a reservation by startTime
	public List<Reservation> findByStartTime(java.sql.Timestamp startTime) {
		return em.createQuery("select n from Reservation n where n.startTime = :startTime").setParameter("startTime", startTime).getResultList();
			
	}
	
	//returns a reservation by endTime
	public List<Reservation> findByEndTime(java.sql.Timestamp endTime) {
		return em.createQuery("select n from Reservation n where n.endTime = :endTime").setParameter("endTime", endTime).getResultList();
				
	}
	
	
	public List<Reservation> findByUserID(String userID) {
		return em.createQuery("select n from Reservation n where n.userID = :userID").setParameter("name", userID).getResultList();
					
	}
	
	public List<Object> findAll(){
		
		Query q = em.createNativeQuery("SELECT reservation.* FROM reservation INNER JOIN User ON reservation.userid=user.userID ");
		return q.getResultList();
		//return em.createQuery("Select n from Reservation n").getResultList();
	}
}

