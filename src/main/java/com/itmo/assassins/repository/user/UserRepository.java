package com.itmo.assassins.repository.user;

import com.itmo.assassins.model.user.User;
import com.itmo.assassins.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Set<User> findByRoleAndBusyFalse(UserRole role);
    Set<User> findByRole(UserRole role);
}
