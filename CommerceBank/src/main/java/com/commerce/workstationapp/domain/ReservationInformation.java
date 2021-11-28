package com.commerce.workstationapp.domain;

import java.util.Optional;

public class ReservationInformation {
    public String token;
    public Optional<Reservation> reservation;
    public Filter filter;
    public class Filter {
        public Optional<String> cubicleID;
        public Optional<String> userID;
        public Optional<ReservationID> reservationID;
        public Optional<java.sql.Timestamp> startTime;
        public Optional<java.sql.Timestamp> endTime;

    }
}
