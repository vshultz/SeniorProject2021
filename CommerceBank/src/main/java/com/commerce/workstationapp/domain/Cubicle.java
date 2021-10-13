package com.commerce.workstationapp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Cubicle {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cubicleID;
	private String cubicleName;
	private String floor;
	private String building;
	
	
//	@OneToMany(mappedBy = "user")
//    List<Reservation> reservations;

}
