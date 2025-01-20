package com.aircraft.tafdatastore.service;

import com.aircraft.tafdatastore.entity.*;
import com.aircraft.tafdatastore.interfaces.BookingInterface;
import com.aircraft.tafdatastore.repositories.BookingRepository;
import com.aircraft.tafdatastore.repositories.FlightRepository;
import com.aircraft.tafdatastore.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingInterface {

    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final FlightRepository flightRepository;
    @Autowired
    private final UserRepository userRepository;
    public static final Logger log = LogManager.getLogger(BookingServiceImpl.class);

    public BookingServiceImpl(BookingRepository bookingRepository, FlightRepository flightRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }


    @Override
    public BookingResponse createBooking(Bookings booking) {
        BookingResponse bookRes = new BookingResponse();
        String flightNum = booking.getFlight().getFlightNumber();
        //Fetch the available seats for a given flight number
        int availableSeats = flightRepository.getAvailableSeats(flightNum);
        Flights flightDetail = flightRepository.findByFlightNumber(flightNum);
        Optional<Users> user = userRepository.findById(booking.getUser().getId());

        //Check if available seat is >0, if yes, go for booking else return error
        try {
            if (availableSeats > 0) {
                //Go for booking
                //Based on booking type (Booked or Cancellation), adjust the available seat count
                int adjustedSeatCount = availableSeats;
                if (booking.getStatus() == BookingStatus.Booked) {
                    //Decrease the available seat by 1
                    adjustedSeatCount = availableSeats - 1;

                }
                flightRepository.updateAvailableSeats(flightNum, adjustedSeatCount);

                //Create final booking object for saving in DB
                booking.setUser(user.get());
                booking.setFlight(flightDetail);

                Bookings finalBookingObj = bookingRepository.save(booking);
                bookRes.setBooking(finalBookingObj);
                bookRes.setHttpStatus(HttpStatus.CREATED);
                return bookRes;
            } else {
                log.info("No available seats. Try another flight");
                bookRes.setBooking(booking);
                bookRes.setHttpStatus(HttpStatus.BAD_REQUEST);
                return bookRes;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error booking a flight");
            bookRes.setBooking(booking);
            bookRes.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            return bookRes;
        }
    }

    @Override
    public Bookings retrieveBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id:" + bookingId));
    }

    @Override
    public List retrieveBookingByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public Bookings cancelBooking(Long bookingId) {
        Bookings booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with bookingId:" + bookingId));

        if (booking.getStatus().equals(BookingStatus.Cancelled)) {
            throw new RuntimeException("Booking is already cancelled");
        }

        Flights flight = booking.getFlight();

        //Get the available seat count for the flight
        int seatCount = flightRepository.getAvailableSeats(flight.getFlightNumber());

        //Update the revised seat count
        flightRepository.updateAvailableSeats(flight.getFlightNumber(), seatCount+1);

        booking.setStatus(BookingStatus.Cancelled);
        return bookingRepository.save(booking);
    }

    @Override
    public Flights updateSeat(String flightNum, int seatCnt) {
        flightRepository.updateAvailableSeats(flightNum, seatCnt);
        Flights flightDetail = flightRepository.findByFlightNumber(flightNum);
        return flightDetail;
    }
}



