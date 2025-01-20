package com.aircraft.tafdatastore.controllers;

import com.aircraft.tafdatastore.entity.Users;
import com.aircraft.tafdatastore.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {

    private final UserServiceImpl userSvcImpl;

    public UserController(UserServiceImpl userSvcImpl) {
        this.userSvcImpl = userSvcImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        return new ResponseEntity<>(userSvcImpl.createUser(user),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Users>> retrieveUsers() {
        return new ResponseEntity<>(userSvcImpl.retrieveUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Transactional
    public ResponseEntity<Users> retrieveUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userSvcImpl.retrieveUserById(userId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Users> updateUser(@RequestBody Users user) {
        return new ResponseEntity<>(userSvcImpl.updateUser(user),HttpStatus.OK);
    }



    public ResponseEntity<String> respondWithError(Exception e){
        return new ResponseEntity<>("Exception Occurred. More Info:"
                + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}

