package com.commerce.workstationapp.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Reservation {

	public Reservation(ReservationID id, Timestamp endTime, String userID) {
		this.id = id;
		this.endTime = endTime;
		this.userID = userID;
	}

	@EmbeddedId
		private ReservationID id;


	private java.sql.Timestamp endTime;
	private String userID;
	private java.sql.Timestamp timeStamp =  new Timestamp(System.currentTimeMillis());

	
	
	

}