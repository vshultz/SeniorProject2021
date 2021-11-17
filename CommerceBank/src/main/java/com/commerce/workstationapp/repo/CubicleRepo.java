package com.commerce.workstationapp.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import org.springframework.stereotype.Repository;

import com.commerce.workstationapp.domain.Cubicle;



@Repository
public interface CubicleRepo extends JpaRepository<Cubicle, String>{

}

