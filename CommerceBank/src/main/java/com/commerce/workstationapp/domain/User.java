package com.commerce.workstationapp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userID;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private boolean admin;

	@OneToMany(mappedBy = "user")
    List<Reservation> reservations;
	
	

}