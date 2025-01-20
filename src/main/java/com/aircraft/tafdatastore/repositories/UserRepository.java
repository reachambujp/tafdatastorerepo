package com.aircraft.tafdatastore.repositories;

import com.aircraft.tafdatastore.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}

