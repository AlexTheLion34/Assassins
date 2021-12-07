package com.itmo.assassins.service.request;

import com.itmo.assassins.model.request.RequestTeam;
import com.itmo.assassins.model.user.User;

import java.util.Set;

public interface RequestTeamService {
    Set<RequestTeam> findByMaster(User user);
    Set<RequestTeam> findByCabman(User user);
    Set<RequestTeam> findByGunsmith(User user);
}
