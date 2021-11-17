package com.commerce.workstationapp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ReservationID implements Serializable {
	
	public java.sql.Timestamp startTime;
	public String cubicleID;

}
