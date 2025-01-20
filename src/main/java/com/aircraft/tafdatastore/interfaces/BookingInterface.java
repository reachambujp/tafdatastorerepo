package com.aircraft.tafdatastore.interfaces;

import com.aircraft.tafdatastore.entity.BookingResponse;
import com.aircraft.tafdatastore.entity.Bookings;
import com.aircraft.tafdatastore.entity.Flights;
import com.aircraft.tafdatastore.entity.Users;

import java.util.List;

public interface BookingInterface {

    BookingResponse createBooking(Bookings booking);
    //List<Bookings> retrieveAllBookings();
    Bookings retrieveBookingById(Long bookingId) throws Exception;
    List retrieveBookingByUserId(Long userId);
    Bookings cancelBooking(Long bookingId);
    Flights updateSeat(String flightNum, int seatCnt);

}
