package com.itmo.assassins.controller.user;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.user.*;
import com.itmo.assassins.service.request.RequestService;
import com.itmo.assassins.service.user.UserSecurityService;
import com.itmo.assassins.service.user.UserService;
import com.itmo.assassins.service.user.UserTeamService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserTeamController {

    private final RequestService requestService;

    private final UserService userService;

    private final UserTeamService teamService;

    private final UserSecurityService securityService;

    @Autowired
    public UserTeamController(RequestService requestService,
                              UserService userService,
                              UserTeamService teamService,
                              UserSecurityService securityService) {

        this.requestService = requestService;
        this.userService = userService;
        this.teamService = teamService;
        this.securityService = securityService;
    }

    @RequestMapping(value = "/change-team", method = RequestMethod.GET)
    public String changeMember(ModelMap model, @RequestParam String requestId, @RequestParam String role) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(requestId));

        request.ifPresent(req -> {

            User currentUser = userService.findUserByUserName(securityService.getLoggedInUserName());
            model.put("user", currentUser);
            model.put("id", requestId);

            switch (UserRole.valueOf(role)) {
                case EXECUTOR:
                    model.put("title", "Ассассины");
                    model.put("team", teamService.findExecutorsExceptGiven(req.getExecutor(),
                            req.getRequestInfo().getDifficulty()));
                    break;
                case GUNSMITH:
                    model.put("title", "Оружейники");
                    model.put("team", teamService.findGunsmithsExceptGiven(req.getGunsmith()));
                    break;
                default:
                    model.put("title", "Извозчики");
                    model.put("team", teamService.findCabmenExceptGiven(req.getCabman()));
                    break;
            }
        });

        return "change-team";
    }

    @RequestMapping(value = "/change-team", method = RequestMethod.POST)
    public String changeMemberPost(@RequestParam String requestId, @RequestParam String userToChangeId) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(requestId));

        request.ifPresent(req -> {

            User userToChange = Hibernate.unproxy(userService.findUserById(Long.parseLong(userToChangeId)), User.class);

            requestService.changeRequestTeam(req, userToChange);
        });

        return "redirect:/change-request?id=" + requestId;
    }
}
