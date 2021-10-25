package com.commerce.workstationapp.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerce.workstationapp.domain.User;




@Repository
public interface UserRepo extends JpaRepository<User, String>{

	
	
	

}

