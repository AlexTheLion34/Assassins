package com.itmo.assassins.controller;

import com.itmo.assassins.model.User;
import com.itmo.assassins.service.RequestService;
import com.itmo.assassins.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController extends BaseController {

	@Autowired
	private RequestService requestService;

	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String showWelcomePage(ModelMap model) {

		String name = getLoggedInUserName();
		User user = userService.findUserByUserName(name);

		model.put("user", user);
		model.put("requests", requestService.getRequestsByUser(user));

		return "user";
	}
}
