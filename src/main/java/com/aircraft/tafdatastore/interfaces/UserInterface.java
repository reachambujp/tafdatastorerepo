package com.aircraft.tafdatastore.interfaces;

import com.aircraft.tafdatastore.entity.Users;

import java.util.List;

public interface UserInterface {
    Users createUser(Users user);
    List<Users> retrieveUsers();
    Users retrieveUserById(Long userId);
    Users updateUser(Users user);
}
