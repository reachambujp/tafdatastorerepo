package com.aircraft.tafdatastore.interfaces;

import com.aircraft.tafdatastore.entity.Flights;
import com.aircraft.tafdatastore.entity.Users;

import java.util.List;

public interface FlightInterface {

    Flights addNewFlight(Flights flight);
    List<Flights> retrieveAllFlight();
    Flights retrieveFlightById(String flightNumber);
    Flights updateFlight(Flights flight);
    String deleteFlight(String flightNumber);


}
