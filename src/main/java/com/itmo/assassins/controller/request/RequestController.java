package com.itmo.assassins.controller.request;

import com.itmo.assassins.model.request.Request;
import com.itmo.assassins.model.request.RequestInfo;
import com.itmo.assassins.model.user.*;
import com.itmo.assassins.service.request.RequestDifficultyComputationService;
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
public class RequestController {

    private final RequestService requestService;

    private final UserService userService;

    private final UserSecurityService securityService;

    private final RequestDifficultyComputationService difficultyComputationService;

    @Autowired
    public RequestController(RequestService requestService,
                             UserService userService,
                             UserSecurityService securityService,
                             RequestDifficultyComputationService difficultyComputationService) {

        this.requestService = requestService;
        this.userService = userService;
        this.securityService = securityService;
        this.difficultyComputationService = difficultyComputationService;
    }

    @RequestMapping(value = "/add-request", method = RequestMethod.GET)
    public String addRequest(ModelMap model) {

        User currentUser = userService.findUserByUserName(securityService.getLoggedInUserName());

        Long maxPrice = userService.countMaxAffordablePrice((Customer) currentUser);

        if (maxPrice == 0) {
            return "balance-error";
        }

        model.addAttribute("requestInfo", new RequestInfo());
        model.addAttribute("maxPrice", maxPrice);

        return "request";
    }

    @RequestMapping(value = "/add-request", method = RequestMethod.POST)
    public String addRequest(RequestInfo requestInfo) {

        User currentUser = userService.findUserByUserName(securityService.getLoggedInUserName());

        requestInfo.setDifficulty(difficultyComputationService.computeDifficulty(requestInfo));

        try {
            requestService.createRequest(requestInfo, (Customer) currentUser);
        } catch (Exception e) {
            return "executor-error";
        }

        return "redirect:/profile";
    }

    @RequestMapping(value = "/view-request", method = RequestMethod.GET)
    public String viewRequest(ModelMap model, @RequestParam String id) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(id));

        request.ifPresent(req -> {
            model.addAttribute("request", req);
            model.addAttribute("user",
                    userService.findUserByUserName(securityService.getLoggedInUserName()));
        });

        return "view-request";
    }

    @RequestMapping(value = "/change-request", method = RequestMethod.GET)
    public String changeRequest(ModelMap model, @RequestParam String id) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(id));

        request.ifPresent(req -> {

            User currentUser = userService.findUserByUserName(securityService.getLoggedInUserName());

            model.put("user", currentUser);
            model.put("id", id);
            model.put("executor", req.getExecutor());
            model.put("cabman", req.getCabman());
            model.put("gunsmith", req.getGunsmith());
        });

        return "change-request";
    }

    @RequestMapping(value = "/confirm-request", method = RequestMethod.GET)
    public String confirmRequest(@RequestParam String requestId) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(requestId));

        User currentUser = userService.findUserByUserName(securityService.getLoggedInUserName());

        request.ifPresent(req -> requestService.confirmRequest(req, (Master) currentUser));

        return "redirect:/profile";
    }

    @RequestMapping(value = "/evaluate", method = RequestMethod.GET)
    public String putRatingForRequest(ModelMap model, @RequestParam String id) {

        Optional<Request> request = requestService.getRequestById(Long.parseLong(id));

        request.ifPresent(req -> model.put("request", req));

        return "evaluate-request";
    }

    @RequestMapping(value = "/evaluate", method = RequestMethod.POST)
    public String putRatingForRequest(Request request, @RequestParam String requestId) {

        Optional<Request> requestFromRepo = requestService.getRequestById(Long.parseLong(requestId));

        requestFromRepo.ifPresent(req -> {
            Request updatedRequest = requestService.putRatingForRequest(req, request.getRequestInfo().getRating());
            userService.countRating(updatedRequest.getExecutor(), updatedRequest.getRequestInfo().getRating());
            requestService.saveRequest(updatedRequest);
        });

        return "redirect:/profile";
    }
}
