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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Reservation {

	@EmbeddedId
		public ReservationID id;

	private java.sql.Timestamp endTime;

	@ManyToOne
    @JoinColumn(name = "userID")
    private User user;
	
	

}