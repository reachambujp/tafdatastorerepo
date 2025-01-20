package com.aircraft.tafdatastore.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BookingResponse {
    HttpStatus httpStatus;
    Bookings booking;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Bookings getBooking() {
        return booking;
    }

    public void setBooking(Bookings booking) {
        this.booking = booking;
    }
}
