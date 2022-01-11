package com.itmo.assassins.service.impl.user;

import com.itmo.assassins.model.request.RequestDifficulty;
import com.itmo.assassins.model.user.*;
import com.itmo.assassins.service.user.UserService;
import com.itmo.assassins.service.user.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserTeamServiceImpl implements UserTeamService {

    private final UserService userService;

    @Autowired
    public UserTeamServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Master findMasterForRequest() {

        List<Master> masters = userService.findUserByRole(UserRole.MASTER_ASSASSIN)
                .stream()
                .map(Master.class::cast)
                .collect(Collectors.toList());

        return masters.get(new Random().nextInt(masters.size()));
    }

    @Override
    public Cabman findCabmanForRequest() {

        List<Cabman> cabmen = userService.findUserByRole(UserRole.CABMAN)
                .stream()
                .map(Cabman.class::cast)
                .collect(Collectors.toList());

        return cabmen.get(new Random().nextInt(cabmen.size()));
    }

    @Override
    public Gunsmith findGunsmithForRequest() {

        List<Gunsmith> gunsmiths = userService.findUserByRole(UserRole.GUNSMITH)
                .stream()
                .map(Gunsmith.class::cast)
                .collect(Collectors.toList());

        return gunsmiths.get(new Random().nextInt(gunsmiths.size()));
    }

    @Override
    public Executor findExecutorForRequest(RequestDifficulty difficulty) {

        List<Executor> executors;

        switch (difficulty) {

            case LOW:
                executors = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> u.getRating() < 3.1)
                        .collect(Collectors.toList());

                break;
            case MEDIUM:
                executors = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> u.getRating() >= 3.1 && u.getRating() <= 4.5)
                        .collect(Collectors.toList());
                break;
            default:
                executors = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> u.getRating() > 4.5)
                        .collect(Collectors.toList());
                break;
        }

        return executors.get(new Random().nextInt(executors.size()));
    }

    @Override
    public List<Cabman> findCabmenExceptGiven(Cabman cabman) {

        List<Cabman> cabmen = userService.findUserByRole(UserRole.CABMAN)
                .stream()
                .map(Cabman.class::cast)
                .collect(Collectors.toList());

        cabmen.remove(cabman);

        return cabmen;
    }

    @Override
    public List<Gunsmith> findGunsmithsExceptGiven(Gunsmith gunsmith) {

        List<Gunsmith> gunsmiths = userService.findUserByRole(UserRole.GUNSMITH)
                .stream()
                .map(Gunsmith.class::cast)
                .collect(Collectors.toList());

        gunsmiths.remove(gunsmith);

        return gunsmiths;
    }

    @Override
    public List<Executor> findExecutorsExceptGiven(Executor executor, RequestDifficulty difficulty) {

        List<Executor> executors;

        switch (difficulty) {

            case LOW:
                executors = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> u.getRating() < 3.1)
                        .collect(Collectors.toList());

                break;
            case MEDIUM:
                executors = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> u.getRating() >= 3.1 && u.getRating() <= 4.5)
                        .collect(Collectors.toList());
                break;
            default:
                executors = userService.findExecutorsByBusy()
                        .stream()
                        .filter(u -> u.getRating() > 4.5)
                        .collect(Collectors.toList());
                break;
        }

        executors.remove(executor);

        return executors;
    }
}
