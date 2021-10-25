package com.commerce.workstationapp.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerce.workstationapp.domain.Reservation;




@Repository
public interface ReservationRepo extends JpaRepository<Reservation, String>{

	
}

