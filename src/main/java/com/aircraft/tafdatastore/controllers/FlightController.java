package com.aircraft.tafdatastore.controllers;

import com.aircraft.tafdatastore.entity.Flights;
import com.aircraft.tafdatastore.entity.Users;
import com.aircraft.tafdatastore.service.FlightServiceImpl;
import com.aircraft.tafdatastore.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")

public class FlightController {

    private final FlightServiceImpl flightSvcImpl;
    public static final Logger log= LogManager.getLogger(FlightController.class);

    public FlightController(FlightServiceImpl flightSvcImpl) {
        this.flightSvcImpl = flightSvcImpl;
    }

    @PostMapping
    public ResponseEntity<Flights> addFlight(@RequestBody Flights flight) {
        return new ResponseEntity<>(flightSvcImpl.addNewFlight(flight),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Flights>> retrieveAllFlight() {
        return new ResponseEntity<>(flightSvcImpl.retrieveAllFlight(), HttpStatus.OK);
    }

    @GetMapping("/{flightNumber}")
    @Transactional
    public ResponseEntity<Flights> retrieveFlightById(@PathVariable String flightNumber) {
        return new ResponseEntity<>(flightSvcImpl.retrieveFlightById(flightNumber), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Flights> updateFlight(@RequestBody Flights flight) {
        return new ResponseEntity<>(flightSvcImpl.updateFlight(flight),HttpStatus.OK);
    }


    @DeleteMapping("/{flightNumber}")
    public ResponseEntity<String> deleteFlight(@PathVariable String flightNumber) {
        String result = flightSvcImpl.deleteFlight(flightNumber);
        String finalResText = "Flight details deleted successfully";

        log.info("Final Result ="+result);
        if("Success".equalsIgnoreCase(result)){
            return ResponseEntity.ok(finalResText);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    public ResponseEntity<String> respondWithError(Exception e){
        return new ResponseEntity<>("Exception Occurred. More Info:"
                + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}

