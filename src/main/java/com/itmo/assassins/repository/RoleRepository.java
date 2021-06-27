package com.itmo.assassins.repository;

import com.itmo.assassins.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
