package com.itmo.assassins.controller;

import com.itmo.assassins.model.Request;
import com.itmo.assassins.model.User;
import com.itmo.assassins.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itmo.assassins.service.RequestService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RequestController extends BaseController {

	private final RequestService requestService;

	private final UserServiceImpl userService;

	@Autowired
	public RequestController(RequestService requestService, UserServiceImpl userService) {
		this.requestService = requestService;
		this.userService = userService;
	}

	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public String payment(ModelMap model, @RequestParam String id) {

		String userName = getLoggedInUserName();
		User user = userService.findUserByUserName(userName);

		Optional<Request> request = requestService.getRequestById(Long.parseLong(id));

		model.put("user", user);
		model.put("id", id);
		request.ifPresent(req -> model.addAttribute("price", req.getPrice()));

		return "payment";
	}

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String paymentProcess(@RequestParam String id) {

		Optional<Request> request = requestService.getRequestById(Long.parseLong(id));

		if (request.isPresent()) {

			Integer price = request.get().getPrice();

			User owner = request.get().getOwner();
			User executor = request.get().getExecutor();

			owner.setBalance(owner.getBalance() - price);
			executor.setBalance(executor.getBalance() + price);
			executor.setCurrentTask(null);
			executor.setBusy(false);

			request.get().setExecutor(null);
			request.get().setStatus("Выполнен");

			requestService.saveRequest(request.get());
		}

		return "redirect:/user";
	}


	@RequestMapping(value = "/view-request", method = RequestMethod.GET)
	public String viewRequest(ModelMap model, @RequestParam String id) {

		Optional<Request> request = requestService.getRequestById(Long.parseLong(id));

		request.ifPresent(value -> model.addAttribute("request", value));

		return "view-request";
	}

	@RequestMapping(value = "/add-request", method = RequestMethod.GET)
	public String addRequest(ModelMap model) {
		model.addAttribute("request", new Request());
		return "request";
	}

	@RequestMapping(value = "/add-request", method = RequestMethod.POST)
	public String addRequest(Request request, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(result);
			return "request";
		}

		String userName = getLoggedInUserName();

		User user = userService.findUserByUserName(userName);

		request.setOwner(user);
		request.setStatus("В процессе");

		Optional<User> executor = userService.findUserByBusy();

		if (executor.isPresent()) {
			request.setExecutor(executor.get());
			executor.get().setCurrentTask(request);
			executor.get().setBusy(true);
			requestService.saveRequest(request);
		}

		return "redirect:/user";
	}
}
