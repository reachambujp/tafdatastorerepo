package com.aircraft.tafdatastore.service;

import com.aircraft.tafdatastore.entity.Flights;
import com.aircraft.tafdatastore.interfaces.FlightInterface;
import com.aircraft.tafdatastore.repositories.FlightRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightInterface {

    @Autowired
    private final FlightRepository flightRepository;
    public static final Logger log= LogManager.getLogger(FlightServiceImpl.class);

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flights addNewFlight(Flights flight) {
        return flightRepository.save(flight);
    }

    @Override
    public List<Flights> retrieveAllFlight() {
        return flightRepository.findAll();
    }

    @Override
    public Flights retrieveFlightById(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    @Override
    public Flights updateFlight(Flights flight) {
        Flights flightEntry = flightRepository.findByFlightNumber(flight.getFlightNumber());
        if (null != flightEntry) {

            if (null != flight.getDeparture()) {
                flightEntry.setDeparture(flight.getDeparture());
            }

            if (null != flight.getArrival()) {
                flightEntry.setArrival(flight.getArrival());
            }

            if (null != flight.getDepartureTime()) {
                flightEntry.setDepartureTime(flight.getDepartureTime());
            }
            if (null != flight.getArrivalTime()) {
                flightEntry.setArrivalTime(flight.getArrivalTime());
            }

            if (null != flight.getPrice()) {
                flightEntry.setPrice(flight.getPrice());
            }

            if (null != flight.getAvailableSeats()) {
                flightEntry.setAvailableSeats(flight.getAvailableSeats());
            }

            flightEntry.setUpdatedAt(LocalDateTime.now());

            return flightRepository.save(flightEntry);

        } else {
            throw new RuntimeException("Flight record not found");
        }
    }

    @Override
    public String deleteFlight(String flightNumber) {
        try {
            log.info("After Delete flight: "+flightNumber);
            flightRepository.deleteByFlightNumber(flightNumber);
            log.info("After Delete flight: "+flightNumber);
            return "Success";
        }catch (Exception e){
            e.printStackTrace();
            return "Failure";
        }
    }

}
