package com.commerce.workstationapp.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.commerce.workstationapp.domain.Cubicle;



@Repository
public class CubicleRepo{

	@PersistenceContext
	private EntityManager em;
	//saves a new location to the database
	
	
	public void save(Cubicle cubicle) {
		em.persist(cubicle);
	}
	//returns a cubicle by cubicleID
	public List<Cubicle> findByCubcileID(String cubicleID) {
		return em.createQuery("select n from Cubicle n where n.cubicleID = :cubicleID").setParameter("cubicleID", cubicleID).getResultList();
		
	}
	
	//returns a cubicle by floor
	public List<Cubicle> findByFloor(String floor) {
		return em.createQuery("select n from Cubicle n where n.floor = :floor").setParameter("floor", floor).getResultList();
			
	}
	
	//returns a cubicle by building
	public List<Cubicle> findByUserID(String building) {
		return em.createQuery("select n from Cubicle n where n.building = :building").setParameter("building", building).getResultList();
				
	}
	
	

}

