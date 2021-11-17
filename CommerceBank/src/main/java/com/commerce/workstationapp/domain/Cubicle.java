package com.commerce.workstationapp.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Cubicle {
	@Id
	private String cubicleID;
	private String floor;
	private String building;
	private java.sql.Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
	
	


}
