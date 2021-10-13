package com.commerce.workstationapp.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.commerce.workstationapp.domain.User;




@Repository
public class UserRepo{

	@PersistenceContext
	private EntityManager em;
	//saves a new location to the database
	public void save(User user) {
		em.persist(user);
	}
	//returns a user by first name
	public List<User> findByFirstName(String fName) {
		return em.createQuery("select n from User n where n.firstName = :fName").setParameter("fName", fName).getResultList();
		
	}
	
	//returns a user by last name
	public List<User> findByLaseName(String lName) {
		return em.createQuery("select n from User n where n.lastName = :lName").setParameter("lName", lName).getResultList();
			
	}
	
	//returns a user by last name
	public User findByUserID(String userID) {
		return (User) em.createQuery("select n from User n where n.userID = :userID").setParameter("userID", userID).getSingleResult();
				
	}
	
	

}

