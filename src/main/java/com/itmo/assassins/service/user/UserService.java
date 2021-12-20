package com.itmo.assassins.service.user;

import com.itmo.assassins.model.user.Executor;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.model.user.UserRole;

import java.util.Set;

public interface UserService {
    User findUserById(Long id);
    User findUserByUserName(String username);
    Set<Executor> findExecutorsByBusy();
    Set<User> findUserByRole(UserRole role);
    void saveUser(User user);
}
