package com.commerce.workstationapp.domain;

import java.util.Optional;

public class ReservationInformation {
    public String token;
    public Filter filter;
    public class Filter {
        public Optional<String> cubicalID;
        public Optional<String> userID;
    }
}
