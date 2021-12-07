package com.itmo.assassins.service.impl.user;

import com.itmo.assassins.model.user.UserRole;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.repository.user.UserRepository;
import com.itmo.assassins.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Set<User> findExecutorsByBusy() {
        return userRepository.findByRoleAndBusyFalse(UserRole.EXECUTOR);
    }

    @Override
    public Set<User> findUserByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
}
