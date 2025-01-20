package com.aircraft.tafdatastore.controllers;

import com.aircraft.tafdatastore.entity.BookingResponse;
import com.aircraft.tafdatastore.entity.Bookings;
import com.aircraft.tafdatastore.entity.Flights;
import com.aircraft.tafdatastore.entity.Users;
import com.aircraft.tafdatastore.service.BookingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingServiceImpl bookingSvcImpl;
    public static final Logger log= LogManager.getLogger(BookingController.class);

    public BookingController(BookingServiceImpl bookingSvcImpl) {
        this.bookingSvcImpl = bookingSvcImpl;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody Bookings booking) {
        return new ResponseEntity<>(bookingSvcImpl.createBooking(booking), HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Bookings> getBookingById(@PathVariable Long bookingId) {
        try {
            Bookings booking = bookingSvcImpl.retrieveBookingById(bookingId);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bookings>> getBookingsByUser(@PathVariable Long userId) {
        List<Bookings> bookings = bookingSvcImpl.retrieveBookingByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Bookings> cancelBooking(@PathVariable Long bookingId) {
        try {
            Bookings booking = bookingSvcImpl.cancelBooking(bookingId);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/{flightNum}/{seatCnt}")
    public ResponseEntity<Flights> updateAvailableSeats(@PathVariable String flightNum, @PathVariable int seatCnt) {
        return new ResponseEntity<>(bookingSvcImpl.updateSeat(flightNum,seatCnt),HttpStatus.OK);
    }

}
