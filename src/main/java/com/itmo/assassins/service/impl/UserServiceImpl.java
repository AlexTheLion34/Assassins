package com.itmo.assassins.service.impl;

import com.itmo.assassins.model.Role;
import com.itmo.assassins.model.User;
import com.itmo.assassins.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByBusy() {
        return userRepository
                .findByBusyFalse()
                .stream()
                .filter(user ->
                        user
                                .getRoles()
                                .stream()
                                .map(Role::getName)
                        .noneMatch(r -> r.equals("ROLE_CUSTOMER"))
                )
                .findFirst();
    }

//    public User findUserById(Long userId) {
//        Optional<User> userFromDb = userRepository.findById(userId);
//        return userFromDb.orElse(new User());
//    }
}
