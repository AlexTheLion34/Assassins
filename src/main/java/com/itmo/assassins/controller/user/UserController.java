package com.itmo.assassins.controller.user;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.user.*;
import com.itmo.assassins.service.user.UserSecurityService;
import com.itmo.assassins.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;

@Controller
public class UserController {

    private final UserService userService;

    private final UserSecurityService securityService;

    @Autowired
    public UserController(UserService userService, UserSecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profilePage(ModelMap model) {

        User user = userService.findUserByUserName(securityService.getLoggedInUserName());

        UserRole userRole = user.getRole();

        model.put("user", user);

        switch (userRole) {
            case CUSTOMER:
                model.put("requests", ((Customer) user).getRequests());
                model.put("customer", user);
                break;
            case EXECUTOR:
                Request currentTask = ((Executor) user).getCurrentTask();
                if (currentTask != null) {
                    model.put("tasks", Collections.singletonList(currentTask));
                } else {
                    model.put("tasks", Collections.emptyList());
                }
                break;
            case MASTER_ASSASSIN:
                model.put("tasks", ((Master) user).getRequests());
                break;
            case GUNSMITH:
                model.put("tasks", ((Gunsmith) user).getRequests());
                break;
            default:
                model.put("tasks", ((Cabman) user).getRequests());
                break;
        }

        return "profile";
    }
}
