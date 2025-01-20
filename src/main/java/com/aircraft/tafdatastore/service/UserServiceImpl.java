package com.aircraft.tafdatastore.service;

import com.aircraft.tafdatastore.entity.Users;
import com.aircraft.tafdatastore.interfaces.UserInterface;
import com.aircraft.tafdatastore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserInterface {
    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public List<Users> retrieveUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users retrieveUserById(Long userId) {
        return userRepository.getById(userId);
    }

    @Override
    public Users updateUser(Users user) {
        Users usrMgmnt = userRepository.getById(user.getId());

        if (null != user.getUsername()) {
            usrMgmnt.setUsername(user.getUsername());
        }
        if (null != user.getEmail()) {
            usrMgmnt.setEmail(user.getEmail());
        }
        if (null != user.getPhone()) {
            usrMgmnt.setPhone(user.getPhone());
        }
        usrMgmnt.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(usrMgmnt);
    }
}
