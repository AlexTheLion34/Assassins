package com.itmo.assassins.service.impl;

import com.itmo.assassins.model.request.*;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.model.user.UserRole;
import com.itmo.assassins.service.*;
import com.itmo.assassins.service.user.UserInfoService;
import com.itmo.assassins.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamFormingServiceImpl implements TeamFormingService {

    private final UserService userService;

    private final UserInfoService userInfoService;

    @Autowired
    public TeamFormingServiceImpl(UserService userService,
                                  UserInfoService userInfoService) {

        this.userService = userService;
        this.userInfoService = userInfoService;
    }

    @Override
    public RequestTeam formTeamForRequest(RequestInfo requestInfo) {

        User executor;

        switch (requestInfo.getDifficulty()) {

            case LOW:
                executor = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> userInfoService.findByUser(u).getRating() < 3.1)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException(""));
                break;
            case MEDIUM:
                executor = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> userInfoService.findByUser(u).getRating() >= 3.1
                                && userInfoService.findByUser(u).getRating() <= 4.5)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException(""));
                break;
            default:
                executor = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> userInfoService.findByUser(u).getRating() > 4.5)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException(""));
                break;
        }

        RequestTeam requestTeam = new RequestTeam();

        requestTeam.setExecutor(executor);
        executor.setBusy(true);

        requestTeam.setMaster(userService.findUserByRole(UserRole.MASTER_ASSASSIN)
                .stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("")));

        requestTeam.setGunsmith(userService.findUserByRole(UserRole.GUNSMITH)
                .stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("")));

        requestTeam.setCabman(userService.findUserByRole(UserRole.CABMAN)
                .stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("")));

        return requestTeam;
    }
}
