package com.itmo.assassins.repository.request;

import com.itmo.assassins.model.request.RequestTeam;
import com.itmo.assassins.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RequestTeamRepository extends JpaRepository<RequestTeam, Long> {
    Set<RequestTeam> findByMaster(User user);
    Set<RequestTeam> findByCabman(User user);
    Set<RequestTeam> findByGunsmith(User user);
}
