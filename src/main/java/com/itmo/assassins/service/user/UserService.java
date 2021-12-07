package com.itmo.assassins.service.user;

import com.itmo.assassins.model.user.User;
import com.itmo.assassins.model.user.UserRole;

import java.util.Set;

public interface UserService {
    User findUserByUserName(String username);
    Set<User> findExecutorsByBusy();
    Set<User> findUserByRole(UserRole role);
}
