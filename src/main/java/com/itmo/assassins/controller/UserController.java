package com.itmo.assassins.controller;

import com.itmo.assassins.model.Role;
import com.itmo.assassins.model.User;
import com.itmo.assassins.service.RequestService;
import com.itmo.assassins.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.Optional;

@Controller
public class UserController extends BaseController {

    private final RequestService requestService;

    private final UserServiceImpl userService;

    @Autowired
    public UserController(RequestService requestService, UserServiceImpl userService) {
        this.requestService = requestService;
        this.userService = userService;
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String profilePage(ModelMap model) {

        String name = getLoggedInUserName();
        User user = userService.findUserByUserName(name);

        Optional<Role> userRole = user.getRoles().stream().findAny();

        if (userRole.isPresent()) {
            if (userRole.get().getName().equals("ROLE_CUSTOMER")) {

                model.put("user", user);
                model.put("requests", requestService.getRequestsByUser(user));


            } else {
                model.put("user", user);
                model.put("task", Collections.singletonList(requestService.getRequestByExecutor(user)));
            }
        }

        return "user";
    }
}
