package com.commerce.workstationapp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.workstationapp.domain.User;
import com.commerce.workstationapp.repo.UserRepo;

@Service
@Transactional
public class UserService {
	@Autowired 
	private UserRepo userRepo;
	
	
	public String save(User user) {
		userRepo.save(user);
		return user.getUserID();
		}
	
	public void delete(String userID) {
		userRepo.delete(userRepo.getById(userID));
	}
	
	public Optional<User> findByID(String id) {
		return userRepo.findById(id);
	}
	
	public List<User> findAll(){
		return userRepo.findAll();
	}
	
	


}