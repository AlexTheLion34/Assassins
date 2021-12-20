package com.itmo.assassins.controller.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.user.User;
import com.itmo.assassins.service.request.PaymentService;
import com.itmo.assassins.service.request.RequestService;
import com.itmo.assassins.service.user.UserSecurityService;
import com.itmo.assassins.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PaymentController {

    private final RequestService requestService;

    private final PaymentService paymentService;

    private final UserService userService;

    private final UserSecurityService securityService;

    @Autowired
    public PaymentController(RequestService requestService,
                             PaymentService paymentService,
                             UserService userService,
                             UserSecurityService securityService) {

        this.requestService = requestService;
        this.paymentService = paymentService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String payment(ModelMap model, @RequestParam String id) {

        String userName = securityService.getLoggedInUserName();
        User user = userService.findUserByUserName(userName);

        Optional<Request> request = requestService.getRequestById(Long.parseLong(id));

        model.put("user", user);
        model.put("id", id);
        request.ifPresent(req -> model.addAttribute("price", req.getRequestInfo().getPrice()));

        return "payment";
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String paymentProcess(@RequestParam String id) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(id));

        request.ifPresent(paymentService::processPayment);

        return "redirect:/profile";
    }

    @RequestMapping(value = "/payment-confirm", method = RequestMethod.POST)
    public String paymentConfirm(@RequestParam String id) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(id));

        request.ifPresent(paymentService::confirmPayment);

        return "redirect:/profile";
    }
}
