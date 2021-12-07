package com.itmo.assassins.controller.user;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestTeam;
import com.itmo.assassins.model.user.UserRole;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.service.request.RequestService;
import com.itmo.assassins.service.request.RequestTeamService;
import com.itmo.assassins.service.user.UserSecurityService;
import com.itmo.assassins.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final RequestService requestService;

    private final UserService userService;

    private final UserSecurityService securityService;

    private final RequestTeamService teamService;

    @Autowired
    public UserController(RequestService requestService,
                          UserService userService,
                          UserSecurityService securityService,
                          RequestTeamService teamService) {

        this.requestService = requestService;
        this.userService = userService;
        this.securityService = securityService;
        this.teamService = teamService;
    }


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profilePage(ModelMap model) {

        User user = userService.findUserByUserName(securityService.getLoggedInUserName());

        UserRole userRole = user.getRole();

        model.put("user", user);
        model.put("userInfo", user.getUserInfo());

        switch (userRole) {
            case CUSTOMER:
                model.put("requests", requestService.getRequestsByUser(user));
                break;
            case EXECUTOR:
                Request currentTask = user.getUserInfo()
                        .getCurrentTask();
                if (currentTask != null) {
                    model.put("tasks", Collections.singletonList(currentTask));
                } else {
                    model.put("tasks", Collections.emptyList());
                }
                break;
            case MASTER_ASSASSIN:
                model.put("tasks", teamService.findByMaster(user)
                        .stream()
                        .map(RequestTeam::getRequest)
                        .collect(Collectors.toList()));
                break;
            case GUNSMITH:
                model.put("tasks", teamService.findByGunsmith(user)
                        .stream()
                        .map(RequestTeam::getRequest)
                        .collect(Collectors.toList()));
                break;
            default:
                model.put("tasks", teamService.findByCabman(user)
                        .stream()
                        .map(RequestTeam::getRequest)
                        .collect(Collectors.toList()));
                break;
        }

        return "profile";
    }
}
