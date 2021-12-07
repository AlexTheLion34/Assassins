package com.itmo.assassins.service.impl.request;

import com.itmo.assassins.model.request.RequestTeam;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.repository.request.RequestTeamRepository;
import com.itmo.assassins.service.request.RequestTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RequestTeamServiceImpl implements RequestTeamService {

    private final RequestTeamRepository requestTeamRepository;

    @Autowired
    public RequestTeamServiceImpl(RequestTeamRepository requestTeamRepository) {
        this.requestTeamRepository = requestTeamRepository;
    }

    @Override
    public Set<RequestTeam> findByMaster(User user) {
        return requestTeamRepository.findByMaster(user);
    }

    @Override
    public Set<RequestTeam> findByCabman(User user) {
        return requestTeamRepository.findByCabman(user);
    }

    @Override
    public Set<RequestTeam> findByGunsmith(User user) {
        return requestTeamRepository.findByGunsmith(user);
    }
}
