package com.commerce.workstationapp.service;
import java.util.List;
import java.util.Optional;

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
	
	public String save(Cubicle cubicle) {
		cubicleRepo.save(cubicle);
		return cubicle.getCubicleID();
		}
	
	public void delete(String cubicleID) {
		cubicleRepo.delete(cubicleRepo.getById(cubicleID));
	}
	
	public Optional<Cubicle> findByID(String id) {
		return cubicleRepo.findById(id);
	}
	
	public List<Cubicle> findAll(){
		return cubicleRepo.findAll();
	}


}