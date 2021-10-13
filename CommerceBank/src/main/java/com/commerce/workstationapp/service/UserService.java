package com.commerce.workstationapp.service;

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
	
	//add user after validating that username is not already taken
	public int join(User user) {
		userRepo.save(user);
		return user.getUserID();
		}


}