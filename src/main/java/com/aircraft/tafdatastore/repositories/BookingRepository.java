package com.aircraft.tafdatastore.repositories;

import com.aircraft.tafdatastore.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Bookings, Long> {


    //List<Booking> findByUserId(Long userId);
    List<Bookings> findByUserId(Long userId);
}
