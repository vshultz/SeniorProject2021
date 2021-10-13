package com.commerce.workstationapp.service;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.workstationapp.domain.Cubicle;
import com.commerce.workstationapp.domain.User;
import com.commerce.workstationapp.repo.CubicleRepo;
import com.commerce.workstationapp.repo.UserRepo;

@Service
@Transactional
public class CubicleService {
	@Autowired 
	private CubicleRepo cubicleRepo;
	
	//add user after validating that username is not already taken
	public int join(Cubicle cubicle) {
		cubicleRepo.save(cubicle);
		return cubicle.getCubicleID();
		}


}